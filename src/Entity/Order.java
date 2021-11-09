package Entity;

import java.util.ArrayList;
import java.util.Scanner;

public class Order {

    private static int order_ID;
    private static int staffID;
    private static int tableId;
    //private OrderItem[] orderItems;
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
            //int itemQuantity = i.getQuantity();

            System.out.println("Name: "+itemName);
        }
    }

    public static void addOrderItem(int index){
        if (orderSize>=10) System.out.println("Maximum Order Size reached. Unable to add more Order Items");
        else{
            System.out.println("Enter the ID of the item: ");

            String itemName = sc.next(); //which to use?
            int itemID = sc.nextInt();

            OrderItem item = new OrderItem(); //which to input?
            orderItems.add(item);
            orderSize++;
        }
        return;
    }

    public void removeOrderItem(int index){
        for(OrderItem i: orderItems){
            if(i.getOrder_ID == index){target = i.getOrder_ID} //currently orderitem id doesn't exist, should we add?
        }
        int target = -1;
        if(target == -1) System.out.println("This item is not in the order.");
        else{
            for(int i=target;i<orderSize-1;i++){
                orderItems.set(i, orderItems.get(i + 1));
            }
            orderSize--;
        }
    }

    public double getOrderPrice(){
        double price1 = 0.0;
        for(int i=0;i<orderSize;i++){
            price1 += orderItems.get(i).getPrice();
        }
        return price1;
    }

    public void printOrders(){
        System.out.println("Item           Quantity            Price\n");
        for(int i=0;i<orderSize;i++){
            System.out.println(orderItems.get(i).getName()+"          "+ orderItems.get(i).getQuantity()+"          "+ orderItems.get(i).getPrice()+ "\n");
        }
    }

}
