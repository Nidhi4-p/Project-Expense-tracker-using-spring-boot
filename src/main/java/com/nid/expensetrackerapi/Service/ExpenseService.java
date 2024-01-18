package com.nid.expensetrackerapi.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nid.expensetrackerapi.entity.Expense;

public interface ExpenseService {

	Page<Expense> getAllExpenses(Pageable page);
	
	Expense getExpenseById(Long Id);
	
	void deleteExpenseById(Long id);
	
	Expense saveExpenseDetails(Expense expense);
	
	Expense updateExpenseDetails(Long id,Expense expense);
	
	List<Expense> readByCategory(String category,Pageable page);
	
	List<Expense> readByName(String keyword,Pageable page);
	
	List<Expense> readByDate(Date startDate, Date endDate, Pageable page);
}
