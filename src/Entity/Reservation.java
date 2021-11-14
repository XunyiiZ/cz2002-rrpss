package Entity;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This entity class defines Reservation object
 *
 * @author Zeng Xunyi
 * @version 1
 * @since 2021-11
 *
 */
public class Reservation {

    /**
     * The reservation ID
     */
    private int reservationId;
    /**
     * The name of customer who made this reservation
     */
    private String name;
    /**
     * The contact of customer who made this reservation
     */
    private String contact;
    /**
     * The number of customers
     */
    private int numberOfPax;
    /**
     * Reserved table ID
     */
    private int tableId;
    /**
     * Reserved time
     */
    private LocalDate appointmentDate;
    /**
     * Reserved date
     */
    private LocalTime appointmentTime;

    /**
     * This constructor initializes reservation id, time, pax,reserved table id, customer contact and customer name.
     *
     * @param reservationId
     *            reservation id
     * @param name
     *            specifies booker's name
     * @param contact
     *            specifies booker's contact
     * @param numberOfPax
     *            specifies number of customers
     * @param date
     *            specifies appointment date
     * @param time
     *            specifies appointment time
     */
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
    /**
     * This is the accessor method of reservationId field.
     *
     * @return reservation id
     */
    public int getReservationId()
    {
        return reservationId;
    }
    /**
     * This is the accessor method of name field.
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }
    /**
     * This is the accessor method of contact field.
     *
     * @return contact
     */
    public String getContact()
    {
        return contact;
    }
    /**
     * This is the accessor method of numberOfPax field.
     *
     * @return numberOfPax
     */
    public int getNumberOfPax()
    {
        return numberOfPax;
    }
    /**
     * This is the accessor method of tableId field.
     *
     * @return tableId
     */
    public int getTableId()
    {
        return tableId;
    }
    /**
     * This is the accessor method of appointmentDate field.
     *
     * @return appointmentDate
     */
    public LocalDate getAppointmentDate(){ return appointmentDate;}
    /**
     * This is the accessor method of appointmentTime field.
     *
     * @return appointmentTime
     */
    public LocalTime getAppointmentTime(){return appointmentTime;}
    /**
     * The method is to return the information about this reservation in string format
     */
    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                "\nReservation made for " + numberOfPax +
                " under the name, " + name +
                "\nThe table number is " + tableId +
                "\nThe appointment date time is " + appointmentDate + " " + appointmentTime.truncatedTo(ChronoUnit.SECONDS) + "\n";
    }
}
