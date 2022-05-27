package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    Page<Expense> getExpenses(Pageable page);

    List<Expense> getExpensesByCategory(String category, Pageable page);

    List<Expense> getExpensesByName(String name, Pageable page);

    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);

    Expense createExpense(Expense expense);

    Expense updateExpenseDetails(Long id, Expense expense);

}
