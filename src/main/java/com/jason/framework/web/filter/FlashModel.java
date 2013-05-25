package com.jason.framework.web.filter;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Redirect Info
 * Share data between two request
 * Mainly provide prompt information
 * @author Jason
 *
 */
public final class FlashModel {

	public static final String MESSAGE_KEY = "message";

	/**
	 * @param redirectAttributes
	 * @param message
	 */
	public static void putFlashAttribute(RedirectAttributes redirectAttributes,Message message) {
		redirectAttributes.addFlashAttribute(MESSAGE_KEY, message);
	}

	/**
	 * @param redirectAttributes
	 * @param info
	 */
	public static void setInfoMessage(RedirectAttributes redirectAttributes,String info) {
		putFlashAttribute(redirectAttributes, new Message(MessageType.info, info));
	}

	/**
	 * @param redirectAttributes
	 * @param warning
	 */
	public static void setWarningMessage(RedirectAttributes redirectAttributes,String warning) {
		putFlashAttribute(redirectAttributes, new Message(MessageType.warning, warning));
	}

	/**
	 * @param redirectAttributes
	 * @param error
	 */
	public static void setErrorMessage(RedirectAttributes redirectAttributes,String error) {
		putFlashAttribute(redirectAttributes, new Message(MessageType.error, error));
	}

	/**
	 * @param redirectAttributes
	 * @param success
	 */
	public static void setSuccessMessage(RedirectAttributes redirectAttributes,String success) {
		putFlashAttribute(redirectAttributes, new Message(MessageType.success, success));
	}


	/**
	 * @author Jason
	 * @date 2013-5-25 下午09:54:25
	 */
	public static final class Message {

		private final MessageType type;
		private final String text;

		public Message(MessageType type, String text) {
			this.type = type;
			this.text = text;
		}

		public MessageType getType() {
			return type;
		}

		public String getText() {
			return text;
		}

		public String toString() {
			return type + ": " + text;
		}

	}

	public static enum MessageType {
		info, success, warning, error
	}

	private FlashModel() {
	}
}
