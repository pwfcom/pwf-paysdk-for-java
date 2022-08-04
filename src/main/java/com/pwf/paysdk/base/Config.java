package com.pwf.paysdk.base;

public class Config {
	
	@Validation(required = true)
    public String apiUrl;
	
	@Validation(required = true)
    public String appToken;
	
	@Validation(required = true)
    public String merchantNo;
	
	@Validation(required = true)
    public String merchantPrivateCertPath;
	
	@Validation(required = true)
    public String pwfPublicCertPath;
	
	@Validation(required = true)
    public String notifyUrl;	
	
	@Validation(required = true)
    public String lang;	
}
