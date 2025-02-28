package com.revature.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.beans.Account;
import com.revature.beans.Item;
import com.revature.util.HibernateUtil;

@Component
public class ItemHibernate implements ItemDAO{
	@Autowired // To be auto-wired it has to be a BEAN
	private HibernateUtil hu;

	public Item save(Item m) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.save(m);
		t.commit();
		s.close();
		return m;
	}

	public List<Item> getAllItems() {
		Session s = hu.getSession();
		List<Item> m = s.createQuery("From com.revature.beans.Item", Item.class).list();
		s.close();
		return m;
	}
	
	@Override
	public List<Item> getItemsByUser(int userId) {
	    Session s = hu.getSession();
	    Query query = s.createQuery("FROM Item WHERE account_id = :userId", Item.class);
	    query.setParameter("userId", userId);
	    List<Item> items = query.list();
	    s.close();
	    return items;
	}

	public Item getById(int i) {
		Session s = hu.getSession();
		Item m = s.get(Item.class, i);
		s.close();
		return m;
	}

	public Item update(Item m) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.update(m);
		t.commit();
		s.close();
		return m;
	}
	
	public List<Item> getItemsByName(String name){
		Session s = hu.getSession();
		String hql = "FROM Item i WHERE i.name = :name";
		Query query = s.createQuery(hql);
		query.setParameter("name", name);
		List<Item> items = query.list();
		
		System.out.println("ITEMS: " + items);
		if(items.isEmpty()) {
			return null;
		}
		s.close();
		
		return items;
	}

	public void delete(Item m) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.delete(m);
		t.commit();
		s.close();
	}
}
