package com.revature.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Account;
import com.revature.services.AccountService;

@RestController
@RequestMapping(value="/login")
@CrossOrigin(origins="http://localhost:4200")
public class LoginController {	
	
	@Autowired
	private AccountService us;
	
    private static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(method=RequestMethod.GET)
	public Account goLogin(HttpSession session) {
		return (Account) session.getAttribute("loggedAccount");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Account login(@RequestParam("user") String username, 
			@RequestParam("pass") String password, HttpSession session) {
		
		logger.info("Username: " + username);

		
		Account u = us.login(username, password);

		if(u != null) {
			
			session.setAttribute("loggedAccount", u);
			return u;
		}
		
		return null;
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@RequestMapping(value="/email", method=RequestMethod.POST)
	public String updateEmail(@RequestParam("newemail") String email, @RequestParam("id") int id, HttpSession session) throws JsonProcessingException {
		System.out.println("asdfasdofaodsifadfiouaosdfuioelkfjvmalksdfjweoifslkjfa;ldsk " + email);
		us.updateEmail(email, id);
		
		Account u = us.getAccountById(id);
		session.setAttribute("loggedAccount", u);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(email);
		return (jsonString);
	}
	
	@RequestMapping(value="/location", method=RequestMethod.POST)
	public String updateLocation(@RequestParam("newlocation") String location, @RequestParam("id") int id, HttpSession session) throws JsonProcessingException {
		System.out.println("asdfasdofaodsifadfiouaosdfuioelkfjvmalksdfjweoifslkjfa;ldsk " + location);
		us.updateLocation(location, id);
		
		Account u = us.getAccountById(id);
		session.setAttribute("loggedAccount", u);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(location);
		return (jsonString);
	}
	
	@RequestMapping(value="/phone", method=RequestMethod.POST)
	public String updatePhone(@RequestParam("newphone") String phone, @RequestParam("id") int id, HttpSession session) throws JsonProcessingException {
		System.out.println("asdfasdofaodsifadfiouaosdfuioelkfjvmalksdfjweoifslkjfa;ldsk " + phone);
		us.updatePhone(phone, id);
		
		Account u = us.getAccountById(id);
		session.setAttribute("loggedAccount", u);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(phone);
		return (jsonString);
	}
	
	@RequestMapping(value="/description", method=RequestMethod.POST)
	public String updateDescription(@RequestParam("newdescription") String description, @RequestParam("id") int id, HttpSession session) throws JsonProcessingException {
		System.out.println("asdfasdofaodsifadfiouaosdfuioelkfjvmalksdfjweoifslkjfa;ldsk " + description);
		us.updateDescription(description, id);
		
		Account u = us.getAccountById(id);
		session.setAttribute("loggedAccount", u);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(description);
		return (jsonString);
	}
	
	@RequestMapping(value = "{pathId}", method = RequestMethod.GET)
	public Account getSellerAccount(@PathVariable("pathId") String id, HttpSession session) {
		int aId = Integer.parseInt(id);
		System.out.println("$$$$$$$$$$$$$$$@@@" + id);
		
		return us.getAccountById(aId);
		
	}

}
