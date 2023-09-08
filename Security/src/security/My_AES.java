package security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class My_AES {
	
	
	public static String encryptAES(String src, String key){
		byte[] result = null;
		//生成KEY
		byte[] keyBytes = key.getBytes();
		
		//key转换
		Key mkey = new SecretKeySpec(keyBytes, "AES");
		
		//加密
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//AES代表高级加密标准，ECB代表电子代码簿模式，PKCS5Padding代表用PKCS#5填充
			cipher.init(Cipher.ENCRYPT_MODE, mkey);
			result = cipher.doFinal(src.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Base64.encodeBase64String(result); //利用Base64将byte数组转换为特殊格式字符串
	}
	
	public static String dectyptAES(String src, String key){
		byte[] result = null;
		//生成KEY
		byte[] keyBytes = key.getBytes();

		//key转换
		Key mkey = new SecretKeySpec(keyBytes, "AES");

		try {
			//解密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, mkey);
			result = cipher.doFinal(Base64.decodeBase64(src)); //利用Base64将特殊格式字符串转换为byte数组
		} catch (Exception e) {
			e.printStackTrace();
		} 
		 return new String(result);	
		
	}

}
