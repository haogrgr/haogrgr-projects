package com.haogrgr.buka.main;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;

import com.haogrgr.buka.util.BukaUtils;
import com.haogrgr.buka.util.GZipUtils;

public class URLTest {
	
	public static void main(String[] args) throws Exception {
		HttpURLConnection conn = getConn("http://cs.sosohaha.com/request.php", "post");
		//setProxy();

		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		String param = BukaUtils.getDetailParamEncode(203307);
//		String param = BukaUtils.getFinishParamEncode(10, 10);
		
		Integer code = write(conn, param).getResponseCode();
		System.out.println(code);

		String content = read(conn);
		if(content != null){
			content = StringEscapeUtils.unescapeJava(content);
		}
		System.out.println(content);

		conn.disconnect();
	}

	public static void setProxy() {
		Properties prop = System.getProperties();
		prop.setProperty("http.proxyHost", "127.0.0.1");
		prop.setProperty("http.proxyPort", "8888");
	}

	private static HttpURLConnection getConn(String url, String method) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(3000);
		conn.setDoOutput(true);
		conn.setRequestMethod(method.toUpperCase());
		return conn;
	}

	private static HttpURLConnection write(HttpURLConnection conn, String content) throws Exception {
		OutputStream out = conn.getOutputStream();
		out.write(content.getBytes());
		out.flush();
		out.close();
		return conn;
	}

	public static String read(HttpURLConnection conn) throws Exception {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		GZipUtils.decompress(conn.getInputStream(), bo);
		String result = new String(bo.toByteArray(), "UTF-8");
		bo.close();
		return result;
	}

}
