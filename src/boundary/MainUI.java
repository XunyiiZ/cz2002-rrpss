package boundary;

import java.io.IOException;
import java.util.Scanner;
import controller.*;


//test
public class MainUI {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int choice;

        do {
            System.out.println("--------Main Control System--------");
            System.out.println("0. Exit");
            System.out.println("1. Reservation");
            System.out.println("2. Table");
            System.out.println("3. Menu");
            System.out.println("4. Order");
            System.out.println("5. Members");
            System.out.println("6. Invoice");
            System.out.println("Your choice: ");
            choice = in.nextInt();
            in.nextLine();

            switch(choice) {
                case 1:
                    ReservationUI reservationUI = new ReservationUI();
                    reservationUI.run();
                    break;
                case 2:
                    TableUI tableUI = new TableUI();
                    tableUI.run();
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
                    break;
            }
        } while (choice != 0);

    }
}