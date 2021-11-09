package controller;
import Entity.*;
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

    public static OrderController getInstance() {
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

    public void createOrder() {
        int resID=-1;
        int staffID = -1;
        int tabID = -1;
        //1. Reservation
        System.out.println("Enter Staff ID: ");
        staffID = sc.nextInt();

        System.out.println("Do you have a reservation?");
        char response = sc.nextLine().charAt(0);
        switch(response){
            case 'Y':
                int count = 2; //check for correct reservation id, check 2x if invalid then break
                while(count<0){
                    System.out.println("Enter reservation ID: ");
                    resID = sc.nextInt();
                    Reservation reservation = getReservationById(resID); //Reservation reservation = getReservationById(resID); --> if null, then ask again for reservation id, if null 2x then break out loop
                    if (reservation == null){
                        System.out.println("Reservation not found. Please try again.");
                    }
                    else{
                        tabID = reservation.getTableId();
                        break;
                    }
                    count --;
                }
                break;
            /*case 'N':
                System.out.println("Enter no. of pax: ");
                numOfPax = sc.nextInt();
                resController.convertIntoEnum();
                break;*/
        }
        //orderID
        orderID = orderID+1;
        Order order = new Order(staffID, orderID, tabID);
        orders.add(order);

        menuController.displayAllMenuItems();
        //while loop
        char res = 'N';
        do{
            System.out.println("Enter ID of Menu Item: ");
            int menuchoice = sc.nextInt();
            order.addOrderItem(menuchoice);
            System.out.println("Order Item has been added.");

            //now display the current orderitems inside
            order.getOrderItems();

            //ask if customer still wants to order
            System.out.println("Do you still want to continue ordering?");
            res = sc.nextLine().charAt(0);
        }while(res == 'Y');

        //no option to remove order item while creating order
        System.out.println("Your order has been created. Here are your order items:");
        order.printOrders();
    }


    public Order getOrderByID(int OrderID){
        for(Order i: orders){
            if(i.getOrder_ID() == OrderID) return i;
        }
        System.out.println("Order not found.");
        return null;
    }

    public void getOrders(){
        for(Order i: orders){
            i.printOrders();
        }
    }
}
