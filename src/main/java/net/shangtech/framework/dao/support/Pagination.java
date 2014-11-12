package net.shangtech.framework.dao.support;

import java.util.List;

public class Pagination<T> {
	
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	private Integer pageNo;
	
	private Integer totalPage;
	
	private Integer totalCount;
	
	private Integer start;
	
	private Integer limit;
	
	private List<T> items;
	
	public Pagination(){
		limit = DEFAULT_PAGE_SIZE;
		setPageNo(1);
	}
	
	public Pagination(Integer limit){
		if(limit == null){
			limit = DEFAULT_PAGE_SIZE;
		}
		this.limit = limit;
		setPageNo(1);
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
	
}
