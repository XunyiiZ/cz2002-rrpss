package boundary;
/**
 * 
 * 
 */
import Entity.Member;
import Entity.Order;
import controller.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

public class InvoiceUI {
    private static InvoiceUI invoiceUI = null;
    private InvoiceController invoiceController = InvoiceController.getInstance();
    private OrderController orderController = OrderController.getInstance();
    private final String DATE_FORMAT = "dd/MM/yyyy";
    private final String TIME_FORMAT = "HH:mm";
    private final String MONTH_FORMAT = "MM/yyyy";
    private static Scanner in = new Scanner(System.in);

    private InvoiceUI() throws IOException {}

    public static InvoiceUI getInstance() throws IOException {
        if (invoiceUI == null)
            invoiceUI = new InvoiceUI();
        return invoiceUI;
    }

    public void run(){


        /* System.out.println("--------Invoice and Report Panel--------");
        System.out.println("0. Go back to MainUI" +
                "\n1. printInvoice"+
                "\n2. print daily revenue report"+
                "\n3. print monthly revenue report"+
                "\n4. back to main panel");

        int choice = in.nextInt(); */
        int choice = displayOptions();
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
            choice = displayOptions();
        }
    }

    private int displayOptions() {
        System.out.println("--------Invoice and Report Panel--------");
        System.out.println("0. Go back to MainUI" +
                "\n1. Print Invoice"+
                "\n2. Print daily revenue report"+
                "\n3. Print monthly revenue report");
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

    private void createInvoice(){
        int orderId;
        /* System.out.print("Enter order ID:");                                // handle invalid input  1. in the orderList
        //                                                                                            2. not been checked out
        orderId = in.nextInt();  */      
        while (true) {
            try {      
                System.out.print("Enter order ID:");                      
                orderId = in.nextInt(); 
                in.nextLine();
                if(orderController.getOrderByID(orderId) == null){
                    System.out.println("the order is not found");
                    return;
                }
                        
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("Invalid input");
                continue;
            }
            break;
        }

        String contact;
        while (true)
        {
            try{
                System.out.println("Enter contact no.: ");
                contact = in.nextLine();
                if (contact.length() != 8)
                    throw new Exception("Invalid contact number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }

        invoiceController.printInvoice(orderId, contact);

    }

    private void printRevenueReportByDay(){
        String dateStr;
        boolean validDateFormat;
        boolean validDate;
        LocalDate date;
        do {
            do {
                System.out.println("Please enter date of reservation: dd/mm/yyyy");
                dateStr = in.nextLine();
                validDateFormat = isValidDateFormat(dateStr, DATE_FORMAT);
            } while (!validDateFormat);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date = LocalDate.parse(dateStr,dateFormatter);
            validDate = isValidDate(date);
        }while(!validDate);

        invoiceController.printDailyReport(date);
    }
    private void printRevenueReportByMonth(){
        String dateStr;
        boolean validDateFormat;
        boolean validDate;
        LocalDate date;
        do {
            do {
                System.out.println("Please enter date of reservation: mm/yyyy");
                dateStr = in.nextLine();
                validDateFormat = isValidDateFormat(dateStr, MONTH_FORMAT);
            } while (!validDateFormat);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date = LocalDate.parse("01/"+dateStr,dateFormatter);
            validDate = isValidDate(date);
        }while(!validDate);

        invoiceController.printMonthlyReport(dateStr);
    }

    private boolean isValidDateFormat(String dateTime, String FORMAT){
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT);
            dateFormat.setLenient(false);
            dateFormat.parse(dateTime);
            return true;
        } catch (ParseException ex) {
            System.out.println("Incorrect date format!");
            return false;
        }
    }

    private boolean isValidDate(LocalDate appointmentDate){
        if(!appointmentDate.isAfter(LocalDate.now())) {
            return true;}
        System.out.println("invalid date!");
        return false;
    }

}