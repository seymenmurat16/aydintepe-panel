package com.aydintepe.panel.enums;

import com.aydintepe.panel.enums.ExpenseType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExpenseTypeConverter implements AttributeConverter<ExpenseType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(ExpenseType expenseType) {
    return expenseType != null ? expenseType.getId() : null;
  }

  @Override
  public ExpenseType convertToEntityAttribute(Integer code) {
    if (code != null) {
      for (ExpenseType expenseType : ExpenseType.values()) {
        if (expenseType.getId() == (code)) {
          return expenseType;
        }
      }
    }
    return null;
  }
}
