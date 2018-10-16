package httpclient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils; 



/** 

 * 类HttpClientTest.java的实现描述：TODO 类实现描述 

 * @author zheng.zhaoz 2012-2-9 下午07:33:18 

 */ 

public class HttpClientTest { 



	public static void main(String[] args) {
		for(int i=0;i<30;i++){
			test();
		}
	} 
	public static void test(){
		HttpClient httpClient = new DefaultHttpClient(); 
		//創建一個httpGet方法 
		HttpPost httpPost = new HttpPost("http://203.208.48.34"); 

		 
		httpPost.setHeader("Accept", "text/plain, text/html, text/xml, text/xml-external-parsed-entity, application/octet-stream, application/vnd.google-earth.kml+xml, application/vnd.google-earth.kmz, image/*"); 
		 
		httpPost.setHeader("Accept-Encoding", "gzip, deflate"); 
		 
		httpPost.setHeader("Accept-Language", "zh-CN,en,*"); 
		 
		httpPost.setHeader("Connection", "Keep-Alive"); 
		 
		httpPost.setHeader("Cache-Contro1", "no-store"); 
		 
		httpPost.setHeader("Host", "kh.google.com"); 
		 
		httpPost.setHeader("Content-Type", "application/octet-stream"); 
		 
		httpPost.setHeader("User-Agent", "kh_lt/LT3.0.0762");

		HttpResponse response = null; 

		try { 

			response = httpClient.execute(httpPost); 

		} catch (ClientProtocolException e) { 

			e.printStackTrace(); 

		} catch (IOException e) { 

			e.printStackTrace(); 

		} 



		//输出响应的所有头信息 

		if(response != null) { 

			Header headers[] = response.getAllHeaders(); 

			int i = 0; 

			while (i < headers.length) { 

				System.out.println(headers[i].getName() + ": " + headers[i].getValue()); 

				i++; 

			} 

			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { 

				try { 

					HttpEntity entity = response.getEntity(); 

					// 将源码流保存在一个byte数组当中，因为可能需要两次用到该流 

					byte[] bytes = EntityUtils.toByteArray(entity); 

					String charSet = ""; 

					// 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取 

					charSet = EntityUtils.getContentCharSet(entity); 

					System.out.println("In header: " + charSet); 

					// 如果头部中没有，需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息 

					if (charSet == "") { 

						String regEx="(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)"; 

						Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE); 

						Matcher m=p.matcher(new String(bytes)); // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响 

						boolean result = m.find(); 

						if (m.groupCount() == 1) { 

							charSet = m.group(1); 

						} else { 

							charSet = ""; 

						} 

					} 

					System.out.println("Last get: " + charSet); 

					// 可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话） 

					System.out.println("Encoding string is: " + new String(bytes, charSet)); 

				} catch (IOException e) { 

					e.printStackTrace(); 

				} 

			} 

		} 
		//關閉聯接 
		httpClient.getConnectionManager().shutdown(); 
	}
}