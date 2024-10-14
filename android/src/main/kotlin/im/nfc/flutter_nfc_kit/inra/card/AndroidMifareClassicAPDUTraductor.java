package im.nfc.flutter_nfc_kit.inra.card;

import android.nfc.tech.MifareClassic;

import im.nfc.flutter_nfc_kit.inra.card.apdu.APDURequest;
import im.nfc.flutter_nfc_kit.inra.card.apdu.APDUResponse;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidApdu;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidAuthApdu;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidRawApdu;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidReadApdu;
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidWriteApdu;
import im.nfc.flutter_nfc_kit.inra.rest.request.CardCommandRequest;
import im.nfc.flutter_nfc_kit.inra.utils.DataUtil;
import im.nfc.flutter_nfc_kit.inra.utils.Hex;

public class AndroidMifareClassicAPDUTraductor {
    private static AndroidMifareClassicAPDUTraductor instance = new AndroidMifareClassicAPDUTraductor();
    private byte[] lastKey;

    static public AndroidMifareClassicAPDUTraductor getInstance() {
        return instance;
    }

    public AndroidRawApdu translateToAndroidRawApdu(MifareClassic card, CardCommandRequest command) {
        try {
            String apduHex = command.getCommand();
            APDURequest apdu = new APDURequest(Hex.decodeHex(apduHex));
            byte[] response = null;
            int type = AndroidRawApdu.APDU_TYPE.UNKNOWN;
            if (apdu.getBytes() != null) {

                switch ((byte) apdu.getINS()) {
                    case INS.READ: {
                        response = new byte[]{0x30, (byte) apdu.getP2()};
                        type = AndroidRawApdu.APDU_TYPE.READ;
                        break;
                    }
                    case INS.WRITE: {
                        response = new byte[apdu.getData().length + 2];
                        response[0] = (byte) 0xA0; // MF write command
                        response[1] = (byte) apdu.getP2(); // block
                        System.arraycopy(apdu.getData(), 0, response, 2, apdu.getData().length);
                        type = AndroidRawApdu.APDU_TYPE.WRITE;
                        break;
                    }
                    case INS.AUTH_KEY: {
                        response = new byte[12];
                        byte[] data = apdu.getBytes();
                        byte key_type = data[8];
                        if (key_type == KEY_TYPES.A)
                            response[0] = 0x60; // A
                        else
                            response[0] = 0x61; // B

                        // Second byte is block address
                        // Authenticate command takes a block address. Authenticating a block
                        // of a sector will authenticate the entire sector.
                        byte block = data[7];
                        response[1] = block;
                        // Next 4 bytes are last 4 bytes of UID
                        byte[] uid = card.getTag().getId();
                        System.arraycopy(uid, uid.length - 4, response, 2, 4);

                        // get key
                    /*byte[] key = null;
                    if (key_type == KEY_TYPES.A)
                        key = BipCardUtils.getKeyATestingNew((int) block / 4);
                    else
                        kemy = BipCardUtils.getKeyBTestingNew((int) block / 4);
                        */

                        // Next 6 bytes are key
                        System.arraycopy(lastKey, 0, response, 6, 6);
                        type = AndroidRawApdu.APDU_TYPE.AUTH;
                        lastKey = null;
                        break;
                    }
                    case INS.LOAD_KEY:
                        byte[] data = apdu.getBytes();
                        lastKey = new byte[6];
                        System.arraycopy(data, 5, lastKey, 0, 6);
                        break;
                }
            }

            return new AndroidRawApdu(type, response);
        } catch (Exception e) {
            return null;
        }
    }

    public AndroidApdu translateToAndroidApdu(CardCommandRequest command) {
        try {
            //logInfo("Command received: " + command.toString());
            String apduHex = command.getCommand();
            APDURequest apdu = new APDURequest(Hex.decodeHex(apduHex));
            AndroidApdu androidApdu = null;
            if (apdu.getBytes() != null) {
                switch ((byte) apdu.getINS()) {
                    case INS.READ: {
                        androidApdu = new AndroidReadApdu(apdu.getP2());
                        break;
                    }
                    case INS.WRITE: {
                        androidApdu = new AndroidWriteApdu(apdu.getP2(), apdu.getData());
                        break;
                    }
                    case INS.AUTH_KEY: {

                        byte[] data = apdu.getBytes();
                        byte key_type = data[8];
                        int keyTypeAndroid;
                        if (key_type == KEY_TYPES.A)
                            keyTypeAndroid = AndroidAuthApdu.KEY_TYPES.A;
                        else
                            keyTypeAndroid = AndroidAuthApdu.KEY_TYPES.B;

                        androidApdu = new AndroidAuthApdu(data[7], keyTypeAndroid, lastKey);
                        lastKey = null;
                        break;
                    }
                    case INS.LOAD_KEY:
                        byte[] data = apdu.getBytes();
                        lastKey = new byte[6];
                        System.arraycopy(data, 5, lastKey, 0, 6);
                        break;
                }
            }


            return androidApdu;
        } catch (Exception e) {
            return null;
        }

    }

    public APDUResponse translateToAPDU(AndroidRawApdu androidAPDU) {
        if (androidAPDU == null || androidAPDU.getApdu() == null) {
            return new APDUResponse(new byte[]{(byte) 0xFF, (byte) 0xFF});
            // the auth returns with 0x00
        } else if (androidAPDU.getType() == AndroidRawApdu.APDU_TYPE.AUTH && androidAPDU.getApdu().length == 1 && androidAPDU.getApdu()[0] == 0x00) {
            return new APDUResponse(new byte[]{(byte) 0x90, 0x00});
            // Writing with no permission returns responses with first byte != 0x0A. (0x0A = writing was ok)
        } else if (androidAPDU.getType() == AndroidRawApdu.APDU_TYPE.WRITE && androidAPDU.getApdu()[0] != 0x0A) {
            return new APDUResponse(new byte[]{(byte) 0xFF, (byte) 0xFF});
        } else {
            byte[] result = DataUtil.concat(androidAPDU.getApdu(), new byte[]{(byte) 0x90, 0x00});
            return new APDUResponse(result);
        }
    }

    /**
     * Define instructions commands => TODO deberiamos tener una clase/interfaz de valores a compartir con el servidor
     */
    private static class INS {
        private static final byte READ = (byte) 0xB0;
        private static final byte WRITE = (byte) 0xD6;
        private static final byte LOAD_KEY = (byte) 0x82;
        private static final byte AUTH_KEY = (byte) 0x86;
        private static final byte GET_UID = (byte) 0xCA;
    }

    /**
     * Define key types
     */
    private static class KEY_TYPES {
        private static final int A = 0x60;
        private static final int B = 0x61;
    }
}
