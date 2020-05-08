package com.hopu.utils;

import java.util.List;

/**
 * 分页对象
 */
public class PageBean<E> {
    private Integer totalCount; // 总记录数
    private Integer totalPage;  // 总页数
    private List<E> list;  //每页的结果集，可以使用泛型定义封装的对象类型
    private Integer pageNum;  // 当前页面,默认从第1页开始
    private Integer pageSize; // 每页显示数量,默认每页5条

    // 分页按钮用的起始页和终止页
    private Integer begin;
    private Integer end;
    private Integer buttonCount=5;  // 分页按钮显示数

    // 写一个私有方法，专门计算begin和end
    private void math(){
        if(this.totalPage<=this.buttonCount){
            this.begin=1;
            this.end=this.totalPage;
        }else {
            // 判断按钮基数偶数问题
            int pre=0;
            int nex=0;
            if( this.buttonCount%2==0){
                pre=this.buttonCount/2;  //3
//                nex=this.buttonCount/2-1; //2
                nex=pre-1; //2
            }else {
                pre=this.buttonCount/2;
                nex=pre;
            }
            this.begin=this.pageNum-pre;
            this.end=this.pageNum+nex;

            // 特殊情况修正
            if(this.begin<=0){
                this.begin=1;
                this.end=this.buttonCount;
            }
            if(this.end>this.totalPage){
                this.end=this.totalPage;
                this.begin=this.totalPage -this.buttonCount+1;
            }
        }
    }

    public Integer getBegin() {
        // 调用计算方法
        math();

        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
