/**
* @Copyright (c) http://www.iqianbang.com/  All rights reserved.
* @Description: TODO
* @date 2016年9月5日 上午10:08:56
* @version V1.0 
*/
package com.iqb.asset.inst.platform.common.util.wechat;

/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class TextMessage extends BaseMessage {

    /** 
     * 文本消息
     * 消息内容 
     */  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }
}
