package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Item;
import com.revature.beans.ItemReview;
import com.revature.data.ItemReviewDAO;


@Service
public class ItemReviewServiceImpl implements ItemReviewService {
	@Autowired
	private ItemReviewDAO ird;

	public ItemReview getItemReviewById(int i) {
		return ird.getById(i);
	}

	public List<ItemReview> getItemReview() {
		return ird.getAllItemReviews();
	}

	public ItemReview saveItemReview(ItemReview r) {
		System.out.println("in service");
		return ird.save(r);
	}

	@Override
	public List<ItemReview> getItemReviews(Item itemid) {
		List<ItemReview> gotReviews = ird.getItemReviews(itemid);
		return gotReviews;
	}


}
