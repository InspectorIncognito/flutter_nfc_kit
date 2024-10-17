package im.nfc.flutter_nfc_kit.inra.utils

import im.nfc.flutter_nfc_kit.inra.card.models.CardDataFormated
import im.nfc.flutter_nfc_kit.inra.card.models.ChargeData
import im.nfc.flutter_nfc_kit.inra.card.models.ValidationData
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat

class CardDataJsonConverter {

    fun toJson(data: CardDataFormated): JSONObject {
        return JSONObject(mapOf(
            "serialNumber" to data.generalData.cardSerialNumber,
            "balance" to data.generalData.purse,
            "chargeEventData" to chargeDataToJson(data.chargeDataList),
            "validationEventData" to validationDataToJson(data.validationDataList),
        ))
    }

    private fun chargeDataToJson(data: List<ChargeData>): JSONArray {
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        val mappedData = data.map {
            JSONObject(mapOf(
                "description" to it.loadContractDescription,
                "date" to format.format(it.date),
                "balance" to it.balance,
            ))
        }
        return JSONArray(mappedData)
    }

    private fun validationDataToJson(data: List<ValidationData>): JSONArray {
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        val mappedData = data.map {
            JSONObject(mapOf(
                "date" to format.format(it.date),
                "balance" to it.balance,
            ))
        }
        return JSONArray(mappedData)
    }
}