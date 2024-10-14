package im.nfc.flutter_nfc_kit.inra.rest;

import java.io.Serializable;

public class RequestErrorMsg implements Serializable {
    private Exception error;
    private String description;
    private Integer commResponse;

    public RequestErrorMsg(RestResponse restWsResponse) {
        error = restWsResponse.getError();
        description = restWsResponse.getData();
        commResponse = restWsResponse.getCode();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public Integer getCommResponse() {
        return commResponse;
    }

    public void setCommResponse(Integer commResponse) {
        this.commResponse = commResponse;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommErrorBroadcastMsg{");
        sb.append("error=").append(error);
        sb.append(", description='").append(description).append('\'');
        sb.append(", commResponse=").append(commResponse);
        sb.append('}');
        return sb.toString();
    }
}