package com.revature.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(generator = "account_id", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "account_id", sequenceName = "acc_id_seq", allocationSize = 1)
	@Column(name = "account_id")
	private Integer id;
	private Integer admin;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private String location;
	private String avatar;
	private String description;

	// These are the items the account is selling
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
	private List<Item> itemsSold = new ArrayList<Item>();

//	//These are the items the account has Bought		//Use GET_ITEM()
//	@OneToMany(fetch=FetchType.LAZY, mappedBy="buyer")
//	private List<Item> itemsBought = new ArrayList<Item>();

	public Account() {
		super();
	}

	public Account(Integer id, Integer admin, String username, String password, String firstname, String lastname,
			String phone, String email, String location, String avatar, List<Item> itemsSold) {
		super();
		this.id = id;
		this.admin = admin;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.location = location;
		this.avatar = avatar;
		this.itemsSold = itemsSold;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "\naccount [id=" + id + ", admin=" + admin + ", username=" + username + ", password=" + password
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone + ", email=" + email
				+ ", location=" + location + ", avatar=" + avatar + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}