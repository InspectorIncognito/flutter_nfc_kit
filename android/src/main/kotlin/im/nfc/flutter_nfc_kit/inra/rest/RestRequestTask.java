package im.nfc.flutter_nfc_kit.inra.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class RestRequestTask implements Callable<RestResponse> {
    private final RestRequest task;

    public RestRequestTask(RestRequest task) {
        this.task = task;
    }

    @Override
    public RestResponse call() {
        RestRequest request = task;
        URL url = null;
        try {
            url = new URL(request.getUrl());
        } catch (MalformedURLException e) {
            return new RestResponse(e);
        }

        String input_data = "";
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        HttpURLConnection conn = null;
        int response_code = HttpURLConnection.HTTP_BAD_REQUEST;
        Exception error = null;
        try {
            conn = (HttpURLConnection) url.openConnection();

            // POST type => doOutput
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Content-Type
            if (request.getContentType() != null)
                conn.setRequestProperty("Content-Type", request.getContentType());

            // post
            String requestParams = request.getParams();
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(requestParams);
            wr.flush();

            response_code = conn.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_MOVED_TEMP) {
                String new_location = conn.getHeaderField("Location");
            }

            // Recogemos la respuesta
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                // Process line...
                input_data += line;
            }

            // no save server information, only response for security

        } catch (Exception e) {
            input_data = e.toString();
            error = e;
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
                if (rd != null) {
                    rd.close();
                }
            } catch (IOException ex) {
                error = ex;
            }
        }

        // return error o received data
        if (error != null) {
            return new RestResponse(error);
        }
        else {
            return new RestResponse("", response_code, input_data);
        }
    }
}
