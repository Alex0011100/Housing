package com.wqcf.kanfang.util.code;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author nxh
 */
public class EncryptUtil {
	public static String md5(String str){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			return new String(Hex.encode(b));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	public static String getMD5(String str) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			// String m = new String();

			return new String(Base64.encode(Hex.encode(b)));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static byte[] encryptedData(String pubKey, byte[] plainData)
			throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] keyBytes = Base64.decode(pubKey);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] enBytes = cipher.doFinal(plainData);

		return enBytes;

	}

	public static byte[] encryptedData(String m_n,String m_e,byte[] plainData) throws Exception {


		PublicKey publicKey = generateRSAPublicKey(m_n,m_e);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] enBytes = cipher.doFinal(plainData);

		return enBytes;

	}

	private static RSAPublicKey generateRSAPublicKey(String m_n, String m_e)throws Exception {

		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(m_n,
				16), new BigInteger(m_e, 16));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}

	}

	public static byte[] desEnryptMode(byte[] keybyte, byte[] src) {
		try {

			SecretKey deskey = new SecretKeySpec(keybyte,"DESede");

			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static byte[] desDecryptMode(byte[] keybyte, byte[] src){


		SecretKey deskey = new SecretKeySpec(keybyte,("DESede"));
		try{
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}

}
