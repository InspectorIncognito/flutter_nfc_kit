package im.nfc.flutter_nfc_kit.inra.utils;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Hex {
    public static String encodeHex(byte[] bytes) {
        String hex = "";

        for (byte b : bytes) {
            hex += String.format("%02X", (b & 0xff));
        }

        return hex.toUpperCase();
    }

    public static String encodeHexBigEndian(byte[] bytes) {
        String hex = "";

        for (byte b : bytes) {
            hex = String.format("%02X", (b & 0xff)) + hex;
        }

        return hex.toUpperCase();
    }

    public static String encodeHexBigEndian(byte[] bytes, int offset, int size) throws Exception {
        if (size > bytes.length || (offset + size) > bytes.length)
            throw new Exception("Size to endode is greater than byte.size");

        String hex = "";

        for (int i = 0; i < size; i++) {
            hex = String.format("%02X", (bytes[offset + i] & 0xff)) + hex;
        }

        return hex.toUpperCase();
    }

    public static String encodeHex(byte[] bytes, int offset, int size) throws Exception {
        if (size > bytes.length || (offset + size) > bytes.length)
            throw new Exception("Size to endode is greater than byte.size");

        String hex = "";

        for (int i = 0; i < size; i++) {
            hex += String.format("%02X", (bytes[offset + i] & 0xff));
        }

        return hex.toUpperCase();
    }

    public static byte[] decodeHex(String string) {
        if (string.length() == 0)
            return new byte[0];

        // take care of strings with spaces and \n
        string = string.replace(" ", "").replace("\n", "");

        if (string.length() % 2 == 1)
            string = "0" + string;

        byte[] res = new byte[string.length() / 2];

        try {
            for (int i = 0; i < res.length; i++) {
                res[i] = Integer.valueOf(string.substring(i * 2, i * 2 + 2), 16).byteValue();
            }

            return res;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Hex string format error: " + string);
        }
    }

}
