package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Account;
import com.revature.beans.Item;
import com.revature.services.AccountService;

@RestController
@RequestMapping(value="/register")
@CrossOrigin(origins="http://localhost:4200")
public class RegistrationController {
	@Autowired
	private AccountService as;

//	@RequestMapping(method=RequestMethod.GET)
//	public Account goLogin(HttpSession session) {
////		if(session.getAttribute("user")!=null)
////			return "redirect:home";
////		return "static/login.html";
//		//return (LoginInfo) session.getAttribute("loggedAdmin");
//		return (Account) session.getAttribute("loggedAccount");
//	}
//	
	@RequestMapping(method=RequestMethod.GET)
	public Account goLogin(HttpSession session) {
//		if(session.getAttribute("user")!=null)
//			return "redirect:home";
//		return "static/login.html";
		//return (LoginInfo) session.getAttribute("loggedAdmin");
		return null;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Account Register(
			@RequestParam("user") String username, @RequestParam("pass") String password, 
			@RequestParam("email") String email, @RequestParam("first") String firstname, 
			@RequestParam("last") String lastname, @RequestParam("admin") Integer admin,
			@RequestParam("country") String location, @RequestParam("phone") String phone, HttpSession session) {
		
		List<Item> itemsSold = new ArrayList<Item>();
		String avatar = null;
		Account a = new Account(null, admin, username, password, firstname, lastname, phone, email, location, avatar, itemsSold);
		
		a = as.saveAccount(a);
		
		System.out.println(username);
		System.out.println(password);
		
		if(a != null) {
			//session.setAttribute("registeredAccount", a);
			return a;
		}
		
		//return null;
		return null;
	}
	
//	@RequestMapping(method=RequestMethod.DELETE)
//	public void logout(HttpSession session) {
//		session.invalidate();
//	}
}
