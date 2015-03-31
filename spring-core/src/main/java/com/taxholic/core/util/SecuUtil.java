package com.taxholic.core.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class SecuUtil {

	private static final String ENC_KEY_STRING = "5efef14a32f054764285558b0d23040d";
	private static final String CHAR_SET = "UTF8";
    private Cipher cipher;
	SecretKey desKey;	

	public SecuUtil() {
		init();
	}

	private void init() {

		try {
			byte[] keydata = ENC_KEY_STRING.getBytes();
			DESedeKeySpec keySpec = new DESedeKeySpec(keydata);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			desKey = keyFactory.generateSecret(keySpec);
			
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
	}	
	
	public String enc(String msg) {
		
		String encodeMsg = "";
		
		try {
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			byte [] plainText = msg.getBytes(CHAR_SET);
			
			//printLog("Plain Text [" + msg + "]");
	
			for(int i = 0; i < plainText.length ; i++) {
				//printLog(plainText[i] + " ");
			}
			// >OH#H-
			byte [] cipherText = cipher.doFinal(plainText);
			encodeMsg = new Base64().encodeToString(cipherText);
			//printLog("\nCipher Text ["+encodeMsg+"]");
			
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
							
		return encodeMsg;	
	}
	
	public String dec(String encodeMsg) {
		
		//printLog("DES MSG:" + encodeMsg);
		//return encodeMsg;

		String decMsg = "";
		
		try {
			//:9H#H- 8p5e7N<- 4Y=C CJ1bH-
			cipher.init(Cipher.DECRYPT_MODE, desKey);
	
			//:9H#H- <vG`              
			byte [] message = new Base64().decodeBase64(encodeMsg);
			byte [] decryptedText = cipher.doFinal(message);                
	
			decMsg = new String(decryptedText, CHAR_SET);
			//printLog("\n Decrypted Text [" + decMsg + "]");


		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return decMsg;
	}
}
