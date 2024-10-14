package im.nfc.flutter_nfc_kit.inra.card

import android.nfc.TagLostException
import android.nfc.tech.MifareClassic
import im.nfc.flutter_nfc_kit.inra.card.apdu.APDUResponse
import im.nfc.flutter_nfc_kit.inra.card.apdu.AndroidRawApdu
import im.nfc.flutter_nfc_kit.inra.rest.request.CardCommandRequest
import im.nfc.flutter_nfc_kit.inra.rest.response.CardCommandResponse
import im.nfc.flutter_nfc_kit.inra.utils.Hex
import java.util.ArrayList

class CardCommandExecutioner {
    private var forceFailReached = false
    private var numAttempt = 0

    fun executeCardCommandsAndroidV2(mifareClassic: MifareClassic,
                                     commands: List<CardCommandRequest>?): List<CardCommandResponse> {
        val responses = ArrayList<CardCommandResponse>()

        // if no exist commands return empty responses
        if (commands == null)
            return responses

        var index = 0
        var lastException: Exception?
        try {
            val mifareClassicReader = MifareClassicFixReader(mifareClassic.tag)
            mifareClassicReader.connectCard()
            forceFailReached = false // reset

            // execute list of commands
            index = 0
            while (index < commands.size) {
                lastException = null
                val itCommand = commands[index]

                val androidAPDUCommand = AndroidMifareClassicAPDUTraductor.getInstance().translateToAndroidApdu(itCommand)
                val apduResponse: APDUResponse

                // if command can be translated
                if (androidAPDUCommand != null) {
                    try {
                        apduResponse = mifareClassicReader.executeCommandOrThrow(androidAPDUCommand)
                        responses.add(createCardCommandResponse(apduResponse, itCommand))
                    } catch (e: Exception) {
                        lastException = e
                    }

                    // check if exist an error executing the command
                    if (lastException != null) {
                        // if tag was lost, we cant continue, so add error 0xFF 0xFE
                        if (lastException is TagLostException) {
                            //                            showProgress(false, null);
                            //showErrorTagWasLost((TagLostException) lastException);
                            while (index < commands.size) {
                                val apduTagLost = CardCommandResponse()
                                apduTagLost.command = Hex.encodeHex(APDUResponse.getAPDUResponseFFFE().getBytes())
                                apduTagLost.id = commands[index].id
                                responses.add(apduTagLost)
                                index++
                            }
                            // we dont continue
                            break
                        } else {
                            //showErrorTransceivingCardCommand((IOException) lastException);
                            if (!itCommand.isIgnoreFailure) {
                                while (index < commands.size) {
                                    val apduIOException = CardCommandResponse()
                                    apduIOException.command = Hex.encodeHex(APDUResponse.getAPDUResponseFFFF().getBytes())
                                    apduIOException.id = commands[index].id
                                    responses.add(apduIOException)
                                    index++
                                }
                                // we dont continue
                                break
                            } else {
                                val apduIOException = CardCommandResponse()
                                apduIOException.command = Hex.encodeHex(APDUResponse.getAPDUResponseFFFF().getBytes())
                                apduIOException.id = itCommand.id
                                responses.add(apduIOException)
                                // we continue with next command
                                index++
                                continue
                            }
                        }// exist other type of error (not tag was lost) and check if we have to continue
                    }

                } else {
                    //logger.logError("Last command couldn't be translated");
                    val responseAPDU = APDUResponse.getAPDUResponse9000()//AndroidMifareClassicAPDUTraductor.getInstance().translateToAPDU(null);
                    val cardAPDUResponse = CardCommandResponse()
                    cardAPDUResponse.id = itCommand.id
                    cardAPDUResponse.command = Hex.encodeHex(responseAPDU.getBytes())
                    responses.add(index, cardAPDUResponse)
                }// @TODO if command cannot be translated
                // load command
                index++
            }

            mifareClassicReader.closeCardConnection()

        } catch (e: Exception) {
            // complete with rest of commands with null default value
            val rawApdu: AndroidRawApdu? = null
            while (index < commands.size) {
                responses.add(createCardCommandResponse(rawApdu, commands[index]))
                index++
            }
        }

        return responses
    }

    private fun createCardCommandResponse(apduResponse: APDUResponse, commandRequest: CardCommandRequest): CardCommandResponse {
        val cardAPDUResponse = CardCommandResponse()
        cardAPDUResponse.id = commandRequest.id
        cardAPDUResponse.command = Hex.encodeHex(apduResponse.bytes)
        return cardAPDUResponse
    }

    private fun createCardCommandResponse(androidAPDUresponse: AndroidRawApdu?, commandRequest: CardCommandRequest): CardCommandResponse {
        val responseAPDU = AndroidMifareClassicAPDUTraductor.getInstance().translateToAPDU(androidAPDUresponse)
        val cardAPDUResponse = CardCommandResponse()
        cardAPDUResponse.id = commandRequest.id
        cardAPDUResponse.command = Hex.encodeHex(responseAPDU.bytes)
        return cardAPDUResponse
    }
}