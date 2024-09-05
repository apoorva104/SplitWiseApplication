package com.Splitwise.dto;

import lombok.Data;

import java.util.List;
@Data
public class AddMemberDTO {
    private Long groupId;
    private List<Integer> membersList;
}
