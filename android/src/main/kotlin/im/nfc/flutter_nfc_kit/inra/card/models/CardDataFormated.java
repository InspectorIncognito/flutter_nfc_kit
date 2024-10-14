package im.nfc.flutter_nfc_kit.inra.card.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class CardDataFormated implements Serializable {
    private static final long serialVersionUID = -3937462166088044722L;
    private GeneralData generalData;
    private List<ContractData> contractDataList = new ArrayList();
    private List<ValidationData> validationDataList = new ArrayList();
    private List<ChargeData> chargeDataList = new ArrayList();

    public CardDataFormated() {
    }

    public String toString() {
        StringBuffer result = new StringBuffer("\t [CardData]========== \r\n");
        result.append("\t\t  General Data\r\n");
        if (this.generalData != null) {
            result.append(this.generalData.toString());
        }

        result.append("\t\t  Contract Data\r\n");
        int i = 0;
        Iterator var4;
        if (this.contractDataList != null && !this.contractDataList.isEmpty()) {
            for(var4 = this.contractDataList.iterator(); var4.hasNext(); ++i) {
                ContractData data = (ContractData)var4.next();
                if (i != 0) {
                    result.append("\r\n");
                }

                result.append(data);
            }
        }

        result.append("\t  Validation Data\r\n");
        if (this.validationDataList != null && !this.validationDataList.isEmpty()) {
            for(var4 = this.validationDataList.iterator(); var4.hasNext(); ++i) {
                ValidationData data = (ValidationData)var4.next();
                if (i != 0) {
                    result.append("\r\n");
                }

                result.append(data);
            }
        }

        result.append("\t  Charge Data\r\n");
        if (this.chargeDataList != null && !this.chargeDataList.isEmpty()) {
            for(var4 = this.chargeDataList.iterator(); var4.hasNext(); ++i) {
                ChargeData data = (ChargeData)var4.next();
                if (i != 0) {
                    result.append("\r\n");
                }

                result.append(data);
            }
        }

        return result.toString();
    }

    public GeneralData getGeneralData() {
        return this.generalData;
    }

    public void setGeneralData(GeneralData generalData) {
        this.generalData = generalData;
    }

    public List<ContractData> getContractDataList() {
        return this.contractDataList;
    }

    public void setContractDataList(List<ContractData> contractDataList) {
        this.contractDataList = contractDataList;
    }

    public List<CardEvent> getEventsOrderedByDate() {
        Map<Long, CardEvent> events = new TreeMap();
        Iterator var3 = this.validationDataList.iterator();

        while(var3.hasNext()) {
            ValidationData validation = (ValidationData)var3.next();
            events.put(validation.getDate().getTime(), validation);
        }

        var3 = this.chargeDataList.iterator();

        while(var3.hasNext()) {
            ChargeData charge = (ChargeData)var3.next();
            events.put(charge.getDate().getTime(), charge);
        }

        return new ArrayList(events.values());
    }

    public List<ValidationData> getValidationDataList() {
        return this.validationDataList;
    }

    public void setValidationDataList(List<ValidationData> validationDataList) {
        this.validationDataList = validationDataList;
    }

    public List<ChargeData> getChargeDataList() {
        return this.chargeDataList;
    }

    public void setChargeDataList(List<ChargeData> chargeDataList) {
        this.chargeDataList = chargeDataList;
    }
}
