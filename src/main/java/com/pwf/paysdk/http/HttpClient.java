package com.pwf.paysdk.http;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.Map.Entry;

import com.pwf.paysdk.utils.MapConverter;
import com.pwf.paysdk.utils.StringUtils;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClient {
	
	private static OkHttpClient mClient;
	
    public static OkHttpClient getOkHttpClient(){
        if(mClient==null) {
        	
        	Interceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY);
            mClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(logInterceptor)
                    .build();
        }
        return mClient;
    }


    public static HttpResponse execute(HttpRequest request) {
        try {
            OkHttpClient okHttpClient = getOkHttpClient();
            
        	String urlString = request.getApiUrl();
        	URL url = new URL(urlString);
        	
        	Request.Builder builder = new Request.Builder();
        	
        	builder.url(url);
        	
            for (String headerName : request.headers.keySet()) {
            	builder.header(headerName, request.headers.get(headerName));
            }
        	
        	String jsonString = MapConverter.toJsonString(request.posts);
        	MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        	RequestBody body = RequestBody.create(JSON, jsonString);
        	
            String method = request.method.toUpperCase();
            
            switch (method) {
                case "POST":
                	builder.post(body);
                    break;
                default:
                    builder.get();
                    break;
            }
            Request requestBuilder = builder.build();
            Response response = okHttpClient.newCall(requestBuilder).execute();
            

            return new HttpResponse(response);
        } catch (Exception e) {
            throw new HttpException(e);
        }
    }
}
