package com.haogrgr.buka.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BukaUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(BukaUtils.class);
	
	public static void main(String[] args) throws Exception {
		HttpClient client = BukaUtils.client();
//		String from = "i={\"f\":\"func_getgroupitems\",\"count\":20,\"start\":"0",\"fn\":11,\"fp\":\"\"}&c=4C79FF629C38F2A3046295A2D2727490&z=1&p=android&v=4&cv=17170469";
		String from = "i={\"f\":\"func_getdetail\",\"mid\":208984}&c=D97A989E1CDD661DE5A19E36ACC9D64F&z=1&p=android&v=4&cv=17170469";
		
		HttpPost post = post(from);
		
		String exec = exec(client, post);
		
		System.err.println(exec);
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid.replace("-", "").toUpperCase());
	
	}
	
	public static HttpClient client() {
		DefaultHttpClient client = (DefaultHttpClient) HttpUtils.getDefaultClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

		client.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
				request.removeHeaders("User-Agent");
			}
		});

		return client;
	}
	
	public static HttpClient client(String host){
		if(host == null){
			host = "127.0.0.1:8888";
		}
		String[] split = host.split(":");
		HttpHost proxy = new HttpHost(split[0], Integer.valueOf(split[1]));
		
		HttpClient client = client();
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		return client;
	}

	public static String encodeParam(String param) throws Exception {
		String encode = URLEncoder.encode(param, "UTF-8");

		encode = encode.replaceAll("%20", " ").replaceAll("%3A", ":").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%3C", "<").replaceAll("%3E", ">").replaceAll("%3D", "=")
				.replaceAll("%26", "&").replaceAll("%25", "%").replaceAll("%24", "$").replaceAll("%23", "#").replaceAll("%2B", "+").replaceAll("%2C", ",").replaceAll("%3F", "?");

		encode = URLEncoder.encode(encode, "UTF-8");
		encode = encode.replaceAll("%20", " ").replaceAll("%3A", ":").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%3C", "<").replaceAll("%3E", ">").replaceAll("%3D", "=")
				.replaceAll("%26", "&").replaceAll("%25", "%").replaceAll("%24", "$").replaceAll("%23", "#").replaceAll("%2B", "+").replaceAll("%2C", ",").replaceAll("%3F", "?");

		return encode;
	}

	public static HttpPost post(String param) throws Exception {
		HttpPost post = new HttpPost("http://cs.sosohaha.com/request.php");
		post.setEntity(new StringEntity(encodeParam(param), "UTF-8"));
		post.removeHeaders("Content-Type");
		post.removeHeaders("User-Agent");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		return post;
	}
	
	public static String content(HttpEntity entity) throws Exception{
		String content = null;
		if (entity != null) {
			try{
				content = IOUtils.toString(GZipUtils.decompress(entity.getContent()));
				if (content != null) {
					content = StringEscapeUtils.unescapeJava(content);
				}
				System.out.println(content);
			}catch(IOException e){
				logger.warn("获取响应正文出错!", e);
			}
		}
		return content;
	}
	
	public static String exec(HttpClient client, HttpPost post) throws Exception {
		HttpResponse resp = client.execute(post);
		HttpEntity entity = resp.getEntity();
		if (entity != null) {
			String content = IOUtils.toString(GZipUtils.decompress(entity.getContent()));
			if (content != null) {
				content = StringEscapeUtils.unescapeJava(content);
				return content;
			}
		}
		return null;
	}
	
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	public static String getDetailParam(Integer mid){
		String param = String.format(BukaCons.DETAIL_PARAM, mid, uuid());
		return param;
	}
	
	public static String getDetailParamEncode(Integer mid) throws Exception{
		String param = String.format(BukaCons.DETAIL_PARAM, mid, uuid());
		param = encodeParam(param);
		return param;
	}
	
	public static String getFinishParamEncode(Integer count, Integer start) throws Exception{
		String param = String.format(BukaCons.FINISH_LIST_PARAM, count, start, uuid());
		param = encodeParam(param);
		return param;
	}
}
