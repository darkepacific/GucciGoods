package com.revature.services;

import java.util.List;

import com.revature.beans.Item;

public interface ItemService {

	Item getItemById(int i);

	List<Item> getItems();
	
	Item update(Item m);
	
	List<Item> getItemsByName(String name);
	
	Item saveItem(Item m);
	
	List<Item> getItemsByUser(int userId);
	
	void deleteItem(Item m);
}
