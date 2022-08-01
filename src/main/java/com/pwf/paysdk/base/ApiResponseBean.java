package com.pwf.paysdk.base;


public class ApiResponseBean {
    @Validation(required = true)
    public int ret;

    @Validation(required = true)
    public String msg;
	
    @Validation(required = true)
    public String data;
    
	@NameInMap("lang")
    @Validation(required = true)
    public String lang;
}
