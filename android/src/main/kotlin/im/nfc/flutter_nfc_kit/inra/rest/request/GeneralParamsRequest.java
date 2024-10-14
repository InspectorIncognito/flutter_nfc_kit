package im.nfc.flutter_nfc_kit.inra.rest.request;

import java.io.Serializable;

import im.nfc.flutter_nfc_kit.inra.DeviceData;

public class GeneralParamsRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String language;
    private DeviceData deviceData;

    public GeneralParamsRequest() {
    }

    public GeneralParamsRequest(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public DeviceData getDeviceData() {
        return this.deviceData;
    }

    public void setDeviceData(DeviceData deviceData) {
        this.deviceData = deviceData;
    }
}
