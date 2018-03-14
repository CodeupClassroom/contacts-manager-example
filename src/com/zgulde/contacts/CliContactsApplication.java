package com.zgulde.contacts;

import java.util.List;
import java.util.Scanner;

/**
 * A class to provide the command line interface to the contacts
 * application. This class handles all of the user interactions, but relies on
 * the ContactsDao to provide any functionality for modifying contacts.
 */
public class CliContactsApplication {
    private final Scanner scanner;
    private final ContactsDao dao;
    private final String contactsFormat;

    public CliContactsApplication() {
        contactsFormat = "| %-10s | %10s |\n";
        dao = new ContactsFromFile();
        scanner = new Scanner(System.in);
    }

    private void showContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("No Contacts Found.");
            return;
        }
        System.out.printf(this.contactsFormat, "Name", "Number");
        System.out.printf(this.contactsFormat, "---------", "--------");
        for (Contact contact : contacts) {
            System.out.printf(this.contactsFormat, contact.getName(), contact.getNumber());
        }
    }

    public void start() {
        System.out.println("Welcome!");
        System.out.println("");
        showMenu();
    }

    private void showMenu() {
        System.out.println("");
        System.out.println("0 - Exit");
        System.out.println("1 - View Contacts");
        System.out.println("2 - Add A Contact");
        System.out.println("3 - Search Contacts");
        System.out.println("4 - Remove A Contact");
        System.out.println();
        System.out.print("Your choice? ");

        String choice = scanner.nextLine();

        switch(choice) {
            case "0":
                exit();
                break;
            case "1":
                viewAllContacts();
                break;
            case "2":
                addContact();
                break;
            case "3":
                searchContacts();
                break;
            case "4":
                removeContact();
                break;
            default:
                System.out.println("\nError: Unknown Option: " + choice);
                showMenu();
                return;
        }
        showMenu();
    }

    private void removeContact() {
        System.out.print("Contact name to remove? ");
        String name = scanner.nextLine();
        List<Contact> results = dao.search(name);
        for (Contact contact : results) {
            System.out.printf("Are you sure you want to remove %s? [y/N]", contact.getName());
            String confirmation = scanner.nextLine();
            if (confirmation.toLowerCase().startsWith("y")) {
                dao.remove(contact);
                System.out.println("Contact Removed!");
            } else {
                System.out.println("Contact not removed.");
            }
        }
    }

    private void searchContacts() {
        System.out.print("Search Term? ");
        String searchTerm = scanner.nextLine();
        List<Contact> results = dao.search(searchTerm);
        showContacts(results);
    }

    private void addContact() {
        System.out.print("Name? ");
        String name = scanner.nextLine();
        System.out.print("Number? ");
        String number = scanner.nextLine();

        dao.add(new Contact(name, number));
        System.out.println("Contact Added!");
    }

    private void viewAllContacts() {
        showContacts(dao.all());
    }

    private void exit() {
        System.out.println("Have a Great Day!");
        System.exit(0);
    }
}
