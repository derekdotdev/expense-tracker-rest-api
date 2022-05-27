package com.derekdileo.expensetrackerrestapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.derekdileo.expensetrackerrestapi.entity.Expense;

/*
* A class to query the database using JPA methods which can be found at:
* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
* */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // SELECT * FROM tbl_expenses WHERE category=?
    // (with pageable pagination and sorting)
    Page<Expense> findByCategory(String category, Pageable page);

}
