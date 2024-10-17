package im.nfc.flutter_nfc_kit.inra.rest.request;

import java.util.Iterator;
import java.util.List;

import im.nfc.flutter_nfc_kit.inra.rest.response.CardCommandResponse;

public class CardLoadCommandRequest extends GeneralParamsRequest {
    private static final long serialVersionUID = 1L;
    private String cardUID;
    private String transactionId;
    private List<CardCommandResponse> operationList;

    public CardLoadCommandRequest() {
    }

    public CardLoadCommandRequest(String language, String cardUID) {
        super(language);
        this.cardUID = cardUID;
    }

    public String toString() {
        StringBuffer result = new StringBuffer("[CardLoadCommandRequest]\r\n");
        result.append("\tcardUID: ").append(this.cardUID).append("\r\n").append("\tlanguage: ").append(super.getLanguage()).append("\r\n").append("\ttransactionId: ").append(this.getTransactionId()).append("\r\n");
        if (super.getDeviceData() != null) {
            result.append("\tDevice data: ").append("\r\n");
            result.append(super.getDeviceData().toStringFormatted());
        }

        result.append("\tcommand list: ").append("\r\n");
        if (this.operationList != null) {
            Iterator var3 = this.operationList.iterator();

            while(var3.hasNext()) {
                CardCommandResponse command = (CardCommandResponse)var3.next();
                result.append(command).append("\r\n");
            }
        }

        return result.toString();
    }

    public String getCardUID() {
        return this.cardUID;
    }

    public void setCardUID(String cardUID) {
        this.cardUID = cardUID;
    }

    public List<CardCommandResponse> getOperationList() {
        return this.operationList;
    }

    public void setOperationList(List<CardCommandResponse> operationList) {
        this.operationList = operationList;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
