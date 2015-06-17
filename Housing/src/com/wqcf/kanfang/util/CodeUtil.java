package com.wqcf.kanfang.util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.wqcf.kanfang.util.code.Base64;
import com.wqcf.kanfang.util.code.EncryptUtil;

public class CodeUtil {
	
	public static String desEncode(String key,String str) throws UnsupportedEncodingException{
		
		return new String(Base64.encode(EncryptUtil.desEnryptMode(key.getBytes(),str.getBytes("UTF8"))));
		
	}
	
	public static String desDecode(String key,String str) throws UnsupportedEncodingException{
		byte[] b0 = Base64.decode(str);
		byte[] b1 = key.getBytes();
		//String strs = new String(EncryptUtil.desDecryptMode(key.getBytes("UTF8"), Base64.decode(str)));
		
		
		byte[] DESedeKey = new byte[24];
		SecureRandom random = new SecureRandom();
		random.nextBytes(DESedeKey);
		String strr = new String(DESedeKey);
		
		byte[] b2 = EncryptUtil.desDecryptMode(DESedeKey,b0);
		
		String strs = new String(b2);
		return strs;
		
	}
	
}
