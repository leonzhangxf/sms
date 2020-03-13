package com.leonzhangxf.sms.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据实体
 *
 * @author leonzhangxf 20180907
 */
@ApiModel("分页数据实体")
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_CURRENT_PAGE = 1;

    public static final int DEFAULT_PAGE_SIZE = 10;

    @ApiModelProperty("数据结果集合")
    private List<T> items;

    @ApiModelProperty("总记录数")
    private int totalCount;// 总记录数

    @ApiModelProperty("总页数")
    private int totalPageCount;// 总页数

    @ApiModelProperty("每页记录个数")
    private int pageSize = DEFAULT_PAGE_SIZE;// 每页记录个数

    @ApiModelProperty("当前页数")
    private int currentPage = DEFAULT_CURRENT_PAGE;// 当前页数

    @ApiModelProperty("当前页是否是第一页")
    private boolean firstPage;
    @ApiModelProperty("当前页是否是最后一页")
    private boolean lastPage;

    @ApiModelProperty("当前页数前一页")
    private int prevPage;//当前页数前一页
    @ApiModelProperty("当前页数下一页")
    private int nextPage;//当前页数下一页

    @ApiModelProperty("当前页第一条记录在总结果集中的位置，序号从0开始")
    private int offset;

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置, 序号从0开始.
     */
    public int getOffset() {
        return ((this.currentPage - 1) * this.pageSize);
    }

    /**
     * 当前页是否是第一页
     *
     * @return
     */
    public boolean isFirstPage() {
        firstPage = this.currentPage == 1 ? true : false;
        return firstPage;
    }

    /**
     * 当前页是否是最后一页
     *
     * @return
     */
    public boolean isLastPage() {
        lastPage = this.currentPage >= this.totalPageCount ? true : false;
        return lastPage;
    }

    /**
     * @param items       包含一页的数据。
     * @param totalCount  总数据数
     * @param pageSize    每页个数
     * @param currentPage 当前页码 从1开始。
     */
    public Page(List<T> items, int totalCount, int pageSize, int currentPage) {
        this.items = items;
        this.pageSize = pageSize > 1 ? pageSize : 1;
        this.currentPage = currentPage > 0 ? currentPage : 1;
        this.totalCount = totalCount;
        this.calProperties();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("totalCount:" + totalCount);
        sb.append(" totalPageCount:" + totalPageCount);
        sb.append(" pageSize:" + pageSize);
        sb.append(" currentPage:" + currentPage);
        sb.append(" items.size:" + items.size());
        sb.append(" prevPage:" + prevPage);
        sb.append(" nextPage:" + nextPage);
        return sb.toString();
    }

    /**
     * 获取上一页页码，不会小于1。
     *
     * @return
     */
    public int getPrevPage() {
        int pre = this.currentPage - 1;
        pre = pre < 1 ? 1 : pre;
        pre = pre < totalPageCount ? pre : totalPageCount;
        this.prevPage = pre;
        return prevPage;
    }

    /**
     * 获取下一页页码，不会超过总页数。
     *
     * @return
     */
    public int getNextPage() {
        int next = this.currentPage + 1;
        next = next > 1 ? next : 1;
        next = next < totalPageCount ? next : totalPageCount;
        this.nextPage = next;
        return nextPage;
    }

    /**
     * 当前页码
     *
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * 每页个数
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.calProperties();
    }

    /**
     * 总记录数
     *
     * @return
     */
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.calProperties();
    }

    /**
     * 总页数
     *
     * @return
     */
    public int getTotalPageCount() {
        return totalPageCount;
    }

    private void calProperties() {
        if (this.totalCount > 0) {
            this.totalPageCount = this.totalCount / this.pageSize;
            if (this.totalCount % this.pageSize > 0)
                this.totalPageCount++;
        } else {
            this.totalCount = 0;
            this.totalPageCount = 0;
        }
    }

}

