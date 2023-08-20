package com.aydintepe.panel.repository;

import com.aydintepe.panel.model.MilkProvider;
import com.aydintepe.panel.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


  Optional<User> findByUsername(String username);

}
