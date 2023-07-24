package com.phone.service;

import com.phone.entities.Contact;
import java.util.*;

/*
Implement the necessary API endpoints to perform the following operations on phone directory records,
ensure that the API adheres to RESTful principles and uses appropriate HTTP methods (GET, POST, PUT, DELETE) for each operation.
        - Add a contact to the directory.
        - Modify an existing contact.
        - Delete an existing contact.
        - Retrieve all contacts.
        - Search contacts by name.
 */
public interface IContactService {

    Contact addContact(Contact contact);
    Contact updateContact(Long id,Contact contact);

    void deleteContact(Long contactId);
    List<Contact> getAllContacts();

    List<Contact>  getContactsByName(String name);


}
