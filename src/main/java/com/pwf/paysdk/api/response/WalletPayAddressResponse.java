package com.pwf.paysdk.api.response;

import com.pwf.paysdk.base.NameInMap;
import com.pwf.paysdk.base.Validation;

public class WalletPayAddressResponse {
	@NameInMap("order_num")
	@Validation(required = true)
    public String order_num;

	@NameInMap("out_trade_no")
	@Validation(required = true)
    public String out_trade_no;
    
	@NameInMap("fiat_currency")
    @Validation(required = true)
    public String fiat_currency;
    
    @NameInMap("fiat_amount")
    @Validation(required = true)
    public float fiat_amount;
    
    @NameInMap("request_time")
    @Validation(required = true)
    public int request_time;

    @NameInMap("expire_time")
    @Validation(required = true)
    public int expire_time;
    
    @NameInMap("pay_url")
    @Validation(required = true)
    public String pay_url;
    
	@Override
	public String toString() {
		return "WalletPayAddressResponse [order_num=" + order_num + ", out_trade_no=" + out_trade_no + ", fiat_currency=" + fiat_currency + ", fiat_amount=" + fiat_amount + ", pay_url=" + pay_url + "]";
	}
}
