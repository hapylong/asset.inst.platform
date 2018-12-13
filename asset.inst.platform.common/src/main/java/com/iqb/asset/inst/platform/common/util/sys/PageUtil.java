package com.iqb.asset.inst.platform.common.util.sys;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * Description: 分页工具类
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年8月23日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class PageUtil{
    
    private static Integer DEFAULT_PAGE_NUM = 1;  // 默认页码
    private static Integer DEFAULT_PAGE_SIZE = 10; // 默认页大小
    
    public static String PageNum = "pageNum";
    public static String PageSize = "pageSize";
    

    /**
     * 
     * Description: 获取分页参数
     * @param
     * @return Map<String,Integer>
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月23日 上午11:11:54
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Integer> getPagePara(Map map){
        int pageNum = DEFAULT_PAGE_NUM;
        int pageSize = DEFAULT_PAGE_SIZE;
        Map<String, Integer> pageMap = new HashMap<String, Integer>();
        if(map != null){
            String pageNumStr = ObjectUtils.toString(map.get(PageNum));
            String pageSizeStr = ObjectUtils.toString(map.get(PageSize));
            pageNum = StringUtils.isEmpty(pageNumStr) ? DEFAULT_PAGE_NUM : Integer.parseInt(pageNumStr);
            pageSize = StringUtils.isEmpty(pageSizeStr) ? DEFAULT_PAGE_SIZE : Integer.parseInt(pageSizeStr);
        }
        pageMap.put("pageNum", pageNum);
        pageMap.put("pageSize", pageSize);
        return pageMap;
    }
    
    /**
     * 
     * Description: 获取总页数
     * @param
     * @return Integer
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年8月24日 下午3:21:40
     */
    public static Integer getPages(int count, int PageSize){
        return (count-1)/PageSize+1;
    }

}
