package net.shangtech.framework.orm.dao.support;

import java.util.List;

import org.springframework.util.Assert;

public class Pagination<T> {
	
	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	private Integer pageNo;
	
	private Integer totalPage;
	
	private Integer totalCount;
	
	private Integer start;
	
	private Integer limit;
	
	private List<T> items;
	
	private Integer page;
	
	private Integer rows;
	
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
		Assert.notNull(pageNo, "can not set a null pageNo");
		this.pageNo = pageNo;
		start = limit * (pageNo - 1);
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
		Assert.notNull(totalCount, "totalCount can not be null");
		this.totalCount = totalCount;
		totalPage = new Double(Math.ceil(totalCount/limit)).intValue();
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		Assert.notNull(start, "start of page can not be null");
		this.start = start;
		pageNo = start/limit + 1;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
		start = limit * (pageNo - 1);
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
	
	public static void main(String[] args){
		for(int i = 0; i < 40; i++){
			System.out.println(i+"/10="+(i/10));
		}
	}
	
	public boolean getIsFirst(){
		return pageNo == 1;
	}
	
	public boolean getIsLast(){
		return pageNo == totalPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
		this.setPageNo(page);
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
		this.setLimit(rows);
	}
	
}
