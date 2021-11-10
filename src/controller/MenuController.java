package controller;

import Entity.*;
import Entity.Set;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Xunyi
 * @09-11-2021
 * MenuController manages the menu implementing the fileIO handling
 *
 * */

public class MenuController extends AbstractController {

    private static MenuController MenuController = null;
    private static final String dir = "src/data/menu.txt";
    private ArrayList<MenuItem> menuList;

    public static MenuController getInstance() throws IOException {
        if (MenuController == null) {
            MenuController = new MenuController();
        }
        return MenuController;
    }

    public MenuController() throws IOException {
        /** using text method */
        File file = new File(dir);
        if (file.exists()) {
            System.out.println("file exist");
            menuList = load(dir);
        } else {
            System.out.println("not exist");
            file.getParentFile().mkdir();
            file.createNewFile();
            menuList = new ArrayList<MenuItem>();
            save(dir, menuList);
        }
    }

    public void displayMenu(){
        for(MenuItem item : menuList){
            System.out.println(item.toString());
        }
    }

    public void addAlaCarte(String name, double price, String description, AlaCarte.Category cat ){
        menuList.add(new AlaCarte(name,description,price,cat));
        save(dir,menuList);
    }

    public void addSet(String name, String description){
        menuList.add(new Set(name, description, 0.0));
        // add alaCarte in it
    }

    public void displayAlaCarte(){
            }

    public MenuItem getItemById(int id){
        return (new MenuItem("name","description", 0.0));
    }

    public static void save(String filenmae, List list){}
}

