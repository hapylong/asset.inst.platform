package com.iqb.asset.inst.platform.common.util.sys;

import java.util.Random;
import java.util.UUID;

/**
 * 生成用户表的userId的公共类
 * @author William
 * 
 */
public class GenerationUtil {
	
	/**
	 * 生成  user 表的 userid 规则 :88(2位)+用户类型(1位)+10位毫秒数+2位随机数   共15 位
	 * @param user_type
	 * @return
	 */
	public String generationUserId(Integer user_type){//userType用户类型
		//获取毫秒数
		Long currTime=System.currentTimeMillis()/1000;
		return "88"+user_type+currTime+getTwoDig();
	}
	
	/**
	 * 生成两位随机数，作为用户号的最后两位,供给generationUserId调用
	 * @return
	 */
	public static int getTwoDig(){
		return new Random().nextInt(100);
	}

	/** 
     * 产生随机的六位数 
     * @return String
     */  
    public static String getSix(){  
        Random rad=new Random();  
          
        String result  = rad.nextInt(1000000) +"";  
          
        if(result.length()!=6){  
            return getSix();  
        }  
        return result;  
    } 
    
    /**
     * 获取uuid
     * Description: 
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年9月21日 下午8:43:53
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
	
}
