package boundary;
import controller.*;
import Entity.*;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This boundary class provides users with access to various functionalities of
 * order management.
 *
 * @author Zeng Xunyi
 * @version 1.0
 * @Date 2021-11
 */
public class OrderUI {
    /**
     * This field provides an instance of orderController
     */
    private OrderController oc = OrderController.getInstance();
    /**
     * This field provides an instance of order UI
     */
    private static OrderUI orderUI = null;
    /**
     * This field provides an instance of member Controller
     */
    private MenuController mc = MenuController.getInstance();
    private static Scanner sc = new Scanner(System.in);
    /**
     * This constructor initializes the OrderUI
     */
    public OrderUI() {
    }
    /**
     * This method is to get instance of OrderUI
     */
    public static OrderUI getInstance() {
        if (orderUI == null) {
            orderUI = new OrderUI();
        }
        return orderUI;
    }
    /**
     * This method provides user interface for all functionalities.
     * User can create and view an order, add order item, remove order item from order and check all orders
     */
    public void run() {
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
                    System.out.println("Enter order ID");
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
    /**
     * This method displays information of the order of given id
     * @param id
     *          the ID of the target order
     */
    private void displayOrder(int id){
        Order order = oc.getOrderByID(id);
        if(order == null) System.out.println("Invalid Order ID");
        else{
            order.displayOrder();
        }
    }
    /**
     * This method is to add order item to a specific order by given order id
     * @param orderId
     *          the ID of the target order
     */
    private void addItem(int orderId){
        Order order = oc.getOrderByID(orderId);
        if(order == null) System.out.println("Invalid Order ID");
        else if(!order.getIsActive()) {
            System.out.println("Order is not active");
        }
        else{
            System.out.println("Order found");
            oc.addItemToOrder(orderId);
        }
    }
    /**
     * This method is to remove order item from a specific order by given order id
     * @param orderId
     *          the ID of the target order
     */
    private void removeItem(int orderId){
        Order order = oc.getOrderByID(orderId);
        if(order == null) System.out.println("Invalid Order ID");
        else if(!order.getIsActive()) {
            System.out.println("Order is not active");
        }
        else{
            System.out.println("order found");
            oc.removeItemFromOrder(orderId);
        }
    }
    /**
     * Displays the order menu
     * @return user's choice
     */
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

