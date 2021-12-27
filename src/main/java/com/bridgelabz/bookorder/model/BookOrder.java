package com.bridgelabz.bookorder.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Order_Details")
public class BookOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long bookId;
	private long userId;
	private String address;
	private int quantity;
	private boolean cancel;
	private double price;
	private LocalDate orderDate;
}
