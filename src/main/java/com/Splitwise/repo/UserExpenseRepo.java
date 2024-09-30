package com.Splitwise.repo;

import com.Splitwise.entity.User;
import com.Splitwise.entity.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserExpenseRepo extends JpaRepository<UserExpense,Long> {

    @Query(value = "SELECT * FROM splitwise_user_expense u WHERE u.group_Id = :groupId AND u.lender = :lender", nativeQuery = true)
   List<UserExpense>  findByGroupIdAndLender(@Param("groupId") Long groupId, @Param("lender") Long lender);

    @Query(value = "SELECT * FROM splitwise_user_expense u WHERE u.group_Id = :groupId AND u.lender = :lender AND u.borrower IN :borrowers",nativeQuery = true)
    List<UserExpense> findByGroupIdAndLenderAndBorrowers(@Param("groupId") Long groupId, @Param("lender") Long lender, @Param("borrowers") List<Long> borrowers);

    @Query(value = "SELECT * FROM splitwise_user_expense u WHERE u.group_Id = :groupId AND u.lender IN :lender AND u.borrower = :borrowers",nativeQuery = true)
    List<UserExpense> findByGroupIdBorrowersAndLender(@Param("groupId") Long groupId, @Param("lender") List<Long> lender, @Param("borrowers") Long borrowers);


}
