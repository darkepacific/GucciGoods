package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Account;
import com.revature.services.AccountService;
import com.revature.services.ItemService;



@RestController
@RequestMapping(value="/admin")
@CrossOrigin(origins="http://localhost:4200")
public class AdminController {
	@Autowired
	private AccountService as;
	@Autowired
	private ItemService is;
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Account> getAccounts(HttpSession session) {
		List<Account> accs = new ArrayList<Account>();
		accs = as.getAccounts();
		logger.info("\n \n" + accs);
		//session.setAttribute("loggedAccount", u);
		return accs;
	}

	@RequestMapping(value = "{pathId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("pathId") String id, HttpSession session) {
		logger.info("Id: " + id);
		int accId = 0;
		int itemId = 0;

		if (id.substring(0, 1).equals("i")) {
			id = id.substring(6);
			itemId = Integer.parseInt(id);
			logger.info("Id: " + itemId);
			is.deleteItem(is.getItemById(itemId));
		} else {
			accId = Integer.parseInt(id);
			logger.info("Account Id:" + accId);
			as.deleteAccount(as.getAccountById(accId));
		}
	}
	
}


