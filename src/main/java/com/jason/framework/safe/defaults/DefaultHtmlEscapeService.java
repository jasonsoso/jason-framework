package com.jason.framework.safe.defaults;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.jason.framework.safe.HtmlEscapeService;

public class DefaultHtmlEscapeService implements HtmlEscapeService {

	
	@Override
	public String cleanRelaxed(String bodyHtml) {
		if(StringUtils.isBlank(bodyHtml)){
			return "";
		}
		return Jsoup.clean(bodyHtml, Whitelist.relaxed());
	}

	@Override
	public boolean isValidRelaxed(String bodyHtml) {
		return Jsoup.isValid(bodyHtml, Whitelist.relaxed());
	}

	@Override
	public String cleanNone(String bodyHtml) {
		if(StringUtils.isBlank(bodyHtml)){
			return "";
		}
		return Jsoup.clean(bodyHtml, Whitelist.none());
	}

	@Override
	public boolean isValidNone(String bodyHtml) {
		return Jsoup.isValid(bodyHtml, Whitelist.none());
	}

}
