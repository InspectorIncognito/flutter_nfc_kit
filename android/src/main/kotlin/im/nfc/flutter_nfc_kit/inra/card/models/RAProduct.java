package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;

public final class RAProduct extends Product implements Serializable {
    private static final long serialVersionUID = -4370567599027924498L;
    private int balance;

    public RAProduct(int balance) {
        this.balance = balance;
    }

    public RAProduct(String description, int balance) {
        super(description);
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RAProduct [balance=").append(this.balance).append("]");
        return super.toString() + " " + builder.toString();
    }
}
