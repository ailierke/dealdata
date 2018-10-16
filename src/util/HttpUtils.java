package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * <p>标题：</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年5月21日</p>
 * @author	admin
 */
@SuppressWarnings("deprecation")
public class HttpUtils {
	
	/**
	 * <p>说明：</p>
	 * <p>时间：2015年5月21日 下午4:30:55</p>
	 * @param path
	 * @param params
	 * @return
	 */
	public String sendPost(String path, Map<String, String> params){
		return this.sendHttpClientPost(path, params, "utf-8");
	}
	
	// 用apache接口实现http的post提交数据
	@SuppressWarnings("resource")
	private String sendHttpClientPost(String path,
			Map<String, String> params, String encode) {

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}

		try {
			// 实现将请求的参数封装到表单中，即请求体中
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
			// 使用post方式提交数据
			HttpPost httpPost = new HttpPost(path);
			httpPost.setEntity(entity);
			// 执行post请求，并获取服务器端的响应HttpResponse
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpPost);

			// 获取服务器端返回的状态码和输入流，将输入流转换成字符串
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = httpResponse.getEntity().getContent();
				return changeInputStream(inputStream, encode);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * // 把从输入流InputStream按指定编码格式encode变成字符串String
	 */
	private String changeInputStream(InputStream inputStream,
			String encode) {

		// ByteArrayOutputStream 一般叫做内存流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					byteArrayOutputStream.write(data, 0, len);
				}
				result = new String(byteArrayOutputStream.toByteArray(), encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		Long start = System.currentTimeMillis();
//		String path = "http://api.d-water.com/user/exit.p";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("yhid", "6");
//		String result = sendHttpClientPost(path, params, "utf-8");
//		Long end = System.currentTimeMillis();
//		System.out.println((end-start)/1000);
//		System.out.println("-result->>" + result);
//
//	}
}