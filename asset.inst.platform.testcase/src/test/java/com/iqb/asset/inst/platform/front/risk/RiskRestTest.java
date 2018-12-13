package com.iqb.asset.inst.platform.front.risk;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.front.AbstractConstant;
import com.iqb.asset.inst.platform.front.utils.HttpClientUtil;

/**
 * 
 * Description: 风控服务测试
 * 
 * @author wangxinbang
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年12月13日    wangxinbang       1.0        1.0 Version 
 * </pre>
 */
public class RiskRestTest extends AbstractConstant {

    protected static final Logger logger = LoggerFactory.getLogger(RiskRestTest.class);
    
    /**
     * 
     * Description: 测试风控信息查询
     * @param
     * @return void
     * {
            "success": 1,
            "retDatetype": 1,
            "retCode": "00000000",
            "retUserInfo": "处理成功",
            "retFactInfo": "处理成功",
            "iqbResult": {
                "result": {
                    "id": null,
                    "regId": "18515262757",
                    "merchType": 3,
                    "checkInfo": "{\"addprovince\":\"四川\",\"contactname1\":\"张子\",\"contactname2\":\"刘楠\",\"contactphone1\":\"18515262757\",\"contactphone2\":\"18515262757\",\"marriedstatus\":\"未婚\",\"phone\":\"18515262757\"}",
                    "step1": null,
                    "step2": null,
                    "step3": null,
                    "step4": null
                }
            }
        }
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月13日 上午11:49:41
     */
    @Test
    public void testQueryRiskInfo1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", "111");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/queryRiskInfo", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 测试保存风控信息
     * @param
     * @return void
     *  {"success":2,"retDatetype":1,"retCode":"risk01010028","retUserInfo":"已进行风控操作","retFactInfo":"数据库已经存在该用户风控信息","iqbResult":{}}
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月13日 上午11:54:50
     */
    @Test
    public void testInsertRiskInfo1() throws Exception {
    	//车测试demo
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", "18515262757");
        params.put("addprovince", "北京天安门");
        params.put("marriedstatus", "已婚");
        params.put("contactname1", "小三");
        params.put("contactphone1", "18515262757");
        params.put("contactname2", "小四");
        params.put("contactphone2", "18515262757");
        String json = JSON.toJSONString(params);
        Map<String,String> last = new HashMap<String, String>();
        last.put("checkInfo", json);
        json = JSON.toJSONString(last);
        String result = HttpClientUtil.httpPost(BASEURL + "/insertRiskInfo", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 测试保存风控信息
     * @param
     * @return void
     *  {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo":"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月13日 上午11:59:32
     */
    @Test
    public void testInsertRiskInfo2() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("checkInfo", "abcdefgh");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/insertRiskInfo", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 测试保存风控信息
     * @param
     * @return void
     *  {"success":1,"retDatetype":1,"retCode":"00000000","retUserInfo":"处理成功","retFactInfo":"处理成功","iqbResult":{}}
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月13日 上午11:59:32
     */
    @Test
    public void testUpdateRiskInfo1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("checkInfo", "aaaaaa");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/updateRiskInfo", json);
        logger.info("返回结果:{}", result);
    }

    /**
     * 
     * Description: 获取风控步骤
     * @param
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 上午11:25:01
     */
    @Test
    public void testGetRiskInfoStep1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/getRiskInfoStep", json);
        logger.info("返回结果:{}", result);
    }
    
    /**
     * 
     * Description: 保存风控步骤信息
     * @param
     *  1.{"addProvince":"北京市昌平区天通苑西苑33栋五单元602","company":"北京市丰台区南三环西路万柳桥北宝隆大厦1-1810","contactMobel1":"13520312909","contactMobel2":"15712936311","contactName1":"王永心","contactName2":"王俪贻","culturalLevel":"JC","income":"30000","marriedStatus":"MARRIED","phoneNum":"18511549941","regAddr":"江苏省高邮市高邮镇宝塔村太平桥组15号","workStatus":"在职"}
     *  2.{"idUrl":"http://www.shandianx.com/images/1480583804897.1480576739122.jpg","idUrl2":"http://www.shandianx.com/images/1480583809099.1480576758782.jpg","imgUrl3":"http://www.shandianx.com/images/1480583812062.1480576977301.jpg"}
     *  3.{"workIncomingUrl":"http://www.shandianx.com/images/1480581946974.jpg"}
     *  4.{"bankWaterUrl":"http://www.shandianx.com/images/1480583885181.jpg"}
     * @return void
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年12月27日 上午11:37:15
     */
    @Test
    public void testSaveStepRiskInfo1() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("step4", "{\"idUrl\":\"111\",\"idUrl2\":\"\",\"imgUrl3\":\"\"}");
        String json = JSON.toJSONString(params);
        String result = HttpClientUtil.httpPost(BASEURL + "/4/saveStepRiskInfo", json);
        logger.info("返回结果:{}", result);
    }
    
}
