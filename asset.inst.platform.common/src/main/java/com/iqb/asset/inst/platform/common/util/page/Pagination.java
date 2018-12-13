package com.iqb.asset.inst.platform.common.util.page;

import java.util.List;

public class Pagination {
	// 分页查询默认每页记录条数
	private int defaultPageSize = 10;
	
	// 页码是否循环显示，即最后一页循环到第一页
	private boolean isLoopPage = false;
	
	// 总记录数
	private int recordCount;
	
	// 总页数
	private int pages;
	
	// 当前页码
	private int pageNo;
	
	// 每页显示的记录数
	private int pageSize;
	
	// 当前页记录偏移量
	private int position;
	
	@SuppressWarnings("rawtypes")
	private List datas;

	public Pagination(int recordCount, int pageNo, int pageSize) {
		this.recordCount = recordCount;
		initPageSize(pageSize);
		initTotalPages();
		initPageNo(pageNo);
		initPosition();
	}
	
	/**
	 * 获取每页返回的记录数
	 * 	如果是0表示不分页，返回总记录数totalRecord
	 * 	如果是负数或者大于总记录数，则返回默认的每页记录数defaultPageSize
	 * 	如果是其他情况，则返回指定的每页记录数
	 * @param pageSize		每页记录数
	 * @return
	 */
	private void initPageSize(int pageSize) {
		if (pageSize == 0) {
			this.pageSize = this.recordCount;
		} else if (pageSize <= 0) {
			this.pageSize = this.defaultPageSize;
		} else {
			this.pageSize = pageSize;
		}
	}
	
	/**
	 * 初始化总页数
	 */
	private void initTotalPages() {
		this.pages = (int)Math.ceil((double)this.recordCount / this.pageSize);
	}
	
	/**
	 * 初始化当前页码，如果大于0且不大于总页数则返回指定页码，否则返回第一页
	 * @param pageNo	页码
	 */
	private void initPageNo(int pageNo) {
		if (pageNo > 0 && pageNo <= this.pages) {
			this.pageNo = pageNo;
		} else {
			this.pageNo = 1;
		}
	}
	
	/**
	 * 通过页码值计算查询记录偏移量
	 */
	private void initPosition() {
		this.position = (pageNo - 1) * pageSize;
	}

	public boolean isLoopPage() {
		return isLoopPage;
	}

	public void setLoopPage(boolean isLoopPage) {
		this.isLoopPage = isLoopPage;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public int getPages() {
		return pages;
	}

	public int getPageNo() {
		return this.pageNo;
	}
	
	/**
	 * 获取下一页码值
	 * 	如果当前页是最后一页，页码循环是返回第一页，否则返回最后一页；
	 * 	如果当前页不是最后一页，那么返回当前页的下一页
	 * @return
	 */
	public int getNextPageNo() {
		if (this.pageNo == this.pages) {
			return this.isLoopPage ? 1 : this.pageNo;
		} else {
			return this.pageNo + 1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取当前页实际记录数。如果是最后一页则为记录总数减去当前页记录偏移量
	 * @return
	 */
	public int getActualPageSize() {
		return (this.position + this.pageSize) > this.recordCount ? (this.recordCount - this.position)
				: this.pageSize;
	}

	public int getPosition() {
		return position;
	}

	/**
	 * 计算下一页的记录偏移量
	 * @return
	 */
	public int getNextPagePosition() {
		return (getNextPageNo() - 1) * this.pageSize;
	}

	@SuppressWarnings("unchecked")
	public List<Object> getDatas() {
		return datas;
	}

	@SuppressWarnings("rawtypes")
	public void setDatas(List datas) {
		this.datas = datas;
	}
}