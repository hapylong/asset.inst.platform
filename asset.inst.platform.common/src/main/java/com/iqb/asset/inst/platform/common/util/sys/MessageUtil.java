package com.iqb.asset.inst.platform.common.util.sys;

import java.io.IOException;

public class MessageUtil {
    
    /**
     * 发送邮件
     * @param text 邮件内容
     * @param mobile 邮箱地址
     * @return
     * @throws IOException
     */
    public static boolean sendByEmail(String toAddress, String title,String text){
        return MailUtil.sendMail(toAddress, title, text);
    }
}
