package com.iqb.asset.inst.platform.service.sms.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.annotation.ConcernProperty.ConcernActionScope;
import com.iqb.asset.inst.platform.common.base.SysServiceReturnInfo;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.SpringUtil;
import com.iqb.asset.inst.platform.common.util.apach.ObjCheckUtil;
import com.iqb.asset.inst.platform.common.util.date.DateUtil;
import com.iqb.asset.inst.platform.common.util.sms.SendSmsUtil;
import com.iqb.asset.inst.platform.common.util.sys.Attr.SmsConst;
import com.iqb.asset.inst.platform.data.bean.conf.SysSmsConfig;
import com.iqb.asset.inst.platform.data.bean.sms.SmsBean;
import com.iqb.asset.inst.platform.service.sms.ISmsEntry;
import com.iqb.asset.inst.platform.service.sms.ISmsService;

/**
 * 
 * Description: 短信服务实现
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2016年12月5日    wangxinbang       1.0        1.0 Version
 * </pre>
 */
@Service
public class SmsEntryImpl implements ISmsEntry {

    private static final Logger logger = LoggerFactory.getLogger(SmsEntryImpl.class);

    private ISmsService smsService;

    @Autowired
    private BaseConfig baseConfig;

    @Override
    public void sendSms(SysSmsConfig sysSmsConfig, SmsBean smsBean)
            throws IqbException {
        try {
            ObjCheckUtil.checkConcernProperty(smsBean, ConcernActionScope.SMS);
        } catch (Exception e) {
            throw new IqbException(SysServiceReturnInfo.SYS_SMS_SEND_FAIL_01010019);
        }
        logger.info("发送短信开始：" + JSONObject.toJSONString(smsBean));
        /** 路由短信通道 **/
        this.routeSmsAccess(smsBean);
        /** 渲染短信模板 **/
        String wechatNo = smsBean.getWechatNo();
        if(!StringUtils.isEmpty(wechatNo) && Integer.parseInt(wechatNo) == 11){
            this.formatSmsTemplateForQiKu(smsBean);
        }else{
            this.formatSmsTemplate(smsBean);
        }
        
        // 升级短信通道 将smsBean 转json格式
        String smsJson = getSmsJson(sysSmsConfig, smsBean);
        logger.info("短信内容为:{}", smsJson);
        /** 启动线程发送短信 **/
        logger.info("短信通道->本地");
        this.sendSmsOnThread(sysSmsConfig, smsJson);

    }

    /**
     * Description:
     * @author haojinlong
     * @param objs
     * @param request
     * @return
     * 2018年9月13日
     * @throws IqbException 
     */
    private void formatSmsTemplateForQiKu(SmsBean smsBean) throws IqbException {
        String msgTemplate = null;
        switch (smsBean.getSmsType()) {
            case SmsConst.Sms_Type_Reg:
                msgTemplate = baseConfig.getSmsMessageTypeRegForQK();
                msgTemplate = this.charSetMsgTemplate(msgTemplate);
                smsBean.setSmsContent(msgTemplate.replace("verifyCode", smsBean.getCode()));
                break;

            case SmsConst.Sms_Type_Reset:
                msgTemplate = baseConfig.getSmsMessageTypeResetQK();
                msgTemplate = this.charSetMsgTemplate(msgTemplate);
                smsBean.setSmsContent(msgTemplate.replace("verifyCode", smsBean.getCode()));
                break;

            case SmsConst.Sms_Type_Reg_Succ:
                msgTemplate = baseConfig.getSmsMessageTypeRegSuccQK();
                msgTemplate = this.charSetMsgTemplate(msgTemplate);
                msgTemplate = msgTemplate.replace("regId", smsBean.getRegId());
                msgTemplate =
                        msgTemplate.replace("regTime", DateUtil.getDateString(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
                smsBean.setSmsContent(msgTemplate);
                break;
                
            default:
                throw new IqbException(SysServiceReturnInfo.SYS_SMS_NO_MSG_TYPE_01010017);
        }

    }

    private String getSmsJson(SysSmsConfig sysSmsConfig, SmsBean smsBean) {
        Map<String, String> map = new HashMap<>();
        map.put("account", sysSmsConfig.getSmsName());
        map.put("password", sysSmsConfig.getSmsPswd());
        map.put("msg", smsBean.getSmsContent());
        map.put("phone", smsBean.getRegId());
        map.put("report", "true");
        return JSONObject.toJSONString(map);
    }

    /**
     * 
     * Description: 设置短信
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月5日 下午5:39:31
     */
    private void formatSmsTemplate(SmsBean smsBean) throws IqbException {
        String msgTemplate = null;
        switch (smsBean.getSmsType()) {
            case SmsConst.Sms_Type_Reg:
                msgTemplate = baseConfig.getSmsMessageTypeReg();
                msgTemplate = this.charSetMsgTemplate(msgTemplate);
                smsBean.setSmsContent(msgTemplate.replace("verifyCode", smsBean.getCode()));
                break;

            case SmsConst.Sms_Type_Reset:
                msgTemplate = baseConfig.getSmsMessageTypeReset();
                msgTemplate = this.charSetMsgTemplate(msgTemplate);
                smsBean.setSmsContent(msgTemplate.replace("verifyCode", smsBean.getCode()));
                break;

            case SmsConst.Sms_Type_Reg_Succ:
                msgTemplate = baseConfig.getSmsMessageTypeRegSucc();
                msgTemplate = this.charSetMsgTemplate(msgTemplate);
                msgTemplate = msgTemplate.replace("regId", smsBean.getRegId());
                msgTemplate =
                        msgTemplate.replace("regTime", DateUtil.getDateString(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));
                smsBean.setSmsContent(msgTemplate);
                break;
            default:
                throw new IqbException(SysServiceReturnInfo.SYS_SMS_NO_MSG_TYPE_01010017);
        }
    }

    /**
     * 
     * Description: 短信模板转换编码
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2017年1月5日 下午2:55:46
     */
    private String charSetMsgTemplate(String msgTemplate) throws IqbException {
        try {
            msgTemplate = new String(msgTemplate.getBytes("ISO-8859-1"), "UTF-8");
            msgTemplate = new String(msgTemplate.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("短信摸版替换异常", e);
            throw new IqbException(SysServiceReturnInfo.SYS_SMS_NO_MSG_TEMP_01010018);
        }
        return msgTemplate;
    }

    /**
     * 
     * Description: 线程发送短信
     * 
     * @param
     * @return void
     * @throws
     * @Author wangxinbang Create Date: 2016年12月5日 下午5:12:09
     */
    private void sendSmsOnThread(final SysSmsConfig sysSmsConfig, final String requestJson) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String resultValue = SendSmsUtil.batchSend(sysSmsConfig.getSmsUrl(), requestJson);
                    logger.info("---本地发送短信返回信息--{}",resultValue);
                } catch (Exception e) {
                    logger.error("发送短信异常:{}", requestJson, e);
                }
            }
        }).start();
    }

    /**
     * 
     * Description: 路由短信通道
     * 
     * @param
     * @return void
     * @throws IqbException
     * @throws
     * @Author wangxinbang Create Date: 2016年12月5日 下午2:44:18
     */
    private void routeSmsAccess(SmsBean smsBean) throws IqbException {
        switch (smsBean.getOperator()) {
            case SmsConst.Operator_ChuangLan:
                smsService = SpringUtil.getBean(ChuangLanSmsImpl.class);
                break;

            default:
                smsService = SpringUtil.getBean(ChuangLanSmsImpl.class);
                break;
        }
        if (smsService == null) {
            throw new IqbException(SysServiceReturnInfo.SYS_SMS_NO_OPERA_01010016);
        }
    }

}
