package boundary;

import Entity.Order;
import controller.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
/**
 * Boundary class for the Invoice User Interface that allows staff to create invoices and generate revenue reports
 * @author Timothy
 * @version 1.0
 * @since 2021-11-13
 */
public class InvoiceUI {
    /**
     * This constant defines the correct date input format
     */
    private final String DATE_FORMAT = "dd/MM/yyyy";
    /**
     * This constant defines the correct month input format
     */
    private final String MONTH_FORMAT = "MM/yyyy";
    /**
     * This field provides an instance of InvoiceUI
     */
    private static InvoiceUI invoiceUI = null;
    /**
     * This field provides an instance of InvoiceController
     */
    private InvoiceController invoiceController = InvoiceController.getInstance();
    /**
     * This field provides an instance of OrderController
     */
    private OrderController orderController = OrderController.getInstance();

    private static Scanner in = new Scanner(System.in);

    private InvoiceUI(){}

    /**
        * Get instance
        * @return InvoiceUI instance and creates a new instance if there was none previously
    */
    public static InvoiceUI getInstance() {
        if (invoiceUI == null)
            invoiceUI = new InvoiceUI();
        return invoiceUI;
    }
    /**
     * This method provides user interface for all functionalities.
     * User can create an invoice, print daily revenue report and print monthly revenue report
     */
    public void run(){

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
    /**
     * Displays the reservation menu
     * @return user's choice
     */
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
    /**
     * Creates a new invoice according to the information entered.
     */
    private void createInvoice(){
        int orderId;
        while (true) {
            try {      
                System.out.print("Enter order ID:");                      
                orderId = in.nextInt(); 
                in.nextLine();

                Order order = orderController.getOrderByID(orderId);
                if(order == null){
                    System.out.println("The order is not found");
                    return;
                }
                else {
                    order.setIsActive(false);
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
    /**
     * print the daily revenue of the day,
     * which will show the total revenue of that day and all invoices for that day
     */
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
    /**
     * print the monthly revenue  report of the given month,
     * which will show the total revenue of that month and highest revenue day and lowest revenue day
     */
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
    /**
     * to check whether the input date string is in correct date format
     * @param dateTime the date string need to be checked
     * @param FORMAT specifies the correct format
     * @return whether the date string is in correct format or not
     */
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
    /**
     * To check whether the date is in the past or not.
     * the system can only check for daily revenue of the past days
     * @param appointmentDate specifies the date string need to be checked
     * @return whether the date string is in the past or not
     */
    private boolean isValidDate(LocalDate appointmentDate){
        if(!appointmentDate.isAfter(LocalDate.now())) {
            return true;}
        System.out.println("invalid date!");
        return false;
    }

}