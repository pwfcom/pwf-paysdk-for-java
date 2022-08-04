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
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.pwf.paysdk.http.HttpClient;
import com.pwf.paysdk.http.HttpRequest;
import com.pwf.paysdk.http.HttpResponse;
import com.pwf.paysdk.utils.MapConverter;

public class PwfClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PwfClient.class);
    
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String SDK_VERSION = "V2.0";
    
    private final String merchantPrivateKey;
    private final String pwfPublicKey;
    
    private final  Map<String, Object> config;
    
    private RSAEncryptor rsaEncryptor;
    
    public PwfClient(Config options) {
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
    
    public ApiResponse execute(String urlpath, Map<String, Object> params) throws Exception {
 
		HttpRequest request = new HttpRequest();
		request.host = getConfig("apiUrl");
        request.method = "POST";
        request.pathname = urlpath;
        
        request.posts = buildPostParams(params);
        
    	HttpResponse response = HttpClient.execute(request);
        //if (response.getResponse() == null) {
        //    try {
        //        response.getResponse().close();
                
                return getApiResponse(response.getResponseBody());
        //    } catch (IOException e) {
        //        LOGGER.warn(e.getMessage(), e);
        //    }
       // }
        
        //throw new RuntimeException("no response data returned");
    }

    public ApiResponse getApiResponse(String responsen_body){

    	ApiResponse apiResponse = new ApiResponse(this);
        apiResponse.setRequestBody(responsen_body);
        return apiResponse;
    }
    
    public String sign(String content, String privateKeyPem) {
        try {
            return rsaEncryptor.doSign(content, CHARSET_UTF8,privateKeyPem);
        } catch (Exception e) {
            String errorMessage = "Signature Exception，content=" + content + "\n privateKeySize=" + privateKeyPem.length() + " reason=" + e.getMessage();
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }
    
    public String decryptResponseData(String data) throws Exception{
       // byte[] decodeDataBytes = Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8));
    	//data = new String(decodeDataBytes);
        return rsaEncryptor.doDecrypt(data, CHARSET_UTF8,merchantPrivateKey);
    }
    
    public static String getSignCheckContent(Map<String, String> params) {
        if (params == null) {
            return null;
        }
    	
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if(!Strings.isNullOrEmpty(key) && !Strings.isNullOrEmpty(value)) {
            	content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
            	index++;
            }
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
