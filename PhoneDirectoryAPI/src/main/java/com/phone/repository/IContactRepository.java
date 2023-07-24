package com.phone.repository;

import com.phone.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends JpaRepository<Contact,Long> {

    Contact findByEmail(String name);

    Contact findByPhoneNumber(String phoneNumber);

}
