package com.aydintepe.panel.repository;

import com.aydintepe.panel.model.Expense;
import com.aydintepe.panel.model.MilkProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
