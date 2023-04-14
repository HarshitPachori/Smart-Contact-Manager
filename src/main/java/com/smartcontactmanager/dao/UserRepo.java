package com.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontactmanager.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
