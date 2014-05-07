package com.ecarinfo.auto.front.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import com.ecarinfo.common.utils.PropUtil;

public class SolrUtils {
	private static final Logger logger = Logger.getLogger(SolrUtils.class);
			
	private static final Map<String, HttpSolrServer> soleServerMap = new HashMap<String, HttpSolrServer>();

	public static HttpSolrServer getSolrServer(String modelName) {
		HttpSolrServer server = soleServerMap.get(modelName);
		if (server != null) {
			return server;
		}
		try {
			String baseUrl = PropUtil.getProp("solr.properties", "solr.url");
			if (baseUrl == null) {
				logger.error("----------solr not found!-------,请配置solr.properties");
				return null;
			}
			if (!baseUrl.endsWith("/")) {
				baseUrl += '/';
			}
			baseUrl += modelName;
			server = new HttpSolrServer(baseUrl);
			server.setSoTimeout(1000);
			server.setConnectionTimeout(100);
			server.setDefaultMaxConnectionsPerHost(100);
			server.setMaxTotalConnections(100);
			server.setFollowRedirects(false);
			server.setAllowCompression(true);
			server.setMaxRetries(1);

			// 提升性能采用流输出方式
			server.setRequestWriter(new BinaryRequestWriter());
			soleServerMap.put(modelName, server);
			return server;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
}
