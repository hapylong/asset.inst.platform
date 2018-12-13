/**
  * Copyright 2017 bejson.com 
  */
package com.iqb.asset.inst.platform.service.dto.allbill;
import java.util.List;

/**
 * Auto-generated: 2017-12-18 12:0:37
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result {

    private int currentPage;
    private int numPerPage;
    private int totalCount;
    private List<RecordList> recordList;
    private int pageCount;
    private int beginPageIndex;
    private int endPageIndex;
    private String countResultMap;
    public void setCurrentPage(int currentPage) {
         this.currentPage = currentPage;
     }
     public int getCurrentPage() {
         return currentPage;
     }

    public void setNumPerPage(int numPerPage) {
         this.numPerPage = numPerPage;
     }
     public int getNumPerPage() {
         return numPerPage;
     }

    public void setTotalCount(int totalCount) {
         this.totalCount = totalCount;
     }
     public int getTotalCount() {
         return totalCount;
     }

    public void setRecordList(List<RecordList> recordList) {
         this.recordList = recordList;
     }
     public List<RecordList> getRecordList() {
         return recordList;
     }

    public void setPageCount(int pageCount) {
         this.pageCount = pageCount;
     }
     public int getPageCount() {
         return pageCount;
     }

    public void setBeginPageIndex(int beginPageIndex) {
         this.beginPageIndex = beginPageIndex;
     }
     public int getBeginPageIndex() {
         return beginPageIndex;
     }

    public void setEndPageIndex(int endPageIndex) {
         this.endPageIndex = endPageIndex;
     }
     public int getEndPageIndex() {
         return endPageIndex;
     }

    public void setCountResultMap(String countResultMap) {
         this.countResultMap = countResultMap;
     }
     public String getCountResultMap() {
         return countResultMap;
     }

}