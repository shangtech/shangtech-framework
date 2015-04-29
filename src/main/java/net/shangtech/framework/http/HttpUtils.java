package net.shangtech.framework.http;

import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	public static String get(String url, QueryString... params) throws HttpRequestException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(buildUrl(url, params));
		CloseableHttpResponse response = null;
		try{
			response = client.execute(get);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK || response.getEntity() == null){
				throw new HttpRequestException();
			}
			return EntityUtils.toString(response.getEntity());
		}catch (Exception e) {
			throw new HttpRequestException();
		}finally{
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(client);
		}
	}
	
	public static String get(CloseableHttpClient client, String url, QueryString... params) throws HttpRequestException{
		HttpGet get = new HttpGet(buildUrl(url, params));
		CloseableHttpResponse response = null;
		try{
			response = client.execute(get);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK || response.getEntity() == null){
				throw new HttpRequestException();
			}
			return EntityUtils.toString(response.getEntity());
		}catch (Exception e) {
			throw new HttpRequestException();
		}finally{
			IOUtils.closeQuietly(response);
		}
	}
	
	public static String post(CloseableHttpClient client, String url, Map<String, String> params) throws HttpRequestException, UnsupportedEncodingException{
		HttpPost post = new HttpPost(url);
		List<NameValuePair> vnps = new ArrayList<NameValuePair>();
		for(Entry<String, String> entry : params.entrySet()){
			NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
			vnps.add(pair);
		}
		HttpEntity entity = new UrlEncodedFormEntity(vnps, "UTF-8");
		post.setEntity(entity);
		CloseableHttpResponse response = null;
		try{
			response = client.execute(post);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK || response.getEntity() == null){
				throw new HttpRequestException();
			}
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
			throw new HttpRequestException();
		}finally{
			IOUtils.closeQuietly(response);
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, HttpRequestException{
		CloseableHttpClient client = HttpClients.createDefault();
		Map<String, String> map = new HashMap<String, String>();
		map.put("md", "## test   \n aaa测试 ");
		String response = post(client, "http://markdown.shangtech.net/", map);
		System.out.println(response);
	}
	
	public static String buildUrl(String url, QueryString... params){
		if(params == null){
			return url;
		}
		StringBuilder sb = new StringBuilder(url);
		if(!url.contains("?")){
			sb.append("?_=");
		}
		for(QueryString param : params){
			sb.append(param.getKey()).append("=").append(param.getValue());
		}
		return sb.toString();
	}
	
	@SuppressWarnings("deprecation")
	public static CloseableHttpClient createSSLInsecureClient(){
		try {
			SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy(){

				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					return true;
				}

			}).build();
			SSLConnectionSocketFactory  sslsf = new SSLConnectionSocketFactory(context);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
