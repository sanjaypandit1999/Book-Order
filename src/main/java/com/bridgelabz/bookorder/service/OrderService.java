package com.bridgelabz.bookorder.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.bookorder.dto.OrderDTO;
import com.bridgelabz.bookorder.dto.ResponseDTO;
import com.bridgelabz.bookorder.exception.BookOrderException;
import com.bridgelabz.bookorder.model.Book;
import com.bridgelabz.bookorder.model.BookOrder;
import com.bridgelabz.bookorder.repository.BookOrderRepository;
import com.bridgelabz.bookorder.util.JwtToken;
@Service
public class OrderService implements IOrderService {

	@Autowired
	BookOrderRepository bookRepository;
	@Autowired
	JwtToken jwtToken;
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseDTO placeOrder(String token, OrderDTO orderDto) {
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {
			BookOrder bookOrder = modelmapper.map(orderDto, BookOrder.class);
			Book book = restTemplate.getForObject("http://BOOKSTORE-BOOK/book/getBook/"+orderDto.getBookId()+"?token="+token,Book.class);
			System.out.println(book.toString());
			if(book != null) {
				if(book.getBookQuantity() > bookOrder.getQuantity()) {
					int quantity = book.getBookQuantity() - bookOrder.getQuantity();
					restTemplate.getForObject("http://BOOKSTORE-BOOK/book/changequantity?token="+token+"&id="+orderDto.getBookId()+"&quantity="+quantity,Book.class);
					bookOrder.setPrice(book.getBookPrice()*orderDto.getQuantity());
					bookOrder.setUserId(jwtToken.decodeToken(token));
					bookOrder.setOrderDate(LocalDate.now());
					bookRepository.save(bookOrder);
					
				}else
					throw new BookOrderException("Only "+book.getBookQuantity()+" avaialables. Please Order less.");
			}
			return new ResponseDTO("Order is placed",bookOrder,200);
				
		}else
			throw new BookOrderException("Access Denied...! please check the login token");
	}

	@Override
	public ResponseDTO getAllOrder(String token) {
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {
			List<BookOrder> bookOrders = bookRepository.findAll();
			if(bookOrders!=null) {
				return new ResponseDTO("", bookOrders,200);
			}else
				throw new BookOrderException("Database is empty");
		}else
			throw new BookOrderException("Access Denied...! please check the login token");
	}

	@Override
	public ResponseDTO getOrderById(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {
			Optional<BookOrder> isPresenet = bookRepository.findById(id);
			if(isPresenet.isPresent()) {
				return new ResponseDTO("Your boo order is", isPresenet.get(),200);
			}else
				throw new BookOrderException("Database is empty");
		}else
			throw new BookOrderException("Access Denied...! please check the login token");
	}

	@Override
	public void cancelOrderById(String token, Long id) {
		boolean verify = restTemplate.getForObject("http://BOOK-USER/user/verify?token="+token, Boolean.class);
		if(verify) {
			Optional<BookOrder> bookOrder = bookRepository.findById(id);
			if(bookOrder.isPresent()) {
				bookOrder.get().setCancel(true);
				 bookRepository.save(bookOrder.get());
			}else {
				throw new BookOrderException("you don't have any order! please add order");
			}
		}else
			throw new BookOrderException("Access Denied...! please check the login token");
		
	}

}
