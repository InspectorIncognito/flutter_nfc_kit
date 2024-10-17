package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;

public class BipWallet implements Serializable {
    private static final long serialVersionUID = 4194678054049654262L;
    int value;
    String desc;

    public BipWallet(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BipWallet [value=").append(this.value).append(", ");
        if (this.desc != null) {
            builder.append("desc=").append(this.desc);
        }

        builder.append("]");
        return builder.toString();
    }
}