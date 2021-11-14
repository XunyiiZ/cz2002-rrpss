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

/**
* Boundary class for the Menu User Interface that allows staff to access menu information, methods to update menu items and generate the menu
* @author Timothy
* @version 1.0
* @since 2021-11-13
*/

public class MenuUI {
    private static MenuUI menuUI = null;
    private MenuController menuController = MenuController.getInstance();
    private static Scanner sc = new Scanner(System.in);

    public MenuUI(){
    }

    /**
    * Get instance
    * @return MenuUI instance and creates a new instance if there was none previously
    */
    public static MenuUI getInstance() {
        if (menuUI == null) {
            menuUI = new MenuUI();
        }
        return menuUI;
    }

    /**
    *Display options for users to access control of menu related methods
    */
    public void run() {
        
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

    /**
    * Method to display options available for interface
    * @return the integer choice of method to call
    */
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

    /**
    * Method that allows the staff to enter the parameters and details needed to add an AlaCarte item 
    */
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
            while (true) {
                try {      
                    System.out.println();
                    System.out.println("Choose the type of the dish: \n1. Main Course \n2. Drink \n3. Side");                    
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
                    cat = Category.MAINCOURSE;
                    break;
                case 2:
                    cat = Category.DRINKS;
                    break;
                case 3:
                    cat = Category.SIDES;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while(choice<1 || choice>3);

        menuController.addAlaCarte(name,price,description,cat);
        System.out.println("AlaCarte added to menu");
    }

    /**
     * Method that allows the staff to enter the parameters and details needed to add a Set item
     */
    private void addSet(){
        System.out.println("Enter set name");
        String name = sc.nextLine();
        System.out.println("Enter description");
        String description = sc.nextLine();

        Set setItem = menuController.addSet(name, description);
        menuController.manageSet(setItem);        
    }    

    /**
     * Method to allow the staff to choose which menu item he would like to update 
    */
    private void updateMenuItem(){
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

    /**
     * Method that allows the staff to input the menu item number to be removed.
     * After which, the control logic will be passed onto menuController's method to remove the menu item.
     */
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
