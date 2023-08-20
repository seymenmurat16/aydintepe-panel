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
import org.springframework.data.repository.cdi.Eager;

@Entity
@Getter @Setter @NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, length = 24)
  @Size(max = 24)
  private String username;
  @Size(max = 36)
  @Column(length = 36)
  private String email;
  private boolean isActive;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}