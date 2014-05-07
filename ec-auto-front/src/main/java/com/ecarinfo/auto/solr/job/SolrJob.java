package com.ecarinfo.auto.solr.job;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.stereotype.Component;

import com.ecarinfo.auto.front.util.SolrUtils;

@Component("solrJob")
public class SolrJob {
	private static final Logger logger = Logger.getLogger(SolrJob.class);
	private Lock lock = new ReentrantLock();

	public void execute() {
		if (lock.tryLock()) {
			try {
				deltaImport(false,"industry_article","industryArticle");
				Thread.sleep(1000);
				deltaImport(false,"simple_article","simpleArticle");
			} catch (InterruptedException e) {
				logger.error("",e);
			} finally {
				lock.unlock();
			}
		}
	}

	private void deltaImport(boolean isFullImport,String coreName,String entity) {
		logger.info("dataimport[isFullImport="+isFullImport+",coreName="+coreName+",entity="+entity);
		HttpSolrServer server = SolrUtils.getSolrServer(coreName);
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("qt", "/dataimport");
		if(isFullImport) {
			params.set("command", "full-import");
			params.set("clean", "true");
		} else {
			params.set("command", "delta-import");
			params.set("clean", "false");
		}
		
		params.set("commit", "true");
		params.set("wt", "json");
		params.set("indent", "true");
		params.set("verbose", "false");
		params.set("optimize", "false");
		params.set("debug", "false");
		params.set("entity", entity);
		
		try {
			server.query(params, METHOD.POST);
		} catch (SolrServerException e) {
			logger.error("", e);
		}
	}
	
	public static void main(String[] args) {
		SolrJob job = new SolrJob();
		job.execute();
	}
}
