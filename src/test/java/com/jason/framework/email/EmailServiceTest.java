package com.jason.framework.email;

import org.junit.Test;

import com.jason.framework.email.domain.EmailConf;

public class EmailServiceTest {
	
	@Test
	public void testSend() throws Exception {
		EmailConf emailConf = new EmailConf();
		emailConf.setSmtpServer("smtp.163.com");
		emailConf.setSender("tanjianna2009@163.com");
		emailConf.setUsername("tanjianna2009");
		emailConf.setPassword("tanjianna5714665");
		emailConf.setReceivers("648636045@qq.com;501393946@qq.com");
		
		//EmailService emailService = new DefaultEmailService();
		//emailService.send(emailConf, "測試標題1111", "測試內容1111");
	}
}
