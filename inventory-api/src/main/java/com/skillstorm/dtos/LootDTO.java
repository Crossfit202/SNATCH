package com.skillstorm.dtos;

public class LootDTO {
	
	private String itemName;
	
	private int quantity;


	public LootDTO(String itemName, int quantity) {
		super();
		this.itemName = itemName;
		this.quantity = quantity;
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

	@Override
	public String toString() {
		return "LootDTO [itemName=" + itemName + ", quantity=" + quantity + "]";
	}
	
	
	
}
