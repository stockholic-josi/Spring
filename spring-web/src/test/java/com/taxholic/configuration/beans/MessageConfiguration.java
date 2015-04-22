package com.taxholic.configuration.beans;

import java.io.IOException;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfiguration {
	private static final String PATH = "classpath:config/properties/server";

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() throws IOException {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(PATH);
		messageSource.setCacheSeconds(5);;

		return messageSource;
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor() throws IOException {
		return new MessageSourceAccessor(messageSource());
	}
}
