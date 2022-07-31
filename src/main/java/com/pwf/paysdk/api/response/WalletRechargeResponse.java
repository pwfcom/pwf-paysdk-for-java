package com.pwf.paysdk.api.response;

import com.pwf.paysdk.base.NameInMap;
import com.pwf.paysdk.base.Validation;

public class WalletRechargeResponse {

	@NameInMap("user_id")
    @Validation(required = true)
    public int user_id;

	@NameInMap("public_chain")
    @Validation(required = true)
    public String public_chain;
   
	@NameInMap("digital_currency")
    @Validation(required = true)
    public String digital_currency;
   
	@NameInMap("wallet_address")
    @Validation(required = true)
    public String wallet_address;
 
	@NameInMap("timestamp")
    @Validation(required = true)
    public int timestamp;
    
	@Override
	public String toString() {
		return "WalletRechargeResponse [user_id=" + user_id + ", public_chain=" + public_chain + ", digital_currency=" + digital_currency + ", wallet_address=" + wallet_address + ", timestamp=" + timestamp + "]";
	}
}
