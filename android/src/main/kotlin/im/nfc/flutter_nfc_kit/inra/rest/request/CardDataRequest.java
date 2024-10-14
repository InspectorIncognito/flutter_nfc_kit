package im.nfc.flutter_nfc_kit.inra.rest.request;

public class CardDataRequest extends GeneralParamsRequest {
    private static final long serialVersionUID = 1L;
    private String cardUID;

    public String toString() {
        StringBuffer result = new StringBuffer("[CardDataRequest]\r\n");
        result.append("\tcardUID: ").append(this.cardUID).append("\r\n").append("\tlanguage: ").append(super.getLanguage()).append("\r\n");
        if (super.getDeviceData() != null) {
            result.append("\tDevice data: ").append("\r\n");
            result.append(super.getDeviceData().toStringFormatted());
        }

        return result.toString();
    }

    public CardDataRequest() {
    }

    public String getCardUID() {
        return this.cardUID;
    }

    public void setCardUID(String cardUID) {
        this.cardUID = cardUID;
    }
}
