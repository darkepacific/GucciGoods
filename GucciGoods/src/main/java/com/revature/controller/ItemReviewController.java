package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Account;
import com.revature.beans.Item;
import com.revature.beans.ItemReview;
import com.revature.services.AccountService;
import com.revature.services.ItemReviewService;
import com.revature.services.ItemService;

@RestController
@RequestMapping(value="/itemreview")
@CrossOrigin(origins="http://localhost:4200")
public class ItemReviewController {
	@Autowired
	private ItemReviewService irs;
	@Autowired
	private AccountService as;
	@Autowired
	private ItemService is;
	
	@RequestMapping(method=RequestMethod.POST,  value="/get")
	public List<ItemReview> getReviews(@RequestParam("itemid") int itemid, HttpSession session) throws JsonProcessingException {
		System.out.println("itemid " + itemid);
		
		Item userItem = is.getItemById(itemid);
		List<ItemReview> gotReviews = irs.getItemReviews(userItem);
		
		return (gotReviews);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public List<ItemReview> addReview(@RequestParam("accountid") int accountid, @RequestParam("itemid") int itemid,
			@RequestParam("description") String description, HttpSession session) throws JsonProcessingException {
		System.out.println("INSIDE ITEMREVIEW CONTROLLER accountid " + accountid);
		System.out.println("itemid " + itemid);
		System.out.println("description " + description);
		
		ItemReview newReview = new ItemReview();
		
		//Account userAccount = as.getAccountById(accountid);
		Item userItem = is.getItemById(itemid);
		
		//newReview.setUser(userAccount);
		newReview.setId(1);
		newReview.setItem(userItem);
		newReview.setReview_description(description);
		
		irs.saveItemReview(newReview);
		
		Item getItems = is.getItemById(itemid);
		List<ItemReview> gotReviews = irs.getItemReviews(getItems);
		
		return (gotReviews);
	}
}
	