package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.BuyerHistory;
import com.revature.data.BuyerHistoryDAO;

@Service
public class BuyerHistoryServiceImpl implements BuyerHistoryService {
	@Autowired
	private BuyerHistoryDAO bhd;

	public BuyerHistory getBuyerHistoryById(int i) {
		return bhd.getById(i);
	}

	public List<BuyerHistory> getBuyerHistory() {
		return bhd.getAllBuyerHistory();
	}

	public BuyerHistory saveBuyerHistory(BuyerHistory b) {
		return bhd.save(b);
	}

}
