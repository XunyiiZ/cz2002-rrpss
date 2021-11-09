package Entity;

public class OrderItem extends MenuItem{

    public int OrderID; //added to identify for addOrderItem and removeOrderItem
    public MenuItem item;
    public String name;
    public String description;
    public double price;

    private int quantity;
    //String name, String description, double price
    public OrderItem(int OrderID, MenuItem item , int quantity){
        super(item.name, item.description, item.price);
        this.OrderID = OrderID;
        this.quantity = quantity;
    }

    public MenuItem getItem(){
        return item;
    }
    public int getQuantity(){
        return quantity;
    }

}
