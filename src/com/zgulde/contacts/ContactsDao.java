package com.zgulde.contacts;

import java.util.List;

public interface ContactsDao {
    List<Contact> all();
    List<Contact> search(String searchTerm);
    void add(Contact contact);
    void remove(Contact contact);
}
