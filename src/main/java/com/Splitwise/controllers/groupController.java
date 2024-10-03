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
    public ServiceRespVO createGroup(@RequestBody GroupDTO groupDTO){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(groupService.createGroup(groupDTO));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;

    }

    //Adding members to the group
    @PostMapping("/group/addMembers")
    public ServiceRespVO addMembers(@RequestBody AddMemberDTO addMemberDTo){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(groupService.addMembers(addMemberDTo));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;
    }

    ///update grp name
    @PostMapping("/group/updateName")
    public ServiceRespVO updateGroupName(@RequestParam int id,@RequestParam String name){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(groupService.updateName(id,name));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;
    }

    ///remove member from grp
    @PostMapping("/group/removeMembers")
    public ServiceRespVO removeMembers(@RequestBody RemoveMembersDTO removeMembersDTO){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(groupService.removeMembers(removeMembersDTO));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;

    }

    @PostMapping("/group/addExpense")
    public ServiceRespVO addExpence(@RequestBody  AddExpenseDTO addExpenseDTO){

        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(groupService.addExpence(addExpenseDTO));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;
    }

    @GetMapping("/group/getGroupDetails")
    public ServiceRespVO getGroupDetails(@RequestParam Long groupId){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(groupService.getGroupDetails(groupId));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;

    }
}
