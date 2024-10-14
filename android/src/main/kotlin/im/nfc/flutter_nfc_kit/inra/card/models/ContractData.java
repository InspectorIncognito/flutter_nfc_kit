package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ContractData implements Serializable {
    private static final long serialVersionUID = -3854745420996231305L;
    private String name;
    private int contractId;
    private Date beginActivationDate;
    private Date endActivationDate;
    private int travelBalance;

    public ContractData() {
    }

    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        StringBuffer result = new StringBuffer();
        result.append("\t\t\tContract Id: ").append(this.contractId).append(" ===== \r\n");
        if (this.name != null) {
            result.append("\t\t\t  Name: ").append(this.name).append("\r\n");
        } else {
            result.append("\t\t\t  Name: ").append("\r\n");
        }

        if (this.beginActivationDate != null) {
            result.append("\t\t\t  Begin activation Date: ").append(df.format(this.beginActivationDate)).append("\r\n");
        } else {
            result.append("\t\t\t  Begin activation Date: ").append("\r\n");
        }

        if (this.endActivationDate != null) {
            result.append("\t\t\t  End activation Date: ").append(df.format(this.endActivationDate)).append("\r\n");
        } else {
            result.append("\t\t\t  End activation Date: ").append("\r\n");
        }

        result.append("\t\t\t  Travel balance: ").append(this.travelBalance).append("\r\n");
        return result.toString();
    }

    public int getContractId() {
        return this.contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Date getBeginActivationDate() {
        return this.beginActivationDate;
    }

    public void setBeginActivationDate(Date beginActivationDate) {
        this.beginActivationDate = beginActivationDate;
    }

    public Date getEndActivationDate() {
        return this.endActivationDate;
    }

    public void setEndActivationDate(Date endActivationDate) {
        this.endActivationDate = endActivationDate;
    }

    public int getTravelBalance() {
        return this.travelBalance;
    }

    public void setTravelBalance(int travelBalance) {
        this.travelBalance = travelBalance;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
