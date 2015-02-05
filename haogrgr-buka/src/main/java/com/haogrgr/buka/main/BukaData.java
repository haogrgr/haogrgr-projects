package com.haogrgr.buka.main;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import com.haogrgr.buka.util.GZipUtils;
import com.haogrgr.buka.util.HttpUtils;

public class BukaData {

	public static void main(String[] args) throws Exception {
		DefaultHttpClient client = (DefaultHttpClient) HttpUtils.getDefaultClient();
		// setProxy(client);

		client.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
				request.removeHeaders("User-Agent");
			}
		});

		int start = 20;

		String from = "i={\"f\":\"func_getgroupitems\",\"count\":20,\"start\":" + start + ",\"fn\":11,\"fp\":\"\"}&c=4C79FF629C38F2A3046295A2D2727490&z=1&p=android&v=4&cv=17170469";
		from = getEncodeQuery(from);

		HttpPost post = new HttpPost("http://cs.sosohaha.com/request.php");
		post.setEntity(new StringEntity(from, "UTF-8"));
		post.removeHeaders("Content-Type");
		post.removeHeaders("User-Agent");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");

		HttpResponse resp = client.execute(post);
		System.out.println(resp.getStatusLine());

		HttpEntity entity = resp.getEntity();
		if (entity != null) {
			String content = IOUtils.toString(GZipUtils.decompress(entity.getContent()));
			if (content != null) {
				content = StringEscapeUtils.unescapeJava(content);
			}
			System.out.println(content);
		}

		HttpUtils.closeClient(client);
	}

	public static String getEncodeQuery(String query) throws Exception {
		String encode = URLEncoder.encode(query, "UTF-8");
		encode = encode.replaceAll("%20", " ").replaceAll("%3A", ":").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%3C", "<").replaceAll("%3E", ">").replaceAll("%3D", "=")
				.replaceAll("%26", "&").replaceAll("%25", "%").replaceAll("%24", "$").replaceAll("%23", "#").replaceAll("%2B", "+").replaceAll("%2C", ",").replaceAll("%3F", "?");

		encode = URLEncoder.encode(encode, "UTF-8");
		encode = encode.replaceAll("%20", " ").replaceAll("%3A", ":").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%3C", "<").replaceAll("%3E", ">").replaceAll("%3D", "=")
				.replaceAll("%26", "&").replaceAll("%25", "%").replaceAll("%24", "$").replaceAll("%23", "#").replaceAll("%2B", "+").replaceAll("%2C", ",").replaceAll("%3F", "?");

		return encode;
	}

	public static void setProxy(DefaultHttpClient client) {
		HttpHost proxy = new HttpHost("127.0.0.1", 8888);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

}
