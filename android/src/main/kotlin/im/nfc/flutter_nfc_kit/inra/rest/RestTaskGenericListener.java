package im.nfc.flutter_nfc_kit.inra.rest;

/**
 * Define a generic callback/listener for events from asynctask that call restful services
 */
public interface RestTaskGenericListener {
    void onProcessResponse(String task_id, RestResponse response);
}
