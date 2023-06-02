package com.smartcontactmanager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontactmanager.models.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer> {
    // this is created for implementing pagination

    // @Query("from Contact as c where c.user.id =:userId")
    // public List<Contact> findContactsByUser(@Param("userId") int userId);
    @Query("from Contact as c where c.user.id =:userId")
    public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
    // yahan ham list na return krke page return kr rhe hai pagination ke liye
    // page is a sublist of a list of objects . it alloows to gain information about
    // the position in entire list
    // pageable used to stoire pagination information ( current page -> page ,
    // contact/object/record per page -> 5 or anynumbertf )
}
