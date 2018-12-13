/**
 * @Copyright (c) www.iqb.com All rights reserved.
 * @Description: TODO
 * @date 2016年12月1日 上午11:47:51
 * @version V1.0
 */
package com.iqb.asset.inst.platform.front.rest.wechat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.common.redis.RedisPlatformDao;
import com.iqb.asset.inst.platform.common.util.http.HttpClientUtil;
import com.iqb.asset.inst.platform.common.util.wechat.MessageUtil;
import com.iqb.asset.inst.platform.common.util.wechat.TextMessage;
import com.iqb.asset.inst.platform.data.bean.user.UserLocationBean;
import com.iqb.asset.inst.platform.front.conf.ParamConfig;
import com.iqb.asset.inst.platform.service.userservice.IUserLocationService;

/**
 * WeWhat服务器配置
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@SuppressWarnings("rawtypes")
@RestController
public class WechatCallbackApi {
    protected static final Logger logger = LoggerFactory.getLogger(WechatCallbackApi.class);
    private static final String TOKEN = "123456789abcdef";

    @Resource
    private ParamConfig paramConfig;
    @Resource
    private RedisPlatformDao redisPlatformDao;
    @Resource
    private IUserLocationService userLocationService;

    @RequestMapping(value = "chat.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void liaotian(Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("进入chat");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            logger.info("signature:{}timestamp:{}nonce:{}echostr:{}", signature, timestamp, nonce, echostr);
            access(request, response);
        } else {
            // 进入POST聊天处理
            logger.debug("进入POST聊天处理 enter post");
            try {
                // 接收消息并返回消息
                acceptMessage(request, response, "2");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "hhInterface", method = {RequestMethod.GET, RequestMethod.POST})
    public void hhInterface(Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("hhInterface");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            logger.info("signature:{}timestamp:{}nonce:{}echostr:{}", signature, timestamp, nonce, echostr);
            access(request, response);
        } else {
            // 进入POST聊天处理
            logger.debug("进入POST聊天处理 enter post");
            try {
                // 接收消息并返回消息
                acceptMessage(request, response, "1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 验证URL真实性
     * 
     * @author morning
     * @date 2015年2月17日 上午10:53:07
     * @param request
     * @param response
     * @return String
     */
    private String access(HttpServletRequest request, HttpServletResponse response) {
        // 验证URL真实性
        logger.debug("进入验证access");
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
        List<String> params = new ArrayList<String>();
        params.add(TOKEN);
        params.add(timestamp);
        params.add(nonce);
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        Collections.sort(params, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        String Str = params.get(0) + params.get(1) + params.get(2);
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        String temp = SHA1(Str);
        if (temp.equals(signature)) {
            try {
                response.getWriter().write(echostr);
                logger.info("成功返回 echostr：{}", echostr);
                return echostr;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("失败 认证");
        return null;
    }

    private void acceptMessage(HttpServletRequest request, HttpServletResponse response, String wechatNo)
            throws IOException {

        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        InputStream inputStream = request.getInputStream();
        // 调用核心服务类接收处理请求
        String respXml = processRequest(inputStream, wechatNo);
        out.print(respXml);
        out.close();
        out = null;
        // // 处理接收消息
        // ServletInputStream in = request.getInputStream();
        // // 将流转换为字符串
        // StringBuilder xmlMsg = new StringBuilder();
        // byte[] b = new byte[4096];
        // for (int n; (n = in.read(b)) != -1;) {
        // xmlMsg.append(new String(b, 0, n, "UTF-8"));
        // }
        // String xmlStr = xmlMsg.toString();
        // InputStream ins = String2InputStream(xmlStr);
        // System.out.println(ins);
        // JAXBContext cxt;
        // Xml xml;
        // try {
        // cxt = JAXBContext.newInstance(Xml.class);
        // Unmarshaller unmarshaller = cxt.createUnmarshaller();
        // xml = (Xml) unmarshaller.unmarshal(ins);
        // BigDecimal latitude = xml.getLatitude();// 纬度
        // BigDecimal longitude = xml.getLongitude();// 经度
        // String location = latitude + "," + longitude;
        // String openId = xml.getFromUserName();
        //
        // String ak = sysParaConfig.getBaidu_geocoding_ak();
        // String url = sysParaConfig.getBaidu_geocoding_url();
        // // 调用百度获取地理位置
        // Map<String, String> param = new LinkedHashMap<String, String>();
        // param.put("location", location);
        // param.put("output", "json");
        // param.put("ak", ak);
        // String result = HttpUtils.sendGetRequest(url, param, "utf-8");
        // logger.debug("百度定位返回数据：{}", result);
        //
        // // 解析百度返回的结果
        // Map retMap = JSON.parseObject(result);
        // Map resMap = (Map) retMap.get("result");
        // Map addMap = (Map) resMap.get("addressComponent");
        // String province = (String) addMap.get("province");// 省份
        // String city = (String) addMap.get("city");// 市
        // String district = (String) addMap.get("district");// 区
        // Map<String, String> addressMap = new HashMap<String, String>();
        // addressMap.put("province", province);
        // addressMap.put("city", city);
        // addressMap.put("district", district);
        // String addressJson = JSON.toJSONString(addressMap);
        // redisTemp.setOpsForTime("address_" + openId, addressJson, 60 * 24);//
        // 存一天
        // logger.debug("用户位置信息存入redis中,key:{}, value:{}", "address_" + openId,
        // addressJson);
        // } catch (JAXBException e) {
        // e.printStackTrace();
        // }
        // logger.debug("接收到的XML为===" + xmlMsg.toString());

    }

    public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1"); //
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            //
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String processRequest(InputStream inputStream, String wechatNo) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = null;
        if ("1".equalsIgnoreCase(wechatNo)) {// 花花
            respContent = "";// "还款操作流程：\n点击首页底部“花的花世界”->点击“个人登录”->“会员中心”页面点击“我要还款”->“我要还款”页面选择要还款的期数并点击“立即还款”->“还款”页面选择支付的银行卡完成支付。\n提示信息：\n如果需要换卡支付，步骤如下：关于我->我的银行卡->点击标识为“已绑定”的银行卡进行解绑，解绑后可使用新卡支付。因招行维护比较频繁请尽量选择使用其他银行卡。\n如有问题请拨打客服电话：010-51690463或微信公众号留言";
        } else {// 爱车帮帮手
            respContent =
                    "您好：总部客户服务电话已变更为 4008256825，如您在业务操作或后续还款遇到任何问题，请在工作时段随时拨打该客服电话，我们会及时为您解决。客户服务时间：工作日（09:00-18:00）";
        }
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(inputStream);
            logger.debug("发送消息" + requestMap);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // event 事件
            String event = requestMap.get("Event");
            // EventKey
            String eventKey = requestMap.get("EventKey");
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
            if (msgType.equals(MessageUtil.MESSAGE_TYPE_EVENT)) {
                logger.debug("event==" + event);

                if (event.equals(MessageUtil.EVENT_LOCATION)) {// 地理位置
                    String key = "address_" + fromUserName;
                    String locationJson = redisPlatformDao.getValueByKey(key);
                    logger.debug("fromUserName：{}locationJson:{}", fromUserName, locationJson);
                    if (locationJson != null) {// 已经存在地址,不需要调用百度接口获取地址了
                        textMessage.setContent(respContent);
                        // 将文本消息对象转换成xml
                        respXml = MessageUtil.messageToXml(textMessage);
                        return respXml;
                    }
                    // openId
                    String ak = paramConfig.getBaidu_geocoding_ak();
                    String url = paramConfig.getBaidu_geocoding_url();
                    // 调用百度获取地理位置
                    Map<String, String> param = new LinkedHashMap<String, String>();
                    param.put("location", requestMap.get("Latitude") + "," + requestMap.get("Longitude"));
                    param.put("output", "json");
                    param.put("ak", ak);
                    String result = HttpClientUtil.get(url, param);
                    logger.debug("百度定位返回数据：{}", result);
                    // 解析百度返回的结果
                    Map retMap = JSON.parseObject(result);
                    Map resMap = (Map) retMap.get("result");
                    Map addMap = (Map) resMap.get("addressComponent");
                    String province = (String) addMap.get("province");// 省份
                    String city = (String) addMap.get("city");// 市
                    String district = (String) addMap.get("district");// 区
                    try {
                        UserLocationBean userLocationBean = new UserLocationBean();
                        userLocationBean.setRegId(fromUserName);
                        userLocationBean.setProvince(province);
                        userLocationBean.setCity(city);
                        userLocationBean.setDistrict(district);
                        userLocationBean.setAddress((String) addMap.get("formatted_address"));
                        userLocationService.insertUserAddress(userLocationBean);
                    } catch (Exception e) {
                        logger.error("用户:{}保存定位地址发生异常", fromUserName, e);
                    }
                    Map<String, String> addressMap = new HashMap<String, String>();
                    addressMap.put("province", province);
                    addressMap.put("city", city);
                    addressMap.put("district", district);
                    String addressJson = JSON.toJSONString(addressMap);
                    redisPlatformDao.setKeyAndValueTimeout(key, addressJson, 60 * 60 * 12);// 存一天
                    logger.debug("用户位置信息存入redis中,key:{}, value:{}", "address_" + fromUserName, addressJson);
                    respContent = "";
                } else if (event.equals(MessageUtil.EVENT_CLICK)) {// 点击事件
                    if (eventKey.equals("ACBHEZUO")) {
                        respContent = "亲爱的，终于等到你！\n 商务合作邮箱： tianxiaoxiao@iqianbang.com \n联   系  电  话： 010-5218 5184";
                    } else if (eventKey.equals("LXKEFU")) {
                        respContent =
                                "如需帮助请通过以下方式联系客服：\n 电话: 010-5218 5184 \n QQ:  2686402674 \n 邮箱: 2686402674@qq.com";
                    }
                }
            }
            // 文本消息
            if (msgType.equals(MessageUtil.MESSAGE_TYPE_TEXT)) {
                respContent = "";
            }
            logger.debug(" respContent: " + respContent);
            // 设置文本消息的内容
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.messageToXml(textMessage);
            logger.debug(" respXml: " + respXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

    /**
     * Str 转 InputStream
     * 
     * @param str
     * @return
     */
    public static InputStream String2InputStream(String str) {
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }
}
