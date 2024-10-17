package im.nfc.flutter_nfc_kit.inra;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;

import im.nfc.flutter_nfc_kit.inra.gson.GsonHelper;
import im.nfc.flutter_nfc_kit.inra.rest.RequestErrorMsg;
import im.nfc.flutter_nfc_kit.inra.rest.RequestRunner;
import im.nfc.flutter_nfc_kit.inra.rest.RestRequest;
import im.nfc.flutter_nfc_kit.inra.rest.RestRequestTask;
import im.nfc.flutter_nfc_kit.inra.rest.RestResponse;
import im.nfc.flutter_nfc_kit.inra.rest.RestTaskGenericListener;
import im.nfc.flutter_nfc_kit.inra.rest.request.CardDataRequest;
import im.nfc.flutter_nfc_kit.inra.rest.request.CardLoadCommandRequest;
import im.nfc.flutter_nfc_kit.inra.rest.request.CardReadCommandRequest;
import im.nfc.flutter_nfc_kit.inra.rest.response.CardCommandResponse;
import im.nfc.flutter_nfc_kit.inra.rest.response.CardDataResponse;
import im.nfc.flutter_nfc_kit.inra.rest.response.CardLoadCommandResponse;
import im.nfc.flutter_nfc_kit.inra.rest.response.CardReadCommandResponse;

import im.nfc.flutter_nfc_kit.inra.utils.Hex;

public class RestMetroClientHelper {
    public interface ReadingCompletionListener {
        void onReadingResponse(CardReadCommandResponse response);
        void onReadingError(RequestErrorMsg response);
    }

    public interface GetDataCompletionListener {
        void onGetCardResponse(CardDataResponse response);
        void onReadingError(RequestErrorMsg response);
    }

    public interface LoadCompletionListener {
        void onLoadResponse(CardLoadCommandResponse wsResponse);
        void onReadingError(RequestErrorMsg response);
    }

    private static int defaultTimeout;
    private static String ROOT_PATH;
    private static String language = Locale.getDefault().toString();
    private static final RestMetroClientHelper instance = new RestMetroClientHelper();


    public static RestMetroClientHelper getInstance() {
        return instance;
    }

    public static int getTimeout() {
        return defaultTimeout;
    }

    public static void setSSRServerPath(String SSRServerPath) {
        ROOT_PATH = SSRServerPath;
    }

    public void getReadingCommands(byte[] card_id, DeviceData device_data, ReadingCompletionListener listener,
                                   String transactionId, List<CardCommandResponse> results) {
        Log.d("FlutterNfcKitPlugin", "getReadingCommands");
        String card = null;
        if (card_id != null)
            card = Hex.encodeHex(card_id);
        RestRequest request = createReadingCommandsRequest(transactionId, card, transactionId, results, device_data);
        postRequest(request, (task_id, response) -> {
            if (response != null && response.getCode() == HttpURLConnection.HTTP_OK) {
                CardReadCommandResponse ws_response = GsonHelper.customGson.fromJson(response.getData(), CardReadCommandResponse.class);
                listener.onReadingResponse(ws_response);
            } else {
                listener.onReadingError(new RequestErrorMsg(response));
            }
        });
    }

    public void getCardData(byte[] card_id, DeviceData device_data, GetDataCompletionListener listener) {
        Log.d("FlutterNfcKitPlugin", "getCardData");
        String card = null;
        if (card_id != null)
            card = Hex.encodeHex(card_id);
        RestRequest request = createGetDataRequest("", card, device_data);
        postRequest(request, (task_id, response) -> {
            if (response != null && response.getCode() == HttpURLConnection.HTTP_OK) {
                CardDataResponse ws_response = GsonHelper.customGson.fromJson(response.getData(), CardDataResponse.class);
                listener.onGetCardResponse(ws_response);
            } else {
                listener.onReadingError(new RequestErrorMsg(response));
            }
        });
    }

    public void getLoadCommands(byte[] card_id, DeviceData device_data, LoadCompletionListener listener,
                                String transactionId, List<CardCommandResponse> results) {
        Log.d("FlutterNfcKitPlugin", "getLoadCommands");
        String card = null;
        if (card_id != null)
            card = Hex.encodeHex(card_id);
        RestRequest request = createLoadCommandsRequest(transactionId, card, transactionId, results, device_data);
        postRequest(request, (task_id, response) -> {
            if (response != null && response.getCode() == HttpURLConnection.HTTP_OK) {
                CardLoadCommandResponse ws_response = GsonHelper.customGson.fromJson(response.getData(), CardLoadCommandResponse.class);
                listener.onLoadResponse(ws_response);
            } else {
                listener.onReadingError(new RequestErrorMsg(response));
            }
        });
    }

    private RestRequest createLoadCommandsRequest(String request_id, String card_id, String transactionId, List<CardCommandResponse> results, DeviceData device_data) {
        CardLoadCommandRequest request = new CardLoadCommandRequest();
        request.setCardUID(card_id);
        request.setLanguage(language);
        request.setTransactionId(transactionId);
        request.setDeviceData(device_data);
        request.setOperationList(results);
        String json = GsonHelper.customGson.toJson(request);

        RestRequest restRequest = new RestRequest(request_id, getURLFormated(SERVER_URL.GET_LOAD_COMMANDS));
        restRequest.setContentType(RestRequest.CONTENT_TYPE.JSON);
        restRequest.setParams(json);
        return restRequest;
    }

    private RestRequest createGetDataRequest(String request_id, String card_id, DeviceData device_data) {
        CardDataRequest request = new CardDataRequest();
        request.setCardUID(card_id);
        request.setLanguage(language);
        request.setDeviceData(device_data);
        String json = GsonHelper.customGson.toJson(request);

        RestRequest restRequest = new RestRequest(request_id, getURLFormated(SERVER_URL.GET_CARD_DATA));
        restRequest.setContentType(RestRequest.CONTENT_TYPE.JSON);
        restRequest.setParams(json);
        return restRequest;
    }

    private RestRequest createReadingCommandsRequest(String request_id, String card_id, String transactionId, List<CardCommandResponse> results, DeviceData device_data) {
        CardReadCommandRequest request = new CardReadCommandRequest();
        request.setCardUID(card_id);
        request.setLanguage(language);
        request.setTransactionId(transactionId);
        request.setDeviceData(device_data);
        request.setOperationList(results);
        String json = GsonHelper.customGson.toJson(request);

        RestRequest restRequest = new RestRequest(request_id, getURLFormated(SERVER_URL.GET_READING_COMMANDS));
        restRequest.setContentType(RestRequest.CONTENT_TYPE.JSON);
        restRequest.setParams(json);
        return restRequest;
    }

    private void postRequest(RestRequest request, RestTaskGenericListener listener) {

        defaultTimeout = 1000 * 65; // 65sec

        RequestRunner runner = new RequestRunner();

        runner.executeAsync(new RestRequestTask(request), (data) -> {
            listener.onProcessResponse("", data);
        });
    }

    private String getURLFormated(String path) {

        if (ROOT_PATH == null)
            ROOT_PATH = SERVER_URL.PRO_PATH;

        return ROOT_PATH + path;
    }

    public static class SERVER_URL {
        public static final String GET_READING_COMMANDS = "/CardCommands/getCardReadingCommands";
        public static final String GET_LOAD_COMMANDS = "/CardCommands/getCardLoadCommands";
        public static final String GET_CARD_DATA = "/CardCommands/getCardData";
        public static final String VALIDATE_APP_VERSION = "/ValidateApp/validateAppVersion";
        public static final String PRO_PATH = "https://ssrnfcmetro.metro.cl/ssr/rest";
        public static final String TEST_PATH = "https://ssrnfcmetrotesting.metro.cl/ssr/rest";
    }

}
