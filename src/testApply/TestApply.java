package testApply;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;

public class TestApply {
	
	
	public static void main(String[] args) {
		  String user_appid =  "2016020401137787";

		  String leaser_appid= "2016071601625173";

		  String private_key="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQClsZxpQ0JZ1RNR3tCW+hKJkd8zAogY9PrsLpDXtwYwyb8VnG3Y/MvGxqq2BDlv2HtEPGQFbd2bAHRwSeCx/C8F60ONiSB73B55MRFegGv6sT0iMDejGFrkA6LiptTtcUPX2Qvs7B6tMdiIsjSYO8AKMJcaOAyl8KrZCG8rIKjdeiM3wr0usjFeoo0nn8zwLnYdNvLdKj26EZsAVtIYnDJfCDupSqOKv2B6n0dn2g4hwn6GmpJShkkeaRNy/kypIpdcw3Q5TCzSfWrIZBWVCbVQUGCiHLc+d8sr4HzCksAqKrYoKmescI5cdRCQH685xUOF5gdTJ33+4dPUyWe87IDNAgMBAAECggEAN6OfqT07r5y8YxaWJsyMowOH1sI332GMTPkKDFXaT1G/x8sCnAWvi+00b/CVNPiV1AEPeT8+VgleEELOoBXV2H/67DEq5kyxMvMGKRyopcMUi0FzvWG1S7Ho5YEuKRmKtZWdyo4zcuc1Fhn5TVFrGEDezKAbpzFcvYp2gh3ydoQ80WdovqkZzV1FisyDKYUDWGa8YMJR48gBSeWILoOlGHYz4iv9RAvxYAqmxHEyGzHrZVNzY4Ner0JwTdxDvneTkQW4Pj4el5p1vA6dZRWjv9G2KuOMRJNJecbZKyl/k0yNGd/otEqyD9oj4KILWqk4yWLZ0UBKrvz/PWQfelzbRQKBgQDmk6YbiBCkQeZkXXtKrl6O9h7ZV/4zA+OklLbxuHxW2o+jflBp1KCSrBfsGWtJSzFLLrkVpnteMM1uEzbcoV77IrPmA7mGTmQyhv+6X6aCKEgbBsR1rQn6090cXbrccxZPqQTYNLLekJTyC0tkhmJOQ1qNwHh28bYraB+ZIuNiAwKBgQC39ozEwelUvCXssch+snX19M4hUekQp53eM80sUxkOkm1CmvR2UTFjVUr2TIOD43G6Mqh3jdjZMVH+OI8JEqASdEdmnLQtfjY3TqM2xFRJ548JVLzgeARLGkBp68NHjIyYlPsqDUYxHzicrX96a9y8NM+U7ouqPijOJQlq24wA7wKBgEoLpE43FdEJ8KjOdGd5M5iJZUK6xUEwwIv7zfw2GIfzzCjeyYgS5jXz2gLR7ugso3nXWUygmAGZfn8Y4IddsxjG/iqhi5Sb3AV14O3DBb/Km81CERkRJqFMJDeVygo9Uy7lwk6ELeTXXV2VTvLLTQqBCrV217iqyquJ1ovg7lBtAoGBAJoOo/ZGOcPXSJ8+hxvrEbQnDdSDhv2GkUM6+FBtSqeUYmGL6LuI5SY9kGhQzTZO67e1orV0GaxRjEusS8SaT2kE7aJ2CsGqV3bbqP09TyURAQ0cZIxKlbXbRyQs5z1s63UAFpIxyZ1revpipUX9PD50r34WgunP4lY4V/mf4FALAoGBANERJVo0adcHH8IINPMOLaV1JvJXt31WfkxMiPSRbs7DfrC+HBX2jXyIKu2bvXSCi+3LcWFdt9P4ACwojdFQpwATszhITY74Dsu/59OSrt6A5Ldnl+LYJmdzaz8Cwk1X1/HDk2E9sukf9gCAPwUSDDkTXGmXp399Kt/Opr/bJBa5";

		  String public_key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApbGcaUNCWdUTUd7QlvoSiZHfMwKIGPT67C6Q17cGMMm/FZxt2PzLxsaqtgQ5b9h7RDxkBW3dmwB0cEngsfwvBetDjYkge9weeTERXoBr+rE9IjA3oxha5AOi4qbU7XFD19kL7OwerTHYiLI0mDvACjCXGjgMpfCq2QhvKyCo3XojN8K9LrIxXqKNJ5/M8C52HTby3So9uhGbAFbSGJwyXwg7qUqjir9gep9HZ9oOIcJ+hpqSUoZJHmkTcv5MqSKXXMN0OUws0n1qyGQVlQm1UFBgohy3PnfLK+B8wpLAKiq2KCpnrHCOXHUQkB+vOcVDheYHUyd9/uHT1MlnvOyAzQIDAQAB";


		//实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", user_appid, private_key , "json", "UTF-8", public_key, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("测试提交");  //描述信息  添加附加数据
        model.setSubject("测试商品"); //商品标题
        model.setOutTradeNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+(int)(Math.random()*90000+10000)); //商家订单编号
        model.setTimeoutExpress("1000m"); //超时关闭该订单时间
        model.setTotalAmount("1");  //订单总金额
        model.setProductCode("QUICK_MSECURITY_PAY"); //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        request.setBizModel(model);
        
        request.setNotifyUrl("http://diwinet.ddns.net:8081/dw-api-V2.0/");  //回调地址
        String orderStr = "";
        try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
                orderStr = response.getBody();
                System.out.println(orderStr);//就是orderString 可以直接给客户端请求，无需再做处理。
            } catch (AlipayApiException e) {
                e.printStackTrace();
        }
	}
}
