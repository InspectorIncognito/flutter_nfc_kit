package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;

public abstract class Product implements Serializable {
    private static final long serialVersionUID = 3378172034573561282L;
    private String description;

    public Product() {
    }

    public Product(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t\tProduct [");
        if (this.description != null) {
            builder.append("description=").append(this.description);
        }

        builder.append("]");
        return builder.toString();
    }
}
