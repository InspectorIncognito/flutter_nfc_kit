package im.nfc.flutter_nfc_kit.inra.card.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class ValidationData extends CardEvent {
    private static final long serialVersionUID = 1L;
    private int validationType;

    public ValidationData() {
    }

    public ValidationData(long operatorId, Date date, int validationType, int validationPlace, int travelBalance) {
        super(operatorId, date, travelBalance, validationPlace);
        this.validationType = validationType;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("\t\t\tValidation type: ").append(this.validationType).append(" ===== \r\n");
        result.append("\t\t\t   Operator Id: ").append(this.getOperatorId()).append(" \r\n");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (this.getDate() != null) {
            result.append("\t\t\t   Date: ").append(df.format(this.getDate())).append(" \r\n");
        }

        result.append("\t\t\t   Balance: ").append(this.getBalance()).append(" \r\n");
        result.append("\t\t\t   Place: ").append(this.getPlace()).append(" \r\n");
        return result.toString();
    }

    public int getValidationType() {
        return this.validationType;
    }

    public void setValidationType(int validationType) {
        this.validationType = validationType;
    }
}
