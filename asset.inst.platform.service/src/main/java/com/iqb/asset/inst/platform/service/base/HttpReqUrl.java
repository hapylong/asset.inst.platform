package com.iqb.asset.inst.platform.service.base;


/**
 * 
 * Description: http的请求url
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月6日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class HttpReqUrl {
    
    /** 请求地址  **/
    private String url;
    /** 说明  **/
    private String commont;
    
    public HttpReqUrl(String url, String commont) {
        this.url = url;
        this.commont = commont;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommont() {
        return commont;
    }

    public void setCommont(String commont) {
        this.commont = commont;
    }

}
