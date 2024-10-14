package im.nfc.flutter_nfc_kit.inra.rest.response;

import java.io.Serializable;

public class GeneralServiceResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String description;

    public GeneralServiceResponse() {
    }

    public GeneralServiceResponse(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description != null && this.description.equals("-") ? "" : this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GeneralServiceResponse [");
        if (this.code != null) {
            builder.append("code=").append(this.code).append(", ");
        }

        if (this.description != null) {
            builder.append("description=").append(this.description);
        }

        builder.append("]");
        return builder.toString();
    }
}
