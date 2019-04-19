package com.revature.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin(origins="http://localhost:4200")
public class HomeController {
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String getHomepakeTravisIsDoingAGoodJob() {
		System.out.println("hello");
		return "static/hello.html";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
}
