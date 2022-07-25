package com.pwf.paysdk.api.response;

import com.pwf.paysdk.base.Validation;

public class NotifyRechargeResponse {

    
    @Validation(required = true)
    public String order_num;

    @Validation(required = true)
    public String out_trade_no;
 
    @Validation(required = true)
    public int status;

    @Validation(required = true)
    public int pay_at;
    
    @Validation(required = true)
    public String fiat_currency;
    
    @Validation(required = true)
    public float fiat_account;
 
    @Validation(required = true)
    public String currency_symbol;
    
    @Validation(required = true)
    public float currency_val;

    @Validation(required = true)
    public String wallet_address;
 
    @Validation(required = true)
    public String status_desc;

	@Override
	public String toString() {
		return "NotifyRechargeResponse [order_num=" + order_num + ", out_trade_no=" + out_trade_no + 
				", status=" + status +", pay_at=" + pay_at +  ", fiat_currency=" + fiat_currency + ", fiat_account=" + fiat_account + 
				", currency_symbol=" + currency_symbol + ", currency_val=" + currency_val +", wallet_address=" + wallet_address +", status_desc=" + status_desc +"]";
	}
}
