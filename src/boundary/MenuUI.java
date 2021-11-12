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
import java.util.InputMismatchException;
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
        while(choice!=0) {
            switch(choice){
                case 1:
                    menuController.displayMenu();
                    break;
                case 2:
                    this.addAlaCarte();
                    break;
                case 3:
                    this.addSet();
                    break;
                case 4:
                    updateMenuItem();
                    break;
                case 5:
                    removeMenuItem();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            
            choice = displayOptions();
        }
    }

    private int displayOptions() {
        System.out.println("--------Menu System--------");
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Display all menu item");
        System.out.println("2. Add AlaCarte to Menu");
        System.out.println("3. Add Set to Menu");
        System.out.println("4. Update a menu item");
        System.out.println("5. Remove a menu item");
        int choice;        
        while (true) {
            try {      
                System.out.println("Your choice: ");                      
                choice = sc.nextInt(); 
                sc.nextLine();   
                        
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input");
                return displayOptions();
            }
            break;
        }
        return choice;
    }

    private void addAlaCarte(){
        System.out.println("Enter name of dish");
        String name = sc.nextLine();
        System.out.println("Enter price");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter description");
        String description = sc.nextLine();

        Category cat = null;
        int choice;
        do{
            System.out.println("Choose the type of the dish: 1.maincourse 2. drinks 3. sides");
            choice=sc.nextInt();
                switch(choice){
                    case 1:
                        cat = Category.MAINCOURSE;
                        break;
                    case 2:
                        cat = Category.DRINKS;
                        break;
                    case 3:
                        cat = Category.SIDES;
                        break;
                    default:
                        System.out.println("invalid input");
                        break;
                }
            } while(choice<0 || choice>3);

        menuController.addAlaCarte(name,price,description,cat);
    }

    private void addSet(){
        System.out.println("Enter set name");
        String name = sc.nextLine();
        System.out.println("Enter description");
        String description = sc.nextLine();

        Set setItem = menuController.addSet(name, description);
        menuController.manageSet(setItem);        
    }    

    private void updateMenuItem() {
        menuController.displayMenu();        
        int idx;        
        while (true) {
            try {      
                System.out.println("Enter Menu Item number to update:");                    
                idx = sc.nextInt(); 
                sc.nextLine();    
                if (idx > menuController.getSizeOfMenu()) {
                    throw new Exception("Invalid Menu Item number");
                }                      
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input");
                continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        MenuItem mItem = menuController.getItemByIndex(idx-1);
        if (mItem instanceof Set) {
            menuController.updateSet(mItem);
        }
        else {
            menuController.updateAlaCarte(mItem);
        }
    }

    /* private void updateAlaCarte(MenuItem aCarte) {
        int choice;
        do{
            System.out.println("============== Update this ala carte ==============\n1. Update name \n2. Update description \n3. Update price \n4. Display ala carte \n5. Finish");
            while (true) {
                try {      
                    System.out.println("Enter choice: ");                      
                    choice = sc.nextInt(); 
                    sc.nextLine();   
                            
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Invalid input");
                    continue;
                }
                break;
            }
            switch(choice){
                case 1:
                    System.out.println("Enter new name: ");
                    aCarte.setName(sc.nextLine());
                    System.out.println("Updated ala carte: ");
                    System.out.println(aCarte.toString());
                    break;
                case 2:          
                    System.out.println("Enter new description: ");
                    aCarte.setDescription(sc.nextLine());
                    System.out.println("Updated ala carte: ");
                    System.out.println(aCarte.toString());
                    break;
                case 3:
                    while (true) {
                        try {      
                            System.out.println("Enter new price:");                    
                            aCarte.setPrice(sc.nextDouble());
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            System.out.println("Invalid input");
                            continue;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    } 
                    System.out.println("Updated ala carte: ");
                    System.out.println(aCarte.toString());
                    break;
                case 4:
                    System.out.println(aCarte.toString());
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }while(choice != 5);        
    } */

    private void removeMenuItem() {
        menuController.displayMenu();        
        int idx;        
        while (true) {
            try {      
                System.out.println("Enter Menu Item number to remove:");                    
                idx = sc.nextInt(); 
                sc.nextLine();    
                if (idx > menuController.getSizeOfMenu()) {
                    throw new Exception("Invalid Menu Item number");
                }                      
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input");
                continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        menuController.removeMenuItem(idx-1);
    }

}
