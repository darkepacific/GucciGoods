package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Account;
import com.revature.data.AccountDAO;


@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDAO ad;
	
	@Override
	public Account login(String user, String pass) {
		Account loggedIn = ad.login(user, pass);
		return loggedIn;
	}
	
	public Account getAccountById(int i) {
		System.out.println("in the service");
		return ad.getById(i);
	}

	public List<Account> getAccounts() {
		return ad.getAllAccounts();
	}

	public Account saveAccount(Account a) {
		return ad.save(a);
	}
	
	public void updateEmail(String newEmail, int id) {
		ad.updateEmail(newEmail, id);
	}
	
	public void updateLocation(String newLocation, int id) {
		ad.updateLocation(newLocation, id);
	}

	public void updatePhone(String newPhone, int id) {
		ad.updatePhone(newPhone, id);
	}

	public void updateDescription(String newDescription, int id) {
		ad.updateDescription(newDescription, id);
	}
	
	public void deleteAccount(Account a) {
		ad.delete(a);
	}
}
