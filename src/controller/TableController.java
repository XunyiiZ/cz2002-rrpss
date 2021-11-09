package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import Entity.*;

public class TableController {

    private Table[] listOfTables;
    final int SIZE=20;
    private static TableController tableController = null;

    public TableController()
    {

        listOfTables = new Table[SIZE];
        int i = 0;
        listOfTables[i++] = new Table(i, 2);
        listOfTables[i++] = new Table(i, 2);
        listOfTables[i++] = new Table(i, 2);
        listOfTables[i++] = new Table(i, 2);

        listOfTables[i++] = new Table(i, 4);
        listOfTables[i++] = new Table(i, 4);
        listOfTables[i++] = new Table(i, 4);
        listOfTables[i++] = new Table(i, 4);

        listOfTables[i++] = new Table(i, 6);
        listOfTables[i++] = new Table(i, 6);
        listOfTables[i++] = new Table(i, 6);
        listOfTables[i++] = new Table(i, 6);

        listOfTables[i++] = new Table(i, 8);
        listOfTables[i++] = new Table(i, 8);
        listOfTables[i++] = new Table(i, 8);
        listOfTables[i++] = new Table(i, 8);

        listOfTables[i++] = new Table(i, 10);
        listOfTables[i++] = new Table(i, 10);
        listOfTables[i++] = new Table(i, 10);
        listOfTables[i++] = new Table(i, 10);

    }

    public static TableController getInstance()
    {
        if(tableController == null) {
            tableController = new TableController();
        }
        return tableController;
    }

    public void setOccupied(int tableId)
    {
        listOfTables[tableId-1].setOccupied();
    }

    public void setUnoccupied(int tableId)
    {
        listOfTables[tableId-1].setUnoccupied();
    }

    public void displayAllTables()
    {
        for (Table i: listOfTables )
        {
            int tableId = i.getTableId();
            boolean status = i.getOccupied();

            System.out.println("Table " + tableId + " | " +" is Occupied: " + status + " | " + "Number of Seats: " + i.getNumOfSeats());
        }
    }

    // Showing in general all unoccupied tables
    public void displayUnoccupiedTables()
    {
        System.out.println("Showing all unoccupied tables: ");
        int j = 1;
        for (Table i: listOfTables)
        {
            if (i.getOccupied() == false)
            {

                System.out.println("Table " + i.getTableId());
            }

            j++;
            if(j >= 13) {
                break;
            }

        }
    }


    // Showing all unoccupied tables corresponding to the number of people
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

    public void displayOccupiedTables()
    {
        for (Table i: listOfTables)
        {
            if (i.getOccupied() == true)
            {
                System.out.println("Table " + i.getTableId());
            }
        }
    }

    public void getTableByID(int tableId)
    {
        System.out.println("Table " + tableId + "Occupied: " + listOfTables[tableId].getOccupied() + " Number of Seats: " + listOfTables[tableId].getNumOfSeats());
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
    /**get table by pax*/
    public ArrayList<Integer> getTableByTablePax(int pax){

        ArrayList<Integer> tables = new ArrayList<>();

        for(int i =0; i<SIZE; ++i){
            if(listOfTables[i].getNumOfSeats() == pax){
                tables.add(listOfTables[i].getTableId());
            }
        }
        return tables;
    }

    public ArrayList<Integer> getCurrentReservedTable(int tablePax) throws IOException {
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