package com.revature.services;

import java.util.List;

import com.revature.beans.Account;

public interface AccountService {
	public Account login(String user, String pass);
	Account getAccountById(int i);
	List<Account> getAccounts();
	Account saveAccount(Account a);
	void updateEmail(String newEmail, int id);
	void updateLocation(String newLocation, int id);
	void updatePhone(String newPhone, int id);
	void updateDescription(String newDescription, int id);
	void deleteAccount(Account a);
}
