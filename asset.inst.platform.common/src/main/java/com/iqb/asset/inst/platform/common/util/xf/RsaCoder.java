package com.iqb.asset.inst.platform.common.util.xf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class RsaCoder
{
  public static final String KEY_ALGORITHM = "RSA";
  public static final String PUBLIC_KEY = "RSAPublicKey";
  public static final String PRIVATE_KEY = "RSAPrivateKey";
  private static final int KEY_SIZE = 512;
  private Map<String, Object> KEY_MAP;

  public RsaCoder()
    throws NoSuchAlgorithmException
  {
    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

    keyPairGen.initialize(512);

    KeyPair keyPair = keyPairGen.generateKeyPair();

    RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();

    this.KEY_MAP = new HashMap(2);
    this.KEY_MAP.put("RSAPublicKey", publicKey);
    this.KEY_MAP.put("RSAPrivateKey", privateKey);
  }

  public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception
  {
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(2, privateKey);
    return cipher.doFinal(data);
  }

  public static String decryptByPrivateKey(String data, String key) throws IOException, Exception
  {
    byte[] dataByte = decryptByPrivateKey(Base64.decodeBase64(data), Base64.decodeBase64(key));
    return new String(dataByte);
  }

  public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception
  {
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(2, publicKey);
    return cipher.doFinal(data);
  }

  public static String decryptByPublicKey(String data, String key) throws IOException, Exception
  {
    byte[] dataByte = decryptByPublicKey(Base64.decodeBase64(data), Base64.decodeBase64(key));
    return new String(dataByte);
  }

  public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception
  {
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(1, publicKey);
    return cipher.doFinal(data);
  }

  public static String encryptByPublicKey(String data, String key) throws UnsupportedEncodingException, IOException, Exception
  {
    byte[] signByte = encryptByPublicKey(data.getBytes("utf-8"), Base64.decodeBase64(key));
    return Base64.encodeBase64String(signByte);
  }

  public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception
  {
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(1, privateKey);
    return cipher.doFinal(data);
  }

  public static String encryptByPrivateKey(String data, String key) throws IOException, Exception
  {
    byte[] signByte = encryptByPrivateKey(data.getBytes(), Base64.decodeBase64(key));
    return Base64.encodeBase64String(signByte);
  }

  public static boolean checkPublicEncrypt(String data, String sign, String pvKey) throws IOException, Exception
  {
    return data.equals(decryptByPrivateKey(sign, pvKey));
  }

  public static boolean checkPrivateEncrypt(String data, String sign, String pbKey) throws IOException, Exception {
    return data.equals(decryptByPublicKey(sign, pbKey));
  }

  public byte[] getPrivateKey()
  {
    Key key = (Key)this.KEY_MAP.get("RSAPrivateKey");
    return key.getEncoded();
  }

  public byte[] getPublicKey()
  {
    Key key = (Key)this.KEY_MAP.get("RSAPublicKey");
    return key.getEncoded();
  }

  public String getPrivateKeyBase64()
  {
    Key key = (Key)this.KEY_MAP.get("RSAPrivateKey");
    return Base64.encodeBase64String(key.getEncoded());
  }

  public String getPublicKeyBase64()
  {
    Key key = (Key)this.KEY_MAP.get("RSAPublicKey");
    return Base64.encodeBase64String(key.getEncoded());
  }
}