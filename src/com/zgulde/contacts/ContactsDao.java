package com.zgulde.contacts;

import java.util.List;

/**
 * An interface that represents a Dao, a Data Access Object.
 *
 * This interface describes an object that can handle operations on
 * contacts, and abstracts over the details of how those operations
 * are performed.
 */
public interface ContactsDao {
    List<Contact> all();
    List<Contact> search(String searchTerm);
    void add(Contact contact);
    void remove(Contact contact);
}
