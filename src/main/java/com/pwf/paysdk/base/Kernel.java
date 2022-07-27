package com.pwf.paysdk.base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwf.paysdk.utils.RSAEncryptor;
import com.pwf.paysdk.utils.StringUtils;
import com.pwf.paysdk.utils.codec.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pwf.paysdk.api.response.WalletPayAddressResponse;
import com.pwf.paysdk.http.HttpClient;
import com.pwf.paysdk.http.HttpRequest;
import com.pwf.paysdk.http.HttpResponse;
import com.pwf.paysdk.utils.MapConverter;

public class Kernel {

    private static final Logger LOGGER = LoggerFactory.getLogger(Kernel.class);
    
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String SDK_VERSION = "V2.0";
    
    private final String merchantPrivateKey;
    private final String pwfPublicKey;
    
    private final  Map<String, Object> config;
    
    private RSAEncryptor rsaEncryptor;
    
    public Kernel(Config options) {
        try {
        	this.config = MapConverter.objectToMap(options);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        
        this.rsaEncryptor = new RSAEncryptor();
        
        merchantPrivateKey = RSAEncryptor.getPrivateKey(getConfig("merchantPrivateCertPath"));
        pwfPublicKey = RSAEncryptor.getPublicKey(getConfig("pwfPublicCertPath"));
       
    }
    
    public String getConfig(String key) {
        if (String.valueOf(config.get(key)) == "null") {
            return null;
        } else {
            return String.valueOf(config.get(key));
        }
    }
    
    public Map<String, String> buildPostParams(Map<String, Object> params) throws Exception {
    	
    	Map<String, Object> sortedMap = MapConverter.getSortedMap(params);
    	String paramsJsonString = MapConverter.toJsonString(sortedMap);

    	String encrypted = rsaEncryptor.doEncrypt(paramsJsonString,CHARSET_UTF8,pwfPublicKey);

    	Map<String, String> postBodyParams = new java.util.HashMap<String, String>();
    	postBodyParams.put("data", encrypted);
    	postBodyParams.put("sign", sign(encrypted,merchantPrivateKey));
    	postBodyParams.put("token", getConfig("appToken"));
    	postBodyParams.put("lang", getConfig("lang"));
    	postBodyParams.put("version", SDK_VERSION);
        
    	return postBodyParams;
    }
    
    public HttpResponse execute(HttpRequest request) {
    	
    	HttpResponse response = HttpClient.execute(request);
    	/*
        if (response.getResponse() != null) {
            try {
                response.getResponse().close();
            } catch (IOException e) {
                LOGGER.warn("关闭链接遭遇异常：" + e.getMessage(), e);
            }
        }*/
    	return response;
    }

    public String sign(String content, String privateKeyPem) {
        try {
            return rsaEncryptor.doSign(content, CHARSET_UTF8,privateKeyPem);
        } catch (Exception e) {
            String errorMessage = "签名遭遇异常，content=" + content + "\n privateKeySize=" + privateKeyPem.length() + " reason=" + e.getMessage();
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }


    public  <T> T getResponseData(String response, Class<T> returnClass) throws Exception {
    	
    	ApiResponse result = new ApiResponse(response);

        if (result.isSuccess()) {
        	
        	String decryptData = decryptResponseData(result.getData());
        	Map dataMap = new Gson().fromJson(decryptData, Map.class);
        	if(verify(dataMap)) {
        		T returnData = new Gson().fromJson(decryptData, returnClass);
        		return returnData;
        	}	

        	throw new PwfError("验签失败，请检查Pwf平台公钥或商户私钥是否配置正确。");
        }else if(!StringUtils.isEmpty(result.getRet())){
            throw new PwfError(result.getMsg());
        }else {
        	throw new PwfError("返回数据出错");
        }
    }
    
    public String decryptResponseData(String data) throws Exception{
        byte[] decodeDataBytes = Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8));
        return rsaEncryptor.doDecrypt(new String(decodeDataBytes), CHARSET_UTF8,merchantPrivateKey);
    }
    
    public static String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        }
    	
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }
        return content.toString();
    }
    
    public Boolean verify(Map<String, String> respMap) throws Exception {
        String sign = (String) respMap.get("sign");
        respMap.remove("sign");
        String content = getSignCheckContent(respMap);

        return rsaEncryptor.doVerify(content, CHARSET_UTF8,pwfPublicKey,sign);
    }
 
}