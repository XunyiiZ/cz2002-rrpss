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

    public void run()
    {
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

                    System.out.println("Please enter the number of person(s): ");
                    numberOfPax = in.nextInt();
                    in.nextLine();

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
                    ArrayList<Reservation> resList = reservationController.getReservationByContact(contact);
                    if(resList == null) System.out.println("no reservation found");
                    else{
                        for(Reservation res : resList){
                            System.out.println(res.toString());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Please enter your contact: ");
                    contact = in.nextLine();
                    reservationController.removeReservationByContact(contact);

                    break;
                case 4:
                    System.out.println("in case 4 to display the reservations");
                    reservationController.displayAllReservations();
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
            return false;
        }
    }
    /** assume that can only make reservation at least one day in advance */
    private boolean isValidDate(LocalDate appointmentDate){
        if(appointmentDate.isAfter(LocalDate.now())) return true;
        System.out.println("Reservations must be made 1 day in advanced");
        return false;
    }




    private int displayOptions() {
        System.out.println("--------Reservation System--------");
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create a new reservation");
        System.out.println("2. Check reservation");
        System.out.println("3. Remove reservation");
        System.out.println("4. Display all reservations");
        System.out.println("Your choice: ");
        int choice = in.nextInt();
        in.nextLine();
        return choice;
    }
}
