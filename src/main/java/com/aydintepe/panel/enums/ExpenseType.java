package com.aydintepe.panel.enums;

public enum ExpenseType {
  GENERAL(1),
  FUEL(2),
  INVOICE(3);
  private final int id;

  ExpenseType(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
