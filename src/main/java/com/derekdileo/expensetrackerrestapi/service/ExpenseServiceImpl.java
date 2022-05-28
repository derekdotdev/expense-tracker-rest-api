package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.Expense;
import com.derekdileo.expensetrackerrestapi.exceptions.ResourceNotFoundException;
import com.derekdileo.expensetrackerrestapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    // Runtime constructor-based injection of ExpenseRepository and UserService dependencies
    private final ExpenseRepository expenseRepo;
    private final UserService userService;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepo, UserService userService) {
        this.expenseRepo = expenseRepo;
        this.userService = userService;
    }

    @Override
    public Expense createExpense(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepo.save(expense);
    }

    @Override
    public Page<Expense> readExpenses(Pageable page) {
        // Call JPA repository method
        return expenseRepo.findByUserId(
                userService.getLoggedInUser().getId(), page);
    }

    @Override
    public List<Expense> readExpensesByCategory(String category, Pageable page) {
        return expenseRepo.findByCategory(userService.getLoggedInUser().getId(), category, page).toList();
    }

    @Override
    public List<Expense> readExpensesByName(String name, Pageable page) {
        return expenseRepo.findByNameContaining(userService.getLoggedInUser().getId(), name, page).toList();
    }

    @Override
    public List<Expense> readExpensesByDate(Date startDate, Date endDate, Pageable page) {
        // null check for dates
        if (startDate == null) {
            startDate = new Date(0);
        }
        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }

        Page<Expense> expenses = expenseRepo.findByDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page);

        return expenses.toList();
    }

    @Override
    public Expense readExpenseById(Long id) {
        Optional<Expense> expense =
                expenseRepo.findByUserIdAndId(
                        userService.getLoggedInUser().getId(), id);
        if (expense.isPresent()) {
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the id: " + id);
    }


    @Override
    public Expense updateExpense(Long id, Expense expense) {
        // Get existing expense object for comparison
        Expense existingExpense = readExpenseById(id);

        // Set fields to existing expense object (only if new information exists)
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
        return expenseRepo.save(existingExpense);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = readExpenseById(id);
        expenseRepo.delete(expense);
    }

}
