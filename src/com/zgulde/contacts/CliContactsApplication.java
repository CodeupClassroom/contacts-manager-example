package com.zgulde.contacts;

import java.util.List;
import java.util.Scanner;

/**
 * A class to provide the control flow for the command line interface to the contacts
 * application. This class handles all of the user interactions, but relies on
 * the ContactsDao to provide any functionality for modifying contacts, and
 * the TerminalDisplay class to show formatted information to the user.
 */
public class CliContactsApplication {
    private final Scanner scanner;
    private final ContactsDao dao;

    public CliContactsApplication(Scanner scanner, ContactsDao dao) {
        this.scanner = scanner;
        this.dao = dao;
    }

    public void start() {
        TerminalDisplay.welcome();
        showMenu();
    }

    private void showMenu() {
        TerminalDisplay.showMenu();
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
                TerminalDisplay.error("Unknown Option: " + choice);
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
        TerminalDisplay.showContacts(results);
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
        TerminalDisplay.showContacts(dao.all());
    }

    private void exit() {
        TerminalDisplay.goodbye();
        System.exit(0);
    }
}
