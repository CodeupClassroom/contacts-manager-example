package com.zgulde.contacts;

import com.zgulde.util.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the ContactsDao that reads and writes contacts
 * to a text file.
 */
public class ContactsFromFile implements ContactsDao {
    private static final String contactsFile = "contacts.txt";

    private Contact fromLine(String line) {
        String[] parts = line.split("\\|");
        return new Contact(parts[0], parts[1]);
    }

    private String toLine(Contact contact) {
        return String.format("%s|%s", contact.getName(), contact.getNumber());
    }

    private void writeToFile(List<Contact> contacts) {
        List<String> lines = new ArrayList<>();
        for (Contact contact : contacts) {
            lines.add(toLine(contact));
        }
        FileHelper.spit(contactsFile, lines);
    }

    @Override
    public List<Contact> all() {
        List<String> lines = FileHelper.slurp(contactsFile);
        List<Contact> contacts = new ArrayList<>();
        for (String line : lines) {
            contacts.add(fromLine(line));
        }
        return contacts;
    }

    @Override
    public List<Contact> search(String searchTerm) {
        List<Contact> searchResults = new ArrayList<>();
        for (Contact contact : all()) {
            boolean isMatch = contact.getName().toLowerCase().contains(searchTerm.toLowerCase());
            if (isMatch) {
                searchResults.add(contact);
            }
        }
        return searchResults;
    }

    @Override
    public void add(Contact contact) {
        List<Contact> contacts = all();
        contacts.add(contact);
        writeToFile(contacts);
    }

    @Override
    public void remove(Contact contact) {
        List<Contact> contacts = all();
        contacts.remove(contact);
        writeToFile(contacts);
    }
}
