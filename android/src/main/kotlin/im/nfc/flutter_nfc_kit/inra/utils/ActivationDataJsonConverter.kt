package im.nfc.flutter_nfc_kit.inra.utils

import im.nfc.flutter_nfc_kit.inra.rest.response.CardLoadCommandResponse
import org.json.JSONArray
import org.json.JSONObject

class ActivationDataJsonConverter {

    fun toJson(data: CardLoadCommandResponse): JSONObject {
        val cardData = data.description.split("bip!")
        var id = ""
        if (cardData.size == 2) {
            id = cardData[1]
        }

        val products = data.rAproducts.map {
            JSONObject(mapOf(
                "balance" to it.balance,
                "description" to it.description,
            ))
        }.plus(data.mandateProducts.filter { it.balance != null }.map {
            JSONObject(mapOf(
                "balance" to it.balance,
                "description" to it.description,
            ))
        })

        return JSONObject(mapOf(
            "wallet" to data.wallet.value,
            "cardId" to id,
            "products" to JSONArray(products),
        ))
    }
}