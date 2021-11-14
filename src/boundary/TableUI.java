package boundary;

import java.util.InputMismatchException;
import java.util.Scanner;
import controller.*;
/**
* Boundary class for the Table User Interface that allows staff to access table information, methods to udisplay occupied or unoccupied tables
* @author Sydney
* @version 1.0
* @since 2021-11-14
*/
public class TableUI {

    Scanner in = new Scanner(System.in);
    private static TableUI tableUI = null;
    private TableController tableController = TableController.getInstance();
    public TableUI() {}

    /**
    * Get instance
    * @return TableUI instance and creates a new instance if there was none previously
    */
    public static TableUI getInstance() {
        if(tableUI == null) {
            tableUI = new TableUI();
        }
        return tableUI;
    }

    /**
    *Display options for users to access control of menu related methods
    */
    public void run() {

        int choice = this.displayOptions();
        while(choice!=0){
            switch(choice) {
                case 1:
                    tableController.displayAllTables();
                    break;
                case 2:
                    tableController.displayUnoccupiedTables();
                    break;
                case 3:
                    tableController.displayOccupiedTables();
                    break;
                default: 
                    System.out.println("Invalid input");
                    break;
            }

            choice = this.displayOptions();
        } 

    }

    /**
    * Method to display options available for interface
    * @return the integer choice of method to call
    */
    private int displayOptions() {
        System.out.println("--------TableUI--------");
        System.out.println("0. Go back to MainUI" +
                "\n1. Display all tables"+
                "\n2. Display unoccupied tables"+
                "\n3. Display occupied tables");
        int choice;        
        while (true) {
            try {      
                System.out.println("Your choice: ");                      
                choice = in.nextInt(); 
                in.nextLine();   
                        
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("Invalid input");
                return displayOptions();
            }
            break;
        }
        return choice;
    }
}

