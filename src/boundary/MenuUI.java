package boundary;
/**
 * @Xunyi
 * @09-11-2021
 * menuUI
 */

import controller.*;
import Entity.AlaCarte.Category;
import Entity.*;
import java.io.IOException;
import java.util.Scanner;

public class MenuUI {
    private static MenuUI menuUI = null;
    private MenuController menuController = MenuController.getInstance();
    private static Scanner sc = new Scanner(System.in);

    public MenuUI() throws IOException {
    }

    public static MenuUI getInstance() throws IOException {
        if (menuUI == null) {
            menuUI = new MenuUI();
        }
        return menuUI;
    }

    public void run(){
        int choice = displayOptions();
        while(choice!=0){
            switch(choice){
                case 1:
                    menuController.displayMenu();
                    break;
                case 2:

            }
        }
    }

    private int displayOptions() {
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Display all menu item");
        System.out.println("2. Add Ala Carte to Menu");
        System.out.println("3. Add Set to Menu");
        System.out.println("4. Update menuItem");
        System.out.println("5. Remove menuItem");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    private void addAlaCarte(){
        System.out.println("enter name");
        String name = sc.next();
        System.out.println("enter price");
        double price = sc.nextDouble();
        System.out.println("enter description");
        String description = sc.nextLine();

        Category cat = null;
        int choice;
        do{
            System.out.println("choose the type of the dish: 1.maincourse 2. drinks 3. desserts");
            choice=sc.nextInt();
                switch(choice){
                    case 1:
                        cat = Category.MAINCOURSE;
                        break;
                    case 2:
                        cat = Category.DRINKS;
                        break;
                    case 3:
                        cat = Category.DESSERTS;
                        break;
                    default:
                        System.out.println("invalid input");
                        break;
                }
            }while(choice<0 || choice>3);

        menuController.addAlaCarte(name,price,description,cat);
    }

    private void addSet(){
        System.out.println("enter name");
        String name = sc.next();
        System.out.println("enter description");
        String description = sc.nextLine();

        menuController.addSet(name,description);
        Set setItem;
        int choice;
        do{
            System.out.println("manage this set: 1.add item 2.remove item 3. display the item in it 4. finish");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    menuController.displayAlaCarte();
                    System.out.println("enter the id to add:");
                    int id = sc.nextInt();
                    setItem.addAlaCarte(menuController.getItemById(id));

                    break;
                case 2:
                    System.out.println("enter id to remove:");

                    break;
                case 3:

                    break;
                case 4:
                    // display this set item
                default:
                    System.out.println("invalid input");
                    break;
            }
        }while(choice<0 || choice>4);
    }

}
