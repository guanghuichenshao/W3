package com.zyc.po;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
public class PageBean implements Serializable {
    private int maxPage;
    private int currentPage;
    private long maxRow;
    private int rowPerPage;
    private List<Article> data;

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(long maxRow) {
        this.maxRow = maxRow;
    }

    public int getRowPerPage() {
        return rowPerPage;
    }

    public void setRowPerPage(int rowPerPage) {
        this.rowPerPage = rowPerPage;
    }

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }
}
