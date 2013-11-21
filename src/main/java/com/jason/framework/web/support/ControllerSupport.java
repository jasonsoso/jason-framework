package com.jason.framework.web.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jason.framework.util.ExceptionUtils;
import com.jason.framework.util.JsonHelper;
import com.jason.framework.web.filter.FlashModel;
import com.jason.framework.web.filter.FlashModel.Message;
import com.jason.framework.web.filter.FlashModel.MessageType;

public abstract class ControllerSupport extends MultiActionController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * @return
	 */
	protected Logger getLogger() {
		return logger;
	}
	
	/**
	 * Redirect Success
	 * @param redirectAttributes
	 * @param success
	 */
	public final void success(RedirectAttributes redirectAttributes,String success) {
		FlashModel.setSuccessMessage(redirectAttributes,success);
	}

	/**
	 * Redirect Error
	 * @param redirectAttributes
	 * @param error
	 */
	public final void error(RedirectAttributes redirectAttributes,String error) {
		FlashModel.setErrorMessage(redirectAttributes,error);
	}

	/**
	 * Forward Error
	 * @param model
	 * @param error
	 */
	public final void error(Model model,String error) {
		model.addAttribute(FlashModel.MESSAGE_KEY,new Message(MessageType.error, error));
	}

	/**
	 * Redirect Warning
	 * @param redirectAttributes
	 * @param warning
	 */
	public final void warning(RedirectAttributes redirectAttributes,String warning) {
		FlashModel.setErrorMessage(redirectAttributes,warning);
	}

	/**
	 * Redirect Info
	 * @param redirectAttributes
	 * @param info
	 */
	public final void info(RedirectAttributes redirectAttributes,String info) {
		FlashModel.setErrorMessage(redirectAttributes,info);
	}
	
	/**
	 * @param response
	 * @param message
	 */
	public static void writeJsonResult(HttpServletResponse response, Object message) {
		try {
			response.setContentType("text/html");
			response.getWriter().write(String.format("%s", JsonHelper.toJsonString(message)));
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.multiaction.MultiActionController#initBinder(javax.servlet.http.HttpServletRequest, org.springframework.web.bind.ServletRequestDataBinder)
	 */
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.setIgnoreInvalidFields(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.multiaction.MultiActionController#bind(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	@Override
	protected void bind(HttpServletRequest request, Object command){
		try {
			super.bind(request, command);
		} catch (Exception e) {
			logger.error("bind error!", e);
		}
	}
}
