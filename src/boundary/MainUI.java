package boundary;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This boundary class provides users with access to different boundaries
 *
 * @author Zeng Xunyi
 * @version 1.0
 * @Date 2021-11
 */
public class MainUI {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int choice;
        choice = displayOptions();

        while (choice!= 0) {            
            

            switch(choice) {
                case 1:
                    ReservationUI reservationUI = ReservationUI.getInstance();
                    reservationUI.run();
                    break;
                case 2:
                    TableUI tableUI = new TableUI();
                    tableUI.run();
                    break;
                case 3:
                    MenuUI menuUI = MenuUI.getInstance();
                    menuUI.run();
                    break;
                case 4:
                    OrderUI orderUI = OrderUI.getInstance();
                    orderUI.run();
                    break;
                case 5:
                    MemberUI memberUI = MemberUI.getInstance();
                    memberUI.run();
                    break;
                case 6:
                    InvoiceUI invoiceUI = InvoiceUI.getInstance();
                    invoiceUI.run();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            choice = displayOptions();
        }

    }

    /**
     * Displays the main control system
     * @return user's choice
     */
    private static int displayOptions() {
        System.out.println("--------Main Control System--------");
        System.out.println("0. Exit");
        System.out.println("1. Reservation");
        System.out.println("2. Table");
        System.out.println("3. Menu");
        System.out.println("4. Order");
        System.out.println("5. Members");
        System.out.println("6. Invoice");     
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