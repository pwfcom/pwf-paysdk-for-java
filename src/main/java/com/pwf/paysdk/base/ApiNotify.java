package com.pwf.paysdk.base;

import java.util.Map;

import com.google.gson.Gson;
import com.pwf.paysdk.http.HttpResponse;
import com.pwf.paysdk.utils.StringUtils;


public class ApiNotify {
    
	public final int SUCCESS_CODE = 1000;
	
	private String data;
	
	private ApiResponseBean bean;

    public Kernel _kernel;
    
    public ApiNotify(Kernel kernel) throws Exception {
        this._kernel = kernel;
    }

	public Boolean verify(String data) {
		

		return false;
	}
  
	public Boolean isSuccess() {
		
		if(!StringUtils.isEmpty(bean.ret) && bean.ret == SUCCESS_CODE) {
			return true;
		}
		return false;
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

}
