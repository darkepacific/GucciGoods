package com.revature.services;

import java.util.List;

import com.revature.beans.BuyerHistory;

public interface BuyerHistoryService {
	
	BuyerHistory getBuyerHistoryById(int i);

	List<BuyerHistory> getBuyerHistory();

	BuyerHistory saveBuyerHistory(BuyerHistory b);
}