
歡迎使用 Pwf Pay SDK for Java 。

## 環境要求
1. 需要配合`JDK 1.8`或以上版本。


2. 使用 Pwf Pay SDK for Java 之前 ，您需要先前往 https://pwf.com 申請開通賬戶並完成開發者接入的一些準備工作，包括創建應用、為應用設置接口相關配置信息等。

3. 準備工作完成後，注意保存如下信息，後續將作為使用SDK的輸入。

* 加簽模式為公私鑰證書模式

`AppID`、`應用的私鑰`、`PWF公鑰`

## 安裝依賴
### 通過[Maven](https://mvnrepository.com/artifact/com.pwf.sdk/pwf-paysdk)來管理項目依賴
推薦通過Maven來管理項目依賴，您只需在項目的`pom.xml`文件中聲明如下依賴

```xml
<dependency>
    <groupId>com.pwf.sdk</groupId>
    <artifactId>pwf-paysdk</artifactId>
    <version>Use the version shown in the maven badge</version>
</dependency>
```

## 快速開始
### 普通調用
以下這段代碼示例向您展示了使用Pwf Pay SDK for Java調用一個API的3個主要步驟：

1. 設置參數（全局只需設置一次）。
2. 發起API調用。
3. 處理響應或異常。

```java
import com.pwf.paysdk.api.response.NotifyPayResponse;
import com.pwf.paysdk.base.ApiClient;
import com.pwf.paysdk.base.Config;

public class Main {
    public static void main(String[] args) throws Exception {
        // 設置參數（全局只需設置一次）
        Config config = getOptions();
        ApiClient.setOptions(config);
        
        try {
            // 發起API調用例子
            Map<String, Object> params = new HashMap<String, Object>();
            
            params.put("trade_name", "trade_name");
            params.put("fiat_currency", "EUR");
            params.put("fiat_amount", 0.01);
            params.put("out_trade_no", "order-009");
            params.put("subject", "order-test");
            params.put("timestamp", 1657895835);
            params.put("notify_url", "https://www.notify.com/notify");
            params.put("return_url", "https://www.return.com/return");
            params.put("collection_model", 1);
            params.put("merchant_no", config.merchantNo);
            
            WalletPayAddressResponse res = ApiClient.wallet().payAddress(params);
            System.out.println("response_body:" + res);


            //處理異步回調通知例子
            String json_string = "{'ret':1000,'msg':'\\u8bf7\\u6c42\\u6210\\u529f','data':'WDlwdnBoSkFDeS96bVdIYjg4WUNaaXVuV3NTQ3JHWU9t.........'}";
            NotifyPayResponse resonpse = ApiClient.callback().pay(json_string);
            System.out.println("response_body:" + resonpse);
            
        } catch (Exception e) {
            System.err.println("調用遭遇異常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
    
        Config config = new Config();
        config.apiUrl = "<-- 請填寫平台分配的接口域名，例如：https://xxx.pwf.com/ -->";
        config.appToken = "<-- 請填寫您的appToken，例如：377b26eb8c25bd... -->";
        config.merchantNo = "<-- 請填寫您的商戶號，例如：202207...964 -->";
    
        //語系(參考文檔中最下方語系表，如:TC)
        config.lang = "TC";
        
        config.merchantPrivateCertPath = "<-- 請填寫您的應用私鑰路徑，例如：/foo/MyPrivateKey.pem -->";
        config.pwfPublicCertPath = "<-- 請填寫PWF平台公鑰證書文件路徑，例如：/foo/PwfPublicKey.pem -->";
    
        return config;
    }
}
```

