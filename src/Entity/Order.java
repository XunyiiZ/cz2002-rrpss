package Entity;

import java.util.ArrayList;
import java.util.Scanner;

public class Order {

    private static int order_ID;
    private static int staffID;
    private static int tableId;
    private static ArrayList <OrderItem> orderItems;
    private static int orderSize;

    static Scanner sc = new Scanner(System.in);

    public Order(int staffID, int order_ID, int tableId){
        this.order_ID = order_ID;
        this.tableId = tableId;
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        this.staffID = staffID;
    }

    public int getTableId(){
        return tableId;
    }

    public int getOrder_ID(){
        return order_ID;
    }

    public void getOrderItems(){
        for(OrderItem i: orderItems){
            String itemName = i.getName();
            //String itemDesc = i.getDescription();
            //double itemPrice = i.getPrice();
            int itemQuantity = i.getQuantity();

            System.out.println("Name: "+itemName +", Quantity: "+ itemQuantity);
        }
    }

    public static void addOrderItem(int index, int quantity){ //quantity
        if (orderSize>=10) System.out.println("Maximum Order Size reached. Unable to add more Order Items");
        else{
            System.out.println("Enter the ID of the item: ");
            int itemID = sc.nextInt();
        
            System.out.println("Enter Quantity: ");
            int quant = sc.nextInt();

            OrderItem item = new OrderItem(itemID,quant); //which to input?
            orderItems.add(item);
            orderSize++;
        }
        return;
    }

    public void removeOrderItem(int index){
        orderItems.remove(index);
    }

    public double getOrderPrice(){
        double totalPrice = 0.0;
        for(int i=0;i<orderSize;i++){
            totalPrice += orderItems.get(i).getPrice();
        }
        return totalPrice;
    }

    public void printOrders(){
        System.out.println("Item           Quantity            Price\n");
        for(int i=0;i<orderSize;i++){
            System.out.println(orderItems.get(i).getName()+"          "+ orderItems.get(i).getQuantity()+"          "+ orderItems.get(i).getPrice()+ "\n");
        }
    }


}
