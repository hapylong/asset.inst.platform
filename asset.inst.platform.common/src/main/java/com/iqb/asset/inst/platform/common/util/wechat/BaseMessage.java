/**
* @Copyright (c) http://www.iqianbang.com/  All rights reserved.
* @Description: TODO
* @date 2016年9月5日 上午10:07:44
* @version V1.0 
*/
package com.iqb.asset.inst.platform.common.util.wechat;

/**
 * 请求消息基类（普通用户 -> 公众帐号）
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
public class BaseMessage {
    /** 
     * 开发者微信号 
     */  
    private String ToUserName;
  
    /** 
     * 发送方帐号（一个OpenID） 
     */  
    private String FromUserName;
  
    /** 
     * 消息创建时间 （整型） 
     */  
    private long CreateTime;
  
    /** 
     * 消息类型 
     */  
    private String MsgType;
  
    /** 
     * 消息ID，64位整型 
     */  
    private long MsgId;
  
    public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(long createTime) {  
        CreateTime = createTime;  
    }  
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }  
  
    public long getMsgId() {  
        return MsgId;  
    }  
  
    public void setMsgId(long msgId) {  
        MsgId = msgId;  
    } 
}
