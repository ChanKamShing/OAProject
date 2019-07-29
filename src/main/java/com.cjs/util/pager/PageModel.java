package com.cjs.util.pager;

public class PageModel {
    //分页默认一页4条数据
    public static final int PAGE_DEFAULT_SIZE = 4;
    //总数据条数
    private long recordCount;
    //当前页面
    private int pageIndex;
    //每页多少条数据
    private int pageSize = PAGE_DEFAULT_SIZE;
    //总页数
    private int totalSize;

    private int firstLimitParam;

    public long getRecordCount() {
        this.recordCount = this.recordCount<=0?0:this.recordCount;
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageIndex() {
        this.pageIndex = this.pageIndex<=0?1:this.pageIndex;
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        this.pageSize = this.pageSize<=PAGE_DEFAULT_SIZE?PAGE_DEFAULT_SIZE:this.pageSize;
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        if (this.getRecordCount() <= 0) {
            totalSize = 0;
        } else {
            totalSize = (int) ((this.getRecordCount()-1)/this.getPageSize()+1);
        }
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getFirstLimitParam(){
        return (this.getPageIndex() - 1) * this.getPageSize();
    }

    public void setFirstLimitParam(int firstLimitParam) {
        this.firstLimitParam = firstLimitParam;
    }
}
