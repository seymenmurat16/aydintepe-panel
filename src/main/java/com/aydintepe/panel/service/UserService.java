package com.aydintepe.panel.service;

import com.aydintepe.panel.dto.UserDto;
import com.aydintepe.panel.dto.converter.UserDtoConverter;
import com.aydintepe.panel.dto.request.RequestChangeUsersActivation;
import com.aydintepe.panel.dto.request.RequestCreateNewUser;
import com.aydintepe.panel.exception.UserNotFoundException;
import com.aydintepe.panel.model.User;
import com.aydintepe.panel.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserDtoConverter userDtoConverter;
  private final AwsCognitoService awsCognitoService;


  public UserService(UserRepository userRepository, UserDtoConverter userDtoConverter, AwsCognitoService awsCognitoService) {
    this.userRepository = userRepository;
    this.userDtoConverter = userDtoConverter;
    this.awsCognitoService = awsCognitoService;
  }

  @Transactional
  public UserDto createNewUser(RequestCreateNewUser requestCreateNewUser, Jwt jwt) {
    User user = saveNewUser(requestCreateNewUser);
    awsCognitoService.callAwsForCreateNewUser(requestCreateNewUser.getUsername(), requestCreateNewUser.getEmail());
    return userDtoConverter.convert(user);
  }

  @Transactional
  public void changeUsersActivation(RequestChangeUsersActivation requestChangeUsersActivation) {
    User user = getUserByUsername(requestChangeUsersActivation.getUsername());
    awsCognitoService.changeActivationOnAws(user);
    user.setActive(!user.isActive());
    userRepository.save(user);
  }

  private void deleteUser(User user) {
    awsCognitoService.callAwsForDeleteUser(user.getUsername());
    userRepository.deleteById(user.getId());
  }

  private User saveNewUser(RequestCreateNewUser requestCreateNewUser) {
    User user = new User();
    user.setUsername(requestCreateNewUser.getUsername());
    user.setEmail(requestCreateNewUser.getEmail());
    user.setCreateTime(LocalDateTime.now());
    user.setActive(true);
    return userRepository.save(user);
  }

  protected User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("User not found. Username : " + username));
  }

  protected User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found. Id : " + id));
  }

  protected String getUsernameById(Long id) {
    return getUserById(id).getUsername();
  }

}
