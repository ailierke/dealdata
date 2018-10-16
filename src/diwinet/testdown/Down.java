package diwinet.testdown;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONObject;

import httpclient.HttpClientUtils;

public class Down {
	public static void main(String[] args) throws Exception {
		Down d = new Down();
		d.testDown();
	}
	private void testDown() throws Exception{
		Long  t = System.currentTimeMillis();
		String sendMsg = "{\"k\":"+2+",\"v\":\""+1+"\"}";
		Map map = new HashMap<String,String>();
		map.put("appid", "LYWL3IBLpU3JrLQ51J7V");
		map.put("secret", "VLSeEtZKAe6CMxhbbNtci5pjgNS1uNOm");
		map.put("value", "898602B61116C0023848");
		map.put("type", "2");
		map.put("message",sendMsg);
		Map signMap = new TreeMap();
		signMap.put("appid", "LYWL3IBLpU3JrLQ51J7V");
		signMap.put("dt",t+"");//时间戳随机数生成
		map.put("message",sendMsg);
		signMap.put("secret", "VLSeEtZKAe6CMxhbbNtci5pjgNS1uNOm");
		signMap.put("type", "2");
		signMap.put("value", "898602B61116C0023848");
		String orderSign = StringUtils.createSign(signMap,"5Ktul9tEP2");//5Ktul9tEP2是固定的
		map.put("sign", orderSign);
		map.put("dt", t+"");
		String str = HttpClientUtils.simplePostInvoke("http://api.cmpyun.com/api/serviceAccept/sendSms", map,"UTF-8");
		System.out.println(str);
	}
	
}
class StringUtils{
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" }; 
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	
	public static String byteArrayToHexString(byte[] b) {  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++) {  
            resultSb.append(byteToHexString(b[i], true));  
        }  
        return resultSb.toString();  
    } 
	
	 private static String byteToHexString(byte b, boolean bigEnding) {  
	        int n = b;  
	        if (n < 0)  
	            n = 256 + n;  
	        int d1 = n / 16;  
	        int d2 = n % 16;  
	        return (bigEnding)?(hexDigits[d1] + hexDigits[d2]):(hexDigits[d2] + hexDigits[d1]);  
	    }  

 
public static String getData(Object value){
		if(null == value)
			return "";
		else 
			return value.toString();
	}
	
	  //md5加密

	public static String createSign(Map<String, String> packageParams,String appSercet) throws Exception{
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			//String k = (String) entry.getKey();
			String k = getData(entry.getKey());
			//String v = (String) entry.getValue();
			String v = getData(entry.getValue());
			if (null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("appSecret=" + appSercet);
		String sign = MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;
	}//加密方式
}

