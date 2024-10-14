package im.nfc.flutter_nfc_kit.inra.utils;

import java.util.Arrays;
import java.util.Random;

public class DataUtil {
    // generador de numeros aleatorios
    private static Random random = new Random(System.currentTimeMillis());

    /**
     * Genera un array de bytes aleatorios
     */
    public static byte[] random(int length) {
        byte[] out = new byte[length];

        random.nextBytes(out);

        return out;
    }

    /**
     * Rota el array de bytes a la izquierda
     */
    public static byte[] rotateLeft(byte[] in) {
        byte[] out = new byte[in.length];

        for (int i = 0; i < in.length; i++) {
            out[i] = in[(i + 1) % in.length];
        }

        return out;
    }

    /**
     * Calcula el CRC16 ISO1443A
     */
    public static byte[] crc16(byte[] data, int crc) {
        for (byte b : data) {
            crc = (crc ^ (b & 0xff));

            for (int j = 0; j < 8; j++) {
                if ((crc & 1) != 0) {
                    crc = ((crc >>> 1) ^ 0x8408);
                } else {
                    crc = (crc >> 1) & 0xffff;
                }
            }
        }

        return toLE(crc, 2);
    }

    /**
     * Calcula el CRC32 ISO1443A
     */
    public static byte[] crc32(byte[] data, long crc) {
        for (byte b : data) {
            crc = (crc ^ (b & 0xff));

            for (int j = 0; j < 8; j++) {
                if ((crc & 1) != 0) {
                    crc = ((crc >>> 1) ^ 0xEDB88320);
                } else {
                    crc = (crc >> 1) & 0xffffffff;
                }
            }
        }

        return toLE(crc, 4);
    }

    /**
     * Concatena dos arrays de bytes => b = b2+b1
     * b1 = 0x01 0x02
     * b2 = 0x0A 0x0B
     * b = b2 + b1 = 0x0A 0x0B 0x01 0x02
     */
    public static byte[] concat(byte[] b1, byte[] b2) {
        return concat(b1, b2, 0, b2.length);
    }

    /**
     * Concatena dos arrays de bytes
     */
    public static byte[] concat(byte[] b1, byte[] b2, int n2) {
        return concat(b1, b2, 0, n2);
    }

    /**
     * Concatena dos arrays de bytes
     */
    public static byte[] concat(byte[] b1, byte[] b2, int from, int length) {
        int n1 = b1.length;

        if (n1 == 0 && from == 0 && length == b2.length)
            return b2;

        byte[] res = new byte[n1 + length];

        if (b2.length < length)
            b2 = Arrays.copyOf(b2, length);

        System.arraycopy(b1, 0, res, 0, n1);
        System.arraycopy(b2, from, res, n1, length);

        return res;
    }

    /**
     * Obtiene los bytes de un numero en BIG ENDIAN
     */
    public static byte[] toBE(long value, int l) {
        byte[] r = new byte[l];

        while (l-- > 0) {
            r[l] = (byte) (value & 0xff);

            value >>= 8;
        }

        return r;
    }

    /**
     * Obtiene los bytes de un numero en LITTLE_ENDIAN
     */
    public static byte[] toLE(long value, int l) {
        byte[] r = new byte[l];

        for (int i = 0; i < l; i++) {
            r[i] = (byte) (value & 0xff);

            value >>= 8;
        }

        return r;
    }

    /**
     * Obtiene el numero a partir de los bytes en LITTLE_ENDIAN
     *
     * @param value datos a transformar
     * @param o     offset
     * @param l     longitud
     */
    public static long fromLE(byte[] value, int o, int l) {
        long n = 0;

        for (int i = 0; i < l; i++) {
            n |= ((value[o + i] & 0xff) << (8 * i));
        }

        return n;
    }

    /**
     * Obtiene el numero a partir de los bytes en BIG_ENDIAN
     *
     * @param value datos a transformar
     * @param o     offset
     * @param l     longitud
     */
    public static long fromBE(byte[] value, int o, int l) {
        long n = 0;

        for (int i = 0; i < l; i++) {
            n <<= 8;
            n |= (value[o + i] & 0xff);
        }

        return n;
    }

    public static byte[] xor(byte[] a, byte[] b) {
        byte r[] = new byte[a.length < b.length ? a.length : b.length];

        for (int i = 0; i < r.length; i++) {
            r[i] = (byte) (a[i] ^ b[i]);
        }

        return r;
    }

    public static byte[] shiftBitsLeft(byte[] l) {
        byte[] r = new byte[l.length];

        for (int i = 0; i < l.length; i++) {
            r[i] = ((byte) (l[i] << 1));

            if (i > 0) {
                r[i - 1] |= (l[i] >> 7) & 1;
            }
        }

        return r;
    }

    public static boolean equals(byte[] bytesA, byte[] bytesB, int length) {
        if (bytesA == bytesB)
            return true;

        if (bytesA == null || bytesB == null)
            return false;

        for (int i = 0; i < length; i++)
            if (bytesA[i] != bytesB[i])
                return false;

        return true;
    }
}

