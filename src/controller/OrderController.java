package controller;

import java.io.IOException;
import Entity.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.ScatteringByteChannel;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
/**
 * OrderController is control class that supports operations related to
 * order.
 *
 * @author Zeng Xunyi
 * @version 1.0
 * @Date 2021-11
 */
public class OrderController extends AbstractController {
    /**
     * This constant defines the file path that store all data of order list
     */
    private static final String dir = "src/data/order.txt";
    /**
     * The field holds all orders
     */
    private static ArrayList<Order> orders;
    /**
     * This field provides an instance of order Controller
     */
    private static OrderController orderController = null;
    /**
     * This field provides access to control over Table objects
     */
    private TableController tableController = TableController.getInstance();
    /**
     * This field provides access to control over menu objects
     */
    private MenuController menuController = MenuController.getInstance();
    /**
     * This field provides access to control over reservation objects
     */
    private ReservationController resController = ReservationController.getInstance();
    /**
     * This is the constructor of this class. It will load orders from
     * external file.
     */
    private Scanner sc = new Scanner(System.in);

    public OrderController() {
        try {
            File file = new File(dir);
            if (file.exists()) {
                orders = load(dir);
            } else {
                file.getParentFile().mkdir();
                file.createNewFile();
                orders = new ArrayList<Order>();
                save(dir, orders);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods returns an instance of OrderController
     *
     * @return OrderController
     */
    public static OrderController getInstance() {
        if (orderController == null) {
            orderController = new OrderController();
        }
        return orderController;
    }

    /**
     * This method is to create a new order
     */
    public void createOrder() {
        int tabID = -1;
        int numOfPax = -1;
        int orderID;
        //1. Reservation
        System.out.println("Enter Staff ID: ");
        int staffID = sc.nextInt();
        sc.nextLine();

        System.out.println("Check in for a reservation? Y/N");
        char response = sc.nextLine().charAt(0);
        response = Character.toUpperCase(response);

        while (response != 'Y' && response != 'N') {
            System.out.println("Invalid input. please enter Y or N:");
            response = sc.nextLine().charAt(0);
            response = Character.toUpperCase(response);
        }

        if (response == 'Y') {
            int resID;
            int count = 2;
            while (count > 0) {
                System.out.println("Enter reservation ID: ");
                resID = sc.nextInt();
                sc.nextLine();
                Reservation reservation = resController.getReservationById(resID);
                if (reservation == null) {
                    System.out.println("Reservation not found. Please try again.");
                } else {
                    System.out.println("The reservation is found!");
                    tabID = reservation.getTableId();
                    numOfPax = reservation.getNumberOfPax();
                    resController.removeReservationById(resID);  // remove his reservation
                    break;
                }
                count--;
            }
            if (count <= 0) {
                System.out.println("Creating an order without a reservation");
                response = 'N';
            }
        }

        if (response == 'N') {
            System.out.println("Enter no. of pax: ");
            numOfPax = sc.nextInt();
            sc.nextLine();

            int tablePax = tableController.getTablePax(numOfPax);
            ArrayList<Integer> availableTables = tableController.getAvailableTableID(tablePax);
            if (availableTables.size() == 0) {
                System.out.println("There is no available table now");
                return;
            } else {
                System.out.println("Available table(s):" + availableTables);
            }

            System.out.println("Assign the Table ID:");
            tabID = sc.nextInt();
            sc.nextLine();
            do {
                try {
                    if (!availableTables.contains(tabID)) {
                        throw new Exception("Invalid table number!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Enter Table ID to reserve:");
                    tabID = sc.nextInt();
                    continue;
                }
                break;
            } while (true);

        }


        if (orders.size() == 0) orderID = 1;
        else {
            orderID = orders.get(orders.size() - 1).getOrderID() + 1;
        }

        Order order = new Order(orderID, staffID, tabID, numOfPax, true);
        orders.add(order);
        System.out.println("Table ID is " + tabID);
        tableController.setOccupied(tabID);

        System.out.println("1. Add item \n2. Remove Item \n3. Display all items \n4. Finish");
        int choice = sc.nextInt();
        sc.nextLine();
        while (true) {
            while (choice > 4 || choice < 1) {
                System.out.println("Invalid input");
                System.out.println("1. Add item \n2. Remove Item \n3. Display all items \n4. Finish");
                choice = sc.nextInt();
                sc.nextLine();
            }
            switch (choice) {
                case 1:
                    addItemToOrder(orderID);
                    break;
                case 2:
                    removeItemFromOrder(orderID);
                    break;
                case 3:
                    order.displayAllItems();
                    break;
                case 4:
                    try {
                        save(dir, orders);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if (choice == 4) break;
            System.out.println("1. Add item \n2. Remove Item \n3. Display all items \n4. Finish");
            choice = sc.nextInt();
            sc.nextLine();
        }
        System.out.println("Your order has been created.");
        order.displayOrder();
    }

    /**
     * This method is to add an order item to this order
     *
     * @param orderID specifies the ID of target order
     */
    public void addItemToOrder(int orderID) {
        Order order = getOrderByID(orderID);
        menuController.displayMenu();  // need to display the index
        System.out.println("Enter the menu item number that you like to add?");
        int itemNum = sc.nextInt();
        sc.nextLine();

        if (itemNum < 1 || itemNum > menuController.getMenuList().size()) {
            System.out.println("Invalid index!");
            return;
        } else {
            MenuItem item = menuController.getItemByIndex(itemNum - 1);
            int itemId = item.getMenuItemId();
            System.out.println(item.toString());
            System.out.println("Enter quantity:");
            int quantity = sc.nextInt();
            order.addOrderItem(itemId, quantity, item.getName(), item.getPrice() * quantity);
            System.out.println("Item added successfully");
            try {
                save(dir, orders);
            } catch (Exception e) {
                e.printStackTrace();
            }
            order.displayOrder();
        }
    }

    /**
     * This method is to remove an order item from this order
     *
     * @param orderID specifies the ID of target order
     */
    public void removeItemFromOrder(int orderID) {
        Order order = getOrderByID(orderID);
        order.displayAllItems();
        System.out.println("Enter the number of menu item");
        int itemNum = sc.nextInt();
        if (order.getOrderItems().size() == 0) {
            System.out.println("Empty order, nothing to delete!");
            return;
        } else if (itemNum < 1 || itemNum > order.getOrderItems().size()) {
            System.out.println("Invalid input, orderItem not removed");
            return;
        } else {
            order.removeOrderItemByIdx(itemNum - 1);
            System.out.println("Remove Successfully");
            order.displayOrder();
        }
    }

    /**
     * This method is to return the order of given orderID
     *
     * @param orderID specifies the ID of target order
     */
    public Order getOrderByID(int orderID) {
        for (Order i : orders) {
            if (i.getOrderID() == orderID) return i;
        }
        return null;
    }

    /**
     * This method is to display all orders in the order list
     */
    public void displayAllOrders() {
        if (orders.size() == 0) {
            System.out.println("No orders found");
            return;
        }
        for (Order order : orders) {
            order.displayOrder();
        }
    }
    /**
     * This method is to inactivate the given order
     * When an order is inactivated, it no longer can be added in or removed the order item
     * @param order the order need to be inactivated
     */
    public void inactivateOrder(Order order){
        order.setIsActive(false);
        save(dir,orders);
    }

    /**
     * This method is to save current orders to external files.
     * @param filename
     *          specifies where the data to be stored
     * @param al
     *          specifies the list to be saved to the file
     */
    @Override
    // text file for orders:
    //  orderId|staffId|tableId|numOfPax|orderSize|menuItemId|name|quantity|menuItemId|name|quantity|....
    public void save(String filename, List al) {
        List alw = new ArrayList();  //to store data

        for (int i = 0; i < al.size(); i++) {
            Order order = (Order) al.get(i);
            StringBuilder st = new StringBuilder();

            st.append(order.getOrderID());
            st.append("|");
            st.append(order.getStaffID());
            st.append("|");
            st.append(order.getTableId());
            st.append("|");
            st.append(order.getPax());
            st.append("|");
            st.append(order.getOrderItems().size());
            st.append("|");
            st.append(order.getIsActive());

            ArrayList<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem item : orderItems) {
                st.append("|");
                st.append(item.getMenuItemID());
                st.append("|");
                st.append(item.getName());        //need name?
                st.append("|");
                st.append(item.getQuantity());
                st.append("|");
                st.append(item.getPrice());
            }

            alw.add(st.toString());
        }
        write(filename, alw);
    }

    /**
     * This method is to load orders from external files
     * @param filename
     *            specifies where the external files stored
     * @return all reservations read from the file
     */
    @Override
    public ArrayList load(String filename) {
        ArrayList stringArray = (ArrayList) read(filename);
        ArrayList alr = new ArrayList();

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, "|");

            int orderId = Integer.parseInt(star.nextToken().trim());
            int staffId = Integer.parseInt(star.nextToken().trim());
            int tableId = Integer.parseInt(star.nextToken().trim());
            int numberOfPax = Integer.parseInt(star.nextToken().trim());
            int orderSize = Integer.parseInt(star.nextToken().trim());  // write the orderSize in the file in order to read different size of order items
            boolean isActive = Boolean.parseBoolean(star.nextToken().trim());

            //create order with no order item
            Order order = new Order(orderId, staffId, tableId, numberOfPax, isActive);
            //add order item in order
            for (int j = 0; j < orderSize; j++) {
                int itemId = Integer.parseInt(star.nextToken().trim());
                String name = star.nextToken().trim();
                int quantity = Integer.parseInt(star.nextToken().trim());
                double price = Double.parseDouble(star.nextToken().trim());
                order.addOrderItem(itemId, quantity, name, price);
            }
            //add order to order list
            alr.add(order);
        }
        return alr;

    }
}