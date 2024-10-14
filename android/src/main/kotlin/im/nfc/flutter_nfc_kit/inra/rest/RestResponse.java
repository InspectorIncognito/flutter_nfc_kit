package im.nfc.flutter_nfc_kit.inra.rest;

import java.io.Serializable;
import java.net.HttpURLConnection;

public class RestResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Exception error;
    protected Integer code;
    protected String data;
    protected String post_id;

    public RestResponse(String post_id, int response_code,
                        String response_data) {
        this.post_id = post_id;
        this.code = response_code;
        this.data = response_data;
    }

    public RestResponse(Exception e) {
        this.error = e;
        this.code = HttpURLConnection.HTTP_BAD_REQUEST;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getPostId() {
        return post_id;
    }

    public void setPostId(String post_id) {
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RestResponse{");
        sb.append("error=").append(error);
        sb.append(", code=").append(code);
        sb.append(", data='").append(data).append('\'');
        sb.append(", post_id='").append(post_id).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public boolean hasError() {
        if (error == null)
            return false;
        else
            return true;
    }

}
