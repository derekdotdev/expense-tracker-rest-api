package com.derekdileo.expensetrackerrestapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.derekdileo.expensetrackerrestapi.entity.Expense;

import java.sql.Date;
import java.util.Optional;

/*
* A class to query the database using JPA finder methods which can be found at:
* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
* */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // SELECT * FROM tbl_expenses WHERE user_id=?
    Page<Expense> findByUserId(Long userId, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND id=?
    Optional<Expense> findByUserIdAndId(Long userId, Long expenseId);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND category=?   (with pagination and sorting)
    Page<Expense> findByUserIdAndCategory(Long userId, String category, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND name LIKE '%keyword%'
    Page<Expense> findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

    // SELECT * FROM tbl_expenses WHERE user_id=? AND date BETWEEN 'startDate' AND 'endDate'
    Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable page);

}
