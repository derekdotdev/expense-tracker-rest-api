package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.Expense;
import com.derekdileo.expensetrackerrestapi.exceptions.ResourceNotFoundException;
import com.derekdileo.expensetrackerrestapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    // Runtime constructor-based injection of ExpenseRepository dependency
    private final ExpenseRepository expenseRepo;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        // Call JPA repository method
        return expenseRepo.findAll(page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()) {
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the id: " + id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        expenseRepo.deleteById(id);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        return expenseRepo.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        // Get existing expense object for comparison
        Expense existingExpense = getExpenseById(id);

        // Set fields to existing expense object (only if new information exists)
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
        return expenseRepo.save(existingExpense);
    }


}
