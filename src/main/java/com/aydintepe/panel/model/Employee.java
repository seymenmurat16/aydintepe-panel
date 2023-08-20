package com.aydintepe.panel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 48)
  @Size(max = 48)
  private String name;

  @Column(length = 11)
  @Size(max = 11)
  private String mobile;

  @Column(length = 26)
  @Size(max = 26)
  private String iban;

  private String address;

  private Float salary;

  private Float advancePayment;

  private LocalDateTime createTime;
}
