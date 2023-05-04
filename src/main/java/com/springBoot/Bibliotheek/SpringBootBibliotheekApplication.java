package com.springBoot.Bibliotheek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springBoot.Bibliotheek.service.BookService;
import com.springBoot.Bibliotheek.service.BookServiceImpl;

@SpringBootApplication
@EnableJpaRepositories("com.springBoot.Bibliotheek.repository")
@EntityScan("com.springBoot.Bibliotheek.model")
public class SpringBootBibliotheekApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBibliotheekApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/book");
	}
	
	@Bean
	BookService bookService() {
		return new BookServiceImpl();
	}
	
}
