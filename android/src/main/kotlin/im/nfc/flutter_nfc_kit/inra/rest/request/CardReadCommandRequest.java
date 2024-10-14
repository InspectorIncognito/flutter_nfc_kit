package im.nfc.flutter_nfc_kit.inra.rest.request;

import java.util.Iterator;
import java.util.List;

import im.nfc.flutter_nfc_kit.inra.rest.response.CardCommandResponse;

public class CardReadCommandRequest extends GeneralParamsRequest {
    private static final long serialVersionUID = 1L;
    private String cardUID;
    private String transactionId;
    private List<CardCommandResponse> operationList;

    public CardReadCommandRequest() {
    }

    public CardReadCommandRequest(String language, String cardUID) {
        super(language);
        this.cardUID = cardUID;
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
