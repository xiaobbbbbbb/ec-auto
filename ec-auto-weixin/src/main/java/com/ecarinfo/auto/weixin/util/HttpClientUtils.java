package com.ecarinfo.auto.weixin.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

public class HttpClientUtils {
	private static final Logger logger = Logger.getLogger(HttpClientUtils.class);
	public static final ThreadLocal<HttpClient> threadLocal = new ThreadLocal<HttpClient>();
	private static final String UTF8 = "UTF-8";
	static HttpClient httpClient = null;
	static {
		HttpParams httpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, UTF8);
		ClientConnectionManager conMgr = new PoolingClientConnectionManager();
		httpClient = new DefaultHttpClient(conMgr);
	}
	public static HttpClient getHttpClient() {
		final DefaultHttpClient httpclient;
		if (threadLocal.get()==null) {
			httpclient = new DefaultHttpClient();	
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  10000);//连接时间10s
	        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,  10000);//
			HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
				@Override
				public boolean retryRequest(IOException exception,
						int executionCount, HttpContext context) {
					logger.info("============enter retryRequest method");
			        if (executionCount >= 1) {
			            return false;
			        }			        
			        HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
			        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest); 
			        if (idempotent) {
			            logger.info("============will retry for idempotent");
			            return true;
			        }
			        return false;
				}
			};
			httpclient.setHttpRequestRetryHandler(retryHandler);
			threadLocal.set(httpclient);
		} else {
			httpclient = (DefaultHttpClient)threadLocal.get();
		}
		return httpclient;
	} 

	public static void closeHttpClient() {
		HttpClient httpClient = getHttpClient();
		if (httpClient!=null) {
			httpClient.getConnectionManager().shutdown();
			threadLocal.remove();
		}
	}
	
	public static void closeResponse(HttpResponse resp) {
		if (resp!=null && resp.getEntity()!=null) {
			try {
				resp.getEntity().getContent().close();
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
	
	public static String httpGetForHtml(String url) throws IOException {
		return httpGetForHtml(url, null, null);
	}
	
	public static String httpGetForHtml(HttpHost proxy, String url) throws IOException {
		return httpGetForHtml(proxy, url, null, null);
	}
	
	public static String httpGetForHtml(String url, Map<String, String> params) throws IOException {
		return httpGetForHtml(url, params, null);
	}
	
	public static String httpGetForHtml(HttpHost proxy, String url, Map<String, String> params) throws IOException {
		return httpGetForHtml(proxy, url, params, null);
	}
	
	public static String httpGetForHtml(String url, Map<String, String> params, Map<String, String> headers) throws IOException {				
		HttpResponse response = null;;
		String content = null;
		try {
			response = httpGet(url, params, headers);
			if (response.getEntity()!=null) {
				content = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			closeHttpClient();
		}
		return content;
	}
	
	public static String httpGetForHtml(HttpHost proxy, String url, Map<String, String> params, Map<String, String> headers)
			throws IOException {				
		HttpResponse response = null;;
		String content = null;
		try {
			response = httpGet(proxy, url, params, headers);
			if (response.getEntity()!=null) {
				content = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			closeHttpClient();
		}
		return content;
	}
	
	public static HttpResponse httpGet(HttpHost proxy, String url, Map<String, String> params, Map<String, String> headers)
			throws IOException {
		/*为什么这个地方用PoolingClientConnectionManager构造的httpClient发送get请求会返回500！！！！！！！*/
		HttpClient httpClient = getHttpClient();
		if (proxy!=null) {
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);  
		}
		//使用抢先认证  
		HttpGet get = null;
		if (!CollectionUtils.isEmpty(params)) {
			get = new HttpGet(url + "?" + URLEncodedUtils.format(getNameValuePairsFromMap(params), UTF8));		
		} else {
			get = new HttpGet(url);
		}
		if (headers!=null) {
			for (Entry<String, String> header : headers.entrySet()) {
				get.setHeader(header.getKey(), header.getValue());
			}
		}				
		HttpResponse response = httpClient.execute(get);
		if (response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
			logger.error("httpGet: " + response.getStatusLine().getStatusCode());
		}
		return response;
	}
	
	public static HttpResponse httpGet(String url, Map<String, String> params, Map<String, String> headers)
			throws IOException {
		return httpGet(null, url, params, headers);
	}
	
	public static String httpPostForHtml(String url) throws IOException {
		return httpPostForHtml(url, null, null);
	}
	
	
	public static String httpPostJson(String url, String json) throws IOException {
		return httpPostData(url, json, "application/json");
	}
	
	public static String httpPostText(String url, String text) throws IOException {
		return httpPostData(url, text, "application/json; encoding=utf-8");
	}
	
	public static String httpPostTextForHtml(String url, Map<String, String> headers, String body) throws IOException {
		HttpResponse response = httpPostText(url, headers, body);
		return EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
	}
	
	public static HttpResponse httpPostText(String url, Map<String, String> headers, String body) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		if (body!=null) {
			StringEntity entity = new StringEntity(body, "UTF-8"); //
			/**
			 * 与上面的方式结果不同
			 * StringEntity entity = new StringEntity(data, ContentType.create("application/json; encoding=utf-8"))
			 */
			entity.setChunked(true);
			post.setEntity(entity);
		}
		if (headers!=null) {
			for (Entry<String, String> header : headers.entrySet()) {
				post.setHeader(header.getKey(), header.getValue());
			}
		}
		HttpResponse response = httpClient.execute(post);
		post.releaseConnection();
		logger.debug(response.getStatusLine().getStatusCode());
		return response;
			
	}
	
	public static String httpPostData(String url, String data, String contentType) throws IOException {
		HttpClient httpClient = getHttpClient();
		String content = null;
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity(data, ContentType.create(contentType)));
			HttpResponse response = httpClient.execute(post);
			if (response.getEntity()!=null) {
				content = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			closeHttpClient();
		}
		
		 
		return content;
	}
	
	public static String httpPostForHtml(String url, Map<String, String> params, Map<String, String> headers)
			throws IOException {
		HttpResponse response = null;;
		String content = null;
		try {
			response = httpPost(url, params, headers);
			if (response.getEntity()!=null) {
				content = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			closeHttpClient();
		}
		return content;
	}

	public static HttpResponse httpPost(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
		HttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		if (!CollectionUtils.isEmpty(params)) {
			post.setEntity(new UrlEncodedFormEntity(getNameValuePairsFromMap(params), UTF8));
		}	
		if (headers!=null) {
			for (Entry<String, String> header : headers.entrySet()) {
				post.setHeader(header.getKey(), header.getValue());
			}
		}
		HttpResponse response = httpClient.execute(post);
		return response;
	}
	
	private static List<NameValuePair> getNameValuePairsFromMap(
			Map<String, String> params) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		if (!CollectionUtils.isEmpty(params)) {
			for (Entry<String, String> e : params.entrySet()) {
				pairs.add(new BasicNameValuePair(e.getKey(), e.getValue()));
			}
		}		
		return pairs;
	}
}
