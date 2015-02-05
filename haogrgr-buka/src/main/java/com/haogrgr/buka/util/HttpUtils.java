package com.haogrgr.buka.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

public class HttpUtils {
	
	private static final String USER_AGENT = "";
	
	public static void main(String[] args) throws Exception {
		
	}
	
	public static HttpClient getSSLClient(File keyStoreFile, String password) throws Exception {
		HttpClient client = getDefaultClient();
		regSSL(client, keyStoreFile, password, "https", 443);
		return client;
	}
	
	public static HttpClient getThreadSafeSSLClient(String keyStorePath, String password) throws Exception {
		HttpClient client = getThreadSafeClient();
		
		File keyStoreFile = getStroeFile(keyStorePath);
		regSSL(client, keyStoreFile, password, "https", 443);
		
		return client;
	}
	
	public static HttpClient getSSLClient(String keyStorePath, String password) throws Exception {
		HttpClient client = getDefaultClient();
		
		File keyStoreFile = getStroeFile(keyStorePath);
		
		regSSL(client, keyStoreFile, password, "https", 443);
		
		return client;
	}

	private static File getStroeFile(String keyStorePath) {
		File keyStoreFile = new File(keyStorePath);
		if(!keyStoreFile.isAbsolute()){
			keyStoreFile = new File(HttpUtils.class.getResource("/").getFile() + keyStorePath);
		}
		return keyStoreFile;
	}
	
	public static DefaultHttpClient getDefaultClient(){
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		setClientTimeOut(client, 3000, 5000);
		return client;
	}
	
	public static DefaultHttpClient getDefaultClient(ClientConnectionManager cm){
		DefaultHttpClient client = new DefaultHttpClient(cm);
		client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		setClientTimeOut(client, 3000, 5000);
		return client;
	}
	
	public static DefaultHttpClient getDefaultClient(HttpRequestInterceptor interceptor){
		DefaultHttpClient client = getDefaultClient();
		client.addRequestInterceptor(interceptor);
		return client;
	}
	
	public static HttpClient getThreadSafeClient(){
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		cm.setMaxTotal(200);
		
		HttpClient client = new DefaultHttpClient(cm);
		
		return client;
	}
	
	public static void regSSL(HttpClient client, File keyStoreFile, String password, String scheme, Integer port) throws Exception {
		if(scheme == null || scheme.trim().length()<1){
			scheme = "https";
		}
		
		if(port == null){
			port = Integer.valueOf(443);
		}
		
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(keyStoreFile);
			try {
				trustStore.load(instream, password.toCharArray());
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
			}

			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			Scheme sch = new Scheme(scheme, port, socketFactory);
			client.getConnectionManager().getSchemeRegistry().register(sch);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static List <NameValuePair> fromMap(Map<String, String> paramMap){
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			String key = entry.getKey();
			if(key != null && key.trim().length() > 0){
				nvps.add(new BasicNameValuePair(key, entry.getValue()));
			}
		}
		
		return nvps;
	}
	
	public static void closeClient(HttpClient client) {
		if(client !=null && client.getConnectionManager()!=null){  
			client.getConnectionManager().shutdown();  
        } 
	}
	
	public static void setClientTimeOut(HttpClient client, int connTimeOut, int soTimeOut){
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeOut);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOut);
	}
	
	public static void setProxy(DefaultHttpClient client) {
		HttpHost proxy = new HttpHost("127.0.0.1", 8888);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
}
