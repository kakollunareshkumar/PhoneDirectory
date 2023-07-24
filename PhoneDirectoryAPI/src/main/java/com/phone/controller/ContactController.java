package com.phone.controller;

import com.phone.entities.Contact;
import com.phone.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private IContactService iContactService;



   @PostMapping("/add")
   public  ResponseEntity<Contact> addContact(@RequestBody @Valid Contact contact){

       Contact addedContact = iContactService.addContact(contact);

       return  new ResponseEntity<>(addedContact, HttpStatus.ACCEPTED);
   }

    @PutMapping("/update/{id}")
    public  ResponseEntity<Contact> updateContact(@PathVariable("id") Long id,@RequestBody @Valid Contact contact){

        Contact updatedContact = iContactService.updateContact(id,contact);

        return  new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<String> deleteContact(@PathVariable("id") Long contactId){

             iContactService.deleteContact(contactId);

        return  new ResponseEntity<>("contact deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/all")
    public  ResponseEntity<List<Contact>> getAllContacts(){

       List<Contact> allContacts =  iContactService.getAllContacts();

        return  new ResponseEntity<>(allContacts, HttpStatus.OK);
    }

    @GetMapping("/all/{name}")
    public  ResponseEntity<List<Contact>> getAllContactsByName(@PathVariable ("name") String name){

        List<Contact> allContactsByName =  iContactService.getContactsByName(name);

        return  new ResponseEntity<>(allContactsByName, HttpStatus.OK);
    }


}
