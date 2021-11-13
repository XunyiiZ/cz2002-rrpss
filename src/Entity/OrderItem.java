package Entity;

import java.awt.*;

public class OrderItem {

    //public int OrderItemID; //added to identify for addOrderItem and removeOrderItem
    private int menuItemId;
 //   private int orderItemId;
    private String name;
//    private String description;
    private double price;

    private int quantity;
    //String name, String description, double price
    public OrderItem(int MenuItemID , int quantity, String name, double price){   // the price should be after multiplied with quantity
        this.menuItemId = MenuItemID;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public int getMenuItemID(){
        return menuItemId;
    }

    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }

    public double getPrice(){
        return price;
    }

    public String toString(){
        return  menuItemId + " name: " + name + " price: " + String.format(".2f",price);
    }

}