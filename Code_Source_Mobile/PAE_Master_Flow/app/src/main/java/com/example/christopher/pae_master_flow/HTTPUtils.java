package com.example.christopher.pae_master_flow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by GROLAUX on 20/04/2016.
 */
public class HTTPUtils {
    public static class HTTPException extends RuntimeException {
        private int code;

        HTTPException(int code, String message) {
            super(message);
            this.code = code;
        }

        public int getHTTPStatus() {
            return code;
        }
    }

    public static class HTTPNetworkException extends RuntimeException {
        HTTPNetworkException(Throwable parent) {
            super(parent);
        }
    }

    private HTTPUtils() {
    }

    public static String performGetCall(String requestURL) {
        return performGetCall(requestURL, null);
    }

    public static String performGetCall(String requestURL,
                                        Map<String, String> requestParams) {
        HttpURLConnection conn = null;
        try {
            if (requestParams != null) {
                requestURL = requestURL + "?" + getPostDataString(requestParams);
            }
            URL url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            int responseCode = conn.getResponseCode();

            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String read;

            while ((read = br.readLine()) != null) {
                sb.append(read);
            }

            br.close();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                return sb.toString();
            } else {
                throw new HTTPException(responseCode, sb.toString());
            }
        } catch (Exception e) {
            throw new HTTPNetworkException(e);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    public static String performPostCall(String requestURL) {
        return performPostCall(requestURL, null);
    }

    public static String performPostCall(String requestURL,
                                         Map<String, String> requestParams) {

        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(requestURL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            if (requestParams != null) {
                writer.write(getPostDataString(requestParams));
            }
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String read;

            while ((read = br.readLine()) != null) {
                sb.append(read);
            }

            br.close();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                return sb.toString();
            } else {
                throw new HTTPException(responseCode, sb.toString());
            }

        } catch (Exception e) {
            throw new HTTPNetworkException(e);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private static String getPostDataString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
