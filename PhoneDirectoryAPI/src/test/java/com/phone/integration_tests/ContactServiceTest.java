package com.phone.integration_tests;

import com.phone.entities.Contact;
import com.phone.exception.ContactAlreadyExistsException;
import com.phone.exception.ContactNotFoundException;
import com.phone.repository.IContactRepository;
import com.phone.service.IContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class ContactServiceTest {

    @Autowired
    IContactService iContactService;

    @MockBean
    IContactRepository iContactRepository;

    private List<Contact> contacts;

    private Contact contact1;
    private  Contact contact2;

    private Contact contact3;


    @BeforeEach
    void initializeContactsBeforeEachTest()
    {
        contact1 = new Contact(1L,"naresh","1122334455","naresh3456@gmail.com");
        contact2 = new Contact(2L,"suresh","2233445566","suresh3456@gmail.com");
        contact3 = new Contact(3L,"suresh","3344556677","sureshkakollu3456@gmail.com");

        contacts  = Arrays.asList(contact1,contact2,contact3);
    }


    @Test
    public  void testAddContact_success() {


        // Mock the behavior of iContactRepository.save() to return the saved Contact
        Contact savedContact = new Contact(4L, "harsha", "1234567890", "harsha3456.com");
        Mockito.when(iContactRepository.save(savedContact)).thenReturn(savedContact);

        // Create the Contact to add
        Contact contactToAdd = new Contact(4L, "harsha", "1234567890", "harsha3456.com");

        // Call the method to test
        Contact resultContact = iContactService.addContact(contactToAdd);

        // Verify that the correct Contact was returned as a result of the addition
        assertEquals(savedContact, resultContact);
    }


    @Test
    public void testAddContact_ContactWithEmailAlreadyExists_ThrowsException() {

        Mockito.when(iContactRepository.findByEmail("naresh3456@gmail.com")).thenReturn(contact1);

        // Create the Contact with duplicate email
        Contact contactToAdd = new Contact(4L, "kumarkakollu", "8866443322", "naresh3456@gmail.com");

        // Call the method to test and expect a ContactAlreadyExistsException
        assertThrows(ContactAlreadyExistsException.class, () -> iContactService.addContact(contactToAdd));
    }


    @Test
    public void testAddContact_ContactWithPhoneNumberAlreadyExists_ThrowsException() {


        // Mock the behavior of iContactRepository.findByPhoneNumber() to return an existing Contact
        Contact existingContact = contact2;
        Mockito.when(iContactRepository.findByPhoneNumber(anyString())).thenReturn(existingContact);

        // Create the Contact with duplicate phoneNumber
        Contact contactToAdd = new Contact(5L, "Jhansi", "1234567890", "jhansi3456@gmail.com");

        // Call the method to test and expect a ContactAlreadyExistsException
        assertThrows(ContactAlreadyExistsException.class, () -> iContactService.addContact(contactToAdd));
    }





    @Test
    public  void updateContact_success() {

        Mockito.when(iContactRepository.findById(1L)).thenReturn(Optional.ofNullable(contact1));


        // Mock the behavior of iContactRepository.save() to return the updated Contact
        Contact updatedContact = new Contact(1L, "NareshKakollu", "9876543210", "nareshkumar3456@hotmail.com");
        Mockito.when(iContactRepository.save(updatedContact)).thenReturn(updatedContact);

        // Create the updated Contact details
        Contact contactToUpdate = new Contact(1L, "NareshKakollu", "9876543210", "nareshkumar3456@hotmail.com");

        // Call the method to test
        Contact resultContact = iContactService.updateContact(1L, contactToUpdate);

        // Verify that the correct Contact was returned as a result of the update
        assertEquals(updatedContact, resultContact);


    }


    @Test
    public  void updateContact_ContactNotFound_ThrowsException() {


        // Mock the behavior of iContactRepository.findById() to return an empty Optional
        Mockito.when(iContactRepository.findById(2L)).thenReturn(Optional.empty());

        // Create the Contact details to update
        Contact contactToUpdate = new Contact(1L, "NareshKakollu", "9876543210", "nareshkumar3456@hotmail.com");

        // Call the method to test and expect a ContactNotFoundException for contactId 1L
        assertThrows(ContactNotFoundException.class, () -> iContactService.updateContact(2L, contactToUpdate));
    }






    @Test
    public  void deleteContactTest_success()
    {
        //find 2nd contact
        Mockito.when(iContactRepository.findById(2L)).thenReturn(Optional.ofNullable(contact2));
        //delete 2nd contact
        iContactService.deleteContact(2L);
        //verify if the delete contact is invoked using verify method
        Mockito.verify(iContactRepository).deleteById(2L);
    }

    @Test
    public  void deleteContactTest_ContactNotFound_ThrowsException()
    {
        //find 2nd contact
        Mockito.when(iContactRepository.findById(2L)).thenReturn(Optional.empty());
        //delete 2nd contact
        //  iContactService.deleteContact(2L);

        assertThrows(ContactNotFoundException.class, () -> {iContactService.deleteContact(2L);});

    }






    @Test
    public void getAllContactTest(){
        //Use either of them both works one we are directly calling on service layer and one directly on repository
        Mockito.when(iContactService.getAllContacts()).thenReturn(contacts);
        Mockito.when(iContactRepository.findAll()).thenReturn(contacts);

        //Call the service class method
       List<Contact> list =  iContactService.getAllContacts();

       //Verify  mock list  getAllContacts and the initial list are same
       assertEquals(contacts,list);

    }






    @Test
    public void getAllContactsByName()
    {
        //find all contacts then return list of contacts
        Mockito.when(iContactRepository.findAll()).thenReturn(contacts);
        //find all contact names with suresh
        List<Contact> allNamesBysuresh = iContactService.getContactsByName("suresh");
         List<Contact>  expectedNames = Arrays.asList(
                new Contact(2L,"suresh","1234567890","suresh3456@gmail.com"),
         new Contact(3L,"suresh","9405354299","sureshkakollu3456@gmail.com")
        );
        //Verify  mock list  suresh with the initial list
        assertEquals(expectedNames,allNamesBysuresh);


    }








}
