package com.bridgelabz.bookorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookorder.dto.OrderDTO;
import com.bridgelabz.bookorder.dto.ResponseDTO;
import com.bridgelabz.bookorder.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderApiController {
	@Autowired
	private IOrderService orderService;
	
	@PostMapping("/addOrderDetails")
	public ResponseEntity<ResponseDTO> placeOrder(@RequestHeader  String token, @RequestBody OrderDTO orderDto){
		ResponseDTO response=orderService.placeOrder(token, orderDto);
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
	}
	@GetMapping("/getAllOrders")
	public ResponseEntity<ResponseDTO>getAllOrderDetails(@RequestHeader  String token){
		ResponseDTO response=orderService.getAllOrder(token);
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getOrder/{id}")
	public ResponseEntity<ResponseDTO> getOrderById(@RequestHeader String token, @PathVariable Long id) {
		ResponseDTO respDTO = orderService.getOrderById(token, id);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	@DeleteMapping("/deleteOrder/{id}")
	public ResponseEntity<ResponseDTO> cancelOrderById(@RequestHeader String token,@PathVariable Long id) {
		orderService.cancelOrderById(token, id);
		ResponseDTO respDTO = new ResponseDTO("Deleted Order Details with id : ","this order no "+id+" is canceled the order",200);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
	}
	

}
