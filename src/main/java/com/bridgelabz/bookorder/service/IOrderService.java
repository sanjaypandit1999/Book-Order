package com.bridgelabz.bookorder.service;

import org.springframework.stereotype.Service;

import com.bridgelabz.bookorder.dto.OrderDTO;
import com.bridgelabz.bookorder.dto.ResponseDTO;

@Service
public interface IOrderService {

	ResponseDTO placeOrder(String token, OrderDTO orderDto);

	ResponseDTO getAllOrder(String token);

	ResponseDTO getOrderById(String token, Long id);

	void cancelOrderById(String token, Long id);

}
