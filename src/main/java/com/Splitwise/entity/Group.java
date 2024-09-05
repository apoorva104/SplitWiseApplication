package com.Splitwise.entity;

import com.Splitwise.dto.GroupDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "splitwise_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    private String members;

    public Group(GroupDTO groupDTO) {
        this.groupName=groupDTO.getGroupName();
    }
}
