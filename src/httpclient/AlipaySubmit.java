package httpclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipaySubmit {
    
    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.sign_type.equals("MD5") ) {
        	mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return mysign;
    }
	
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }

  

   
	public static void main(String[] args) throws Exception {
		Map<String,String> sParaTemp = new HashMap<String,String>();
		sParaTemp.put("_input_charset","utf-8");
	 	sParaTemp.put("account_name", "北京云网天成科技有限公司");
	 	sParaTemp.put("batch_no", "201511050000117");
		sParaTemp.put("batch_fee", "4.00");
		sParaTemp.put("batch_num", "2");
		sParaTemp.put("detail_data", "0315011^enterlee.dragon@foxmail.com^李茂林^2.00^hello|0315012^724941972@qq.coom^蒋星^2.00^hello");
		sParaTemp.put("email", "zhuangyuan@diwinet.com");
		sParaTemp.put("pay_date", "20151105");
	    sParaTemp.put("partner", "2088911180621236");
		sParaTemp.put("service", "batch_trans_notify");
		
		sParaTemp = buildRequestPara(sParaTemp);
		String responseStr = HttpClientUtils.simpleGetInvoke("https://mapi.alipay.com/gateway.do", sParaTemp, "utf-8");
		System.out.println(responseStr);
	}
}
