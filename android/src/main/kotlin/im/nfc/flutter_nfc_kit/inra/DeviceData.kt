package im.nfc.flutter_nfc_kit.inra

class DeviceData(val type: String, val osVersion: String, val model: String,
                 val nfcDevice: String, val deviceId: String, val appVersion: String) {

    companion object {
        private const val serialVersionUID = 1L
    }

    override fun toString(): String {
        val result = StringBuffer("\r\n-- Device Data").append("\r\n")
        result.append("  Type: ").append(type).append("\r\n").append("  Model: ").append(model).append("\r\n").append("  OS Version: ").append(osVersion).append("\r\n").append("  NFC Device: ").append(nfcDevice).append("\r\n").append("  Device ID: ").append(deviceId).append("\r\n").append("  App version: ").append(appVersion).append("\r\n")
        return result.toString()
    }

    fun toStringFormatted(): String {
        val result = StringBuffer()
        result.append("          [Type]: ").append(type).append("\r\n").append("          [Model]: ").append(model).append("\r\n").append("          [OS version]: ").append(osVersion).append("\r\n").append("          [NFC device]: ").append(nfcDevice).append("\r\n").append("          [Device ID]: ").append(deviceId).append("\r\n").append("          [App version]: ").append(appVersion).append("\r\n")
        return result.toString()
    }
}