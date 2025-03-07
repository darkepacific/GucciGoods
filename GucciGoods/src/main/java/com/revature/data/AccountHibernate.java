package com.revature.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.beans.Account;
import com.revature.controller.LoginController;
import com.revature.util.HibernateUtil;

@Component
public class AccountHibernate implements AccountDAO{
	@Autowired			//To be auto-wired it has to be a BEAN
	private HibernateUtil hu;
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Override
	public Account login(String username, String password) {
		Session s = hu.getSession();
		logger.info(username);
		logger.info(password);
		String hql = "FROM Account a WHERE a.username = :username AND a.password = :password";
		Query query = s.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("password", password);
		List<Account> accs = query.list();
		
		System.out.println("ACCOUNTS: " + accs);
		if(accs.isEmpty()) {
			return null;
		}
		Account a = accs.get(0);
		s.close();
		return a;
	}
	
	public Account save(Account a) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.save(a);
		t.commit();
		s.close();
		return a;
	}
	
	public List<Account> getAllAccounts() {
		Session s = hu.getSession();
		List<Account> a = s.createQuery("From com.revature.beans.Account", Account.class).list();
		s.close();
		return a;
	}

	public Account getById(int i) {
		Session s = hu.getSession();
		Account a = s.get(Account.class, i);
		s.close();
		return a;
	}
	
	public Account update(Account a) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.update(a);
		t.commit();
		s.close();
		return a;
	}
	
	public void delete(Account a) {
		Session s = hu.getSession();
		Transaction t = s.beginTransaction();
		s.delete(a);
		t.commit();
		s.close();
	}

	@Override
	public void updateEmail(String newEmail, int id) {
	    Session s = hu.getSession();
	    Transaction t = null;
	    try {
	        t = s.beginTransaction();
	        Query query = s.createQuery("update Account set email = :email where id = :userid");
	        query.setParameter("email", newEmail);
	        query.setParameter("userid", id);
	        int result = query.executeUpdate();
	        logger.info("Result " + result);
	        t.commit();
	    } catch (Exception e) {
	        if (t != null) t.rollback(); // ✅ Rollback on failure
	        logger.error("Error updating email", e);
	    } finally {
	        s.close();
	    }
	}

	@Override
	public void updateLocation(String newLocation, int id) {
	    Session s = hu.getSession();
	    Transaction t = null;
	    try {
	        t = s.beginTransaction();
	        Query query = s.createQuery("update Account set location = :location where id = :userid");
	        query.setParameter("location", newLocation);
	        query.setParameter("userid", id);
	        int result = query.executeUpdate();
	        logger.info("Result " + result);
	        t.commit();
	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        logger.error("Error updating location", e);
	    } finally {
	        s.close();
	    }
	}

	@Override
	public void updatePhone(String newPhone, int id) {
	    Session s = hu.getSession();
	    Transaction t = null;
	    try {
	        t = s.beginTransaction();
	        Query query = s.createQuery("update Account set phone = :phone where id = :userid");
	        query.setParameter("phone", newPhone);
	        query.setParameter("userid", id);
	        int result = query.executeUpdate();
	        logger.info("Result " + result);
	        t.commit();
	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        logger.error("Error updating phone", e);
	    } finally {
	        s.close();
	    }
	}

	@Override
	public void updateDescription(String newDescription, int id) {
	    Session s = hu.getSession();
	    Transaction t = null;
	    try {
	        t = s.beginTransaction();
	        Query query = s.createQuery("update Account set description = :description where id = :userid");
	        query.setParameter("description", newDescription);
	        query.setParameter("userid", id);
	        int result = query.executeUpdate();
	        logger.info("Result " + result);
	        t.commit();
	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        logger.error("Error updating description", e);
	    } finally {
	        s.close();
	    }
	}

	@Override
	public void updateAvatar(String newAvatar, int id) {
	    Session s = hu.getSession();
	    Transaction t = null;
	    try {
	        t = s.beginTransaction();
	        Query query = s.createQuery("update Account set avatar = :avatar where id = :userid");
	        query.setParameter("avatar", newAvatar);
	        query.setParameter("userid", id);
	        int result = query.executeUpdate();
	        logger.info("Result " + result);
	        t.commit();
	    } catch (Exception e) {
	        if (t != null) t.rollback();
	        logger.error("Error updating avatar", e);
	    } finally {
	        s.close();
	    }
	}

	
}

