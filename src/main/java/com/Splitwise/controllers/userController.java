package com.Splitwise.controllers;

import com.Splitwise.dto.GrpupResponseDTO;
import com.Splitwise.dto.UserDTO;
import com.Splitwise.dto.UserResponseDTO;
import com.Splitwise.entity.User;
import com.Splitwise.exception.ExceptionMsg;
import com.Splitwise.exception.ServiceRespVO;
import com.Splitwise.repo.UserRepo;
import com.Splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class userController {

    @Autowired
    UserService userService;
//    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
//    public ServiceRespVO register(@RequestBody UserDTO userDTO){
//        ServiceRespVO serviceRespVO=new ServiceRespVO();
//        serviceRespVO.setResponseData(userService.register(userDTO));
//        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
//        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
//        return serviceRespVO;
//    }
//    @PostMapping  ("/user/login")
//    public ServiceRespVO login(@RequestBody UserDTO userDTO){
//        ServiceRespVO serviceRespVO=new ServiceRespVO();
//        serviceRespVO.setResponseData(userService.login(userDTO));
//        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
//        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
//        return serviceRespVO;
//
//    }

    @GetMapping ("/user/getUserList")
    public ServiceRespVO getUserName(){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(userService.getUserName());
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;

    }

    @GetMapping("/user/getUserByGroup")
    public ServiceRespVO getUserByGroup(@RequestParam(name="groupId") int groupId){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(userService.getUserByGroup(groupId));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;

    }

    ///fetch all grp by members
    @GetMapping("/users/")
    public ServiceRespVO home(@RequestParam(name="userId") int groupId){
        ServiceRespVO serviceRespVO=new ServiceRespVO();
        serviceRespVO.setResponseData(userService.homeDetails(groupId));
        serviceRespVO.setResponseCode(ExceptionMsg.SUCCESS_CODE);
        serviceRespVO.setResponseMessage(ExceptionMsg.SUCCESS_MESSAGE);
        return serviceRespVO;

    }
}
