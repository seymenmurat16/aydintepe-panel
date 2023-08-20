package com.aydintepe.panel.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserDto {
  private Long id;

  private String username;

  private String email;
  private boolean isActive;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
