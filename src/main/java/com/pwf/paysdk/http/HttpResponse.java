package com.pwf.paysdk.http;

import okhttp3.Headers;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwf.paysdk.utils.StringUtils;

public class HttpResponse {

    public Response response;
    public int statusCode;
    public String statusMessage;
    public HashMap<String, String> headers;
    public InputStream body;

    public HttpResponse() {
        headers = new HashMap<String, String>();
    }

    public HttpResponse(Response response) {
        headers = new HashMap<String, String>();
        this.response = response;
        statusCode = response.code();
        statusMessage = response.message();
        body = response.body().byteStream();
        Headers headers = response.headers();
        Map<String, List<String>> resultHeaders = headers.toMultimap();
        for (Map.Entry<String, List<String>> entry : resultHeaders.entrySet()) {
            this.headers.put(entry.getKey(), StringUtils.join(";", entry.getValue()));
        }
    }

    public InputStream getResponse() {
        return this.body;
    }

    public String getResponseBody() {
        if (null == body) {
        	throw new HttpException(statusMessage);
        }
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        try {
            while (true) {
                final int read = body.read(buff);
                if (read == -1) {
                    break;
                }
                os.write(buff, 0, read);
            }
        } catch (Exception e) {
            throw new HttpException(e.getMessage(), e);
        }
        return new String(os.toByteArray());
    }


}