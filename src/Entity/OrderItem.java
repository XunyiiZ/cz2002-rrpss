package Entity;
public class OrderItem extends MenuItem{

    private MenuItem item;
    private String name;
    private String description;
    private double price;
    private int quantity;

    public OrderItem(MenuItem item , int quantity){
        super(item.getName(), item.getDescription(), item.getPrice());
        //this.OrderItemID = OrderID;
        this.quantity = quantity;
    }

    public MenuItem getItem(){
        return item;
    }
    public int getQuantity(){
        return quantity;
    }

    public double getPrice(){
        return price;
    }

}
