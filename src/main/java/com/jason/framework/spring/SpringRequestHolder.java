
package com.jason.framework.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Spring Request Holder
 * @author Jason
 * @date 2013-1-27 上午11:26:35
 */
public final class SpringRequestHolder {
	public SpringRequestHolder(){}
	
	/**
	 * get request
	 * @param requestAttributes
	 * @return
	 */
	public static HttpServletRequest getRequest(
			RequestAttributes requestAttributes) {
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 * get session
	 * @return
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = getRequest(RequestContextHolder
				.currentRequestAttributes());
		return request.getSession();
	}
}
