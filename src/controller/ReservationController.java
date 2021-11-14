package controller;

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.*;
import Entity.*;

/**
 * ReservationController is control class that supports operations related to
 * reservation.
 *
 * @author Zeng Xunyi
 * @version 1.0
 * @Date 2021-11
 */

public class ReservationController extends AbstractController {
    /**
     * This constant defines the threshold to determine whether a reservation is
     * expired. If the current time exceeds the reserved time by that much, a
     * reservation is expired.
     */
    private final int EXPIRE_PERIOD = 30;
    /**
     * This constant defines the file path that store all data of reservation list
     */
    private static final String dir = "src/data/reservation.txt";
    /**
     * The field holds all reservations
     */
    private ArrayList<Reservation> reservationList;
    /**
     * This field provides an instance of reservation Controller
     */
     private static ReservationController reservationController = null;
    /**
     * This field provides access to control over Table objects
     */
    private TableController tableController = TableController.getInstance();
    private static Scanner in = new Scanner(System.in);

    /**
     * This is the constructor of this class. It will load reservations from
     * external files.
     */
    public ReservationController() {
        try {
            File file = new File(dir);
            if (file.exists()) {
                reservationList = load(dir);
            } else {

                file.getParentFile().mkdir();
                file.createNewFile();
                reservationList = new ArrayList<Reservation>();
                save(dir, reservationList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods returns an instance of ReservationController
     * @return ReservationController
     */
    public static ReservationController getInstance() {
        if (reservationController == null) {
            reservationController = new ReservationController();
        }
        return reservationController;
    }

    /**
     * This method is to remove all expired but yet checked in reservations
     */
    private void clearReservation() {
        LocalDate today = LocalDate.now();
        LocalTime curTime = LocalTime.now();

        ArrayList<Reservation> toRemove = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            LocalTime expireTime = reservation.getAppointmentTime().plusMinutes(EXPIRE_PERIOD);
            if (reservation.getAppointmentDate().isBefore(today) ||( reservation.getAppointmentDate().equals(today) && curTime.isAfter(expireTime))) {
                toRemove.add(reservation);
            }
        }
        reservationList.removeAll(toRemove);
        save(dir, reservationList);
    }
    /**
     * This method is to display all reservations with details
     */
    public void displayAllReservations(){
        clearReservation();
        for (Reservation r: reservationList) {
            System.out.println(r.toString());
        }
    }

    /**
     * This method is to find a certain reservation by a given reservation id
     * @param reservationId
     *              specifies the reservation id
     * @return reservation of given reservation id
     */
    public Reservation getReservationById(int reservationId){
    clearReservation();
    for (Reservation res : reservationList){
        if( res.getReservationId() == reservationId)
            return res;
    }
    return null;
}
    /**
     * This method is to get all conflict reservations of corresponding data, time and tablePax
     *The criterion of conflicts is the reserved time of neither of them
     * is within 2 hours of the other's and tablePax is same.
     *
     * @param date
     *            the date to be checked
     * @param time
     *            the time to be checked
     * @param tablePax
     *            specifies the tablePax that need to be checked
     * @return a list of conflict reservations
     */
    public ArrayList<Reservation> getConflictReservation(LocalDate date, LocalTime time, int tablePax){
        ArrayList<Reservation> conflictReservation = new ArrayList<>();
        LocalTime startTime = time.minusMinutes(119);
        LocalTime endTime = time.plusMinutes(119);
        for(Reservation reservation : reservationList){
            LocalTime otherEndTime = reservation.getAppointmentTime().plusMinutes(119);
            LocalTime otherStartTime = reservation.getAppointmentTime().minusMinutes(119);
            int curTablePax = tableController.getTablePax(reservation.getNumberOfPax());
            if(curTablePax == tablePax && reservation.getAppointmentDate().equals(date) && ( (otherEndTime.isAfter(startTime) && otherEndTime.isBefore(endTime))
            || (otherStartTime.isAfter(startTime) && otherStartTime.isBefore(endTime)))){
                conflictReservation.add(reservation);
                }
            }
        return conflictReservation;
    }
    /**
     * This method is to create a new reservation
     *
     * @param name
     *              the customer's name of this reservation
     * @param contact
     *              customer's contact of this reservation
     * @param numberOfPax
     *              number of pax who will dine in this reservation
     * @param date
     *              the reserved date
     * @param time
     *              the reserved time
     */
    public void createReservation(String name, String contact, int numberOfPax, LocalDate date, LocalTime time)
    {
        clearReservation();
        // get all table of specific pax
        int tablePax = tableController.getTablePax(numberOfPax);
        ArrayList<Integer> tableList = tableController.getTableByTablePax(tablePax);

        // check time to remove the reserved table from table list
        for(Reservation reservation : getConflictReservation(date,time,tablePax)){
            if(tableList.contains(reservation.getTableId())) {
                //System.out.println(reservation.getTableId());
                int idx = tableList.indexOf(reservation.getTableId());
                tableList.remove(idx);
            }
        }

        // display available table id and ask user to choose the id
        if(tableList.size()==0){
            System.out.println("The reservation list is full. Unable to reserve any tables for this slot");
            return;
        }

        System.out.println("Unreserved table(s): " + tableList.toString());
        System.out.println("Enter the table id to reserve the table:");
        int tableId = in.nextInt();
        do {
            try {
                if (!tableList.contains(tableId)) {
                    throw new Exception("Invalid table number");
                }
            } 	catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Enter the table id to reserve the table:");
                tableId = in.nextInt();
                continue;
            }
            break;
        } while (true);

        int reservationId = reservationList.get(reservationList.size()-1).getReservationId()+1;

        // create the reservation
        Reservation reservation = new Reservation(reservationId, name, contact, numberOfPax, tableId, date, time);
        reservationList.add(reservation);
        System.out.println("The reservation is created!");
        System.out.println("Here is the details for the reservation:");
        System.out.println(reservation.toString());
        save(dir,reservationList);
    }

    /**
     * This method is to search a certain reservation by its customer's contact.
     *
     * @param contact
     *            the customer's contact to be searched
     * @return a list of possible reservations
     */
    public ArrayList<Reservation> getReservationByContact(String contact){
        clearReservation();
//         boolean found = false;
        ArrayList<Reservation> foundList = new ArrayList<>();
        try {
            if (contact.length() != 8)
                throw new Exception("Invalid contact number");
            for (Reservation r : reservationList) {
                if (r.getContact().contains(contact)){
                    foundList.add(r);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    return foundList;
}

    /**
     * This method is to search a certain reservation by its customer's contact.
     *
     * @param contact
     *            the customer's contact to be searched
     * @return a list of possible reservations
     */
    public void removeReservationByContact(String contact) {
        clearReservation();
        try {
            if (contact.length() != 8)
                throw new Exception("Invalid contact number");

            //boolean found = checkReservation(contact);
            ArrayList<Reservation> foundList = getReservationByContact(contact);
            if (foundList.size() != 0) {
                for(Reservation res: foundList){
                    System.out.println(res.toString());
                }

                System.out.println("Enter reservation no. to delete: ");
                int Id;
                while (true) {
                    try {                            
                        Id = in.nextInt(); 
                        in.nextLine();   
                                
                    } catch (InputMismatchException e) {
                        in.nextLine();
                        System.out.println("Invalid input");
                        System.out.println("Enter reservation no. to delete: ");
                        continue;
                    }
                    break;
                }
                Reservation res = getReservationById(Id);
                if(foundList.contains(res)){
                    System.out.println("Do you wish to remove this reservation? Y/N");
                    if (in.nextLine().toUpperCase().charAt(0) == 'Y')  removeReservationById(Id);
                }
            }
            else System.out.println("There are no reservations found for this contact");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    /**
     * This method is to remove a certain reservation by its id.
     *
     * @param id
     *            the reservation id to be removed
     */
    public boolean removeReservationById(int id){
        Reservation res = getReservationById(id);
        if(res == null){
            return false;
        }
        reservationList.remove(res);
        save(dir,reservationList);
        System.out.println("Reservation removed");
        return true;
    }
    /**
     * This method is to load reservations from external files
     * @param filename
     *            specifies where the external files stored
     * @return all reservations read from the file
     */
    @Override
    public ArrayList load(String filename) {
        ArrayList stringArray = (ArrayList) read(filename);
        ArrayList alr = new ArrayList();  // to store reservation data

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, "|");


            int reservationId = Integer.parseInt(star.nextToken().trim());
            String name = star.nextToken().trim();
            String contact = star.nextToken().trim();
            int numOfPax = Integer.parseInt(star.nextToken().trim());
            int tableId = Integer.parseInt(star.nextToken().trim());
            LocalDate date = LocalDate.parse(star.nextToken().trim());
            LocalTime time = LocalTime.parse(star.nextToken().trim());

            // create reservation object from file data
            Reservation reservation = new Reservation(reservationId,name,contact,numOfPax,tableId,date,time);

            //add to reservationList
            alr.add(reservation);
        }
        return alr;
    }
    /**
     * This method is to save current reservations to external files.
     * @param filename
     *          specifies where the data to be stored
     * @param al
     *          specifies the list to be saved to the file
     */
    @Override
    public void save(String filename, List al){
        List alw = new ArrayList();

        for (int i = 0; i < al.size(); i++) {
            Reservation reservation = (Reservation) al.get(i);
            StringBuilder st = new StringBuilder();
            st.append(reservation.getReservationId());
            st.append("|");
            st.append(reservation.getName());
            st.append("|");
            st.append(reservation.getContact());
            st.append("|");
            st.append(reservation.getNumberOfPax());
            st.append("|");
            st.append(reservation.getTableId());
            st.append("|");
            st.append(reservation.getAppointmentDate());
            st.append("|");
            st.append(reservation.getAppointmentTime());
            alw.add(st.toString());
        }
        write(filename, alw);
    }
}

