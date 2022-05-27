package com.derekdileo.expensetrackerrestapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

// Lombok
@Data // getters, setters, toString, hashCode, equals
@AllArgsConstructor
@NoArgsConstructor

// javax.persistence
@Entity
@Table(name = "tbl_expenses")

public class Expense {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_name")
    @NotNull(message = "Expense name must not be null")
    @Size(min = 3, message = "Expense name must be at least 3 characters")
    private String name;

    // @Column(name = 'description') not required because name matches
    @NotNull(message = "Expense description must not be null")
    private String description;

    @Column(name = "expense_amount")
    @NotNull(message = "Expense amount must not be null")
    private BigDecimal amount;

    @NotNull(message = "Expense category must not be null")
    private String category;

    @NotNull(message = "Expense date must not be null")
    private Date date;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
