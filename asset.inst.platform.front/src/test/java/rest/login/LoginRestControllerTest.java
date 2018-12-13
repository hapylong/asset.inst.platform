/**
 * Description:
 * 
 * @author crw
 * @version 1.0
 * 
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2017年6月5日下午4:20:48 crw   1.0        1.0 Version 
 * </pre>
 */
package rest.login;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.util.http.HttpJsonUtils;

/**
 * @author crw
 *
 */
public class LoginRestControllerTest {


    @Test
    public void testUpdateUserInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("regId", "13681486576");
        map.put("realName", "郝金龙");
        map.put("idNo", "140621198208115539");
        map.put("bankMobile", "18581486576");
        JSONObject resultStr =
                HttpJsonUtils.doPost("http://127.0.0.1:8080/asset.inst.platform.front/unIntcpt-req/updateUserInfoByRegId", JSONObject.toJSONString(map));
        System.out.println("------------------"+resultStr);
    }
    
    @Test
    public void testGetImageVerify() {
        Map<String, String> map = new HashMap<>();
        map.put("regId", "13681486576");
        map.put("realName", "aaaaaaaa");
        map.put("idNo", "140621198208115539");
        
        JSONObject resultStr =
                HttpJsonUtils.doPost("http://api.ishandian.cn/asset.inst.platform.front/unIntcpt-req/getImageVerify/user", JSONObject.toJSONString(map));
        System.out.println("------------------"+resultStr);
    }
}
