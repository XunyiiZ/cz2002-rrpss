package controller;

import java.io.*;
import java.util.*;

import Entity.*;
public abstract class AbstractController{

    public AbstractController(){}

    /**
     * write fixed content to the given file
     */

    public static void write(String fileName, List data) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));

        try {
            for (int i = 0; i < data.size(); i++) {
                out.println((String) data.get(i));
            }
        } finally {
            out.close();
        }
    }

    public static List read(String filename) throws IOException {
        List data = new ArrayList();
        Scanner scanner = new Scanner(new FileInputStream(filename));
        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        return data;
    }

    public abstract ArrayList load(String filename) throws IOException;

    public static void save(String filename, List al) throws IOException {
    }
}
