package com.iqb.asset.inst.platform.common.util.xf;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class UcfDigestUtils
{
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    public static String digest(String input)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] bDigests = md.digest(input.getBytes("UTF-8"));

            return byte2hex(bDigests);
        } catch (Exception e) {}
        return "";
    }

    public static boolean isEqual(String digesta, String digestb) {
        try {
            return MessageDigest.isEqual(digesta.toUpperCase().getBytes(), digestb.toUpperCase().getBytes());
        } catch (Exception e) {
            return false;
        }
    }

    public static String getSignData(String signName, Map<String, String> params)
    {
        StringBuffer content = new StringBuffer();
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            if (signName.equals(key)) {
                continue;
            }
            String value = (String) params.get(key);
            value = null == value ? "" : value;
            content.append("&" + key + "=" + value);
        }
        String str = content.toString();
        if (isEmpty(str)) {
            return str;
        }
        if (str.startsWith("&")) {
            return str.substring("&".length());
        }
        return str;
    }

    public static String encode(String key, String data, String type)
            throws GeneralSecurityException, CoderException
    {
        if ("AES".equals(type)) {
            byte[] keyByte = Hex.decodeHex(new String(key).toCharArray());
            Key k = AESCoder.toKey(keyByte);
            byte[] encryptData = AESCoder.encrypt(data.getBytes(), k);
            return Base64.encodeBase64String(encryptData);
        }
        if ("MD5".equals(type)) {
            return digestMD5(data + key).toLowerCase();
        }
        return "";
    }

    public static String decode(String key, String data, String type) throws Exception {
        byte[] keyByte = Hex.decodeHex(key.toCharArray());
        Key k = AESCoder.toKey(keyByte);
        byte[] decryptData = AESCoder.decrypt(Base64.decodeBase64(data), k);
        return new String(decryptData);
    }

    private static boolean isEmpty(String str)
    {
        return (str == null) || (str.length() == 0);
    }

    public static String digestMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] bDigests = md.digest(input.getBytes("UTF-8"));

            return byte2hex(bDigests);
        } catch (Exception e) {}
        return "";
    }

    private static String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
}
