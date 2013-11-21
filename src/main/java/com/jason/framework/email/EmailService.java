package com.jason.framework.email;

import com.jason.framework.email.domain.EmailConf;


public interface EmailService {

	/**
	 * 
	 * @param subject
	 * @param content
	 */
	public void send(EmailConf emailConf, String subject, String content);
}
