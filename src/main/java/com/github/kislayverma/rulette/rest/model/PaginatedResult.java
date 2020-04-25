package com.github.kislayverma.rulette.rest.model;

import java.io.Serializable;
import java.util.List;

public class PaginatedResult<T> implements Serializable {
    private List<T> data;
    private int pageNum;
    private int pageSize;
    private int totalRecordCount;
    private boolean hasNext;
    private boolean hasPrev;

    public PaginatedResult() {
    }

    public PaginatedResult(List<T> rules, int pageNum, int pageSize, int totalRecordCount, boolean hasNext, boolean hasPrev) {
        this.data = rules;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalRecordCount = totalRecordCount;
        this.hasNext = hasNext;
        this.hasPrev = hasPrev;
    }

    public List<T> getData() {
        return data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }
}
