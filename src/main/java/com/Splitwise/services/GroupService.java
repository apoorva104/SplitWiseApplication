package com.Splitwise.services;

import ch.qos.logback.core.joran.sanity.Pair;
import com.Splitwise.dto.AddExpenseDTO;
import com.Splitwise.dto.AddMemberDTO;
import com.Splitwise.dto.GroupDTO;
import com.Splitwise.dto.RemoveMembersDTO;
import com.Splitwise.entity.*;
import com.Splitwise.entity.Expense;
import com.Splitwise.enums.TransEnum;
import com.Splitwise.exception.ExceptionMsg;
import com.Splitwise.exception.SWException;
import com.Splitwise.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.*;

@Service
public class GroupService {
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
     GroupRepo groupRepository;  // Assuming you have a GroupRepository

    @Autowired
    GroupExpenseRepo groupExpenseRepo;
    @Autowired
    ExpenseRepo expenseRepo;
    @Autowired
    TransRepo transRepo;
    @Autowired
    UserExpenseRepo userExpenseRepo;

    public String createGroup(GroupDTO groupDTO){
        Group group1=new Group(groupDTO);
        groupRepo.save(group1);
        return "Group created Successfully!!!!";
    }

    public String addMembers(AddMemberDTO addMemberDTO){

        //Check weather group exist or not
        Group group = checkGroupExistence(addMemberDTO.getGroupId());
        String groupMembers=group.getMembers();


        //check weather all members are valid or not and already not added
        int size=addMemberDTO.getMembersList().size();
        for(int i=0;i<size;i++){
            User user=userRepo.findById(addMemberDTO.getMembersList().get(i));
            if(user==null){
                throw new SWException(ExceptionMsg.USER_NOT_FOUND_CODE,ExceptionMsg.USER_NOT_FOUND_MESSAGE);
            }


         List<UserExpense>    userExpense=  userExpenseRepo.findByGroupIdAndLender(addMemberDTO.getGroupId(), Long.valueOf(addMemberDTO.getMembersList().get(i)));
            if(!userExpense.isEmpty()){
                throw new SWException(ExceptionMsg.USER_ALREADY_PRESENT_CODE,ExceptionMsg.USER_ALREADY_PRESENT_MESSAGE);
            }
        }

        //adding lender and borrower in user_expense table

        //check if some members are already present
        if(groupMembers != null && !groupMembers.isEmpty()){
            groupMembers = groupMembers.replaceAll("[^0-9]", ""); // Remove all non-digit characters (spaces and commas)

            int[] digits = new int[groupMembers.length()]; // Create an array to store the digits

            for (int i = 0; i < groupMembers.length(); i++) {
                digits[i] = Character.getNumericValue(groupMembers.charAt(i)); // Convert each character to a digit
            }

            // mapping from lender to borrower
            for(int i=0;i< digits.length;i++){          //table ki list
                for(int j=0;j<size;j++){                //request ki list
                    if(addMemberDTO.getMembersList().get(j)!=digits[i]){
                        UserExpense userExpense=new UserExpense();
                        userExpense.setGroupId(addMemberDTO.getGroupId());
                        userExpense.setLender(Long.valueOf(addMemberDTO.getMembersList().get(j)));
                        userExpense.setBorrower((long) digits[i]);
                        userExpenseRepo.save(userExpense);
                    }

                }
            }
            //mapping from borrower to lender
            for(int i=0;i< size;i++){
                for(int j=0;j<digits.length;j++){
                    if(addMemberDTO.getMembersList().get(i)!=digits[j]){
                        UserExpense userExpense=new UserExpense();
                        userExpense.setGroupId(addMemberDTO.getGroupId());
                        userExpense.setBorrower(Long.valueOf(addMemberDTO.getMembersList().get(i)));
                        userExpense.setLender((long) digits[j]);
                        userExpenseRepo.save(userExpense);
                    }
                }
            }

            //mapping the request list members to eachother
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    if(addMemberDTO.getMembersList().get(i)!=addMemberDTO.getMembersList().get(j)){

                        UserExpense userExpense=new UserExpense();
                        userExpense.setGroupId(addMemberDTO.getGroupId());
                        userExpense.setLender(Long.valueOf(addMemberDTO.getMembersList().get(i)));
                        userExpense.setBorrower(Long.valueOf(addMemberDTO.getMembersList().get(j)));

                        userExpenseRepo.save(userExpense);
                    }

                }
            }

        }
        //if no members are present in grp i.e adding initial members
        else{
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    if(addMemberDTO.getMembersList().get(i)!=addMemberDTO.getMembersList().get(j)){

                        UserExpense userExpense=new UserExpense();
                        userExpense.setGroupId(addMemberDTO.getGroupId());
                        userExpense.setLender(Long.valueOf(addMemberDTO.getMembersList().get(i)));
                        userExpense.setBorrower(Long.valueOf(addMemberDTO.getMembersList().get(j)));

                        userExpenseRepo.save(userExpense);
                    }

                }
            }
        }





        // add all member's groupId to their respective user id
        for(int i=0;i<size;i++){
            User user=userRepo.findById(addMemberDTO.getMembersList().get(i));
            String str=user.getGroupId();
            //System.out.println(str);
            StringBuilder strB;
            if(str==null){
                strB=new StringBuilder();
            }
            else{
                strB=new StringBuilder(str);
            }
            //System.out.println(strB+"   dvsdvdfvs");

            if(strB.indexOf(addMemberDTO.getGroupId().toString()) == -1){
                System.out.println("hello");
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

    public String addExpence(AddExpenseDTO addExpenseDTO) {
        checkGroupExistence(addExpenseDTO.getGroupId());
        //adding in expense table
        Expense expense=new Expense();
        expense.setGroupId(addExpenseDTO.getGroupId());
        expense.setAddedBy(addExpenseDTO.getAddedBy());
        expense.setAmount(addExpenseDTO.getAmount());
        expense.setExpName(addExpenseDTO.getExpName());
        
        //adding in transaction table
        int length=addExpenseDTO.getMembers().size();
        double individualExp=addExpenseDTO.getAmount()/length;
        List<Transaction> list = getTransactions(addExpenseDTO, length, individualExp, expense);
        expense.setTransactions(list);
        expense=expenseRepo.save(expense);

        List<UserExpense> userExpenseList=userExpenseRepo.findByGroupIdAndLenderAndBorrowers(addExpenseDTO.getGroupId(),addExpenseDTO.getAddedBy(),addExpenseDTO.getMembers());
        List<UserExpense> userExpenseList1=userExpenseRepo.findByGroupIdBorrowersAndLender(addExpenseDTO.getGroupId(),addExpenseDTO.getMembers(),addExpenseDTO.getAddedBy());

        //adding in user_expense table
        Map<Long,Double> map=new HashMap<>();
        for(int i=0;i<userExpenseList.size();i++){
            map.put(userExpenseList.get(i).getBorrower(), userExpenseList.get(i).getAmt());
        }


        for(int i=0;i<userExpenseList.size();i++){
            double amt=map.get(userExpenseList.get(i).getBorrower())+individualExp;
            userExpenseList.get(i).setAmt(amt);
        }

        for(int i=0;i<userExpenseList1.size();i++){
            double amt=-(map.get(userExpenseList1.get(i).getLender()))-individualExp;
            userExpenseList1.get(i).setAmt(amt);
        }

        userExpenseRepo.saveAll(userExpenseList);
        userExpenseRepo.saveAll(userExpenseList1);



        //adding in user_expense table
//        double amt=addExpenseDTO.getAmount();
//        double individualAmt= amt/length;
//        List<UserExpense> userExpList=userExpenseRepo.findByGroupIdAndLender(addExpenseDTO.getGroupId(),addExpenseDTO.getAddedBy());
//        Boolean flag=false;
//
//        if( !userExpList.isEmpty()){
//
//            for(int i=0;i<length;i++){
//                if(addExpenseDTO.getMembers().get(i)!=addExpenseDTO.getAddedBy()){
//                    for (UserExpense userExpense : userExpList) {
//                        if (userExpense.getBorrower()== addExpenseDTO.getMembers().get(i)) {
//                            userExpense.setAmt(userExpense.getAmt() + individualAmt);
//                            userExpenseRepo.save(userExpense);
//                            flag=true;
//                        }
//                    }
//                    if(!flag){
//                        UserExpense userExpense=new UserExpense();
//                        UserExpense userExpense2=new UserExpense();
//                        userExpense.setGroupId(addExpenseDTO.getGroupId());
//                        userExpense.setLender(addExpenseDTO.getAddedBy());
//                        userExpense.setAmt(individualAmt);
//                        userExpense.setBorrower(addExpenseDTO.getMembers().get(i));
//
//                        userExpense2.setGroupId(addExpenseDTO.getGroupId());
//                        userExpense2.setLender(addExpenseDTO.getMembers().get(i));
//                        userExpense2.setAmt(-individualAmt);
//                        userExpense2.setBorrower(addExpenseDTO.getAddedBy());
//
//                        userExpenseRepo.save(userExpense);
//                        userExpenseRepo.save(userExpense2);
//
//                    }
//
//                }
//            }
//        }
//        else{
//            for(int i=0;i<length;i++){
//                if(!Objects.equals(addExpenseDTO.getMembers().get(i), addExpenseDTO.getAddedBy())){
//                    UserExpense userExpense=new UserExpense();
//                    UserExpense userExpense2=new UserExpense();
//                    userExpense.setGroupId(addExpenseDTO.getGroupId());
//                    userExpense.setLender(addExpenseDTO.getAddedBy());
//                    userExpense.setAmt(individualAmt);
//                    userExpense.setBorrower(addExpenseDTO.getMembers().get(i));
//
//                    userExpense2.setGroupId(addExpenseDTO.getGroupId());
//                    userExpense2.setLender(addExpenseDTO.getMembers().get(i));
//                    userExpense2.setAmt(-individualAmt);
//                    userExpense2.setBorrower(addExpenseDTO.getAddedBy());
//
//                    userExpenseRepo.save(userExpense);
//                    userExpenseRepo.save(userExpense2);
//                }
//
//            }
//        }





    return "";
    }

//    public String getGroupDetails(Long groupId){
//
//    }

    private Group checkGroupExistence(Long addExpenseDTO) {
      return    groupRepository.findById(addExpenseDTO)
                .orElseThrow(() -> new SWException(ExceptionMsg.GROUP_NOT_FOUND_CODE, ExceptionMsg.GROUP_NOT_FOUND_MESSAGE));
    }

    private static List<Transaction> getTransactions(AddExpenseDTO addExpenseDTO, int length, double individualExp, Expense expense) {
        List<Transaction> list=new ArrayList<>();
        for(int i = 0; i< length; i++){
            Transaction transaction = new Transaction();
            transaction.setAmount(individualExp);
            transaction.setMembers(addExpenseDTO.getMembers().get(i));
            transaction.setStatus(TransEnum.ACTIVE);
            transaction.setExpense(expense);
            list.add(transaction);

        }
        return list;
    }
}
