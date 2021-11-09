package boundary;

import controller.*;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.time.LocalDate;

public class InvoiceUI {
    private static InvoiceUI invoiceUI = null;
    private InvoiceController invoiceController = InvoiceController.getInstance();

    private static Scanner in = new Scanner(System.in);

    private InvoiceUI() throws IOException {}

    public static InvoiceUI getInstance() throws IOException {
        if (invoiceUI == null)
            invoiceUI = new InvoiceUI();
        return invoiceUI;
    }

    public void run(){


        System.out.println("--------Invoice and Report Panel--------");
        System.out.println("0. Go back to MainUI" +
                "\n1. printInvoice"+
                "\n2. print daily revenue report"+
                "\n3. print monthly revenue report"+
                "\n4. back to main panel");

        int choice = in.nextInt();
        while(choice != 0) {
            switch (choice) {
                case 1:
                    createInvoice();
                    break;
                case 2:
                    printRevenueReportByDay();
                    break;
                case 3:
                    printRevenueReportByMonth();
                    break;
                case 4:
                    System.out.println("back to main panel...");
                    return;
                default:
                    System.out.println("Invalid input");
            }
            System.out.println("Enter option:");
            choice = in.nextInt();
        }
    }

    private void createInvoice(){
        int orderId;
        System.out.print("enter order ID:");                                // handle invalid input  1. in the orderList
        //                                                                                            2. not been checked out
        orderId = in.nextInt();

        System.out.println("enter phoneNumber to check membership");

        String number = in.next();
        invoiceController.printInvoice(orderId, number);

    }

    private void printRevenueReportByDay(){
        System.out.println("enter the date: (dd/mm/yyyy)");
        String dateString = in.next();

    }
    private void printRevenueReportByMonth(){
        System.out.println("Please enter the month(MM/YYYY)");
        String dateStr = in.next();
        invoiceController.printMonthlyReport(dateStr);
    }
}