package com.phone.integration_tests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.phone.controller.ContactController;
import com.phone.entities.Contact;
import com.phone.service.IContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IContactService iContactService;

    private List<Contact> contacts;
    private ObjectMapper objectMapper;

    private Contact contact1;
    private  Contact contact2;

    private Contact contact3;

    @BeforeEach
    void setUp() {

     contact1 =  new Contact(1L,"naresh","1122334455","naresh3456@gmail.com");
            contact2 = new Contact(2L,"suresh","2233445566","suresh3456@gmail.com");
                contact3 = new Contact(4L,"suresh","3344556677","sureshkakollu3456@gmail.com");
        // Initialize test data before each test
        contacts = Arrays.asList(
                 contact1,contact2,contact3
        );

        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllContactsTest() throws Exception {
        // Arrange
        when(iContactService.getAllContacts()).thenReturn(contacts);

        // Act & Assert
        mockMvc.perform(get("/contact/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("naresh"))
                .andExpect(jsonPath("$[1].name").value("suresh"));

        // Verify that the iContactService.getAllContacts() method is called only once
        verify(iContactService, times(1)).getAllContacts();
    }

    @Test
    public void addContactTest() throws Exception {
        // Arrange
        Contact newContact = new Contact(4L, "harsha", "9402681156", "harsha3456@gmail.com");
        when(iContactService.addContact(any(Contact.class))).thenReturn(newContact);

        // Act
        MvcResult result = mockMvc.perform(post("/contact/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newContact)))
                .andExpect(status().isAccepted())
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(newContact), content);

        // Verify that the iContactService.addContact() method is called only once with the correct parameter
        verify(iContactService, times(1)).addContact(any(Contact.class));
    }

    @Test
    public void getAllContactsByNameTest() throws Exception {
        // Arrange
        String name = "suresh";
        Contact contact1 = contact2;
        Contact contact2 = contact3;
        List<Contact> contacts = Arrays.asList(contact1, contact2);
        when(iContactService.getContactsByName(name)).thenReturn(contacts);

        // Act
        MvcResult result = mockMvc.perform(get("/contact/all/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        List<Contact> responseContacts = objectMapper.readValue(content, new TypeReference<List<Contact>>() {});

        assertEquals(contacts.size(), responseContacts.size());
        assertEquals(contacts.get(0).getName(), responseContacts.get(0).getName());
        assertEquals(contacts.get(1).getName(), responseContacts.get(1).getName());

        // Verify that the iContactService.getContactsByName() method is called only once with the correct parameter
        verify(iContactService, times(1)).getContactsByName(name);
    }




    @Test
    public void updateContactTest() throws Exception {
        // Arrange
        Long contactId = 1L;
        Contact updatedContact = new Contact(contactId, "uday", "1234567890", "uday3456@gmail.com");
        when(iContactService.updateContact(contactId, updatedContact)).thenReturn(updatedContact);

        // Act
        MvcResult result = mockMvc.perform(put("/contact/update/{id}", contactId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) // Set the Accept header to accept JSON response
                        .content(objectMapper.writeValueAsString(updatedContact)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String content = result.getResponse().getContentAsString();
        Contact responseContact = objectMapper.readValue(content, Contact.class);
        assertEquals(updatedContact.getName(), responseContact.getName());
        assertEquals(updatedContact.getPhoneNumber(), responseContact.getPhoneNumber());
        assertEquals(updatedContact.getEmail(), responseContact.getEmail());

        // Verify that the iContactService.updateContact() method is called only once with the correct parameters
        verify(iContactService, times(1)).updateContact(contactId, updatedContact);
    }


    @Test
    public void deleteContactTest() throws Exception {
        // Arrange
        Long contactId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/contact/{id}", contactId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the iContactService.deleteContact() method is called once with the correct contact ID
        verify(iContactService, times(1)).deleteContact(contactId);
    }


}
