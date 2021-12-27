package com.bridgelabz.bookorder.dto;

import lombok.Data;

@Data
public class OrderDTO {
	
	private long bookId;
	private String address;
	private int quantity;
	private double price;
}
