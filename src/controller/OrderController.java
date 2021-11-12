package controller;

import java.io.IOException;
import Entity.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class OrderController extends AbstractController {

    private static ArrayList<Order> orders;
    private static OrderController orderController = null;
    private TableController tableController = TableController.getInstance();
    private MenuController menuController = MenuController.getInstance();
    private ReservationController resController = ReservationController.getInstance();
    private static int numOfOrders = 0;
    private int numOfPax = 0;
    private static Scanner sc = new Scanner(System.in);
    private static int orderID = 0;
    private static final String dir = "src/data/order.txt";

    public OrderController() throws IOException {
        /** using text method */
        File file = new File(dir);
        if (file.exists()) {
            orders = load(dir);
        } else {
            file.getParentFile().mkdir();
            file.createNewFile();
            orders = new ArrayList<Order>();
            save(dir, orders);
        }
    }

    public static OrderController getInstance() throws IOException {
        if (orderController == null) {
            orderController = new OrderController();
        }
        return orderController;
    }

    public void showOrderItems(int id) {
        for (Order i : orders) {
            if (i.getOrderID() == id) {
                i.getOrderItems();
            }
            break;
        }
    }

    public void createOrder() throws IOException {
        int resID = -1;
        int staffID = -1;
        int tabID = -1;
        //1. Reservation
        System.out.println("Enter Staff ID: ");
        staffID = sc.nextInt();
        sc.nextLine();
        System.out.println("check in for a reservation? Y/N");
        char response = sc.nextLine().charAt(0);
        response = Character.toUpperCase(response);

        while(response != 'Y' && response !='N'){
            System.out.println("invalid input. please enter Y or N:");
            response = sc.nextLine().charAt(0);
            response = Character.toUpperCase(response);
        }
 //       System.out.println("having input " + response);

        if (response == 'Y') {
            System.out.println("check reservation");
            int count = 2; //check for correct reservation id, check 2x if invalid then break
            while (count < 0) {
                System.out.println("Enter reservation ID: ");
                resID = sc.nextInt();
                sc.nextLine();
                Reservation reservation = resController.getReservationById(resID); //Reservation reservation = getReservationById(resID); --> if null, then ask again for reservation id, if null 2x then break out loop
                if (reservation == null) {
                    System.out.println("Reservation not found. Please try again.");
                } else {
                    tabID = reservation.getTableId();
                    resController.removeReservationById(resID);  // remove his reservation
                    break;
                }
                count--;
            }
            if (count < 0){
                System.out.println("Creating an order without a reservation");
                response = 'N';
            }
        }

        if (response == 'N') {
 //           System.out.println("no reservation ");
            //call tablecontroller and show unoccupied unreserved tables
            System.out.println("Enter no. of pax: ");
            numOfPax = sc.nextInt();
            sc.nextLine();
            int tablePax = tableController.getTablePax(numOfPax);
            ArrayList<Integer> availableTables = getAvailableTable(tablePax);
            if (availableTables.size() == 0) {
                System.out.println("There is no available table now");
            } else {
                System.out.println("Available table:" + availableTables.toString());
            }

            System.out.println("Assign the Table id:");
            tabID = sc.nextInt();
            sc.nextLine();
            do {
                try {
                    if (!availableTables.contains(tabID)) {
                        throw new Exception("Invalid table number!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Enter the table id to reserve the table");
                    tabID = sc.nextInt();
                    continue;
                }
                break;
            } while (true);

        }


        //orderID
        ArrayList<Order> orders = orderController.getAllOrders();
        if(orders.size()==0) orderID =1;
        else{ orderID = orders.get(orders.size()-1).getOrderID()+1;}

        Order order = new Order(staffID, orderID, tabID, numOfPax);
        orders.add(order);
        System.out.println("the table id is " + tabID);
        tableController.setOccupied(tabID);

        //while loop
        System.out.println("1. Add item \n2.Remove Item \n3. Dispaly all items \n4. Finish");
        int choice = sc.nextInt(); sc.nextLine();
        while (true) {
            if (choice > 4 || choice < 1)
            {
                System.out.println("invalid input! \n1. Add item \n2. Remove Item \n3. Dispaly all items \n4. Finish");
                choice = sc.nextInt(); sc.nextLine();
            }
            else {
                switch (choice) {
                    case 1:
                        addItemToOrder(order);
                        break;
                    case 2:
                        removeItemFromOrder(order);
                        System.out.println("Removed successfully");
                        break;
                    case 3:
                        order.displayAllItems();
                        break;
                    case 4:
                        save(dir,orders);
                        break;
                }
            }
            if (choice == 4) break;
            System.out.println("1. Add item \n2.Remove Item \n3. Dispaly all items \n4. Finish");
            choice = sc.nextInt(); sc.nextLine();
        }

        //no option to remove order item while creating order
        System.out.println("Your order has been created.");
        order.displayOrder();
        orders.add(order);
        save(dir,orders);

    }//        do{
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



    //menu item id should be unique and not neccessarily in sequence
    //menu item id is only used in text file to trace the menu item
    //when display menu item can use index

    public void addItemToOrder(Order order) throws IOException {
        menuController.displayMenu();  // need to display the index
        System.out.println("Enter the menu item number that you like to add?");
        int itemNum = sc.nextInt();
        sc.nextLine();
//        if (!menuController.isValidMenuItemId(itemId)) {
//            System.out.println("Invalid input, add item unsuccessfully");
//            return;
//        }

        if(itemNum<1 || itemNum > menuController.getMenuList().size()){
            System.out.println("invalid index!");
            return;
        }
        else {
            MenuItem item = menuController.getItemByIndex(itemNum-1);
            int itemId = item.getMenuItemId();
            System.out.println(item.toString());
            System.out.println("Enter quantity:");
            int quantity = sc.nextInt();
            order.addOrderItem(itemId, quantity, item.getName(), item.getPrice()*quantity);

            System.out.println("Item added successfully");
        }
    }

    public ArrayList<Order> getAllOrders(){
        return orders;
    }

    public void removeItemFromOrder(Order order) throws IOException {
        order.displayAllItems();
        System.out.println("Enter the number of menu item");
        int itemNum = sc.nextInt();
        if (order.getOrderItems().size() == 0) {
            System.out.println("Empty order, nothing to delete!");
            return;
        } else if (itemNum < 1 || itemNum > order.getOrderItems().size()) {
            System.out.println("Invalid input, remove item unsuccessfully");
            return;
        } else {
            order.removeOrderItemByIdx(itemNum-1);
            save(dir,orders);
            System.out.println("Remove Successfully");
        }
    }

    public void removeOrder(int OrderID) throws IOException {
        orders.removeIf(order -> order.getOrderID() == OrderID);
        save(dir,orders);
    }

    public Order getOrderByID(int OrderID) {
        for (Order i : orders) {
            if (i.getOrderID() == OrderID) return i;
        }
        System.out.println("Order not found.");
        return null;
    }


    private ArrayList<Integer> getAvailableTable(int tablePax) throws IOException {
        ArrayList<Integer> tableList = tableController.getTableByTablePax(tablePax);
        tableList.remove(tableController.getCurrentReservedTable(tablePax));
        return tableList;
    }

    public void displayAllOrders() {
        if(orders.size() == 0){
            System.out.println("no orders found");
            return;
        }
        for (Order order : orders) {
            order.displayOrder();
        }
    }

    /**
     * save method
     * save method will be different with different controlelr
     */

    // text file for orders:
    //  orderId|staffId|tableId|numOfPax|orderSize|menuItemId|name|quantity|menuItemId|name|quantity|....

    public void save(String filename, List al) throws IOException {
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


    public ArrayList load(String filename) throws IOException {
        ArrayList stringArray = (ArrayList) read(filename);
        ArrayList alr = new ArrayList();  // to store invoices data

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, "|");

            int orderId = Integer.parseInt(star.nextToken().trim());
            int staffId = Integer.parseInt(star.nextToken().trim());
            int tableId = Integer.parseInt(star.nextToken().trim());
            int numberOfPax = Integer.parseInt(star.nextToken().trim());
            int orderSize = Integer.parseInt(star.nextToken().trim());  // write the orderSize in the file in order to read different size of order items

            //create order with no order item
            Order order = new Order(staffId,orderId,tableId,numberOfPax);
            //add order item in order
            for(int j=0; j<orderSize; j++){
                int itemId = Integer.parseInt(star.nextToken().trim());
                String name = star.nextToken().trim();
                int quantity = Integer.parseInt(star.nextToken().trim());
                double price = Double.parseDouble(star.nextToken().trim());
                order.addOrderItem(itemId,quantity,name,price);
            }
            //add order to order list
            alr.add(orders);
        }
        return alr;
    }
}
