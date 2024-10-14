package im.nfc.flutter_nfc_kit.inra.card.apdu;

import java.io.Serializable;
import java.util.Arrays;

public final class APDURequest implements Serializable {

    private static final long serialVersionUID = -398787749649492587L;

    // private static final int MAX_APDU_SIZE = 65544;

    // value of apdu
    private byte[] apdu = null;

    // value of nc
    private transient int nc;

    // value of ne
    private transient int ne;

    // index of start of data within the apdu array
    private transient int dataOffset;

    public APDURequest() {
    }

    /**
     * Constructs a CommandAPDU from a byte array containing the complete APDU
     * contents (header and body).
     *
     * <p>
     * Note that the apdu bytes are copied to protect against subsequent
     * modification.
     *
     * @param apdu
     *            the complete command APDU
     *
     * @throws NullPointerException
     *             if apdu is null
     * @throws IllegalArgumentException
     *             if apdu does not contain a valid command APDU
     */
    public APDURequest(byte[] apdu) {
        this.apdu = apdu.clone();
        parse();
    }

    /**
     * Command APDU encoding options:
     *
     * case 1: |CLA|INS|P1 |P2 | len = 4 case 2s: |CLA|INS|P1 |P2 |LE | len = 5
     * case 3s: |CLA|INS|P1 |P2 |LC |...BODY...| len = 6..260 case 4s:
     * |CLA|INS|P1 |P2 |LC |...BODY...|LE | len = 7..261 case 2e: |CLA|INS|P1
     * |P2 |00 |LE1|LE2| len = 7 case 3e: |CLA|INS|P1 |P2 |00
     * |LC1|LC2|...BODY...| len = 8..65542 case 4e: |CLA|INS|P1 |P2 |00
     * |LC1|LC2|...BODY...|LE1|LE2| len =10..65544
     *
     * LE, LE1, LE2 may be 0x00. LC must not be 0x00 and LC1|LC2 must not be
     * 0x00|0x00
     */

    private void parse() {
        if (apdu.length < 4) {
            throw new IllegalArgumentException("apdu must be at least 4 bytes long");
        }

        if (apdu.length == 4) {
            // case 1
            return;
        }

        int l1 = apdu[4] & 0xff;

        if (apdu.length == 5) {
            // case 2s
            this.ne = (l1 == 0) ? 256 : l1;
            return;
        }

        if (l1 != 0) {
            if (apdu.length == 4 + 1 + l1) {
                // case 3s
                this.nc = l1;
                this.dataOffset = 5;
                return;
            } else if (apdu.length == 4 + 2 + l1) {
                // case 4s
                this.nc = l1;
                this.dataOffset = 5;
                int l2 = apdu[apdu.length - 1] & 0xff;
                this.ne = (l2 == 0) ? 256 : l2;
                return;
            } else {
                throw new IllegalArgumentException("Invalid APDU: length=" + apdu.length + ", b1=" + l1);
            }
        }

        if (apdu.length < 7) {
            throw new IllegalArgumentException("Invalid APDU: length=" + apdu.length + ", b1=" + l1);
        }

        int l2 = ((apdu[5] & 0xff) << 8) | (apdu[6] & 0xff);

        if (apdu.length == 7) {
            // case 2e
            this.ne = (l2 == 0) ? 65536 : l2;
            return;
        }

        if (l2 == 0) {
            throw new IllegalArgumentException("Invalid APDU: length=" + apdu.length + ", b1=" + l1 + ", b2||b3=" + l2);
        }

        if (apdu.length == 4 + 3 + l2) {
            // case 3e
            this.nc = l2;
            this.dataOffset = 7;
            return;
        } else if (apdu.length == 4 + 5 + l2) {
            // case 4e
            this.nc = l2;
            this.dataOffset = 7;
            int leOfs = apdu.length - 2;
            int l3 = ((apdu[leOfs] & 0xff) << 8) | (apdu[leOfs + 1] & 0xff);
            this.ne = (l3 == 0) ? 65536 : l3;
        } else {
            throw new IllegalArgumentException("Invalid APDU: length=" + apdu.length + ", b1=" + l1 + ", b2||b3=" + l2);
        }
    }

    /**
     * Returns the value of the instruction byte INS.
     *
     * @return the value of the instruction byte INS.
     */
    public int getINS() {
        return apdu[1] & 0xff;
    }

    /**
     * Returns the value of the parameter byte P1.
     *
     * @return the value of the parameter byte P1.
     */
    public int getP1() {
        return apdu[2] & 0xff;
    }

    /**
     * Returns the value of the parameter byte P2.
     *
     * @return the value of the parameter byte P2.
     */
    public int getP2() {
        return apdu[3] & 0xff;
    }

    /**
     * Returns a copy of the data bytes in the command body. If this APDU as no
     * body, this method returns a byte array with length zero.
     *
     * @return a copy of the data bytes in the command body or the empty byte
     *         array if this APDU has no body.
     */
    public byte[] getData() {
        byte[] data = new byte[nc];
        System.arraycopy(apdu, dataOffset, data, 0, nc);
        return data;
    }

    /**
     * Returns a copy of the bytes in this APDU.
     *
     * @return a copy of the bytes in this APDU.
     */
    public byte[] getBytes() {
        return apdu.clone();
    }

    /**
     * Returns a string representation of this command APDU.
     *
     * @return a String representation of this command APDU.
     */
    @Override
    public String toString() {
        return "CommmandAPDU: " + apdu.length + " bytes, nc=" + nc + ", ne=" + ne;
    }

    /**
     * Compares the specified object with this command APDU for equality.
     * Returns true if the given object is also a CommandAPDU and its bytes are
     * identical to the bytes in this CommandAPDU.
     *
     * @param obj
     *            the object to be compared for equality with this command APDU
     * @return true if the specified object is equal to this command APDU
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof APDURequest == false) {
            return false;
        }

        APDURequest other = (APDURequest) obj;

        return Arrays.equals(this.apdu, other.apdu);
    }

    /**
     * Returns the hash code value for this command APDU.
     *
     * @return the hash code value for this command APDU.
     */

    public int hashCode() {
        return Arrays.hashCode(apdu);
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        apdu = (byte[]) in.readUnshared();
        // initialize transient fields
        parse();
    }
}
