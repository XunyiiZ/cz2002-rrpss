package boundary;
import controller.*;
import Entity.*;

import java.awt.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderUI {

    private OrderController oc = OrderController.getInstance();
    private static OrderUI orderUI = null;
    private MenuController mc = MenuController.getInstance();
    private static Scanner sc = new Scanner(System.in);

    public OrderUI() throws IOException {
    }

    public static OrderUI getInstance() throws IOException {
        if (orderUI == null) {
            orderUI = new OrderUI();
        }
        return orderUI;
    }
    public void run() throws IOException {
        int option;
        option = choose();
        while(option != 0){
            switch(option){
                case 1: //create order
                    oc.createOrder();
                    break;
                case 2: //display
                    //check order
                    System.out.println("Enter OrderID: ");
                    int orderID = sc.nextInt();
                    displayOrder(orderID);
                    break;
                case 3: //add order item, KIV
                    System.out.println("Enter OrderID: ");
                    int orderId = sc.nextInt();
                    addItem(orderId);
                    break;
               case 4: //remove order, KIV
                    System.out.println("Enter the ID of item to remove.");
                    int id = sc.nextInt();
                    removeItem(id);
                    //code for removing here
                    break;
                case 5:
                    //display all orders
                    oc.displayAllOrders();
                    break;
                default: 
                    System.out.println("Invalid input");
                    break;
            }
            option = choose();
        }
    }

    private void displayOrder(int id){
        Order order = oc.getOrderByID(id);
        if(order == null) System.out.println("Invalid Order ID");
        else{
            order.displayOrder();
        }
    }

    private void addItem(int orderId) throws IOException {
        Order order = oc.getOrderByID(orderId);
        if(order == null) System.out.println("Invalid Order ID");
        else{
            System.out.println("Order found");
            oc.addItemToOrder(orderId);
        }
    }

    private void removeItem(int orderId) throws IOException {
        Order order = oc.getOrderByID(orderId);
        if(order == null) System.out.println("Invalid Order ID");
        else{
            System.out.println("order found");
            oc.removeItemFromOrder(orderId);
        }
    }

    private static int choose(){
        System.out.println("--------Order System--------");
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create Order");
        System.out.println("2. View Order Items");
        System.out.println("3. Add Order Item to Order");
        System.out.println("4. Remove Order Item from Order");
        System.out.println("5. Display all orders");

        try{
            System.out.println("Your choice: ");
            return sc.nextInt();
        } catch(InputMismatchException e) {
            System.out.println("Invalid input");
            sc.nextLine();
            return choose();
        }
    }
}

