package com.Splitwise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data

@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String userName;

    public UserResponseDTO(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
