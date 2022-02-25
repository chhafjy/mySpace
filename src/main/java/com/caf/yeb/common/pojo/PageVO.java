package com.caf.yeb.common.pojo;

import com.github.pagehelper.Page;

/**
 * TODO
 *
 * @author chenhaohao
 * @version 1.0
 * @date 2022/1/17 14:44
 */
public class PageVO {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;

    public PageVO() {
    }

    public PageVO(PageParam page, Long total) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = total;
    }

    public PageVO(Integer pageSize, Long total) {
        this.pageSize = pageSize;
        this.total = total;
    }

    public PageVO(Page page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setPageNum(final Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(final Long total) {
        this.total = total;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageVO)) {
            return false;
        } else {
            PageVO other = (PageVO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$pageNum = this.getPageNum();
                    Object other$pageNum = other.getPageNum();
                    if (this$pageNum == null) {
                        if (other$pageNum == null) {
                            break label47;
                        }
                    } else if (this$pageNum.equals(other$pageNum)) {
                        break label47;
                    }

                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$total = this.getTotal();
                Object other$total = other.getTotal();
                if (this$total == null) {
                    if (other$total != null) {
                        return false;
                    }
                } else if (!this$total.equals(other$total)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageVO;
    }


    public String toString() {
        return "PageVO(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ", total=" + this.getTotal() + ")";
    }
}
