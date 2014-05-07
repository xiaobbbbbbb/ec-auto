package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

public class SolrSimpleArticleVO implements Serializable {

	private static final long serialVersionUID = -8780045208095228178L;
	private SolrResponse response;
	private SolrResponseHeader responseHeader;

	public SolrResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(SolrResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public SolrResponse getResponse() {
		return response;
	}

	public void setResponse(SolrResponse response) {
		this.response = response;
	}

	public static class SolrResponseHeader {
		private SolrParam params;

		public SolrParam getParams() {
			return params;
		}

		public void setParams(SolrParam params) {
			this.params = params;
		}

		public static class SolrParam {
			private Integer start;
			private Integer rows;
			private String q;

			public Integer getStart() {
				return start;
			}

			public void setStart(Integer start) {
				this.start = start;
			}

			public Integer getRows() {
				return rows;
			}

			public void setRows(Integer rows) {
				this.rows = rows;
			}

			public String getQ() {
				return q;
			}

			public void setQ(String q) {
				this.q = q;
			}

		}
	}

	public static class SolrResponse {
		private Integer numFound;
		private Integer start;
		private List<SolrDoc> docs;

		public Integer getNumFound() {
			return numFound;
		}

		public void setNumFound(Integer numFound) {
			this.numFound = numFound;
		}

		public Integer getStart() {
			return start;
		}

		public void setStart(Integer start) {
			this.start = start;
		}

		public List<SolrDoc> getDocs() {
			return docs;
		}

		public void setDocs(List<SolrDoc> docs) {
			this.docs = docs;
		}

	}

	public static class SolrDoc {
		private Long id;
		private String title;
		private String pubTime;// 车牌名
		private String[] keyword;// 文章情感值
		private String mediaName;// 来源媒体名字
		private String url;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getPubTime() {
			return pubTime;
		}

		public void setPubTime(String pubTime) {
			this.pubTime = pubTime;
		}

		public String[] getKeyword() {
			return keyword;
		}

		public void setKeyword(String[] keyword) {
			this.keyword = keyword;
		}

		public String getMediaName() {
			return mediaName;
		}

		public void setMediaName(String mediaName) {
			this.mediaName = mediaName;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

}
