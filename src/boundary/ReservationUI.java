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

public class ReservationUI {

    private ReservationController reservationController = ReservationController.getInstance();
    private final String DATE_FORMAT = "dd/MM/yyyy";
    private final String TIME_FORMAT = "HH:mm";
    private static ReservationUI reservationUI = null;
    private static Scanner in = new Scanner(System.in);

    public ReservationUI() throws IOException {

    }

    public static ReservationUI getInstance() throws IOException {
        if (reservationUI == null)
            reservationUI = new ReservationUI();
        return reservationUI;
    }

    public void run() throws IOException {
        String dateStr;
        String timeStr;
        String name;
        String contact;
        int numberOfPax;

        int choice = this.displayOptions();
        while (choice != 0) {
            switch (choice) {
                case 1:
                    boolean validDateFormat;
                    boolean validDate;
                    LocalDate appointmentDate;
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
                    reservationController.getReservationByContact(contact);  //zhe ge shi gan ma?
                    break;
                case 2:
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
                    break;
                case 3:
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

                    break;
                case 4:
                    reservationController.displayAllReservations();
                    break;
                default: 
                    System.out.println("Invalid input");
                    break;
            }

            choice = this.displayOptions();
        }


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
    /** assume that can only make reservation at least one day in advance */
    private boolean isValidDate(LocalDate appointmentDate){
        if(appointmentDate.isAfter(LocalDate.now())) {
            return true;}
            System.out.println("Reservations must be made at least 1 day in advance");
            return false;
    }





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
