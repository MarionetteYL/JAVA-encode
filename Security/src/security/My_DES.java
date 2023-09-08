package security;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

public class My_DES {


	public static String encryptDES(String src, String key){
		byte[] result = null;
		try {
			//KEY转换
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());//创建一个DESKeySpec对象，用于保存密钥（key）的字节形式。
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//通过使用DES算法，初始化一个SecretKeyFactory实例。
			Key convertSecretKey = factory.generateSecret(desKeySpec);//利用密钥工厂（factory）里的`generateSecret()`方法，将DESKeySpec转换成一个SecretKey对象（convertSecretKey）。
			
			//加密
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//使用DES算法和ECB模式以及填充方式`PKCS5Padding`，创建Cipher对象。
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);//初始化cipher对象为加密模式（`Cipher.ENCRYPT_MODE`），并设置转换后的convertSecretKey为加密密钥。
			result = cipher.doFinal(src.getBytes());//使用cipher对象加密原始数据（src），将其转换为字节数组，并将加密结果赋值给result。
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Hex.encodeHexString(result);//将byte数组转化为16进制字符串
	}
	
	public static String dectyptDES(String src, String key){
		byte[] result = null;
		try {
			//KEY转换
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
			Key convertSecretKey = factory.generateSecret(desKeySpec);
			
			//解密
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
			result = cipher.doFinal(hexStringToBytes(src));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new String(result);
	}
	
	//将16进制字符串转化为byte数组
	public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
	public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
