package im.nfc.flutter_nfc_kit.inra.rest.response;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import im.nfc.flutter_nfc_kit.inra.card.models.CardDataFormated;
import im.nfc.flutter_nfc_kit.inra.card.models.Product;

public class CardDataResponse extends GeneralServiceResponse {
    private static final long serialVersionUID = 1L;
    private String cardUID;
    private CardDataFormated cardData;
    private List<Product> productList;

    public CardDataResponse() {
    }

    public String toString() {
        StringBuffer result = new StringBuffer("[CardDataResponse]\r\n");
        result.append("\tcardUID: ").append(this.cardUID).append("\r\n").append("\tresult code: ").append(this.getCode()).append("\r\n").append("\tresult description: ").append(this.getDescription()).append("\r\n");
        if (this.cardData != null) {
            result.append("\tDatos de la tarjeta \r\n");
            result.append(this.cardData.toString());
        }

        if (this.productList != null) {
            result.append("\tDatos de productos cargados \r\n");
            Iterator var3 = this.productList.iterator();

            while(var3.hasNext()) {
                Product product = (Product)var3.next();
                result.append(product.toString()).append("\r\n");
            }
        }

        result.append("\r\n");
        return result.toString();
    }

    public CardDataResponse(Integer code, String description) {
        super(code, description);
    }

    public String getCardUID() {
        return this.cardUID;
    }

    public void setCardUID(String cardUID) {
        this.cardUID = cardUID;
    }

    public CardDataFormated getCardData() {
        return this.cardData;
    }

    public void setCardData(CardDataFormated cardData) {
        this.cardData = cardData;
    }

    public List<Product> getProductList() {
        return this.productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
