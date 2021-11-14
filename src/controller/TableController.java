package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import Entity.*;


/**
 * TableController is control class that supports operations related to
 * Table.
 *
 * @author Sydney
 * @version 1.0
 * @Since 2021-11-13
 */
public class TableController {

    /**
     * An array of tables holding the table instances to mimic the behavior of a database
     */
    private Table[] listOfTables;
    final int SIZE=20;
    private static TableController tableController = null;

    /**
     * This is the constructor of this class. It will load tables from
     * external files.
     */
    public TableController()
    {

        listOfTables = new Table[SIZE];
        int i = 0;
        listOfTables[i] = new Table(++i, 2);
        listOfTables[i] = new Table(++i, 2);
        listOfTables[i] = new Table(++i, 2);
        listOfTables[i] = new Table(++i, 2);

        listOfTables[i] = new Table(++i, 4);
        listOfTables[i] = new Table(++i, 4);
        listOfTables[i] = new Table(++i, 4);
        listOfTables[i] = new Table(++i, 4);

        listOfTables[i] = new Table(++i, 6);
        listOfTables[i] = new Table(++i, 6);
        listOfTables[i] = new Table(++i, 6);
        listOfTables[i] = new Table(++i, 6);

        listOfTables[i] = new Table(++i, 8);
        listOfTables[i] = new Table(++i, 8);
        listOfTables[i] = new Table(++i, 8);
        listOfTables[i] = new Table(++i, 8);

        listOfTables[i] = new Table(++i, 10);
        listOfTables[i] = new Table(++i, 10);
        listOfTables[i] = new Table(++i, 10);
        listOfTables[i] = new Table(++i, 10);

    }

    /**
     * This methods returns an instance of TableController
     * @return TableController
     */
    public static TableController getInstance()
    {
        if(tableController == null) {
            tableController = new TableController();
        }
        return tableController;
    }

    /**
     * This set method is to set table to occupied
     */
    public void setOccupied(int tableId)
    {
        listOfTables[tableId-1].setOccupied();
    }

    /**
     * This set method is to set table to unoccupied
     */
    public void setUnoccupied(int tableId)
    {
        listOfTables[tableId-1].setUnoccupied();
    }

    /**
     * This method prints and display all tables
     */
    public void displayAllTables()
    {
        for (Table i: listOfTables )
        {
            int tableId = i.getTableId();
            boolean status = i.getOccupied();

            System.out.println("Table " + tableId + " | " +" is Occupied: " + status + " | " + "Number of Seats: " + i.getNumOfSeats());
        }
    }

    /**
    * This methods shows in general all unoccupied tables
    */ 
    public void displayUnoccupiedTables()
    {
        int count = 0;

        System.out.println("Showing all unoccupied tables: ");
        int j = 1;
        for (Table i: listOfTables)
        {
            if (i.getOccupied() == false)
            {

                System.out.println("Table " + i.getTableId());
                count++;
            }

            j++;
            if(j >= 13) {
                break;
            }

        }

        if (count == 0)
        {
            System.out.println("All tables are occupied");
        }
    }

    /**
    * This methods shows all unoccupied tables corresponding to the number of people
    */ 
    
    public void displayUnoccupiedTables(int numberOfPax)
    {
        int tablePax = getTablePax(numberOfPax);

        System.out.println("Showing all unoccupied tables: ");
        int j = 1;
        for (Table i: listOfTables)
        {
            if (i.getOccupied() == false && i.getNumOfSeats() == tablePax)
            {

                System.out.println("Table " + i.getTableId());
            }

            j++;
            if(j >= 13) {
                break;
            }

        }
    }

    /**
    * This methods displays all unoccupied tables
    */ 
    public void displayOccupiedTables()
    {
        int count = 0;

        System.out.println("Showing all occupied tables: ");
        for (Table i: listOfTables)
        {
            if (i.getOccupied() == true)
            {
                System.out.println("Table " + i.getTableId());
                count++;
            }
        }

        if (count == 0)
        {
            System.out.println("No tables are occupied");
        }

    }

    /**
     * Getter method for the table ID
     * Print the ID of the table
     */
    public void getTableByID(int tableId)
    {
        System.out.println("Table " + tableId + "Occupied: " + listOfTables[tableId].getOccupied() + " Number of Seats: " + listOfTables[tableId].getNumOfSeats());
    }

    public ArrayList<Integer> getAvailableTableID(int tablePax) {
        ArrayList<Integer> tableList = tableController.getTableByTablePax(tablePax);
        tableList.removeAll(tableController.getCurrentReservedTableID(tablePax));
        return tableList;
    }

    public int getTablePax(int pax){
        int tablePax=0;
        switch (pax)
        {
            case 1:
            case 2:
                tablePax = 2;
                break;
            case 3:
            case 4:
                tablePax = 4;
                break;
            case 5:
            case 6:
                tablePax = 6;
                break;
            case 7:
            case 8:
                tablePax = 8;
                break;
            case 9:
            case 10:
                tablePax = 10;
                break;
        }
        return tablePax;
    }
    /**Get table by pax*/

    public ArrayList<Integer> getTableByTablePax(int pax){

        ArrayList<Integer> tables = new ArrayList<>();

        for(int i =0; i<SIZE; ++i){
            if(listOfTables[i].getNumOfSeats() == pax && listOfTables[i].getOccupied()==false){
                tables.add(listOfTables[i].getTableId());
            }
        }
        return tables;
    }

    public ArrayList<Integer> getCurrentReservedTableID(int tablePax){
        ArrayList<Integer> tableList = new ArrayList<>();
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        ReservationController reservationController = ReservationController.getInstance();
        for(Reservation res : reservationController.getConflictReservation(date,time,tablePax)){
            tableList.add(res.getTableId());
        }
        return tableList;
    }
}