
欢迎使用 Pwf Pay SDK for Java 。

## 环境要求
1. 需要配合`JDK 1.8`或其以上版本。


2. 使用 Pwf Pay SDK for Java 之前 ，您需要先前往[PWF开发平台](https://pwf.com/)注册并完成开发者接入的一些准备工作，包括创建应用、为应用设置接口加签方式等。

3. 准备工作完成后，注意保存如下信息，后续将作为使用SDK的输入。

* 加签模式为公钥证书模式

`AppID`、`应用的私钥`、`PWF公钥`

## 安装依赖
### 通过[Maven](https://mvnrepository.com/artifact/com.pwf.sdk/pwf-paysdk)来管理项目依赖
推荐通过Maven来管理项目依赖，您只需在项目的`pom.xml`文件中声明如下依赖

```xml
<dependency>
    <groupId>com.pwf.sdk</groupId>
    <artifactId>pwf-paysdk</artifactId>
    <version>Use the version shown in the maven badge</version>
</dependency>
```

## 快速开始
### 普通调用
以下这段代码示例向您展示了使用Pwf Pay SDK for Java调用一个API的3个主要步骤：

1. 设置参数（全局只需设置一次）。
2. 发起API调用。
3. 处理响应或异常。

```java
import com.pwf.paysdk.api.response.NotifyPayResponse;
import com.pwf.paysdk.base.ApiClient;
import com.pwf.paysdk.base.Config;

public class Main {
    public static void main(String[] args) throws Exception {
        // 设置参数（全局只需设置一次）
        Config config = getOptions();
        ApiClient.setOptions(config);
        
        try {
             // 发起API调用例子
            Map<String, Object> params = new HashMap<String, Object>();
            
            params.put("trade_name", "trade_name");
            params.put("pay_type", 1);
            params.put("fiat_currency", "EUR");
            params.put("fiat_amount", 0.01);
            params.put("out_trade_no", "order-009");
            params.put("subject", "order-test");
            params.put("timestamp", 1657895835);
            params.put("return_url", "https://www.return.com/return");
            params.put("collection_model", 1);
            params.put("merchant_no", config.merchantNo);
            params.put("notify_url", config.notifyUrl);

            WalletPayAddressResponse res = ApiClient.wallet().payAddress(params);
            System.out.println("response_body:" + res);


            //处理异步回调通知例子
            String json_string = "{'ret':1000,'msg':'\\u8bf7\\u6c42\\u6210\\u529f','data':'WDlwdnBoSkFDeS96bVdIYjg4WUNaaXVuV3NTQ3JHWU9t.........'}";
            NotifyPayResponse resonpse = ApiClient.callback().pay(json_string);
            System.out.println("response_body:" + resonpse);
            
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
    
        Config config = new Config();
        config.apiUrl = "<-- 请填写平台分配的接口域名，例如：https://xxx.pwf.com/ -->";
        config.appToken = "<-- 请填写您的appToken，例如：377b26eb8c25bd... -->";
        config.merchantNo = "<-- 请填写您的商户号，例如：202207...964 -->";
    
        //語系(參考文檔中最下方語系表，如:EN)
        config.lang = "CN";
        
        config.merchantPrivateCertPath = "<-- 请填写您的应用私钥路径，例如：/foo/MyPrivateKey.pem -->";
        config.pwfPublicCertPath = "<-- 请填写PWF平台公钥证书文件路径，例如：/foo/PwfPublicKey.pem -->";
    
        config.notifyUrl = "<-- 请填写您的异步通知接收服务地址，例如：https://www.notify.com/notify -->";

        return config;
    }
}
```

