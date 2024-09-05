package com.Splitwise.entity;

import com.Splitwise.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "splitwise_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userName;
    private String email;
    private String password;
    private String  groupId;

    public User(UserDTO userDTO) {
        this.name=userDTO.getName();
        this.userName=userDTO.getUserName();
        this.email=userDTO.getEmail();
        this.password=userDTO.getPassword();
    }

}
