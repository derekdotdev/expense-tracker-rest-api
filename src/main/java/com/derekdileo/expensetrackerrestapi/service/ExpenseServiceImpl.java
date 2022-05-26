package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.Expense;
import com.derekdileo.expensetrackerrestapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    /* Inject ExpenseRepository at runtime
     Using constructor-based dependency injection */
    private final ExpenseRepository expenseRepo;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepo) {
        this.expenseRepo = expenseRepo;
    }

    @Override
    public List<Expense> getAllExpenses() {
        // Call JPA repository method
        return expenseRepo.findAll();
    }
}
