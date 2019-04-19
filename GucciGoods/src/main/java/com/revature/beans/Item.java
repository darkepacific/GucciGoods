package com.revature.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item {
	@Id
	@GeneratedValue(generator="item_id", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="item_id", sequenceName="item_id_seq", allocationSize=1)
	@Column(name="item_id")
	private Integer id;
	private String name;
	private String item_description;
	private String tags;
	private Double cost;
	private String image;
	private Integer sold;
	
	
	@ManyToOne(fetch=FetchType.EAGER) //, cascade=CascadeType.ALL
	@JoinColumn(name="account_id")
	private Account seller;
	
//	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
//	@JoinColumn(name="account_id")
//	private Account buyer;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="item")
	private List<ItemReview> itemReviews = new ArrayList<ItemReview>();
	
	public Item() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getSeller() {
		return seller;
	}

	public void setSeller(Account seller) {
		this.seller = seller;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSold() {
		return sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

	@Override
	public String toString() {
		return "item [id=" + id + ", seller=" + seller + ", name=" + name + ", item_description="
				+ item_description + ", tags=" + tags + ", cost=" + cost + ", image=" + image
				+ ", sold=" + sold + "]";
	}

}