package com.revature.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.beans.BuyerHistory;
import com.revature.util.HibernateUtil;


@Component
public class BuyerHistoryHibernate implements BuyerHistoryDAO{
	@Autowired // To be auto-wired it has to be a BEAN
	private HibernateUtil hu;
	
	public BuyerHistory save(BuyerHistory b) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.save(b);
		t.commit();
		s.close();
		return b;
	}

	public List<BuyerHistory> getAllBuyerHistory() {
		Session s = hu.getSession();
		List<BuyerHistory> b = s.createQuery("From com.revature.beans.BuyerHistory", BuyerHistory.class).list();
		s.close();
		return b;
	}

	public BuyerHistory getById(int i) {
		Session s = hu.getSession();
		BuyerHistory b = s.get(BuyerHistory.class, i);
		s.close();
		return b;
	}

	public BuyerHistory update(BuyerHistory b) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.update(b);
		t.commit();
		s.close();
		return b;
	}

	public void delete(BuyerHistory b) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.delete(b);
		t.commit();
		s.close();
	}
}
