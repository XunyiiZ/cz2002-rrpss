package Entity;
public class OrderItem{

    //public int OrderItemID; //added to identify for addOrderItem and removeOrderItem
    private int MenuItemID;
    private String name;
    private String description;
    private double price;

    private int quantity;
    //String name, String description, double price

    public OrderItem(int MenuItemID , int quantity){
        this.MenuItemID = MenuItemID;
        this.quantity = quantity;
    }

    public int getMenuItemID(){
        return MenuItemID;
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

}
