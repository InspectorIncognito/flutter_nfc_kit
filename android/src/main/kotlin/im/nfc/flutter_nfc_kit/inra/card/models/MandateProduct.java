package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;

public final class MandateProduct extends Product implements Serializable {
    private static final long serialVersionUID = 6041867349831782694L;
    public static final String DEFAULT_DESCRIPTION = "Desconocido";
    private String mandateName;
    private Integer balance;

    public MandateProduct(String mandateName) {
        this.mandateName = mandateName;
    }

    public MandateProduct(Integer balance) {
        this.balance = balance;
    }

    public MandateProduct(String description, String mandateName) {
        super(description);
        this.mandateName = mandateName;
    }

    public String getMandateName() {
        return this.mandateName;
    }

    public void setMandateName(String mandateName) {
        this.mandateName = mandateName;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MandateProduct [");
        if (this.mandateName != null) {
            builder.append("mandateName=").append(this.mandateName);
        }

        if (this.balance != null) {
            builder.append("balance=").append(this.balance);
        }

        builder.append("]");
        return super.toString() + " " + builder.toString();
    }
}
