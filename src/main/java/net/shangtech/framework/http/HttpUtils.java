package net.shangtech.framework.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
}
