package com.Splitwise.controllers;

import com.Splitwise.dto.AddExpenseDTO;
import com.Splitwise.dto.AddMemberDTO;
import com.Splitwise.dto.GroupDTO;
import com.Splitwise.dto.RemoveMembersDTO;
import com.Splitwise.exception.ExceptionMsg;
import com.Splitwise.exception.ServiceRespVO;
import com.Splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class groupController {
    @Autowired
    GroupService groupService;

    //Creating new group
    @PostMapping("/group/create")
    public String createGroup(@RequestBody GroupDTO groupDTO){
    return groupService.createGroup(groupDTO);
    }

    //Adding members to the group
    @PostMapping("/group/addMembers")
    public String addMembers(@RequestBody AddMemberDTO addMemberDTo){
    return groupService.addMembers(addMemberDTo);
    }

    ///update grp name
    @PostMapping("/group/updateName")
    public String updateGroupName(@RequestParam int id,@RequestParam String name){
    return groupService.updateName(id,name);
    }

    ///remove member from grp
    @PostMapping("/group/removeMembers")
    public String removeMembers(@RequestBody RemoveMembersDTO removeMembersDTO){
        return groupService.removeMembers(removeMembersDTO);
    }

    @PostMapping("/group/addExpense")
    public ServiceRespVO addExpence(@RequestBody  AddExpenseDTO addExpenseDTO){

        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(groupService.addExpence(addExpenseDTO));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;
    }

//    @GetMapping("/getGroupDetails")
//    public ServiceRespVO getGroupDetails(@RequestParam Long GroupId){
//
//
//    }
}
