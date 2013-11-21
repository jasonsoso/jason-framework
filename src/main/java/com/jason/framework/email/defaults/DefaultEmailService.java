package com.jason.framework.email.defaults;


import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.jason.framework.email.EmailService;
import com.jason.framework.email.domain.EmailConf;

public class DefaultEmailService implements EmailService {

	@Override
	public void send(final EmailConf emailConf, final String subject, final String content) {
		
		if (!emailConf.hasReceiver()) {
			return;
		}

		JavaMailSender mailSender = createMailSender(emailConf);
		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

				helper.setFrom(emailConf.getSender());
				for (String recevicer : emailConf.getReceiversSet()) {
					helper.addTo(recevicer);
				}
				helper.setSubject(subject);
				helper.setText(content, true);
			}

		});
	}

	protected JavaMailSender createMailSender(EmailConf emailConf) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailConf.getSmtpServer());
		mailSender.setDefaultEncoding("utf-8");
		mailSender.setUsername(emailConf.getUsername());
		mailSender.setPassword(emailConf.getPassword());
		return mailSender;
	}

}
