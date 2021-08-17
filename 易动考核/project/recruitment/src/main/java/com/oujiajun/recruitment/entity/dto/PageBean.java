package com.oujiajun.recruitment.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private Integer currentPage;
    private Integer startIndex;
    private Integer pageSize=5;
    private Integer totalCount;
    private Integer totalPage;

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.startIndex=(this.currentPage-1)*this.pageSize;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        //计算总页数
        this.totalPage=(int)Math.ceil((this.totalCount*1.0/this.pageSize));
    }

}
