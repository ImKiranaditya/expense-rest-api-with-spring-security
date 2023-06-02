package com.kiran.expenseagain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_expense")
public class Expense {

    @Id
    @SequenceGenerator(
            name = "seqid-gen",
            sequenceName = "tbl_expense_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqid-gen")
    private Long id;

    @NotBlank(message = "Expense Name must not be null")
    @Size(min = 3, message = "Expense Name must be at least 3 characters")
    @Column(name = "expense_name")
    private String name;

    private String description;
    @Column(name = "expense_amount")

    @NotNull(message = "Please provide expense amount")
    private BigDecimal amount;

    @NotBlank(message = "Please provide expense category")
    private String category;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updateAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "user_id",  nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
}
