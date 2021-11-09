package Entity;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Reservation {

    private int reservationId;
    private String name;
    private String contact;
    private int numberOfPax;
    private int tableId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    public Reservation(int reservationId, String name, String contact, int numberOfPax, int tableId, LocalDate date, LocalTime time)
    {
        this.reservationId = reservationId;
        this.name = name;
        this.contact = contact;
        this.numberOfPax = numberOfPax;
        this.tableId = tableId;
        appointmentDate=date;
        appointmentTime=time;
    }

    public int getReservationId()
    {
        return reservationId;
    }

    public String getName()
    {
        return name;
    }

    public String getContact()
    {
        return contact;
    }

    public int getNumberOfPax()
    {
        return numberOfPax;
    }

    public int getTableId()
    {
        return tableId;
    }

    public String toString() {
        return "Reservation ID: " + reservationId +
                "\nReservation made for " + numberOfPax +
                " under the name, " + name +
                "\nThe table number is " + tableId +
                //".\nThe appointment date time is " + reservationTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + "\n";
                // need to change to date and time
                "\nThe appointment date time is "+appointmentDate+" " + appointmentTime.truncatedTo(ChronoUnit.SECONDS) + "\n";
    }

    public LocalDate getAppointmentDate(){ return appointmentDate;}
    public LocalTime getAppointmentTime(){return appointmentTime;}
}
