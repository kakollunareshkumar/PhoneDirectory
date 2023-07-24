package com.phone.service;

import com.phone.entities.Contact;
import com.phone.exception.ContactAlreadyExistsException;
import com.phone.exception.ContactNotFoundException;
import com.phone.repository.IContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IContactServiceImpl implements IContactService {
    @Autowired
    public IContactRepository iContactRepository;
    @Override
    public Contact addContact(Contact contact) {


        // Check if a contact with the same name already exists
        Contact existingContactWithName = iContactRepository.findByEmail(contact.getEmail());
        if (existingContactWithName != null) {
            throw new ContactAlreadyExistsException("Contact with the same email already exists.");
        }

        // Check if a contact with the same phoneNumber already exists
        Contact existingContactWithPhoneNumber = iContactRepository.findByPhoneNumber(contact.getPhoneNumber());
        if (existingContactWithPhoneNumber != null) {
            throw new ContactAlreadyExistsException("Contact with the same phoneNumber already exists.");
        }


        Contact savedContact = iContactRepository.save(contact);
        return savedContact;
    }

    @Override
    public Contact updateContact(Long id,Contact contact) {
        Contact updateContact = null;

        Optional<Contact> findContact = iContactRepository.findById(id);

        if(findContact.isPresent())
        {
            updateContact = findContact.get();
            updateContact.setName(contact.getName());
            updateContact.setPhoneNumber(contact.getPhoneNumber());
            updateContact.setEmail(contact.getEmail());
            Contact updatedContact = iContactRepository.save(updateContact);
            return updatedContact;
        }
        else {

           throw  new ContactNotFoundException("contact with id "+id+" does not exist");
        }

    }

    @Override
    public void  deleteContact(Long contactId) {

        if(iContactRepository.findById(contactId).isPresent()){
            iContactRepository.deleteById(contactId);
        }
        else {
            throw  new ContactNotFoundException("contact with id "+contactId+" does not exist");
        }

    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> allContacts =iContactRepository.findAll();
        return allContacts;
    }

    @Override
    public List<Contact> getContactsByName(String name) {

        List<Contact>  getAllContacts = iContactRepository.findAll();

        List<Contact> filterContactsByName = getAllContacts.stream().filter(contact -> contact.getName().equals(name)).collect(Collectors.toList());
        return filterContactsByName;
    }
}
