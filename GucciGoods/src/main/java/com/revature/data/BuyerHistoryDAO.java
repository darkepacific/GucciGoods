package com.revature.data;

import java.util.List;

import com.revature.beans.BuyerHistory;

public interface BuyerHistoryDAO {
	BuyerHistory save(BuyerHistory b);

	List<BuyerHistory> getAllBuyerHistory();

	BuyerHistory getById(int i);

	BuyerHistory update(BuyerHistory b);

	void delete(BuyerHistory b);
}
