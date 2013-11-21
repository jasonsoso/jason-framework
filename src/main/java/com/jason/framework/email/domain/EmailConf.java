package com.jason.framework.email.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


public class EmailConf implements Serializable {
	private static final long serialVersionUID = 1L;

	private String smtpServer;
	private String sender;
	private String username;
	private String password;

	private String receivers;

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public boolean hasReceiver() {
		return !getReceiversSet().isEmpty();
	}

	public Set<String> getReceiversSet() {
		if (StringUtils.isBlank(getReceivers())) {
			return Collections.emptySet();
		}
		List<String> asList = Arrays.asList(getReceivers().split(";|；"));
		Set<String> sets = new HashSet<String>();
		for (String str : asList) {
			sets.add(str);
		}
		return sets;
	}

}
