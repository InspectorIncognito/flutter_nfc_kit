package im.nfc.flutter_nfc_kit.inra.card.apdu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import im.nfc.flutter_nfc_kit.inra.utils.Hex;

public final class APDUResponse {

    private byte[] apdu;

    /**
     * Empty constructor for JSON
     */
    public APDUResponse() {
    }

    /**
     * Constructs a ResponseAPDU from a byte array containing the complete APDU
     * contents (conditional body and trailed).
     * <p>
     * <p>
     * Note that the byte array is cloned to protect against subsequent
     * modification.
     *
     * @param apdu the complete response APDU
     * @throws NullPointerException     if apdu is null
     * @throws IllegalArgumentException if apdu.length is less than 2
     */

    public APDUResponse(byte[] apdu) {
        apdu = apdu.clone();
        check(apdu);
        this.apdu = apdu;
    }

    private static void check(byte[] apdu) {
        if (apdu.length < 2) {
            throw new IllegalArgumentException("apdu must be at least 2 bytes long");
        }
    }

    public static APDUResponse getAPDUResponse9000() {
        return new APDUResponse(new byte[]{(byte) 0x90, 0x00});
    }

    public static APDUResponse getAPDUResponseFFFF() {
        return new APDUResponse(new byte[]{(byte) 0xFF, (byte) 0xFF});
    }

    public static APDUResponse getAPDUResponseFFFE() {
        return new APDUResponse(new byte[]{(byte) 0xFF, (byte) 0xFE});
    }

    public static APDUResponse getAPDUResponseFFFD() {
        return new APDUResponse(new byte[]{(byte) 0xFF, (byte) 0xFD});
    }

    /**
     * Returns the number of data bytes in the response body (Nr) or 0 if this
     * APDU has no body. This call is equivalent to
     * <code>getData().length</code>.
     *
     * @return the number of data bytes in the response body or 0 if this APDU
     * has no body.
     */
    public int getNr() {
        return apdu.length - 2;
    }

    /**
     * Returns a copy of the data bytes in the response body. If this APDU as no
     * body, this method returns a byte array with a length of zero.
     *
     * @return a copy of the data bytes in the response body or the empty byte
     * array if this APDU has no body.
     */
    public byte[] getData() {
        byte[] data = new byte[apdu.length - 2];

        System.arraycopy(apdu, 0, data, 0, data.length);

        return data;
    }

    /**
     * Returns the value of the status byte SW1 as a value between 0 and 255.
     *
     * @return the value of the status byte SW1 as a value between 0 and 255.
     */
    public int getSW1() {
        return apdu[apdu.length - 2] & 0xff;
    }

    /**
     * Returns the value of the status byte SW2 as a value between 0 and 255.
     *
     * @return the value of the status byte SW2 as a value between 0 and 255.
     */
    public int getSW2() {
        return apdu[apdu.length - 1] & 0xff;
    }

    /**
     * Returns the value of the status bytes SW1 and SW2 as a single status word
     * SW. It is defined as <code>(getSW1() << 8) | getSW2()</code>.
     *
     * @return the value of the status word SW.
     */
    public int getSW() {
        return (getSW1() << 8) | getSW2();
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
     * Returns a string representation of this response APDU.
     *
     * @return a String representation of this response APDU.
     */
    @Override
    public String toString() {
        return "ResponseAPDU: " + apdu.length + " bytes, SW=" + Integer.toHexString(getSW());
    }

    /**
     * Returns a JSON representation of this command APDU.
     *
     * @return a JSONObject representation of this command APDU.
     */
    public JSONObject toJson() {
        final JSONObject json = new JSONObject();

        try {
            json.put("bytes", Hex.encodeHex(getBytes()));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return json;
    }

    /**
     * Compares the specified object with this response APDU for equality.
     * Returns true if the given object is also a ResponseAPDU and its bytes are
     * identical to the bytes in this ResponseAPDU.
     *
     * @param obj the object to be compared for equality with this response APDU
     * @return true if the specified object is equal to this response APDU
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof APDUResponse == false) {
            return false;
        }

        APDUResponse other = (APDUResponse) obj;

        return Arrays.equals(this.apdu, other.apdu);
    }

    /**
     * Returns the hash code value for this response APDU.
     *
     * @return the hash code value for this response APDU.
     */
    public int hashCode() {
        return Arrays.hashCode(apdu);
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        apdu = (byte[]) in.readUnshared();

        check(apdu);
    }


}
