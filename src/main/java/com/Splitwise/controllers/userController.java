package com.Splitwise.controllers;

import com.Splitwise.dto.GrpupResponseDTO;
import com.Splitwise.dto.UserDTO;
import com.Splitwise.dto.UserResponseDTO;
import com.Splitwise.entity.User;
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
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(@RequestBody UserDTO userDTO){
    return userService.register(userDTO);
    }
    @GetMapping ("/user/login")
    public String login(@RequestBody UserDTO userDTO){
    return userService.login(userDTO);
    }

    @GetMapping ("/user/getUserList")
    public List<UserResponseDTO> getUserName(){
        return userService.getUserName();
    }

    @GetMapping("/user/getUserByGroup")
    public List<UserResponseDTO> getUserByGroup(@RequestParam(name="groupId") int groupId){
        return userService.getUserByGroup(groupId);
    }

    ///fetch all grp by members
    @GetMapping("/users/")
    public List<GrpupResponseDTO> home(@RequestParam(name="userId") int groupId){
        return userService.homeDetails(groupId);
    }
}
