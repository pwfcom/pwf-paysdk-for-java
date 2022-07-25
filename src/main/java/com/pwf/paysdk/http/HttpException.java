package com.pwf.paysdk.http;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class HttpException  extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public String code;
    public String message;
    public Map<String, Object> data;

    public HttpException() {
    }

    public HttpException(Throwable cause) {
        super("", cause);
        message = cause.getMessage();
    }
    
    public HttpException(String message) {
    	super(message);
	}    
    
    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("unchecked")
	public HttpException(Map<String, ?> map) {
        this.setCode(String.valueOf(map.get("code")));
        this.setMessage(String.valueOf(map.get("message")));
        Object obj = map.get("data");
        if (obj == null) {
            return;
        }
        if (obj instanceof Map) {
            data = (Map<String, Object>) obj;
            return;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                hashMap.put(field.getName(), field.get(obj));
            } catch (Exception e) {
                continue;
            }
        }
        this.data = hashMap;
    }

	@Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
