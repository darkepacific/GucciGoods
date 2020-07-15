package com.revature.beans;

import javax.persistence.*;

@Entity
@Table(name="item_review")
public class ItemReview {
	@Id
	@GeneratedValue(generator="item_review_id", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="item_review_id", sequenceName="item_review_id_seq", allocationSize=1)
	@Column(name="review_id")
	private Integer id;
	
	private String review_description;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="name")
	private Item item;		
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="account_id")
	private Account user;	
	
	

	public ItemReview() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getReview_description() {
		return review_description;
	}

	public void setReview_description(String review_description) {
		this.review_description = review_description;
	}

	@Override
	public String toString() {
		return "ItemReview [id=" + id + ", user=" + user + ", item=" + item + ", review_description="
				+ review_description + "]";
	}

	
}