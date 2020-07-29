package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Account;
import com.revature.beans.Item;
import com.revature.services.AccountService;
import com.revature.services.ItemService;



@RestController
@RequestMapping(value="/item")
@CrossOrigin(origins="http://localhost:4200")
public class ItemController {
	@Autowired
	private ItemService is;
	@Autowired
	private AccountService as;
	
	private static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(method=RequestMethod.GET)
	public Item getItem(@RequestParam("id") String itemId, HttpSession session)
	{
		int id = Integer.parseInt(itemId);
		Item i = is.getItemById(id);
		logger.info("Id: " + id);

		if(i != null) {

			session.setAttribute("item", i);
			System.out.println(i);
			return i;
		}

		return null;
	}

	@RequestMapping(method=RequestMethod.GET, value="/all")
	public List<Item> getItems(HttpSession session) {
		List<Item> items = new ArrayList<Item>();
		items = is.getItems();
		logger.info("\n \n" + items);
		//session.setAttribute("loggedAccount", u);
		return items;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/buy")
	public Item buyItem(@RequestParam("id") Integer itemId, @RequestParam("name") String name, 
						@RequestParam("quantity") Integer quantity, HttpSession session)
	{
		//Account a = as.getAccountById(accountid);
		
		//Item i = is.getItemById(id);
		List<Item> items = new ArrayList<Item>();
		Item it = is.getItemById(itemId);
		
		
		items = is.getItemsByName(name);
		if(quantity > items.size()) {
			return null;
		}
		
		if(quantity < 1) {
			for(int i = 0; i < quantity; i++) {
				it = items.get(i);
				it.setSold(1);
				is.update(it);
			}
		}else {
			it.setSold(1);
			is.update(it);
		}
		
		//System.out.println(id);
		return it;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/search")
	public List<Item> searchItem(@RequestParam("query") String query, HttpSession session)
	{
		List<Item> results = new ArrayList<Item>();
		List<Item> items = new ArrayList<Item>();
		items = is.getItems();
		
		query = query.toLowerCase();
		
		String[] splitQuery = query.trim().split("\\s+");
		
		
		for(String s : splitQuery) {
			System.out.print(s +  " ");
		}System.out.println(" ");
		
		for(Item item : items) {
			String name = item.getName().toLowerCase();
			String tags = item.getTags().toLowerCase();
			
			String[] splitName = name.trim().split("\\s+");
			String[] splitTags = tags.trim().split("\\s+");
			
			//If query matches anything
			if(	//by Name
				name.toLowerCase().contains(query) || 
				(splitQuery.length > 1 && name.contains(splitQuery[1]) ) ||
				(splitQuery.length > 2 && name.contains(splitQuery[2]) ) ||
				(splitQuery.length > 3 && name.contains(splitQuery[3]) ) || 
				
				//By Tag
				tags.contains(query) ||
				tags.contains(splitQuery[0]) ||
				(splitQuery.length > 1 && tags.contains(splitQuery[1]) ) ||
				(splitQuery.length > 2 && tags.contains(splitQuery[2]) ) ||
				(splitQuery.length > 3 && tags.contains(splitQuery[3]) )
				) 
				{
				
					//If query has EXACT match
					if( //byName
						name.equals(query) ||
						name.equals(splitQuery[0]) ||
						splitName[0].equals(splitQuery[0])	||			
						(splitQuery.length > 1 && name.equals(splitQuery[1]) ) ||
						(splitQuery.length > 1 && splitName[0].equals(splitQuery[1]) ) ||
						(splitQuery.length > 2 && name.equals(splitQuery[2]) ) ||
						//byTag
						splitTags[0].equals(splitQuery[0]) || splitTags[0].equals(splitQuery[0] + ",") ||
						(splitTags.length > 1 && (splitTags[1].equals(splitQuery[0]) ||  splitTags[1].equals(splitQuery[0] + ",") )) ||
						(splitTags.length > 2 && (splitTags[2].equals(splitQuery[0]) ||  splitTags[2].equals(splitQuery[0] + ",") ))
						
					) {
						logger.info("@@ Added to the start " + name + ", names" );
						results.add(0, item);
					}
					else {
						logger.info("@@ Added to the bottom " + name + ", names" );
						results.add(item);
					}	
				
					for(String s : splitName) {
						System.out.print(s +  " ");
					}
					System.out.println(" , tags");
					
					for(String s : splitTags) {
						System.out.print(s +  " ");
					}
					System.out.println(" ");
			}
		}
		
		//Basic Sort -- If the query is the same as the item name absolutely add to the top
		String queryWord = " "+query;
		
		for(int i = 0; i < results.size(); i++) {
			if(results.get(i).getName().toLowerCase().equals(query) ||
					results.get(i).getName().toLowerCase().contains(queryWord)	
					) {
				logger.info("@@ Exact Match: Added to the top ");
				results.add(0, results.get(i));
				results.remove(i+1);
			}
		}
		
		//Finally sorts again by precedence of name, Item names where the query is closer to the first 3 words go to the top again
		for(int i = 0; i < results.size(); i++) {
			String itemName = results.get(i).getName().toLowerCase();
			if(itemName.toLowerCase().contains(queryWord)) {
				String[] splitName = itemName.trim().split("\\s+");
				for(int j = 0; j < 3; j++) {
					if(splitName[j].equals(query)) {
						logger.info("@@ 3 Word Match: Added to the top ");
						results.add(0, results.get(i));
						results.remove(i+1);
					}
				}
			}
		}

		
		//If query is same as name 
		
		return results;
	}
}
