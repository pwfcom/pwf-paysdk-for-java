package com.pwf.paysdk.api;

import java.util.Map;

import com.pwf.paysdk.base.Kernel;
import com.pwf.paysdk.api.response.WalletOrderStatusResponse;
import com.pwf.paysdk.api.response.WalletPayAddressResponse;
import com.pwf.paysdk.api.response.WalletRechargeResponse;
import com.pwf.paysdk.http.HttpRequest;
import com.pwf.paysdk.http.HttpResponse;
public class Wallet {

    public Kernel _kernel;
    public Wallet(Kernel kernel) {
        this._kernel = kernel;
    }

  //支付請求接口
	public WalletPayAddressResponse payAddress(Map<String, Object> params) throws Exception {
    	try {

    		HttpRequest request = new HttpRequest();
    		request.host = _kernel.getConfig("apiUrl");
            request.method = "POST";
            request.pathname = "/api/v2/wallet/payAddress";
            
            request.posts = _kernel.buildPostParams(params);
    
            HttpResponse response =  _kernel.execute(request);

            return _kernel.getResponseData(response.getResponseBody(), WalletPayAddressResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	//訂單狀態查詢接口
	public WalletOrderStatusResponse orderStatus(Map<String, Object> params) throws Exception {
    	try {

    		HttpRequest request = new HttpRequest();
    		request.host = _kernel.getConfig("apiUrl");
            request.method = "POST";
            request.pathname = "/api/v2/wallet/payAddress";
            
            request.posts = _kernel.buildPostParams(params);
    
            HttpResponse response =  _kernel.execute(request);

            return _kernel.getResponseData(response.getResponseBody(), WalletOrderStatusResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
    //無訂單充值請求接口
	public WalletRechargeResponse recharge(Map<String, Object> params) throws Exception {
    	try {

    		HttpRequest request = new HttpRequest();
    		request.host = _kernel.getConfig("apiUrl");
            request.method = "POST";
            request.pathname = "/api/v2/wallet/payAddress";
            
            request.posts = _kernel.buildPostParams(params);
    
            HttpResponse response =  _kernel.execute(request);

            return _kernel.getResponseData(response.getResponseBody(), WalletRechargeResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
