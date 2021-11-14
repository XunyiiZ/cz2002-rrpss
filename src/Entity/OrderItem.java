package Entity;
/**
 * This entity class defines Order item object
 *
 @author Zeng Xunyi
 @version 1.0
 @since 2021-11
 */
public class OrderItem {
    /**
     * The ID in menu of this order item
     */
    private int menuItemId;
    /**
     * The name of this order item
     */
    private String name;
    /**
     * The price of this order item
     */
    private double price;
    /**
     * The quantity of this order item
     */
    private int quantity;
    /**
     * This constructor initializes the order item.
     *
     * @param menuItemID
     *            id of this item in menu
     * @param quantity
     *            specifies the quantity of this order item
     * @param name
     *            specifies name of this order item
     * @param price
     *            specifies total price of this order item
     */
    public OrderItem(int menuItemID , int quantity, String name, double price){   // the price should be after multiplied with quantity
        this.menuItemId = menuItemID;
        this.quantity = quantity;
        this.name = name;
        this.price = Math.round(price*10.0)/10.0;
    }
    /**
     * This is the accessor method of menuItemId field.
     *
     * @return id of this order item in menu
     */
    public int getMenuItemID(){
        return menuItemId;
    }
    /**
     * This is the accessor method of name field.
     *
     * @return order item name
     */
    public String getName(){
        return name;
    }
    /**
     * This is the accessor method of quantity field.
     *
     * @return quantity of order item
     */
    public int getQuantity(){
        return quantity;
    }
    /**
     * This is the accessor method of price field.
     *
     * @return total price of this order
     */
    public double getPrice(){
        return price;
    }
    /**
     * The method is to return the information about this reservation in string format
     */
    @Override
    public String toString(){
        return  menuItemId + " name: " + name + " price: " + String.format(".2f",price);
    }

}