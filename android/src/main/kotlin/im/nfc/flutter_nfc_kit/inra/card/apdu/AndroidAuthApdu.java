package im.nfc.flutter_nfc_kit.inra.card.apdu;

import im.nfc.flutter_nfc_kit.inra.utils.Hex;

public class AndroidAuthApdu extends AndroidApdu {
    private final int block;
    private final int keyType;
    private final byte[] key;

    public AndroidAuthApdu(int block, int type, byte[] key) {
        super("auth apdu");
        this.block = block;
        this.keyType = type;
        this.key = key;
    }

    public int getBlock() {
        return block;
    }

    public boolean isAuthKeyA() {
        if (keyType == KEY_TYPES.A)
            return true;
        else
            return false;
    }

    public boolean isAuthKeyB() {
        if (keyType == KEY_TYPES.B)
            return true;
        else
            return false;
    }

    public byte[] getKey() {
        return key;
    }

    public int getKeyType() {
        return keyType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AndroidAuthApdu{");
        sb.append("block=").append(block);
        sb.append(", keyType=").append(keyType);
        sb.append(", key=");
        if (key == null) sb.append("null");
        else {
            sb.append(Hex.encodeHex(key));
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Define key types
     */
    public static class KEY_TYPES {
        public static final int A = 0;
        public static final int B = 1;
    }
}
