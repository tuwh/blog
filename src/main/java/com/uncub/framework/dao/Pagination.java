package com.uncub.framework.dao;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 必须继承自RowBounds，否则mybatis会报错
 * @param <E>
 */
public class Pagination<E> extends RowBounds{
    private int DEFAULT_PAGE_SIZE = 20;
    private int DEFAULT_END_ROW = 20;
    private int DEFAULT_START_ROW = 0;
    private int DEFAULT_CURRENT_PAGE = 1;

    private int pageSize = DEFAULT_PAGE_SIZE;
    private int startRow = DEFAULT_START_ROW;
    private int endRow = DEFAULT_END_ROW;
    private long total;
    private int pages;
    private int currentPage = DEFAULT_CURRENT_PAGE;

    private List<E> result;

    public Pagination(){

    }

    public Pagination(int pages, int pageSize) {
        this.pages = pages;
        this.pageSize = pageSize;
        this.startRow = pages > 0 ? (pages - 1) * pageSize : 0;
        this.endRow = pages * pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pages=" + pages +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int getOffset() {
        return startRow;
    }

    @Override
    public int getLimit() {
        return pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }
}
