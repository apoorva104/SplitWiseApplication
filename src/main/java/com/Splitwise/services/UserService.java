package com.Splitwise.services;

import com.Splitwise.dto.GrpupResponseDTO;
import com.Splitwise.dto.UserDTO;
import com.Splitwise.dto.UserResponseDTO;
import com.Splitwise.entity.User;
import com.Splitwise.exception.ExceptionMsg;
import com.Splitwise.exception.SWException;
import com.Splitwise.repo.GroupRepo;
import com.Splitwise.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    GroupRepo groupRepo;

    @Autowired
    private PasswordEncoder passwordEncoder; //
    public boolean userExists(String userName, String email) {
        return userRepo.findByUserName(userName) != null || userRepo.findByEmail(email) != null;
    }

    public void saveUser(User user) {
        userRepo.save(user); // Save user to the database
    }


    ///register
//    public String register(UserDTO userDTO){
//        Optional<User> user = userRepo.findByUserNameOrEmail(userDTO.getUserName(), userDTO.getEmail());
//        if (user.isPresent()){
//            throw new SWException(ExceptionMsg.USER_ALREADY_PRESENT_CODE,ExceptionMsg.USER_ALREADY_PRESENT_MESSAGE);
//       }
//            User user2=new User(userDTO);
//            user2.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            userRepo.save(user2);
//            return "User Registered Successfully!!!!!";
//
//
//    }
    ////login
//    public String login(UserDTO userDTO){
//        Optional<User> user=userRepo.findByUserName(userDTO.getUserName());
//        if (user.isPresent() && userDTO.getPassword().equals(user.get().getPassword())){
//            return "Successfully Logged In!!!";
//        }
//        else{
//            return "userName/password is Invalid!!!!";
//        }
//    }

    public List<UserResponseDTO>  getUserName() {
       List<User>  user= userRepo.findAllUsernames();
       List<UserResponseDTO> list=new ArrayList<>();
       for(int i=0;i<user.size();i++){
           UserResponseDTO userResponseDTO=new UserResponseDTO();
           userResponseDTO.setUserName(user.get(i).getUserName());
           userResponseDTO.setId(user.get(i).getId());
           list.add(userResponseDTO);
       }

       return list;
    }
    ///getUserByGroup
    public List<UserResponseDTO> getUserByGroup(int groupId){
        if(groupRepo.findById(groupId)==null){
            throw  new SWException(ExceptionMsg.GROUP_NOT_FOUND_CODE,ExceptionMsg.GROUP_NOT_FOUND_MESSAGE);
        }

        List<User> list=userRepo.findAll();
        List<UserResponseDTO> ans=new ArrayList<>();

        for(int i=0;i<list.size();i++){
            if(list.get(i).getGroupId()!=null && list.get(i).getGroupId().contains(String.valueOf(groupId))){
                UserResponseDTO userResponseDTO=new UserResponseDTO();
                userResponseDTO.setId(list.get(i).getId());
                userResponseDTO.setUserName(list.get(i).getUserName());
                ans.add(userResponseDTO);
            }
        }
        return ans;
    }

    public List<GrpupResponseDTO> homeDetails(int Id){
       User user= userRepo.findById(Id);
       if(user==null){
           throw new SWException(ExceptionMsg.USER_NOT_FOUND_CODE,ExceptionMsg.USER_NOT_FOUND_MESSAGE);
       }
       String groupId=user.getGroupId();

       String[] string=groupId.replaceAll("\\ ", "").split(",");

       int[] arr=new int[string.length];

       for(int i=0;i<string.length;i++){
           //System.out.println(arr[i]+"      "+string[i]);
           arr[i]=Integer.valueOf(string[i]);
       }
       List<GrpupResponseDTO> list=new ArrayList<>();
       for(int i=0;i<arr.length;i++){

           GrpupResponseDTO grpupResponseDTO=new GrpupResponseDTO();
           grpupResponseDTO.setId((long)arr[i]);
           grpupResponseDTO.setGroupName(groupRepo.findById(arr[i]).getGroupName());
           list.add(grpupResponseDTO);
       }

       return list;
    }
}
