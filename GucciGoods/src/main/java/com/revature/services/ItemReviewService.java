package com.revature.services;

import java.util.List;

import com.revature.beans.Item;
import com.revature.beans.ItemReview;

public interface ItemReviewService {

	ItemReview getItemReviewById(int i);

	List<ItemReview> getItemReview();
	List<ItemReview> getItemReviews(Item itemid);

	ItemReview saveItemReview(ItemReview r);
}