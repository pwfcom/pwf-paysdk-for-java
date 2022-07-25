package com.pwf.paysdk.api.response;

import com.pwf.paysdk.base.Validation;

public class WalletPayAddressResponse {
    @Validation(required = true)
    public String order_num;

    @Validation(required = true)
    public String out_trade_no;
    
    @Validation(required = true)
    public String fiat_currency;
    
    @Validation(required = true)
    public float fiat_account;
    
    @Validation(required = true)
    public String pay_url;
    
	@Override
	public String toString() {
		return "WalletPayAddressResponse [order_num=" + order_num + ", out_trade_no=" + out_trade_no + ", fiat_currency=" + fiat_currency + ", fiat_account=" + fiat_account + ", pay_url=" + pay_url + "]";
	}
}
