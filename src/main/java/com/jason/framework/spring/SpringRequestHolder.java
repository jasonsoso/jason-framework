
package com.jason.framework.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Jason
 * @date 2013-1-27 上午11:26:35
 */
public final class SpringRequestHolder {
	
	/**
    * get request
    */
   public static HttpServletRequest getRequest(RequestAttributes requestAttributes) {
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}
   /**
    * get session
    */
   public static HttpSession getSession(){
   		HttpServletRequest request = getRequest(RequestContextHolder.currentRequestAttributes());
   		return  request.getSession();
   }
}
