package com.pwf.paysdk.http;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String s) {
        System.out.println(s);
    }
}
