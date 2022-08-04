package com.pwf.paysdk.base;


public class ApiResponseBean {
	
	@NameInMap("ret")
    @Validation(required = true)
    public int ret;

	@NameInMap("msg")
    @Validation(required = true)
    public String msg;
	
	@NameInMap("data")
    @Validation(required = true)
    public String data;
	
	@NameInMap("lang")
    @Validation(required = true)
    public String lang;
}
