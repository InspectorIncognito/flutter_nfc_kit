package im.nfc.flutter_nfc_kit.inra.card.apdu;

public class AndroidReadApdu extends AndroidApdu{
    int block;

    public AndroidReadApdu(int block) {
        super("read apdu");
        this.block = block;
    }

    public int getBlock() {
        return block;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AndroidReadApdu{");
        sb.append("block=").append(block);
        sb.append('}');
        return sb.toString();
    }
}
