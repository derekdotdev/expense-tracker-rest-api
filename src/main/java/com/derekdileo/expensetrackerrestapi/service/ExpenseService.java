package com.derekdileo.expensetrackerrestapi.service;

import com.derekdileo.expensetrackerrestapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {

    Expense createExpense(Expense expense);

    Expense readExpenseById(Long id);

    Page<Expense> readExpenses(Pageable page);

    List<Expense> readExpensesByCategory(String category, Pageable page);

    List<Expense> readExpensesByName(String name, Pageable page);

    List<Expense> readExpensesByDate(Date startDate, Date endDate, Pageable page);

    Expense updateExpenseDetails(Long id, Expense expense);

    void deleteExpenseById(Long id);

}
