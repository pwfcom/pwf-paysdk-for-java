package com.pwf.paysdk.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class HttpRequest {

    public final static String URL_ENCODING = "UTF-8";

    public String host;

    public Integer port;

    public String method;

    public String pathname;

    public Map<String, String> query;

    public Map<String, String> headers;

    public Map<String, String> posts;
 

    public HttpRequest() {
        query = new HashMap<String, String>();
        posts = new HashMap<String, String>();
        headers = new HashMap<String, String>();
    }

    public static HttpRequest create() {
        return new HttpRequest();
    }
    
    
    public String getApiUrl() {
  
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(host);
        if (null != pathname) {
            urlBuilder.append(pathname);
        }
        
        return urlBuilder.toString();
    }
    
}
