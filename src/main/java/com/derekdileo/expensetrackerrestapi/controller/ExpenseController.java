package com.derekdileo.expensetrackerrestapi.controller;

import com.derekdileo.expensetrackerrestapi.entity.Expense;
import com.derekdileo.expensetrackerrestapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    // Runtime constructor-based injection of ExpenseService dependency
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id) {
        return expenseService.getExpenseById(id);
    }

    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam Long id) {
        expenseService.deleteExpenseById(id);
    }

    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@RequestBody Expense expense) {
        return expenseService.saveExpenseDetails(expense);
    }


}












//    // Get Expense by id and user using query string (String for now)
//     // localhost:8080/users/expenses?userId=23&id=34
//    @GetMapping("/users/expenses")
//    public String getExpenseById(@RequestParam("userId") Long user, @RequestParam Long id) {
//        return "The expense id is: " + id + " and the user id is: " + userId;
//    }