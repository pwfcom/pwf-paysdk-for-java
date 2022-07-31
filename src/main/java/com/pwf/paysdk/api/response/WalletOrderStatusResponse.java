package com.pwf.paysdk.api.response;

import com.pwf.paysdk.base.NameInMap;
import com.pwf.paysdk.base.Validation;

public class WalletOrderStatusResponse {

	@NameInMap("order_num")
    @Validation(required = true)
    public String order_num;

	@NameInMap("out_trade_no")
    @Validation(required = true)
    public String out_trade_no;
 
	@NameInMap("status")
    @Validation(required = true)
    public int status;

	@NameInMap("pay_at")
    @Validation(required = true)
    public int pay_at;
    
	@NameInMap("fiat_currency")
    @Validation(required = true)
    public String fiat_currency;
    
	@NameInMap("fiat_amount")
    @Validation(required = true)
    public float fiat_amount;
 
	@NameInMap("currency_symbol")
    @Validation(required = true)
    public String currency_symbol;
    
	@NameInMap("currency_val")
    @Validation(required = true)
    public float currency_val;

	@NameInMap("wallet_address")
    @Validation(required = true)
    public String wallet_address;
 
	@NameInMap("status_desc")
    @Validation(required = true)
    public String status_desc;

	@Override
	public String toString() {
		return "WalletOrderStatusResponse [order_num=" + order_num + ", out_trade_no=" + out_trade_no + 
				", status=" + status +", pay_at=" + pay_at +  ", fiat_currency=" + fiat_currency + ", fiat_amount=" + fiat_amount + 
				", currency_symbol=" + currency_symbol + ", currency_val=" + currency_val +", wallet_address=" + wallet_address +", status_desc=" + status_desc +"]";
	}
}
