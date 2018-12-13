package com.iqb.asset.inst.platform.front.protu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.util.sms.SendSmsUtil;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.bean.DataBillBean;
import com.iqb.asset.inst.platform.deal_online_data.generateBillInfo.dao.DataBillDao;
import com.iqb.asset.inst.platform.front.stat.AppConfig;

/**
 * 
 * Description: 发短信临时服务
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年1月10日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings("resource")
public class SmsBSend{

    private static final Logger logger = LoggerFactory.getLogger(SmsBSend.class);

    public void test() {
        int i = 0;
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(AppConfig.class);
        webApplicationContext.afterPropertiesSet();
        DataBillDao bbd = webApplicationContext.getBean(DataBillDao.class);
        List<DataBillBean> smsUserList = bbd.getInstBak2VcInfoList4Sms();
        for(DataBillBean dataBillBean : smsUserList){
            sendSmsOnThread(dataBillBean.getRegId(), null, Integer.parseInt(dataBillBean.getOpenId()));
            i++;
        }
        System.out.println(i);
    }

    private static void sendSmsOnThread(final String regId, final String smsContent, final Integer wechatNo) {
//        try {
//            String url = "";
//            String name = "";
//            String pwd = "";
//            String smsC = "";
//            if(wechatNo == 2){
//                url = "http://222.73.117.158/msg/HttpBatchSendSM";
//                name = "aiqianb";
//                pwd = "Tch123456";
//                smsC = "您好！为了进一步提升我司产品的使用体验，近期我司对手机客户端系统进行了全面的改版升级。本次系统升级后，手机客户端功能有所增加，请您尽快登录进行体验。今后，您可通过“爱车帮帮手”查询本人的每期应还款金额及还款时间，并可随时还款。以后请以“爱车帮帮手”中显示的还款事宜做为还款依据，以免发生逾期。由此可能给您带来的不便，敬请谅解。";
//                String res = SendSmsUtil.batchSend(
//                    url,
//                    name,
//                    pwd,
//                    regId,
//                    1,
//                    smsC);
//                System.out.println(res);
//            }else{
//                url = "http://222.73.117.158/msg/HttpBatchSendSM";
//                name = "huah123";
//                pwd = "Admin888";
//                smsC = "您好！为了进一步提升我司产品的使用体验，近期我司对手机客户端系统进行了全面的改版升级。本次系统升级后，手机客户端功能有所增加，请您尽快登录进行体验。今后，您可通过“花的花世界”查询本人的每期应还款金额及还款时间，并可随时还款。以后请以“花的花世界”中显示的还款事宜做为还款依据，以免发生逾期。由此可能给您带来的不便，敬请谅解。";
//                String res = SendSmsUtil.batchSend(
//                    url,
//                    name,
//                    pwd,
//                    regId,
//                    1,
//                    smsC);
//                System.out.println(res);
//            }
//        } catch (Exception e) {
//            logger.error("发送短信异常:{}", JSONObject.toJSONString(regId), e);
//        }
    }

}
