package controller;

import java.io.*;
import java.util.*;

import Entity.*;
/**
 * this is the abstract controller
 *
 * @Author Xunyi
 * @version 1
 * @since 2021-11
 */
public abstract class AbstractController{

    public AbstractController(){}

    /**
     * write fixed content to the given file
     * @param fileName the path of the external files
     * @param data the content need to be stored
     *
     */
    public static void write(String fileName, List data) {
        try{
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            try {
                for (int i = 0; i < data.size(); i++) {
                    out.println((String) data.get(i));
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("write contents unsuccessfully");
            e.printStackTrace();
        }
    }
    /**
     * read content from a given file
     * @param filename the path of the external files
     * @return the contents in the file
     */
    public static List read(String filename) {
        try{
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
            }catch(IOException e){
                System.out.println("reading file unsuccessfully");
                e.printStackTrace();
                return null;
            }
    }

    public abstract ArrayList load(String filename);

    public abstract void save(String filename, List al);
}
