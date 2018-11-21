package com.wcw.contacts.service;

import com.wcw.contacts.entity.Contact;

import java.util.List;

/**
 * @author 点觉CTM
 */
public interface ContactService {

    /**
     * add a contact
     * @param contact a contact
     * @return affected rows
     */
    int addContact(Contact contact);

    /**
     * update the contact info
     * @param contact a contact
     * @return affected rows
     */
    int updateContact(Contact contact);

    /**
     * delete a contact by contact id
     * @param contactId a contact's id
     * @return affected rows
     */
    int deleteContactById(int contactId);

    /**
     * Get a contact by id
     * @param contactId a contact's id
     * @return a contact
     */
    Contact getContactById(int contactId);

    /**
     * get all contacts
     * @return all contacts
     */
    List<Contact> findAllContact();

}

