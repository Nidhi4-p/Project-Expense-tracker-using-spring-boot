package com.nid.expensetrackerapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nid.expensetrackerapi.Service.ExpenseService;
import com.nid.expensetrackerapi.entity.Expense;

import javax.validation.Valid;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseservice;
	@GetMapping("/expenses")
	public List<Expense> getAllExpenses(Pageable page)
	{
		return expenseservice.getAllExpenses(page).toList();
	}
	
	@GetMapping("/expenses/{id}")
	public Expense getExpenseById(@PathVariable("id") Long id)
	{
		return expenseservice.getExpenseById(id);
	}
	
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void DeletetExpenseById(@RequestParam("id") Long id )
	{
		expenseservice.deleteExpenseById(id);
	}
	
	@ResponseStatus(value=HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expense saveexpenseDetails(@Valid @RequestBody Expense expense)
	{
		return expenseservice.saveExpenseDetails(expense);
	}
	@PutMapping("/expenses/{id}")
	public Expense updateExpenseDetails(@RequestBody Expense expense,@PathVariable("id") Long id)
	{
		return expenseservice.updateExpenseDetails(id,expense);
	}
	
	@GetMapping("/expenses/category")
	public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable page)
	{
		return expenseservice.readByCategory(category,page);
	}
	@GetMapping("/expenses/name")
	public List<Expense> getExpenseByName(@RequestParam String keyword, Pageable page)
	{
		return expenseservice.readByName(keyword,page);
	}
	@GetMapping("/expenses/date")
	public List<Expense> getAllExpenseByDate(@RequestParam(required = false) Date startDate,
	@RequestParam(required = false) Date endDate,Pageable page)
	{
		return expenseservice.readByDate(startDate,endDate,page);
	}
}
