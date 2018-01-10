package com.my.sampleGw.common.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;


public class MessageUtil implements MessageSourceAware {
	
	private MessageSource messageSource;
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessage(String key, Object[] params, Locale locale )
			throws NoSuchMessageException {
		return messageSource.getMessage(key, params, locale);
	}
	
	public String getMessage( String key, Locale locale ) 
			throws NoSuchMessageException {
		return getMessage(key, null, locale);
	}

	/*public String getMessage( String key ) 
			throws NoSuchMessageException {
		return getMessage(key, null, null);
	}*/

}
