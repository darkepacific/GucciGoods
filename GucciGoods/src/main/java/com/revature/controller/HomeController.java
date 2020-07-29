package com.revature.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin(origins="http://localhost:4200")
public class HomeController {
	
	private static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String getHomepage() {
		logger.info("hello");
		return "static/hello.html";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
}
