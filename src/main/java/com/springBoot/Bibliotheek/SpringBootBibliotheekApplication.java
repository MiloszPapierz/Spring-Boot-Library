package com.springBoot.Bibliotheek;


import java.util.Locale;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.springBoot.Bibliotheek.exceptions.BookNotFoundException;
import com.springBoot.Bibliotheek.perform.PerformRestExample;
import com.springBoot.Bibliotheek.service.BookService;
import com.springBoot.Bibliotheek.service.BookServiceImpl;
import com.springBoot.Bibliotheek.validator.FormAuthorsWrapperValidation;
import com.springBoot.Bibliotheek.validator.IsbnValidation;

@SpringBootApplication
@EnableJpaRepositories("com.springBoot.Bibliotheek.repository")
@EntityScan("com.springBoot.Bibliotheek.model")
public class SpringBootBibliotheekApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBibliotheekApplication.class, args);
		
		try {
			new PerformRestExample();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/books");
		registry.addViewController("/403").setViewName("403");
	}
	
	
	@Bean
	BookService bookService() {
		return new BookServiceImpl();
	}
	
	@Bean
	IsbnValidation isbnValidation() {
		return new IsbnValidation();
	}
	
	@Bean
	FormAuthorsWrapperValidation formAuthorsWrapperValidation() {
		return new FormAuthorsWrapperValidation();
	}
	
	/*@Bean
	LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}*/
	
	@Bean
	SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		
		Properties mappings = new Properties();
		mappings.put("exceptions.CustomGenericException", "error/bookNotFound");
		
		r.setDefaultErrorView("error/error");
		r.setExceptionMappings(mappings);
		
		return r;
	}
}
