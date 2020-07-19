package com.wsf.workflow.repository;

import com.wsf.workflow.entity.User;
import com.wsf.workflow.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {
    UserInformation getUserInformationByUser(User user);
}
