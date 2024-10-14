package im.nfc.flutter_nfc_kit.inra.card.apdu;

public abstract class AndroidApdu {
    String apduDesc = "";

    public AndroidApdu(String apduDesc) {
        this.apduDesc = apduDesc;
    }

    public String getApduDesc() {
        return apduDesc;
    }
}

