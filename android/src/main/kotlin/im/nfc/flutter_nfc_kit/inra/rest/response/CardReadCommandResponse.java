package im.nfc.flutter_nfc_kit.inra.rest.response;

import java.util.Iterator;
import java.util.List;

import im.nfc.flutter_nfc_kit.inra.rest.request.CardCommandRequest;

public class CardReadCommandResponse extends GeneralServiceResponse {
    private static final long serialVersionUID = 1L;
    private String cardUID;
    private String transactionId;
    private List<CardCommandRequest> commands;

    public CardReadCommandResponse() {
    }

    public CardReadCommandResponse(Integer code, String description) {
        super(code, description);
    }

    public String getCardUID() {
        return this.cardUID;
    }

    public void setCardUID(String cardUID) {
        this.cardUID = cardUID;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<CardCommandRequest> getCommands() {
        return this.commands;
    }

    public void setCommands(List<CardCommandRequest> commands) {
        this.commands = commands;
    }

    public String toString() {
        StringBuffer result = new StringBuffer("[CardReadCommandResponse]\r\n");
        result.append("\tcardUID: ").append(this.cardUID).append("\r\n").append("\ttransactionId: ").append(this.transactionId).append("\r\n").append("\tresult code: ").append(this.getCode()).append("\r\n").append("\tresult description: ").append(this.getDescription()).append("\r\n").append("\tcommand to execute list: ").append("\r\n");
        if (this.commands != null) {
            Iterator var3 = this.commands.iterator();

            while(var3.hasNext()) {
                CardCommandRequest command = (CardCommandRequest)var3.next();
                result.append(command).append("\r\n");
            }
        }

        result.append("\r\n");
        return result.toString();
    }
}
