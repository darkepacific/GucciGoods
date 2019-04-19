package com.revature.beans;

import javax.persistence.*;

//@Entity
//@Table(name="buyer_history")
public class BuyerHistory {
//	@Id
//	@GeneratedValue(generator="buyer_history_id", strategy=GenerationType.SEQUENCE)
//	@SequenceGenerator(name="buyer_history_id", sequenceName="buyer_id_seq", allocationSize=1)
//	@Column(name="buyer_id")
	private Integer id;
//	
//	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL) 		//You don't need a join here because you're not storing an account object
//	@JoinColumn(name="account_id")
	private Account buyer;										
	
//	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
//	@JoinColumn(name="item_id")
	private Item item;										//One item has one buyer

	public BuyerHistory() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getBuyer() {
		return buyer;
	}

	public void setBuyer(Account buyer) {
		this.buyer = buyer;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "BuyerHistory [id=" + id + ", buyer=" + buyer + ", item=" + item + "]";
	}


}