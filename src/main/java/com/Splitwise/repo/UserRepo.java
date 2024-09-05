package com.Splitwise.repo;

import com.Splitwise.dto.UserResponseDTO;
import com.Splitwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    User findById(int Id);

    List<User> findAll();

  @Query(value = "SELECT * FROM splitwise_user u ", nativeQuery = true)
  List<User> findAllUsernames();


}
