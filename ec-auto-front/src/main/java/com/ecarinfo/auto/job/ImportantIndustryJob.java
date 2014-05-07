package com.ecarinfo.auto.job;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ecarinfo.persist.service.GenericService;

@Component("importantIndustryJob")
public class ImportantIndustryJob {
	private static final Logger logger = Logger.getLogger(ImportantIndustryJob.class);
	private Lock lock = new ReentrantLock();
	
	@Resource
	private GenericService genericService;
//	@Resource
//	private IndustryService industryService;
//	@Resource
//	private SqlSessionFactory sqlSessionFactory;
	
	public void execute() {
		if (lock.tryLock()) {
			try {
				save();
//				 List<TempIAVO> list = industryService .listIndustryArticleForJob();
//				 saveWithBatch(list);
			} catch (Exception e) {
				logger.error("",e);
			} finally {
				lock.unlock();
			}
		}
	}
	// floor(1+rand()*16) 随机数
	void save()	{
		genericService.findListBySql("truncate table tmp_industry_article");
		genericService.findListBySql("INSERT INTO tmp_industry_article(	hot_count,title,url,brand_id,media_id,pub_time) " +
				"SELECT	count(id) as cou,title,url,brand_id,media_id,pub_time FROM	industry_article WHERE brand_id>0  GROUP BY	title");
	}
	
	

	
	// 批量保存
//	private void saveWithBatch(List<TempIAVO> list) {
//		System.err.println("saveWithBatch---");
//		genericService.findListBySql("truncate table tmp_industry_article");
//		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//		int idx = 1;
//		try {
//			Random random = new Random();
//			for (TempIAVO tvo : list) {
//				TmpIndustryArticle article = new TmpIndustryArticle();
//				article.setHotCount((Math.abs(random.nextInt())%12+tvo.getCount()));
//				article.setMediaId(tvo.getMediaId());
//				article.setPubTime(tvo.getPubTime());
//				article.setTitle(tvo.getTitle());
//				article.setBrandId(tvo.getBrandId());
//				article.setUrl(tvo.getUrl());
//				
//				session.insert(TmpIndustryArticleDao.class.getName() + ".insert", article);
//				if (idx % 100 == 0) {
//					session.commit();
//					System.err.println("--done---");
//				}
//				idx++;
//			}
//			session.commit();
//		} catch (Exception e) {
//			if (session != null) {
//				session.rollback();
//			}
//
//			e.printStackTrace();
//
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//	}

	 public static void main(String[] args) {
         Random random = new Random();
         System.out.println((Math.abs(random.nextInt())%12));
    }
}
