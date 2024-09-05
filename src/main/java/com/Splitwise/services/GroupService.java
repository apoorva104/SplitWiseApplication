package com.Splitwise.services;

import com.Splitwise.dto.AddMemberDTO;
import com.Splitwise.dto.GroupDTO;
import com.Splitwise.dto.RemoveMembersDTO;
import com.Splitwise.entity.Group;
import com.Splitwise.entity.User;
import com.Splitwise.exception.ExceptionMsg;
import com.Splitwise.exception.SWException;
import com.Splitwise.repo.GroupRepo;
import com.Splitwise.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
     GroupRepo groupRepository;  // Assuming you have a GroupRepository

    public String createGroup(GroupDTO groupDTO){
        Group group1=new Group(groupDTO);
        groupRepo.save(group1);
        return "Group created Successfully!!!!";
    }

    public String addMembers(AddMemberDTO addMemberDTO){
        //Check weather group exist or not
        Group group = groupRepository.findById(addMemberDTO.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        //check weather all members are valid or not
        int size=addMemberDTO.getMembersList().size();
        for(int i=0;i<size;i++){
            User user=userRepo.findById(addMemberDTO.getMembersList().get(i));
            if(user==null){
                return "invalid UserName!!!!";
            }
        }
        // add all member's groupId to their respective user id
        for(int i=0;i<size;i++){
            User user=userRepo.findById(addMemberDTO.getMembersList().get(i));
            String str=user.getGroupId();
            StringBuilder strB;
            if(str==null){
                strB=new StringBuilder();
            }
            else{
                strB=new StringBuilder(str);
            }
            if(!(str.contains(addMemberDTO.getGroupId().toString()))){
                strB.append(addMemberDTO.getGroupId()+", ");
                user.setGroupId(strB.toString());
                userRepo.save(user);
            }

        }

        //fetching the already present string and checking weather already entry is there or not
        String str=group.getMembers();
        StringBuilder strBui;
            if(str==null){
                 strBui=new StringBuilder();
            }
            else{
                 strBui=new StringBuilder(str);
            }

        for(int i=0;i<size;i++){
            if(strBui.indexOf(addMemberDTO.getMembersList().get(i).toString())<0){
                strBui.append(addMemberDTO.getMembersList().get(i)+", ");
            }

        }
        group.setMembers(strBui.toString());

        groupRepo.save(group);

        return "Members added successfully!!!!!";
    }

    public String updateName(int id,String name){
        Group group=groupRepo.findById(id);
        if(group==null){
            throw new SWException(ExceptionMsg.GROUP_NOT_FOUND_CODE,ExceptionMsg.GROUP_NOT_FOUND_MESSAGE);
        }
        group.setGroupName(name);
        groupRepo.save(group);
        return "Name updated successfully";
    }

    public String removeMembers(RemoveMembersDTO removeMembersDTO){
        Group group=groupRepo.findById(removeMembersDTO.getId());
        if(group==null){
            throw new SWException(ExceptionMsg.GROUP_NOT_FOUND_CODE,ExceptionMsg.GROUP_NOT_FOUND_MESSAGE);
        }

        int size=removeMembersDTO.getMembersList().size();
        String str=group.getMembers();
        for(int i=0;i<size;i++){
            int a=removeMembersDTO.getMembersList().get(i);
            System.out.println(str+"   "+a);
            if(str.contains(String.valueOf(a))){
                System.out.println(removeMembersDTO.getId());
                str= str.replace(a+", ","");
            }

        }
        group.setMembers(str);
        groupRepo.save(group);
        return "updated!!!!";

    }
}
