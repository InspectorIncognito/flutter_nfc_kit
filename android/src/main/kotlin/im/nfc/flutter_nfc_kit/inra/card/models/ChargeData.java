package im.nfc.flutter_nfc_kit.inra.card.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class ChargeData extends CardEvent {
    private static final long serialVersionUID = -2571150567978046428L;
    private int loadContractCode;
    private String loadContractDescription;

    public ChargeData() {
    }

    public ChargeData(long eventId, Date loadEventDate, int loadEventPlace, int loadContractCode, int travelBalance) {
        super(eventId, loadEventDate, travelBalance, loadEventPlace);
        this.loadContractCode = loadContractCode;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("\t\t\tLoad contract code: ").append(this.loadContractCode).append(" ===== \r\n");
        result.append("\t\t\t   contract description: ").append(this.getLoadContractDescription()).append(" \r\n");
        result.append("\t\t\t   Operator Id: ").append(this.getOperatorId()).append(" \r\n");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (this.getDate() != null) {
            result.append("\t\t\t   Date: ").append(df.format(this.getDate())).append(" \r\n");
        }

        result.append("\t\t\t   Balance: ").append(this.getBalance()).append(" \r\n");
        result.append("\t\t\t   Place: ").append(this.getPlace()).append(" \r\n");
        return result.toString();
    }

    public int getLoadContractCode() {
        return this.loadContractCode;
    }

    public void setLoadContractCode(int loadContractCode) {
        this.loadContractCode = loadContractCode;
    }

    public String getLoadContractDescription() {
        return this.loadContractDescription;
    }

    public void setLoadContractDescription(String loadContractDescription) {
        this.loadContractDescription = loadContractDescription;
    }
}
