package im.nfc.flutter_nfc_kit.inra.rest.response;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.nfc.flutter_nfc_kit.inra.card.models.BipWallet;
import im.nfc.flutter_nfc_kit.inra.card.models.GenericOperationInfo;
import im.nfc.flutter_nfc_kit.inra.card.models.MandateProduct;
import im.nfc.flutter_nfc_kit.inra.card.models.RAProduct;
import im.nfc.flutter_nfc_kit.inra.rest.request.CardCommandRequest;

public class CardLoadCommandResponse extends GeneralServiceResponse {
    private static final long serialVersionUID = 1L;
    private String cardUID;
    private String transactionId;
    private List<CardCommandRequest> commands;
    private List<RAProduct> raproducts;
    private List<MandateProduct> mandateProducts;
    private BipWallet wallet;
    private List<GenericOperationInfo> extraInfo;

    public CardLoadCommandResponse() {
    }

    public CardLoadCommandResponse(Integer code, String description) {
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

    public List<RAProduct> getRAproducts() {
        return this.raproducts;
    }

    public void setRAproducts(List<RAProduct> rAproducts) {
        this.raproducts = rAproducts;
    }

    public List<MandateProduct> getMandateProducts() {
        return this.mandateProducts;
    }

    public void setMandateProducts(List<MandateProduct> mandateProducts) {
        this.mandateProducts = mandateProducts;
    }

    public BipWallet getWallet() {
        return this.wallet;
    }

    public void setWallet(BipWallet wallet) {
        this.wallet = wallet;
    }

    public List<GenericOperationInfo> getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(List<GenericOperationInfo> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String toString() {
        StringBuffer result = new StringBuffer("[CardLoadCommandResponse]\r\n");
        result.append("\tcardUID: ").append(this.cardUID).append("\r\n").append("\ttransactionId: ").append(this.transactionId).append("\r\n").append("\tresult code: ").append(this.getCode()).append("\r\n").append("\tresult description: ").append(this.getDescription()).append("\r\n").append("\tcommand to execute list: ").append("\r\n");
        Iterator var3;
        if (this.commands != null) {
            var3 = this.commands.iterator();

            while(var3.hasNext()) {
                CardCommandRequest command = (CardCommandRequest)var3.next();
                result.append(command).append("\r\n");
            }
        }

        if (this.raproducts != null) {
            var3 = this.raproducts.iterator();

            while(var3.hasNext()) {
                RAProduct product = (RAProduct)var3.next();
                result.append("\r\n    ").append(product.toString()).append("\r\n");
            }
        }

        if (this.mandateProducts != null) {
            var3 = this.mandateProducts.iterator();

            while(var3.hasNext()) {
                MandateProduct product = (MandateProduct)var3.next();
                result.append("\r\n    ").append(product.toString()).append("\r\n");
            }
        }

        if (this.wallet != null) {
            result.append("\r\n    ").append(this.wallet.toString()).append("\r\n");
        }

        result.append("\r\n");
        return result.toString();
    }

    public Integer getBipNumber() {
        Integer bipNumber = null;

        try {
            if (this.getDescription() != null) {
                String desc = this.getDescription();
                String wordToFind = "Nº bip! ";
                Pattern word = Pattern.compile(wordToFind);
                Matcher match = word.matcher(desc);
                if (match.find()) {
                    String regex = "(\\d+)";
                    String subString = desc.substring(match.end() - 1, desc.length());
                    Matcher matcherBipNumber = Pattern.compile(regex).matcher(subString);
                    if (matcherBipNumber.find()) {
                        bipNumber = Integer.valueOf(matcherBipNumber.group());
                    }
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return bipNumber;
    }
}
