package com.kiran.expenseagain.service;

import com.kiran.expenseagain.entity.Expense;
import com.kiran.expenseagain.exception.ResourceNotFoundException;
import com.kiran.expenseagain.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserService userService;

    @Override
    public Page<Expense> getAllExpense(Pageable page) {
        log.info("ExpenseServiceImpl :: getAllExpenses called");
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public Expense getExpenseById(Long expenseId) {
        log.info("ExpenseServiceImpl :: getExpenseById called");
        Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(), expenseId);
        if (expense.isPresent()) {
            return expense.get();
        } else {
            throw new ResourceNotFoundException("Expense with the id " + expenseId + " is not found");
        }
    }

    @Override
    public Expense saveExpense(Expense expense) {
        log.info("ExpenseServiceImpl :: saveExpense called");
        expense.setUser(userService.getLoggedInUser());
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        log.info("ExpenseServiceImpl :: deleteExpense called");
        Expense expenseById = getExpenseById(id);
        log.info("ExpenseServiceImpl :: deleteExpense :: expense: " + expenseById);
        expenseRepository.delete(expenseById);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        log.info("ExpenseServiceImpl :: updateExpense called");
        Expense expenseById = getExpenseById(id);
        if (expenseById != null) {
            expenseById.setName(expense.getName() != null ? expense.getName() : expenseById.getName());
            expenseById.setDescription(expense.getDescription() != null ? expense.getDescription() : expenseById.getDescription());
            expenseById.setAmount(expense.getAmount() != null ? expense.getAmount() : expenseById.getAmount());
            expenseById.setCategory(expense.getCategory() != null ? expense.getCategory() : expenseById.getCategory());
            return expenseRepository.save(expenseById);
        } else {
            throw new ResourceNotFoundException("Resource with the id %s is not found" + id);
        }
    }

    @Override
    public List<Expense> filterByCategory(String category, Pageable page) {
        log.info("ExpenseServiceImpl :: filterByCategory called");
        return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
    }

    @Override
    public List<Expense> filterByName(String keyword, Pageable page) {
        log.info("ExpenseServiceImpl :: filterByName called");
        return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
    }
}
