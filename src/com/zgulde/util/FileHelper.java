package com.zgulde.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * a simple class that provides two static methods to help with file io:
 *
 * - slurp: to read the contents of a file
 * - spit: to write to a file
 */
public class FileHelper {

    public static List<String> slurp(String filepath) {
        Path path = Paths.get(filepath);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.printf("Error when trying to slurp %s: %s\n", filepath, e.getMessage());
            System.exit(1);
            return new ArrayList<>(); // we'll never get here, but the compiler doesn't know that
        }
    }

    /**
     * The way we can define an "optional" argument in Java is with overloaded methods.
     * That is, one version of spit is defined with 2 parameters, and the other is defined
     * with 3.
     * The version defined with 2 parameters just calls the other one, and passes the default value.
     * This is a fairly common pattern.
     */
    public static void spit(String filename, List<String> contents) {
        spit(filename, contents, false);
    }
    public static void spit(String filename, List<String> contents, boolean append) {
        // set the file open mode, either append to an existing file, or overwrite the existing contents
        StandardOpenOption option = append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING;
        Path path = Paths.get(filename);
        // make sure to create parent directories
        // (Files.write will create the file if it doesn't exist, but not directories)
        if (! Files.exists(path)) {
            try {
                Files.createDirectories(path);
                Files.createFile(path);
            } catch (IOException e) {
                System.out.printf("Error creating file %s: %s\n", path, e.getMessage());
                System.exit(1);
            }
        }
        // write the file content
        try {
            Files.write(path, contents, option);
        } catch (IOException e) {
            System.out.printf("Error writing contents to %s: %s\n", filename, e.getMessage());
            System.exit(1);
        }
    }

}
