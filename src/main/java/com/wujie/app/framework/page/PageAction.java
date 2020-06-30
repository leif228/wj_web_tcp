package com.wujie.app.framework.page;

/**
 * 
 *************************************************
 *	分页实体
 * @author  ?->MengDaNai                   
 * @version 1.0                
 * @date    2019年2月18日 创建文件                                            
 * @See                            
 *************************************************
 */
public class PageAction {
	
	/** 记录总数 **/
	protected Integer totalCount = 0;
	
	/** 总页数 **/
	protected Integer totalPage = 0;
	
	/**	每页记录数	**/
	protected Integer pageSize = 10;
	
	/** 当前页数 **/
	protected Integer currentPage = 1;
	
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




	@Override
	public String toString() {
		return "PageAction [totalCount=" + totalCount + ", totalPage=" + totalPage + ", pageSize=" + pageSize
				+ ", currentPage=" + currentPage + ", pageCount=" + pageCount
				+ ", countPage=" + countPage + ", keyword=" + keyword + "]";
	}

}
