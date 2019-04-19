package com.revature.data;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.beans.Account;
import com.revature.beans.Item;
import com.revature.beans.ItemReview;
import com.revature.util.HibernateUtil;

@Component
public class ItemReviewHibernate implements ItemReviewDAO{
	@Autowired // To be auto-wired it has to be a BEAN
	private HibernateUtil hu;

	public ItemReview save(ItemReview r) {
		System.out.println("In ItemReview HIBERNATE!@#!@#");
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.save(r);
		t.commit();
		s.close();
		return r;
	}

	public List<ItemReview> getAllItemReviews() {
		Session s = hu.getSession();
		List<ItemReview> r = s.createQuery("From com.revature.beans.ItemReview", ItemReview.class).list();
		s.close();
		return r;
	}

	public ItemReview getById(int i) {
		Session s = hu.getSession();
		ItemReview r = s.get(ItemReview.class, i);
		s.close();
		return r;
	}

	public ItemReview update(ItemReview r) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.update(r);
		t.commit();
		s.close();
		return r;
	}

	public void delete(ItemReview r) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.delete(r);
		t.commit();
		s.close();
	}

	@Override
	public List<ItemReview> getItemReviews(Item item) {
		Session s = hu.getSession();
		String hql = "FROM ItemReview";
		Query query = s.createQuery(hql);
		
		List<ItemReview> accs = query.list();
		
		List<ItemReview> reviews = new ArrayList<ItemReview>();
		
		for(int i = 0; i < accs.size(); i++) {
			if(accs.get(i).getItem().getName().equals(item.getName())) {
				reviews.add(accs.get(i));
			}
		}
		if(accs.isEmpty()) {
			return null;
		}
		System.out.println("REVIEWS      " +reviews);
		s.close();
		return reviews;
	}
}
