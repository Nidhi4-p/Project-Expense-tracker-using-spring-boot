package com.nid.expensetrackerapi.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nid.expensetrackerapi.entity.Expense;
import com.nid.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.nid.expensetrackerapi.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenserepo;
	
	@Autowired
	private UserService usersrvc;
	
	@Override
	public Page<Expense> getAllExpenses(Pageable page)
	{
		System.out.println(usersrvc.getLoggedInUser().getId());
		return expenserepo.findByUserId(usersrvc.getLoggedInUser().getId(),page);
		 
	}
	@Override
	public Expense getExpenseById(Long id)
	{
		Optional<Expense> expense = expenserepo.findByUserIdAndId(usersrvc.getLoggedInUser().getId(),id);
		if(expense.isPresent())
		{
			return expense.get();
		}
		throw new ResourceNotFoundException("Exception occured : Id not found " + id);
		
	}
	@Override
	public void deleteExpenseById(Long id)
	{
		Expense expense = getExpenseById(id);
		expenserepo.delete(expense);
	}
	@Override 
	public Expense saveExpenseDetails(Expense expense)
	{
		expense.setUser(usersrvc.getLoggedInUser());
		return expenserepo.save(expense);
	}
	@Override
	public Expense updateExpenseDetails(Long id, Expense expense)
	{
		Expense existingExpense = getExpenseById(id);
		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName() );
		existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription() );
		existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory() );
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate() );
		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount() );
		return expenserepo.save(existingExpense);
	}
	@Override
	public List<Expense> readByCategory(String category, Pageable page) {
		return expenserepo.findByUserIdAndCategory(usersrvc.getLoggedInUser().getId(),category, page).toList();
		
	}
	@Override
	public List<Expense> readByName(String keyword, Pageable page) {
		return expenserepo.findByUserIdAndNameContaining(usersrvc.getLoggedInUser().getId(),keyword,page).toList();
	}
	@Override
	public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
		if(startDate == null)
		{
			startDate = new Date(0);
		}
		if(endDate == null)
		{
			endDate = new Date(System.currentTimeMillis());
		}
		return expenserepo.findByUserIdAndDateBetween(usersrvc.getLoggedInUser().getId(),startDate,endDate,page).toList();
	}
	
	
}
