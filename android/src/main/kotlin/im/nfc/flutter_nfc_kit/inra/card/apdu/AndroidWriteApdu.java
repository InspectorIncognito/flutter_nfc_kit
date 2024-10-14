package im.nfc.flutter_nfc_kit.inra.card.apdu;

import im.nfc.flutter_nfc_kit.inra.utils.Hex;

public class AndroidWriteApdu extends AndroidApdu {
    int block;
    byte[] data;

    public AndroidWriteApdu(int block, byte[] data) {
        super("write apdu");
        this.block = block;
        this.data = data;
    }

    public int getBlock() {
        return block;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AndroidWriteApdu{");
        sb.append("block=").append(block);
        sb.append(", data=");
        if (data == null) sb.append("null");
        else {
            sb.append(Hex.encodeHex(data));
        }
        sb.append('}');
        return sb.toString();
    }
}