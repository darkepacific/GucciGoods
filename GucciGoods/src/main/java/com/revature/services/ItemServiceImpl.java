package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Item;
import com.revature.data.ItemDAO;


@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemDAO itemDAO;
	
	public Item getItemById(int i) {
		return itemDAO.getById(i);
	}

	public List<Item> getItems() {
		return itemDAO.getAllItems();
	}

	public Item saveItem(Item m) {
		return itemDAO.save(m);
	}
	
	public Item update(Item m) {
		return itemDAO.update(m);
	}
	public List<Item> getItemsByName(String name){
		return itemDAO.getItemsByName(name);
	}
	
	public List<Item> getItemsByUser(int userId) {
	    return itemDAO.getItemsByUser(userId);
	}
	
	public void deleteItem(Item m) {
		itemDAO.delete(m);
	}
	
}