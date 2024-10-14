package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class GeneralData implements Serializable {
    private static final long serialVersionUID = 7100081965996759231L;
    private int cardSerialNumber;
    private Date activationCardDate;
    private String userName;
    private byte[] baNTar;
    private int cardType;
    private int userProfile1;
    private int userProfile2;
    private Date userProfile1BeginDate;
    private Date userProfile1EndDate;
    private Date userProfile2BeginDate;
    private Date userProfile2EndDate;
    private int purse;

    public GeneralData() {
    }

    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        StringBuffer result = new StringBuffer();
        result.append("\t\t\tCard serial number: ").append(this.cardSerialNumber).append("\r\n");
        if (this.activationCardDate != null) {
            result.append("\t\t\tActivationCardDate: ").append(df.format(this.activationCardDate)).append("\r\n");
        } else {
            result.append("\t\t\tActivationCardDate: ").append("\r\n");
        }

        if (this.userName != null) {
            result.append("\t\t\tUser name: ").append(this.userName).append("\r\n");
        } else {
            result.append("\t\t\tUser name: ").append("\r\n");
        }

        if (this.baNTar != null) {
            result.append("\t\t\tbaNTar: ").append(this.baNTar).append("\r\n");
        } else {
            result.append("\t\t\tbaNTar: ").append("\r\n");
        }

        result.append("\t\t\tCard Type: ").append(this.cardType).append("\r\n");
        result.append("\t\t\tUser profile 1: ").append(this.userProfile1).append("\r\n");
        result.append("\t\t\tUser profile 2: ").append(this.userProfile2).append("\r\n");
        if (this.userProfile1BeginDate != null) {
            result.append("\t\t\tUser profile 1 begin Date: ").append(df.format(this.userProfile1BeginDate)).append("\r\n");
        } else {
            result.append("\t\t\tUser profile 1 begin Date: ").append("\r\n");
        }

        if (this.userProfile1EndDate != null) {
            result.append("\t\t\tUser profile 1 end Date: ").append(df.format(this.userProfile1EndDate)).append("\r\n");
        } else {
            result.append("\t\t\tUser profile 1 end Date: ").append("\r\n");
        }

        if (this.userProfile2BeginDate != null) {
            result.append("\t\t\tUser profile 2 begin Date: ").append(df.format(this.userProfile2BeginDate)).append("\r\n");
        } else {
            result.append("    User profile 2 begin Date: ").append("\r\n");
        }

        if (this.userProfile2EndDate != null) {
            result.append("\t\t\tUser profile 21 end Date: ").append(df.format(this.userProfile2EndDate)).append("\r\n");
        } else {
            result.append("\t\t\tUser profile 2 end Date: ").append("\r\n");
        }

        result.append("\t\t\tPurse: ").append(this.purse).append("\r\n");
        return result.toString();
    }

    public int getCardSerialNumber() {
        return this.cardSerialNumber;
    }

    public void setCardSerialNumber(int cardSerialNumber) {
        this.cardSerialNumber = cardSerialNumber;
    }

    public Date getActivationCardDate() {
        return this.activationCardDate;
    }

    public void setActivationCardDate(Date activationCardDate) {
        this.activationCardDate = activationCardDate;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getBaNTar() {
        return this.baNTar;
    }

    public void setBaNTar(byte[] baNTar) {
        this.baNTar = baNTar;
    }

    public int getCardType() {
        return this.cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getUserProfile1() {
        return this.userProfile1;
    }

    public void setUserProfile1(int userProfile1) {
        this.userProfile1 = userProfile1;
    }

    public int getUserProfile2() {
        return this.userProfile2;
    }

    public void setUserProfile2(int userProfile2) {
        this.userProfile2 = userProfile2;
    }

    public Date getUserProfile1BeginDate() {
        return this.userProfile1BeginDate;
    }

    public void setUserProfile1BeginDate(Date userProfile1BeginDate) {
        this.userProfile1BeginDate = userProfile1BeginDate;
    }

    public Date getUserProfile2EndDate() {
        return this.userProfile2EndDate;
    }

    public void setUserProfile2EndDate(Date userProfile2EndDate) {
        this.userProfile2EndDate = userProfile2EndDate;
    }

    public Date getUserProfile1EndDate() {
        return this.userProfile1EndDate;
    }

    public void setUserProfile1EndDate(Date userProfile1EndDate) {
        this.userProfile1EndDate = userProfile1EndDate;
    }

    public Date getUserProfile2BeginDate() {
        return this.userProfile2BeginDate;
    }

    public void setUserProfile2BeginDate(Date userProfile2BeginDate) {
        this.userProfile2BeginDate = userProfile2BeginDate;
    }

    public int getPurse() {
        return this.purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }
}
