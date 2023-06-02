package com.kiran.expenseagain.controller;

import com.kiran.expenseagain.entity.Expense;
import com.kiran.expenseagain.service.ExpenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@Slf4j
@AllArgsConstructor
public class ExpenseController {

    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(Pageable page) {
        log.info("ExpenseController :: getAllExpenses called");
        List<Expense> expenses = expenseService.getAllExpense(page).toList();
        return new ResponseEntity<List<Expense>>(expenses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("id") Long expenseId) {
        log.info("ExpenseController :: getExpenseById called");
        return new ResponseEntity<Expense>(expenseService.getExpenseById(expenseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Expense> saveExpense(@Valid @RequestBody Expense expense) {
        log.info("ExpenseController :: saveExpense called");
        return new ResponseEntity<Expense>(expenseService.saveExpense(expense), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId) {
        log.info("ExpenseController :: deleteExpense called");
        expenseService.deleteExpense(expenseId);
        return new ResponseEntity<String>("Expense with id: " + expenseId + "deleted successfully", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") Long id, @RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.updateExpense(id, expense), HttpStatus.OK);
    }

    @GetMapping("/name")
    ResponseEntity<List<Expense>> getExpenseByCategoryName(
            @RequestParam String keyword, Pageable page) {
        List<Expense> expenses = expenseService.filterByName(keyword, page);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/category")
    ResponseEntity<List<Expense>> getExpenseByCategory(@RequestParam String category, Pageable page) {
        List<Expense> expenses = expenseService.filterByCategory(category, page);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
}
