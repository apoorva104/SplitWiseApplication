package com.Splitwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String userName;
    private String email;
    private String password;

    public UserDTO(String jwt) {
    }
}
