package com.ecarinfo.auto.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecarinfo.auto.dao.IndustryCloudDao;
import com.ecarinfo.auto.po.Cloud;
import com.ecarinfo.auto.service.CloudService;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.CloudVO;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;

@Service
public class CloudServiceImpl implements CloudService {

	@Resource
	private GenericDao genericDao;
	
	@Resource
	private IndustryCloudDao industryCloudDao;
	
	
	/**
	 * @laochen industry
	 */
	@Override
	public List<CloudVO> findHotClouds(Integer maxRows,String ctx) {
		if(maxRows <=0) {
			maxRows = 30;
		}
		if(maxRows > 30) {
			maxRows = 30;
		}
		List<Cloud> list = genericDao.findList(Cloud.class, new Criteria()
		.setPage(1, maxRows));
		List<CloudVO> voList = new ArrayList<CloudVO>();
		for(Cloud c:list) {
			CloudVO vo = new CloudVO();
			vo.setId(c.getId());
			vo.setText(c.getTitle());
			vo.setUrl(ctx+"/industry/listCloud?cloudId="+c.getId());//TODO 此处的URL不应该直接写在服务层，服务层只负责出数据
			vo.setWeight(c.getHotGrade());
			voList.add(vo);
		}
		return voList;
	}
	
	/**
	 * @laochen industry
	 */
	@Override
	public ECPage<ArticleSimpleListVO> findArticlesByCloudId(final Long cloudId,Integer pageNo,Integer pageSize) {

		ECPage<ArticleSimpleListVO> page = PagingManager.list(new Paginable<ArticleSimpleListVO>(pageNo,pageSize) {

			@Override
			public List<ArticleSimpleListVO> findList() {
				return RowMapperUtils.map2List(industryCloudDao.findIndustryArticleByCloudId(cloudId, this.getOffset(), this.getRowsPerPage()), ArticleSimpleListVO.class);
			}

			@Override
			public Long count() {
				Long c = industryCloudDao.countIndustryArticleByCloudId(cloudId);
				if(c == null) {
					return 0l;
				}
				return c;
			}
		});
		return page;
	}

	
}
