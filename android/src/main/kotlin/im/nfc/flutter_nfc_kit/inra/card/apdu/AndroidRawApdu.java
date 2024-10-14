package im.nfc.flutter_nfc_kit.inra.card.apdu;

public class AndroidRawApdu {
    byte[] apdu = null;
    int type;

    public AndroidRawApdu(int type, byte[] apdu) {
        this.apdu = apdu;
        this.type = type;
    }

    public byte[] getApdu() {
        return apdu;
    }

    public void setApdu(byte[] apdu) {
        this.apdu = apdu;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class APDU_TYPE {
        public static final int UNKNOWN = -1;
        public static final int AUTH = 0;
        public static final int READ = 1;
        public static final int WRITE = 2;
    }
}
