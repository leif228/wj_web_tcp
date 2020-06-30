package com.wujie.app.business.util.page;


import com.wujie.app.framework.constant.SystemConstant;

/**
 * 分页实体
 * @ClassName: PageAction.java
 * @author MengDaNai
 * @version 1.0
 * @Date 2019年2月18日 下午5:42:38
 */
public class PageAction {

	/** 记录总数 **/
	protected Integer totalCount = 0;

	/** 总页数 **/
	protected Integer totalPage = 0;

	/**	每页记录数	**/
	protected Integer pageSize = SystemConstant.DEFAULT_PAGESIZE_10;

	/** 当前页数 **/
	protected Integer currentPage = 1;

	/** 开始的记录 **/
	protected Integer startRow = 0;

	protected Integer pageCount = 0;

	protected Integer countPage;

	/** 模糊搜索 **/
	private String keyword;

	PageAction(){}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getCountPage() {
		return countPage;
	}

	public void setCountPage(Integer countPage) {
		this.countPage = countPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**	使所有参数变为有效值 **/
	public void validateSite() {
		// 总数不能小于0
		if (totalCount < 1) {
			totalCount = 0;
		}
		// 页大小不能小于1 不能大于1000
		if (pageSize < 1) {
			pageSize = 1;
		}
		if (pageSize > 1000) {
			pageSize = 1000;
		}
		// 取得页总数
		totalPage = (totalCount + pageSize - 1) / pageSize;
		// 取得当前页
		if (currentPage < 1) {
			currentPage = 1;
			countPage=currentPage;
		}
		// 取得记录起启位置
		startRow = (currentPage - 1) * pageSize;
	}

	@Override
	public String toString() {
		return "PageAction [totalCount=" + totalCount + ", totalPage=" + totalPage + ", pageSize=" + pageSize
				+ ", currentPage=" + currentPage + ", startRow=" + startRow + ", pageCount=" + pageCount
				+ ", countPage=" + countPage + ", keyword=" + keyword + "]";
	}

}
