package com.taxholic.core.util;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

public class EncryptUtil {
	
	private static final String PRIVATE_KEY = "TAXHOLIC";
	private static final String ENCYPTION_PRIVATE_KEY_ALGORITHM = "PBEWithMD5AndDES";
	private static StandardPBEStringEncryptor cryptor;
	
	public static void createConfigurationEncryptor(){

		EnvironmentStringPBEConfig envConfig = new EnvironmentStringPBEConfig();
		envConfig.setAlgorithm(ENCYPTION_PRIVATE_KEY_ALGORITHM);
		envConfig.setPassword(PRIVATE_KEY);

		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setConfig(envConfig);
		
		cryptor = standardPBEStringEncryptor;

	}

	public static String encrypt(String message) {
		
		if(cryptor == null){
			createConfigurationEncryptor();
		}
		
		return cryptor.encrypt(message);
	}
	
	public static String decrypt(String message) {
		
		if(cryptor == null){
			createConfigurationEncryptor();
		}
		
		return cryptor.decrypt(message);
	}

	

}
