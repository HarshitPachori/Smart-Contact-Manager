package com.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontactmanager.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);

}
