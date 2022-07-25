package com.pwf.paysdk.base;

import java.util.Map;
import com.pwf.paysdk.api.Wallet;
import com.pwf.paysdk.api.Notify;

public class ApiClient {

	public static Kernel context;

    public static void setOptions(Config options) {
    	
        try {
        	context = new Kernel(options);
        	
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    
    public static Wallet wallet() {
    	return new Wallet(context);
    }
	
    public static Notify callback() {
    	return new Notify(context);
    }

}
