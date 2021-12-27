package com.bridgelabz.bookorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookorder.model.BookOrder;
@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {

}
