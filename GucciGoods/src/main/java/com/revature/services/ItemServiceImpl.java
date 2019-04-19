package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Item;
import com.revature.data.ItemDAO;


@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemDAO md;
	
	public Item getItemById(int i) {
		return md.getById(i);
	}

	public List<Item> getItems() {
		return md.getAllItems();
	}

	public Item saveItem(Item m) {
		return md.save(m);
	}
	
	public Item update(Item m) {
		return md.update(m);
	}
	public List<Item> getItemsByName(String name){
		return md.getItemsByName(name);
	}
	
	public void deleteItem(Item m) {
		md.delete(m);
	}
	
}