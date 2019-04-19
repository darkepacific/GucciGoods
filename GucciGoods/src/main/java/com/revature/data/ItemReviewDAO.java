package com.revature.data;

import java.util.List;

import com.revature.beans.Item;
import com.revature.beans.ItemReview;

public interface ItemReviewDAO {

	ItemReview save(ItemReview r);

	List<ItemReview> getAllItemReviews();
	
	List<ItemReview> getItemReviews(Item itemid);

	ItemReview getById(int i);

	ItemReview update(ItemReview r);

	void delete(ItemReview r);
}
