package com.pwf.paysdk.api;

import com.pwf.paysdk.base.Kernel;
import com.pwf.paysdk.api.response.NotifyPayResponse;
import com.pwf.paysdk.api.response.NotifyRechargeResponse;


public class Notify {

    public Kernel _kernel;
    public Notify(Kernel kernel) {
        this._kernel = kernel;
    }

  //訂單異步回調通知
	public NotifyPayResponse pay(String jsonString) throws Exception {
    	try {
            return _kernel.getResponseData(jsonString, NotifyPayResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	//無訂單充值結果通知
	public NotifyRechargeResponse rechage(String jsonString) throws Exception {
    	try {
            return _kernel.getResponseData(jsonString, NotifyRechargeResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
