package com.zgulde;

import com.zgulde.contacts.CliContactsApplication;
import com.zgulde.contacts.ContactsFromFile;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactsFromFile dao = new ContactsFromFile();

        CliContactsApplication app = new CliContactsApplication(scanner, dao);
        app.start();
    }
}
