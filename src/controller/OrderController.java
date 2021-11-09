package controller;
import Entity.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class OrderController {

    private static ArrayList<Order> orders = new ArrayList<Order>();
    private static OrderController orderController = null;
    private TableController tableController = TableController.getInstance();
    private MenuController menuController = MenuController.getInstance();
    private ReservationController resController = ReservationController.getInstance();
    private static int numOfOrders = 0;
    private int numOfPax = 0;
    private static Scanner sc = new Scanner(System.in);
    private static int orderID = 0;

    public OrderController() throws IOException {
    }

    public static OrderController getInstance() throws IOException {
        if (orderController == null) {
            orderController = new OrderController();
        }
        return orderController;
    }

    public void showOrderItems(int id) {
        for(Order i : orders){
            if(i.getOrder_ID() == id){
                i.getOrderItems();
            }
            break;
        }
    }

    public void createOrder() throws IOException {
        int resID=-1;
        int staffID = -1;
        int tabID = -1;
        //1. Reservation
        System.out.println("Enter Staff ID: ");
        staffID = sc.nextInt();

        System.out.println("check in for a reservation? Y/N");
        char response = sc.nextLine().charAt(0);

        if(response == 'Y'){
            int count = 2; //check for correct reservation id, check 2x if invalid then break
            while(count<0){
                System.out.println("Enter reservation ID: ");
                resID = sc.nextInt();
                Reservation reservation = resController.getReservationById(resID); //Reservation reservation = getReservationById(resID); --> if null, then ask again for reservation id, if null 2x then break out loop
                if (reservation == null){
                    System.out.println("Reservation not found. Please try again.");
                }
                else{
                    tabID = reservation.getTableId();
                    resController.removeReservationById(resID);  // remove his reservation
                    break;
                }
                count --;
            }
            if(count <0) response = 'N';
        }

        if(response == 'N'){
            //call tablecontroller and show unoccupied unreserved tables
                System.out.println("Enter no. of pax: ");
                numOfPax = sc.nextInt();
                int tablePax = tableController.getTablePax(numOfPax);
                ArrayList<Integer> availableTables = getAvailableTable(tablePax);
                if(availableTables.size()==0){
                    System.out.println("there is no available table now");
                }
                else{
                    System.out.println("available table:" + availableTables.toString());
                }
            System.out.println("assign the table id:");
            int tableId = sc.nextInt();
            do {
                try {
                    if (!availableTables.contains(tableId)) {
                        throw new Exception("Invalid table number!");
                    }
                } 	catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("enter the table id to reserve the table");
                    tableId = sc.nextInt();
                    continue;
                }
                break;
            } while (true);

        }
        else{
            System.out.println("invalid input!");
        }


        //orderID
        orderID = orders.size()+1;

        Order order = new Order(staffID, orderID, tabID, numOfPax);
        orders.add(order);
        tableController.setOccupied(tabID);


        //while loop
        System.out.println("1. add item, 2.remove item 3. dispaly all items and 4. finish");
        int choice = sc.nextInt();
        while(true){
            if(choice>4 || choice<1) System.out.println("invalid input! \n 1. add item, 2.remove item 3. dispaly all items and 4. finish ");
            else{
                switch(choice){
                    case 1:
                        addItemToOrder(order);
                        System.out.println("add successfully");
                        break;
                    case 2:
                        removeItemFromOrder(order);
                        System.out.println("remove successfully");
                        break;
                    case 3:
                        order.displayAllItems();
                        break;
                    case 4:
                        break;
                }
            }
            if(choice == 4) break;
        }
//        do{
//
//            System.out.println("Enter index of Menu Item: ");
//            int menuchoice = sc.nextInt();
//            System.out.println("Enter the quantity of this item ");
//            int addQuantity = sc.nextInt();
//            order.addOrderItem(menuchoice, addQuantity); //exception handle
//            System.out.println("Order Item has been added.");
//
//            //now display the current orderitems inside
//            order.getOrderItems();
//
//            //ask if customer still wants to order
//            System.out.println("Do you still want to continue ordering?");
//            res = sc.nextLine().charAt(0);
//        }while(res == 'Y');

        //no option to remove order item while creating order
        System.out.println("Your order has been created.");
        order.displayOrder();
        save(dir,order);
    }

    //menu item id should be unique and not neccessarily in sequence
    //menu item id is only used in text file to trace the menu item
    //when display menu item can use index

    private void addItemToOrder(Order order){
        menuController.displayAllMenuItems();  // need to display the index
        System.out.println("enter the index of menu item");
        int itemIdx = sc.nextInt();
        if(itemIdx<0 || itemIdx >= menuController.getAllMenu().size()){
            System.out.println("invalid input, add item unsuccessfully");
            return;
        }
        else{
            int itemId = menuController.getItemByIdx(Idx);
            System.out.println("enter quantity:");
            int quantity = sc.nextInt();
            order.addOrderItem(itemId, quantity);
        }
    }

    private void removeItemFromOrder(Order order){
        order.displayAllItems();
        System.out.println("enter the index of menu item");
        int itemIdx = sc.nextInt();
        if(order.getOrderItems().size()==0){
            System.out.println("empty order, nothing to delete!");
            return;
        }
        else if(itemIdx<0 || itemIdx >= order.getOrderItems().size()){
            System.out.println("invalid input, remove item unsuccessfully");
            return;
        }
        else{
            order.removeOrderItemByIdx(itemIdx);
            System.out.println("Remove Successfully");
        }
    }

    public void removeOrder(int OrderID){
        orders.removeIf(order -> order.getOrder_ID() == OrderID);
    }

    public Order getOrderByID(int OrderID){
        for(Order i: orders){
            if(i.getOrder_ID() == OrderID) return i;
        }
        System.out.println("Order not found.");
        return null;
    }


    private ArrayList<Integer> getAvailableTable(int tablePax) throws IOException {
        ArrayList<Integer> tableList =  tableController.getTableByTablePax(tablePax);
        tableList.remove(tableController.getCurrentReservedTable(tablePax));
        return tableList;
    }

    public void displayAllOrders(){
        for(Order order:orders){
            order.displayOrder();
        }
    }
}