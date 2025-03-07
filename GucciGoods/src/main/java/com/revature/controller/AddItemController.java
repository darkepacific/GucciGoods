package com.revature.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Account;
import com.revature.beans.Item;
import com.revature.services.AccountService;
import com.revature.services.ItemService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value="/itemadd")
@CrossOrigin(origins="http://localhost:4200")
public class AddItemController {
	@Autowired
	private ItemService is;
	@Autowired
	private AccountService as;
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	public Item getItemSold() {
		Item newItem = new Item(); 
		return newItem;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Item getItemSold(
	        @RequestParam("id") int id,
	        @RequestParam("accountid") int accountid,
	        @RequestParam("name") String name,
	        @RequestParam("description") String description,
	        @RequestParam("tags") String tags,
	        @RequestParam("cost") String cost,
	        @RequestParam("image") String image,
	        @RequestParam("sold") int sold,
	        HttpSession session) {

//	    try {
//	        name = URLDecoder.decode(name, StandardCharsets.UTF_8.toString());
//	        description = URLDecoder.decode(description, StandardCharsets.UTF_8.toString());
//	        tags = URLDecoder.decode(tags, StandardCharsets.UTF_8.toString());
//	        image = URLDecoder.decode(image, StandardCharsets.UTF_8.toString());
//	    } catch (UnsupportedEncodingException e) {
//	        e.printStackTrace(); // Log decoding error
//	    }

	    logger.info(id + " " + accountid + " " + name + " " + description + " " + tags + " " + cost + " " + image + " " + sold);

	    Item newItem = new Item();
	    newItem.setId(id);
	    newItem.setName(name);
	    newItem.setItem_description(description);
	    newItem.setTags(tags);
	    newItem.setCost(Double.parseDouble(cost));
	    newItem.setImage(image);
	    newItem.setSold(sold);

	    Account seller = as.getAccountById(accountid);

	    logger.info("ACCOUNT " + seller);
	    newItem.setSeller(seller);

	    is.saveItem(newItem);

	    return newItem;
	}

}
