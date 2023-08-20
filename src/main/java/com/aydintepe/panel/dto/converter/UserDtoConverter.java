package com.aydintepe.panel.dto.converter;


import com.aydintepe.panel.dto.UserDto;
import com.aydintepe.panel.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

  public UserDto convert(User from) {
    if (from == null) {
      return null;
    }
    return new UserDto(from.getId(), from.getUsername(), from.getEmail(), from.isActive(), from.getCreateTime(), from.getUpdateTime());
  }
}
