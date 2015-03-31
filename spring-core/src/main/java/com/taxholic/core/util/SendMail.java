package com.taxholic.core.util;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.*; 

import org.springframework.web.multipart.MultipartFile;

import java.io.*;


public class SendMail {
	
	private static final String SMTP_HOST_NAME = "127.0.0.1";
	private static final String SMTP_PORT = "25";
	
	/**
	 * 메일발송
	 * @param toMail 수신메일
	 * @param toName 수신인
	 * @param fromMail 발송메일
	 * @param fromName 발송인
	 * @param msgSubj 제목
	 * @param msgText 내용
	 * @param flag 0:Text, 1:Html
	 * @return boolean
	 */
	public boolean sendText(String toMail, String toName, String fromMail, String fromName, String msgSubj, String msgText,int flag) {
		
		boolean result = false;
		
		try {
		
			Properties props = new Properties();
			Session sess = Session.getDefaultInstance(props, null);
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.port", SMTP_PORT);
			
			InternetAddress address = new InternetAddress(toMail, toName, "UTF-8");
			InternetAddress fadd = new InternetAddress(fromMail, fromName, "UTF-8");
			
			Message msg = null;
			msg = new MimeMessage(sess);
			msg.setFrom(fadd);
			msg.setRecipient(Message.RecipientType.TO, address);
			msg.setSubject(MimeUtility.encodeText(msgSubj,"UTF-8","B"));
			msg.setContent(msgText, (flag == 0) ? "text/plain;charset=utf-8" : "text/html;charset=utf-8");
	
			msg.setSentDate(new Date());

			Transport.send(msg);
	
			result = true;
		
		} catch (Exception e) {} 

		return result;
	}
	
	
	/**
	 * 메일발송 (첨부파일 포함)
	 * @param toMail 수신메일
	 * @param toName 수신인
	 * @param fromMail 발송메일
	 * @param fromName 발송인
	 * @param msgSubj 제목
	 * @param msgText 내용
	 * @param flag 0:Text, 1:Html
	 * @return boolean
	 */
	public boolean sendAttach(String toMail, String toName, String fromMail, String fromName, String msgSubj, String msgText, String attachName,InputStream attachStream,int flag) {
		
		boolean result = false;
		try {
			Properties props = new Properties();
			Session sess = Session.getDefaultInstance(props, null);
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.port", SMTP_PORT);
			
			Message msg = null;
			InternetAddress toAddress = new InternetAddress(toMail, toName, "UTF-8");
			InternetAddress fromAddress = new InternetAddress(fromMail, fromName, "UTF-8");
			
			msg = new MimeMessage(sess);
			msg.setFrom(fromAddress);
			msg.setRecipient(Message.RecipientType.TO, toAddress);
			msg.setSubject(MimeUtility.encodeText(msgSubj,"UTF-8","B"));
			
		
			// 메일내용
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(msgText, (flag == 0) ? "text/plain;charset=utf-8" : "text/html;charset=utf-8");
						
			// 파일첨부
			MimeBodyPart mbp2 = new MimeBodyPart();
			
			/*
			FileDataSource fds = new FileDataSource("/tmp/logo.gif");
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			*/

			mbp2.setDataHandler(new DataHandler(new ByteArrayDataSource(attachStream, "application/octet-stream")));
			mbp2.setFileName(MimeUtility.encodeText(attachName,"UTF-8","B"));
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			
			msg.setContent(mp);
			msg.setSentDate(new Date());

			Transport.send(msg);

			result = true;
		
		} catch (Exception e) {
			e.getStackTrace();
		} 

		
		return result;
	}
	
}




