package com.revature.data;

import java.util.List;

import com.revature.beans.Account;

public interface AccountDAO {
	Account login(String username, String password);	
	Account save(Account a);
	List<Account> getAllAccounts();
	Account getById(int i);
	Account update(Account a);
	void delete(Account a);
	void updateEmail(String newEmail, int id);
	void updateLocation(String newEmail, int id);
	void updatePhone(String newEmail, int id);
	void updateDescription(String newEmail, int id);
	
}
