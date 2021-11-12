package controller;

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.*;
import Entity.*;

import javax.sound.sampled.SourceDataLine;

public class ReservationController extends AbstractController {
    private ArrayList<Reservation> reservationList;
    private static ReservationController reservationController = null;
    private final int EXPIRE_PERIOD = 30;
    private static final String dir = "src/data/reservation.txt";
    private TableController tableController = TableController.getInstance();
    private static Scanner in = new Scanner(System.in);

    private static int reservationId = 0;

    public static ReservationController getInstance() throws IOException {
        if (reservationController == null) {
            reservationController = new ReservationController();
        }
        return reservationController;
    }

    public ReservationController() throws IOException {
        File file = new File(dir);
        if (file.exists()) {
            reservationList = load(dir);
        } else {

            file.getParentFile().mkdir();
            file.createNewFile();
            reservationList = new ArrayList<Reservation>();
            save(dir, reservationList);
        }
    }

        private void clearReservation() throws IOException {
        //try {
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
//            } catch (IOException e) {
//                System.out.println("What error?");
//                e.printStackTrace();
//            }
        }


        // display all the attributes of reservation class corresponding to its reservation id
        public void displayAllReservations() throws IOException {
            clearReservation();
            for (Reservation r: reservationList) {
                System.out.println(r.toString());
            }
        }

        public Reservation getReservationById(int reservationId) throws IOException {
        clearReservation();
        for (Reservation res : reservationList){
            if( res.getReservationId() == reservationId)
                return res;
        }
        System.out.println("Reservation not found");
        return null;
    }

        //clearReservation() is to remove the expired Reservations from reservation list

        public ArrayList<Reservation> getConflictReservation(LocalDate date, LocalTime time, int tablePax){
            ArrayList<Reservation> conflictReservation = new ArrayList<>();
            for(Reservation reservation : reservationList){
                LocalTime otherTime = reservation.getAppointmentTime().plusMinutes(119);
                int curTablePax = tableController.getTablePax(reservation.getNumberOfPax());
                if(curTablePax == tablePax && reservation.getAppointmentDate().equals(date) && otherTime.isAfter(time)){
                    conflictReservation.add(reservation);
                    }
                }
            return conflictReservation;
        }

        public void createReservation(String name, String contact, int numberOfPax, LocalDate date, LocalTime time)
        {
            try{
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
                    System.out.println("the reservation is full");
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

                reservationId = reservationList.get(reservationList.size()-1).getReservationId()+1;

                // create the reservation
                Reservation reservation = new Reservation(reservationId, name, contact, numberOfPax, tableId, date, time);
                reservationList.add(reservation);
                System.out.println("The reservation is created!");
                System.out.println("Here is the details for the reservation:");
                System.out.println(reservation.toString());
                save(dir,reservationList);
            }catch (IOException e) {
                System.out.println("shen me error? ");
                e.printStackTrace();
        }
   }


        // check reservation method: clearReservation method + search by contact no.
        // return the reservation by contact
        public ArrayList<Reservation> getReservationByContact(String contact) throws IOException {
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



    // method to remove expired Reservations: clearReservation method + loop through all reservations and return index of reservation(s)

    public void removeReservationByContact(String contact) throws IOException {
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
                    if (in.nextLine().charAt(0) == 'Y')  removeReservationById(Id);
                }
            }
            else System.out.println("There are no reservations found for this contact");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean removeReservationById(int id){
        try{
            Reservation res = getReservationById(id);
            if(res == null){
                System.out.println("Reservation not found");
                return false;
            }
            reservationList.remove(res);
            save(dir,reservationList);
            System.out.println("Reservation removed");
            return true;
        } catch (IOException e) {
            System.out.println("Error: Reservation not saved");
            e.printStackTrace();
            return true;
        }
    }



        //   load method will be different between different controller
        public ArrayList load(String filename) throws IOException {
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
         * save method
         * save method will be different with different controller
         */

        public void save(String filename, List al) throws IOException {
        List alw = new ArrayList();  //to store data

        for (int i = 0; i < al.size(); i++) {
            Reservation reservation = (Reservation) al.get(i);
            StringBuilder st = new StringBuilder();
            st.append(reservation.getReservationId()); // trim() ??
            st.append("|");
            st.append(reservation.getName());
            st.append("|");
            st.append(reservation.getContact());  // ke yi ma?
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

