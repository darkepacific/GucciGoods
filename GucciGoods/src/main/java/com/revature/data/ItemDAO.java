package com.revature.data;

import java.util.List;

import com.revature.beans.Item;

public interface ItemDAO {
	Item save(Item m);

	List<Item> getAllItems();
	
    List<Item> getItemsByUser(int userId);

	Item getById(int i);

	Item update(Item m);

	void delete(Item m);
	
	List<Item> getItemsByName(String name);
}