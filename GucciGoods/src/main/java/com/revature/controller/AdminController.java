package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Account> getAccounts(HttpSession session) {
		List<Account> accs = new ArrayList<Account>();
		accs = as.getAccounts();
		System.out.println("\n \n" + accs);
		//session.setAttribute("loggedAccount", u);
		return accs;
	}

	@RequestMapping(value = "{pathId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("pathId") String id, HttpSession session) {
		System.out.println("@@@$$$$$$$$$$$$$$$" + id);
		int aId = 0;
		int iId = 0;

		if (id.substring(0, 1).equals("i")) {
			id = id.substring(6);
			iId = Integer.parseInt(id);
			System.out.println("$$$$$$$$$$$$$$$" + id);
			is.deleteItem(is.getItemById(iId));
		} else {
			aId = Integer.parseInt(id);
			System.out.println("$$$$$$$$$$$$$$$@@@" + id);
			as.deleteAccount(as.getAccountById(aId));
		}
	}
	
}


