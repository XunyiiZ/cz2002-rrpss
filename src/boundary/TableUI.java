package boundary;

import java.util.Scanner;
import controller.*;
public class TableUI {

    Scanner in = new Scanner(System.in);
    private static TableController single_instance = null;
    private TableController tableController = TableController.getInstance();
    public TableUI() {}

    public static TableController getInstance() {
        if(single_instance == null) {
            single_instance = new TableController();
        }
        return single_instance;
    }

    public void run() {

        int choice = this.displayOptions();
        do {
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
            }

            choice = this.displayOptions();

        } while (choice != 0);

    }


    private int displayOptions() {
        System.out.println("--------TableUI--------");
        System.out.println("0. Go back to MainUI" +
                "\n1. displayAllTables"+
                "\n2. displayUnoccupiedTables"+
                "\n3. displayOccupiedTables");
        int choice = in.nextInt();
        in.nextLine();
        return choice;
    }
}

