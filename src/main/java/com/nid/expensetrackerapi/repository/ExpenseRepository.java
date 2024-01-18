package com.nid.expensetrackerapi.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nid.expensetrackerapi.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Page<Expense> findByUserIdAndCategory(Long userid, String category, Pageable page);
	Page<Expense> findByUserIdAndNameContaining(Long userid,String keyword, Pageable page);
	Page<Expense> findByUserIdAndDateBetween(Long userid,Date startDate, Date endDate, Pageable page );
	Page<Expense> findByUserId(Long userid, Pageable page);
	Optional<Expense> findByUserIdAndId(Long userid,Long expenseid);
} 
