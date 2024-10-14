package im.nfc.flutter_nfc_kit.inra.card;

import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;

import java.io.IOException;
import java.util.Arrays;

import im.nfc.flutter_nfc_kit.inra.card.apdu.APDUResponse;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidApdu;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidAuthApdu;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidReadApdu;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidWriteApdu;
import im.nfc.flutter_nfc_kit.inra.utils.DataUtil;

public class MifareClassicFixReader {
    private final MifareClassic mifareClassic;

    public MifareClassicFixReader(Tag tag) throws Exception {
        try {
            // fix the tag if necessary
            this.mifareClassic = MifareClassic.get(patchTag(tag));
        } catch (Exception e) {
            throw e;
        }
    }

    public void connectCard() throws TagLostException {
        if (!mifareClassic.isConnected()) {
            try {
                mifareClassic.connect();
            } catch (IOException e) {
                throw new TagLostException("Tag not connected");
            }
        }
    }

    public void closeCardConnection() throws IOException {
        mifareClassic.close();
    }

    /**
     * Patch a possibly broken Tag object of HTC One (m7/m8) or Sony
     * Xperia Z3 devices (with Android 5.x.)
     * <p>
     * HTC One: "It seems, the reason of this bug is TechExtras of NfcA is null.
     * However, TechList contains MifareClassic." -- bildin.
     * This method will fix this. For more information please refer to
     * https://github.com/ikarus23/MifareClassicTool/issues/52
     * This patch was provided by bildin (https://github.com/bildin).
     * <p>
     * Sony Xperia Z3 (+ emmulated MIFARE Classic tag): The buggy tag has
     * two NfcA in the TechList with different SAK values and a MifareClassic
     * (with the Extra of the second NfcA). Both, the second NfcA and the
     * MifareClassic technique, have a SAK of 0x20. According to NXP's
     * guidelines on identifying MIFARE tags (Page 11), this a MIFARE Plus or
     * MIFARE DESFire tag. This method creates a new Extra with the SAK
     * values of both NfcA occurrences ORed (as mentioned in NXP's
     * MIFARE type identification procedure guide) and replace the Extra of
     * the first NfcA with the new one. For more information please refer to
     * https://github.com/ikarus23/MifareClassicTool/issues/64
     * This patch was provided by bildin (https://github.com/bildin).
     *
     * @param tag The possibly broken tag.
     * @return The fixed tag.
     */
    public Tag patchTag(Tag tag) {
        if (tag == null) {
            return null;
        }

        String[] techList = tag.getTechList();

        Parcel oldParcel = Parcel.obtain();
        tag.writeToParcel(oldParcel, 0);
        oldParcel.setDataPosition(0);

        int len = oldParcel.readInt();
        byte[] id = new byte[0];
        if (len >= 0) {
            id = new byte[len];
            oldParcel.readByteArray(id);
        }
        int[] oldTechList = new int[oldParcel.readInt()];
        oldParcel.readIntArray(oldTechList);
        Bundle[] oldTechExtras = oldParcel.createTypedArray(Bundle.CREATOR);
        int serviceHandle = oldParcel.readInt();
        int isMock = oldParcel.readInt();
        IBinder tagService;
        if (isMock == 0) {
            tagService = oldParcel.readStrongBinder();
        } else {
            tagService = null;
        }
        oldParcel.recycle();

        int nfcaIdx = -1;
        int mcIdx = -1;
        short sak = 0;
        boolean isFirstSak = true;

        for (int i = 0; i < techList.length; i++) {
            if (techList[i].equals(NfcA.class.getName())) {
                if (nfcaIdx == -1) {
                    nfcaIdx = i;
                }
                if (oldTechExtras[i] != null
                        && oldTechExtras[i].containsKey("sak")) {
                    sak = (short) (sak
                            | oldTechExtras[i].getShort("sak"));
                    isFirstSak = (nfcaIdx == i);
                }
            } else if (techList[i].equals(MifareClassic.class.getName())) {
                mcIdx = i;
            }
        }

        boolean modified = false;

        // Patch the double NfcA issue (with different SAK) for
        // Sony Z3 devices.
        if (!isFirstSak) {
            oldTechExtras[nfcaIdx].putShort("sak", sak);
            modified = true;
        }

        // Patch the wrong index issue for HTC One devices.
        if (nfcaIdx != -1 && mcIdx != -1 && oldTechExtras[mcIdx] == null) {
            oldTechExtras[mcIdx] = oldTechExtras[nfcaIdx];
            modified = true;
        }

        if (!modified) {
            // Old tag was not modivied. Return the old one.
            return tag;
        }

        // Old tag was modified. Create a new tag with the new data.
        Parcel newParcel = Parcel.obtain();
        newParcel.writeInt(id.length);
        newParcel.writeByteArray(id);
        newParcel.writeInt(oldTechList.length);
        newParcel.writeIntArray(oldTechList);
        newParcel.writeTypedArray(oldTechExtras, 0);
        newParcel.writeInt(serviceHandle);
        newParcel.writeInt(isMock);
        if (isMock == 0) {
            newParcel.writeStrongBinder(tagService);
        }
        newParcel.setDataPosition(0);
        Tag newTag = Tag.CREATOR.createFromParcel(newParcel);
        newParcel.recycle();

        return newTag;
    }

    public APDUResponse executeCommandOrThrow(AndroidApdu apdu) throws IOException {
        APDUResponse apduResponse = null;
        if (apdu != null) {
            //loggerHelper.logDebug(">> " + apdu.toString());
            if (apdu instanceof AndroidAuthApdu) {
                apduResponse = authApdu(apdu);
            } else if (apdu instanceof AndroidWriteApdu) {
                apduResponse = writeApdu(apdu);
            } else if (apdu instanceof AndroidReadApdu) {
                apduResponse = readApdu(apdu);
            }
        }
        return apduResponse;
    }

    private APDUResponse readApdu(AndroidApdu apdu) throws IOException {
        try {
            AndroidReadApdu readApdu = (AndroidReadApdu) apdu;
            byte[] resp = mifareClassic.readBlock(readApdu.getBlock());
            if (resp != null) {
                // mMFC.readBlock(i) must return 16 bytes or throw an error.
                // At least this is what the documentation says.
                // On Samsung's Galaxy S5 and Sony's Xperia Z2 however, it
                // sometimes returns < 16 bytes for unknown reasons.
                // Update: Aaand sometimes it returns more than 16 bytes...
                // The appended byte(s) are 0x00.
                if (resp.length < 16) {
                    throw new IOException();
                }

                if (resp.length > 16) {
                    resp = Arrays.copyOf(resp, 16);
                }
                return new APDUResponse(DataUtil.concat(resp, new byte[]{(byte) 0x90, 0x00}));
            } else {
                String msg = "Null response reading";
                throw new IOException(msg);
            }
        } catch (Exception e) {
            throw new IOException("Reading apdu", e);
        }
    }

    private APDUResponse writeApdu(AndroidApdu apdu) throws IOException {
        try {
            AndroidWriteApdu writeApdu = (AndroidWriteApdu) apdu;
            mifareClassic.writeBlock(writeApdu.getBlock(), writeApdu.getData());
            if (writeApdu.getBlock() != 0) {
                AndroidReadApdu readApdu = new AndroidReadApdu(writeApdu.getBlock());
                APDUResponse readResponse = readApdu(readApdu);
                if (!Arrays.equals(writeApdu.getData(), readResponse.getData())) {
                    String msg = "Check write error";
                    throw new IOException(msg);
                }
            }

            return APDUResponse.getAPDUResponse9000();
        } catch (Exception e) {
            throw new IOException("Writing apdu", e);
        }
    }


    private APDUResponse authApdu(AndroidApdu apdu) throws IOException {
        try {
            AndroidAuthApdu authApdu = (AndroidAuthApdu) apdu;
            boolean authenticated;
            if (authApdu.isAuthKeyA()) {
                authenticated = mifareClassic.authenticateSectorWithKeyA(authApdu.getBlock() / 4, authApdu.getKey());
            } else {
                authenticated = mifareClassic.authenticateSectorWithKeyB(authApdu.getBlock() / 4, authApdu.getKey());
            }
            if (authenticated) {
                return APDUResponse.getAPDUResponse9000();
            } else {
                throw new IOException("Error authenticating");
            }
        } catch (Exception e) {
            throw new IOException("Authenticating apdu", e);

        }
    }
}
