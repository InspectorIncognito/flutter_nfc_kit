package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;

public class GenericOperationInfo implements Serializable {
    private static final long serialVersionUID = -4678212499780226352L;
    String desc;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public GenericOperationInfo(String desc) {
        this.desc = desc;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GenericOperationInfo [");
        if (this.desc != null) {
            builder.append("desc=").append(this.desc);
        }

        builder.append("]");
        return builder.toString();
    }
}
