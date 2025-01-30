package com.skillstorm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "loot")
public class Loot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loot_id")
	private int lootId;
	
	@Column(name = "item_name")
	private String itemName;
	
	@Column(name = "quantity")
	private int quantity;
	
	
	// CONSTRUCTORS
	public Loot() {
		super();
	}


	public Loot(int lootId, String itemName, int quantity) {
	super();
	this.lootId = lootId;
	this.itemName = itemName;
	this.quantity = quantity;
	}


	// GETTERS AND SETTERS
	public int getLootId() {
		return lootId;
	}



	public void setLootId(int lootId) {
		this.lootId = lootId;
	}



	public String getItemName() {
		return itemName;
	}



	public void setItemName(String itemName) {
		this.itemName = itemName;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	// TO STRING
	@Override
	public String toString() {
		return "Loot [lootId=" + lootId + ", itemName=" + itemName + ", quantity=" + quantity + "]";
	}
	
	
}
