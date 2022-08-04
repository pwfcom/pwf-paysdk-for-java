package com.pwf.paysdk.base;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pwf.paysdk.utils.StringUtils;


public class ApiResponse {
    
	public final int SUCCESS_CODE = 1000;
	
	private ApiResponseBean bean;
	
	private String body;

    public PwfClient _kernel;

	private Map<String, String> dataMap;
	
	public ApiResponse (PwfClient kernel) {
		this._kernel = kernel;
	}
	
	public void setRequestBody (String body)  {
		this.body = body;
		this.bean = readAsJson(body);
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
	
	public Boolean Verify()
    {
		try {
			String decryptData = _kernel.decryptResponseData(getData());
			Map<String, String> decryptDataMap = new Gson().fromJson(decryptData, new TypeToken<Map<String, String>>() {}.getType());

		
			if (decryptDataMap != null && _kernel.verify(decryptDataMap))
			{
				this.dataMap = decryptDataMap;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}
}
