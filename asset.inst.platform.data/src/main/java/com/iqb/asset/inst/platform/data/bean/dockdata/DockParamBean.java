package com.iqb.asset.inst.platform.data.bean.dockdata;

import com.iqb.asset.inst.platform.data.bean.BaseEntity;

/**
 * 
 * Description: 医美数据字典bean
 * @author wangxinbang
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月14日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class DockParamBean extends BaseEntity{
    
    /** 类型  **/
    private String type;
    /** 代码  **/
    private String code;
    /** 内容  **/
    private String content;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
