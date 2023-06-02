package com.kiran.expenseagain.service;

import com.kiran.expenseagain.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

   Page<Expense> getAllExpense(Pageable page);

   Expense getExpenseById(Long id);

    Expense saveExpense(Expense expense);

    void deleteExpense(Long id);

    Expense updateExpense(Long id, Expense expense);

    List<Expense> filterByCategory(String category, Pageable page);


    List<Expense> filterByName(String keyword, Pageable page);
}
