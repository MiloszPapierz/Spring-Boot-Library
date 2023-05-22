package com.springBoot.Bibliotheek.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.springBoot.Bibliotheek.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().and()
			.authorizeHttpRequests(requests ->
					requests.requestMatchers("/api/**").permitAll()
					.requestMatchers("/login**").permitAll()
					.requestMatchers("/css/**").permitAll()
					.requestMatchers("/403**").permitAll()
					.requestMatchers("/books/add").hasRole("ADMIN")
					.requestMatchers("/**").hasAnyRole("USER","ADMIN"))
			.formLogin(form ->
			form.defaultSuccessUrl("/books",true)
			.loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password"))
			.exceptionHandling().accessDeniedPage("/403");
		
		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		var encoder = new BCryptPasswordEncoder();
		
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}
}
