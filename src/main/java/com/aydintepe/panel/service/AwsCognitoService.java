package com.aydintepe.panel.service;

import com.aydintepe.panel.exception.ClientExistsWithSameEmailException;
import com.aydintepe.panel.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminDeleteUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminDisableUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminEnableUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

@Service
public class AwsCognitoService {

  @Value("${aws.userpool.id}")
  private String userPoolId;
  @Value("${spring.profiles.active}")
  private String activeProfile;

  protected void changeActivationOnAws(User user) {
    if (Boolean.TRUE.equals(user.isActive())) {
      callAwsForDisableUser(user.getUsername());
    } else {
      callAwsForEnableUser(user.getUsername());
    }
  }

  protected void callAwsForCreateNewUser(String name, String email) {
    try (CognitoIdentityProviderClient cognitoClient = getCognitoClient()) {
      AttributeType userAttrs = AttributeType.builder()
          .name("email")
          .value(email)
          .build();

      AttributeType userAttrsEmailVerified = AttributeType.builder()
          .name("email_verified")
          .value("True")
          .build();

      AdminCreateUserRequest userRequest = AdminCreateUserRequest.builder()
          .userPoolId(userPoolId)
          .username(name)
          .userAttributes(userAttrs, userAttrsEmailVerified)
          .build();
      cognitoClient.adminCreateUser(userRequest);
    } catch (CognitoIdentityProviderException e) {
      if (e.awsErrorDetails().errorMessage().equals("An account with the email already exists.")) {
        throw new ClientExistsWithSameEmailException();
      }
      throw e;
    }
  }

  protected void callAwsForDisableUser(String username) {
    try (CognitoIdentityProviderClient cognitoClient = getCognitoClient()) {
      AdminDisableUserRequest request = AdminDisableUserRequest.builder()
          .userPoolId(userPoolId)
          .username(username)
          .build();

      cognitoClient.adminDisableUser(request);
    } catch (CognitoIdentityProviderException e) {
      throw e;
    }
  }

  protected void callAwsForEnableUser(String username) {
    try (CognitoIdentityProviderClient cognitoClient = getCognitoClient()) {
      AdminEnableUserRequest request = AdminEnableUserRequest.builder()
          .userPoolId(userPoolId)
          .username(username)
          .build();

      cognitoClient.adminEnableUser(request);
    } catch (CognitoIdentityProviderException e) {
      throw e;
    }
  }

  protected void callAwsForDeleteUser(String username) {
    try (CognitoIdentityProviderClient cognitoClient = getCognitoClient()) {
      AdminDeleteUserRequest request = AdminDeleteUserRequest.builder()
          .userPoolId(userPoolId)
          .username(username)
          .build();

      cognitoClient.adminDeleteUser(request);
    } catch (CognitoIdentityProviderException e) {
      throw e;
    }
  }


  protected CognitoIdentityProviderClient getCognitoClient() {
    if ("dev".equals(activeProfile)) {
      return CognitoIdentityProviderClient.builder()
          .region(Region.EU_NORTH_1)
          .credentialsProvider(ProfileCredentialsProvider.create())
          .build();
    }
    return CognitoIdentityProviderClient.builder()
        .region(Region.EU_NORTH_1)
        .credentialsProvider(InstanceProfileCredentialsProvider.create())
        .build();
  }
}
