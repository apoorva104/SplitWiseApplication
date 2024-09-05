package com.Splitwise.repo;

import com.Splitwise.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group,Long> {
    Group findById(int groupId);
}
