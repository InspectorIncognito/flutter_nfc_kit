package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;
import java.util.Date;

public class CardEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private long operatorId;
    private Date date;
    private int balance;
    private int place;

    public CardEvent(long operatorId, Date date, int balance, int place) {
        this.operatorId = operatorId;
        this.date = date;
        this.balance = balance;
        this.place = place;
    }

    public CardEvent() {
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPlace() {
        return this.place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
