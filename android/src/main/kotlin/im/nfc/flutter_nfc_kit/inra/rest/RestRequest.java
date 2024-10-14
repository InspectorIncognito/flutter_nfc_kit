package im.nfc.flutter_nfc_kit.inra.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class RestRequest {
    private String url;
    private String id;
    private String params;
    private String content_type;
    public RestRequest(String id, String url) {
        this.url = url;
        this.id = id;
    }

    public String getContentType() {
        return content_type;
    }

    public void setContentType(String content_type) {
        this.content_type = content_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "RestRequest{" +
                "url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", params='" + params + '\'' +
                '}';
    }

    public static class CONTENT_TYPE {
        public static String JSON = "application/json";
    }
}
