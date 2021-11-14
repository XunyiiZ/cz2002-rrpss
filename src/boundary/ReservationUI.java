package boundary;
import Entity.Reservation;
import controller.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This boundary class provides users with access to various functionalities of
 * reservation management.
 *
 * @author Xunyi
 * @version 1.0
 * @Date 2021-11
 */
public class ReservationUI {
    /**
     * This field provides an instance of ReservationController
     */
    private ReservationController reservationController = ReservationController.getInstance();
    /**
     * This field provides an instance of TableController
     */
    private TableController tableController =TableController.getInstance();
    /**
     * This field provides date format
     */
    private final String DATE_FORMAT = "dd/MM/yyyy";
    /**
     * This field provides time format
     */
    private final String TIME_FORMAT = "HH:mm";
    /**
     * This field provides an instance of ReservationUI
     */
    private static ReservationUI reservationUI = null;
    private static Scanner in = new Scanner(System.in);


    /**
     * This constructor initializes the ReservationManager
     */
    public ReservationUI(){}

    /**
     * This method is to get instance of ReservationUI
     */
    public static ReservationUI getInstance(){
        if (reservationUI == null)
            reservationUI = new ReservationUI();
        return reservationUI;
    }

    /**
     * This method provides user interface for all functionalities.
     * User can create, check and remove a certain reservation and check for all reservations
     */
    public void run(){
        int choice = this.displayOptions();
        while (choice != 0) {
            switch (choice) {
                case 1:
                    CreateReservation();
                    break;
                case 2:
                    CheckReservation();
                    break;
                case 3:
                    RemoveReservation();
                    break;
                case 4:
                    displayAllReservation();
                    break;
                default: 
                    System.out.println("Invalid input");
                    break;
            }
            choice = this.displayOptions();
        }


    }
    /**
     * This method is to check whether a given date string or time string is of stipulated format.
     * @param dateTime specifies the date string to be checked
     * @param FORMAT specifies the format
     * @return whether this date string is valid.
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
     * This method is to check whether a given date at least one day in advance.
     * @param appointmentDate specifies the date be checked
     * @return whether this date is in advance.
     */
    private boolean isValidDate(LocalDate appointmentDate){
        if(appointmentDate.isAfter(LocalDate.now())) {
            return true;}
            System.out.println("Reservations must be made at least 1 day in advance");
            return false;
    }

    /**
     * Creates a new reservation according to the information entered.
     */
    private void CreateReservation(){
        boolean validDateFormat;
        boolean validDate;
        String dateStr;
        String timeStr;
        String name;
        LocalDate appointmentDate;
        int numberOfPax;
        String contact;

        do {
            do {
                System.out.println("Please enter date of reservation: dd/mm/yyyy");
                dateStr = in.next();
                validDateFormat = isValidDateFormat(dateStr, DATE_FORMAT);
            } while (!validDateFormat);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            appointmentDate = LocalDate.parse(dateStr,dateFormatter);
            validDate = isValidDate(appointmentDate);
        }while(!validDate);


        do {
            System.out.println("Please enter time of reservation: HH:mm");
            timeStr = in.next();
            validDateFormat = isValidDateFormat(timeStr, TIME_FORMAT);
        }while(!validDateFormat);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime appointmentTime = LocalTime.parse(timeStr,timeFormatter);

        while (true) {
            try {
                System.out.println("Please enter the number of person(s): ");
                numberOfPax = in.nextInt();
                in.nextLine();
                if (numberOfPax > 10 || numberOfPax <=0) {
                    throw new Exception("Maximum pax is 10!");
                }
            } catch (java.util.InputMismatchException e) {
                in.nextLine();
                System.out.println("Invalid input!");
                continue;
            } catch(Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;

        }

        System.out.println("Please enter your name: ");
        name = in.nextLine();

        System.out.println("Please enter your contact: ");
        contact = in.nextLine();
        while (true)
        {
            try {
                if (contact.length() != 8)
                    throw new Exception("Invalid contact number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter your contact: ");
                contact = in.nextLine();
                continue;
            }
            break;
        }
        reservationController.createReservation(name, contact, numberOfPax, appointmentDate, appointmentTime);
        reservationController.getReservationByContact(contact);  //-----------------------------------------------??? need exception handling
    }

    /**
     * This method is to check information of a certain reservation by
     * customer's contact
     */
    private void CheckReservation(){
        String contact;
        System.out.println("Please enter your contact: ");
        contact = in.nextLine();
        while (true)
        {
            try {
                if (contact.length() != 8)
                    throw new Exception("Invalid contact number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter your contact: ");
                contact = in.nextLine();
                continue;
            }
            break;
        }
        ArrayList<Reservation> resList = reservationController.getReservationByContact(contact);
        if(resList.size() == 0) System.out.println("No reservation found");
        else{
            for(Reservation res : resList){
                System.out.println(res.toString());
            }
        }
    }

    /**
     * This method is to check remove a certain reservation by
     * customer's contact
     */
    private void RemoveReservation(){
        String contact;
        System.out.println("Please enter your contact: ");
        contact = in.nextLine();
        while (true)
        {
            try {
                if (contact.length() != 8)
                    throw new Exception("Invalid contact number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter your contact: ");
                contact = in.nextLine();
                continue;
            }
            break;
        }
        reservationController.removeReservationByContact(contact);
    }

    /**
     * This method is to display all reservations in reservation list
     */
    private void displayAllReservation(){
        reservationController.displayAllReservations();
    }

    /**
     * Displays the reservation menu
     * @return user's choice
     */
    private int displayOptions() {
        System.out.println("--------Reservation System--------");
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create a new reservation");
        System.out.println("2. Check reservation");
        System.out.println("3. Remove reservation");
        System.out.println("4. Display all reservations");
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
