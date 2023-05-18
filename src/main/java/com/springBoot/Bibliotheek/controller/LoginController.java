package com.springBoot.Bibliotheek.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private MessageSource messageSource;
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping
	public String getLoginForm(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,Model model,Locale locale) {
		log.info("Getting login form page");
		
        if (error != null) {
        	String errorMessage = messageSource.getMessage("loginpage.error",null, locale);
            model.addAttribute("error", errorMessage);
        }
        if (logout != null) {
        	String logoutMessage = messageSource.getMessage("loginpage.message", null,locale);
            model.addAttribute("msg",logoutMessage);
        }
		
		return "login";
	}
	
}
