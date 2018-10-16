package httpclient;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TestApply {
	public static void main(String[] args) throws Exception {
	Map<String,String> sParaTemp = new HashMap<String,String>();

	 	sParaTemp.put("_input_charset","utf-8");
	 	sParaTemp.put("account_name", "北京云网天成科技有限公司");
		sParaTemp.put("batch_fee", "1.00");
		sParaTemp.put("batch_num", "1");
		sParaTemp.put("detail_data", "0315009^enterlee.dragon@foxmail.com^李茂林^1.00^hello");
		sParaTemp.put("email", "zhuangyuan@diwinet.com");
		sParaTemp.put("pay_date", "20151105");
	    sParaTemp.put("partner", "2088911180621236");
		sParaTemp.put("service", "batch_trans_notify");
		sParaTemp.put("notify_url", "");
		
		
		sParaTemp = AlipayCore.paraFilter(sParaTemp);
		String text = AlipayCore.createLinkString(sParaTemp);
		String sign = MD5.sign(text, AlipayConfig.key, "utf-8");

		
		sParaTemp.put("sign_type", "MD5");
		sParaTemp.put("sign", sign);
		
		String responseStr = HttpClientUtils.simpleGetInvoke("https://mapi.alipay.com/gateway.do", sParaTemp, "utf-8");
		System.out.println(responseStr);
	}
}
