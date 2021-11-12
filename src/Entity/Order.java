package Entity;


import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Order {

    private static int orderID;
    private static int staffID;
    private static int tableId;
    private static int pax;
    private static ArrayList <OrderItem> orderItems = new ArrayList<>();
//    private static int orderSize;

    static Scanner sc = new Scanner(System.in);

    public Order(int staffID, int orderID, int tableId, int pax){
        this.orderID = orderID;
        this.tableId = tableId;
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        this.staffID = staffID;
        this.pax=pax;
    }

    public int getTableId(){
        return tableId;
    }

    public int getOrderID(){
        return orderID;
    }

    public int getPax(){return pax;}

    public int getStaffID(){return staffID;}

//    public void getOrderItems(){
//        for(OrderItem i: orderItems){
//            String itemName = i.getName();
//            //String itemDesc = i.getDescription();
//            //double itemPrice = i.getPrice();
//            int itemQuantity = i.getQuantity();
//
//            System.out.println("Name: "+itemName +", Quantity: "+itemQuantity);
//        }
//    }
    public ArrayList<OrderItem> getOrderItems(){
        return orderItems;
    }

//    public static void addOrderItem(int index, int quantity){ //quantity
//        if (orderSize>=10) System.out.println("Maximum Order Size reached. Unable to add more Order Items");
//        else{
//            System.out.println("Enter the ID of the item: ");
//            int itemID = sc.nextInt();
//            System.out.println("Enter Quantity: ");
//            int quant = sc.nextInt();
//
//            OrderItem item = new OrderItem(itemID,quant); //which to input?
//            orderItems.add(item);
//            orderSize++;
//        }
//        return;
//    }

    public static void addOrderItem(int menuItemId, int quantity, String name){ //quantity
        if (orderItems != null && orderItems.size()>=10) System.out.println("Maximum Order Size reached. Unable to add more Order Items");
        else{
            OrderItem item = new OrderItem(menuItemId,quantity,name); //which to input?
            orderItems.add(item);
        }
        return;
    }

    public void removeOrderItemByIdx(int index){

        orderItems.remove(index);

    }

    public double getOrderPrice(){
        double totalPrice = 0.0;
        for(int i=0;i<orderItems.size();i++){
            totalPrice += orderItems.get(i).getPrice();
        }
        return totalPrice;
    }

    public void displayAllItems(){
        System.out.println("Item           Quantity            Price\n");
        for(int i=0;i<orderItems.size();i++){
            System.out.println((i+1) + " " + orderItems.get(i).getName()+"          "+ orderItems.get(i).getQuantity()+"          "+ orderItems.get(i).getQuantity()*orderItems.get(i).getPrice()+ "\n");
        }
    }

    public void displayOrder(){
        System.out.println("order id " + orderID+
                "\nstaff id " + staffID+
                "\ntable id "+tableId+
                "\nnumber of pax " + pax +"\n");
        displayAllItems();

    }

}
