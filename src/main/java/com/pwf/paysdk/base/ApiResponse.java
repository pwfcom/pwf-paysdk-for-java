package com.pwf.paysdk.base;

import java.util.Map;

import com.google.gson.Gson;
import com.pwf.paysdk.http.HttpResponse;
import com.pwf.paysdk.utils.StringUtils;


public class ApiResponse {
    
	public final int SUCCESS_CODE = 1000;
	
	private HttpResponse response;
	
	private ApiResponseBean bean;
	
	private String body;

	public ApiResponse () {
		
	}
	
	public ApiResponse (String body)  {
		this.body = body;
		this.bean = readAsJson(body);
	}
	
	public ApiResponse (HttpResponse response) {
		bean = readAsJson(response.getResponseBody());
		this.response = response;
	}

    public ApiResponseBean readAsJson(String responseBody)  {
        return new Gson().fromJson(responseBody, ApiResponseBean.class);
    }
    
    public void setBean(ApiResponseBean bean) {
    	this.bean = bean;
    }
	
	public Boolean isSuccess() {
		
		if(!StringUtils.isEmpty(bean.ret) && bean.ret == SUCCESS_CODE) {
			return true;
		}
		return false;
	}
	
	public String getResponseBody() {
		return body != null ? body : response.getResponseBody();
	}

	public int getRet() {
		return bean.ret;
	}
	
	public String getMsg() {
		return bean.msg;
	}
	
	public String getData() {
		return bean.data;
	}

	public String getLang() {
		return bean.lang;
	}
}
