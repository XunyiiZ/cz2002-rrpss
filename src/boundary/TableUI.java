package boundary;

import java.util.InputMismatchException;
import java.util.Scanner;
import controller.*;
public class TableUI {

    Scanner in = new Scanner(System.in);
    private static TableUI tableUI = null;
    private TableController tableController = TableController.getInstance();
    public TableUI() {}

    public static TableUI getInstance() {
        if(tableUI == null) {
            tableUI = new TableUI();
        }
        return tableUI;
    }

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

