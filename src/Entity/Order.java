package Entity;
import java.util.ArrayList;
 /**
 * This entity class defines Order Class
 *
 @author Zeng Xunyi
 @version 1.0
 @since 2021-11
 */
public class Order {
    /**
     * The order ID
     */
    private int orderID;
    /**
     * The staff ID who served this order
     */
    private int staffID;
    /**
     * The table ID of this order
     */
    private int tableId;
    /**
     * The number of customers
     */
    private int pax;
    /**
     * Status of the order. True specifies the order hasn't been paid
     */
    private boolean isActive;
    /**
     * all order items in this order
     */
    private ArrayList<OrderItem> orderItems;
    /**
     * This constructor initializes order id, staff ID, table ID, number of Pax and the payment status of the order
     *
     * @param orderID
     *            order ID
     * @param staffID
     *            staff's ID who serve this order
     * @param tableId
     *            the table ID
     * @param pax
     *            specifies number of customers
     * @param isActive
     *            specifies payment status of this order
     */
    public Order(int orderID, int staffID, int tableId, int pax, boolean isActive){
        this.orderID = orderID;
        this.tableId = tableId;
        orderItems = new ArrayList<OrderItem>();
        this.staffID = staffID;
        this.pax=pax;
        this.isActive = isActive;
    }
    /**
     * This is the accessor method of tableId field.
     *
     * @return table ID
     */
    public int getTableId(){
        return tableId;
    }
    /**
     * This is the accessor method of orderID field.
     *
     * @return order ID
     */
    public int getOrderID(){
        return orderID;
    }
    /**
     * This is the accessor method of pax field.
     *
     * @return number of customer of this order
     */
    public int getPax(){return pax;}
    /**
     * This is the accessor method of staffID field.
     *
     * @return staff's ID  who served this order
     */
    public int getStaffID(){return staffID;}
    /**
     * This is the accessor method of isActive field.
     *
     * @return payment status of this order. True means this order hasn't been paid
     */
    public boolean getIsActive() {return isActive;}
    /**
     * This is the accessor method of staffID field.
     *
     * @return staff's ID  who served this order
     */
    public void setIsActive(boolean isActive) {this.isActive = isActive;}

    /**
     * This is the mutator method of orderItems field.
     *
     * @return all order items in this order
     */
    public ArrayList<OrderItem> getOrderItems(){
        return orderItems;
    }
    /**
     * The method adds the order item to the order
     *
     * @param menuItemId
     *              the ID of this order in the menu item
     * @param name
     *              the name of this order item
     * @param price
     *              the price of this order item
     */
    public void addOrderItem(int menuItemId, int quantity, String name, double price){
        if (orderItems != null && orderItems.size()>=10) System.out.println("Maximum Order Size reached. Unable to add more Order Items");
        else{
            OrderItem item = new OrderItem(menuItemId,quantity,name,price); //which to input?
            orderItems.add(item);
        }
        return;
    }
    /**
     * The method removes the order item by corresponding index
     *
     * @param index the index of order item to be removed
     */
    public void removeOrderItemByIdx(int index){
        orderItems.remove(index);
    }
    /**
     * The method is to calculate the total price of the order
     *
     * @return order price
     */
    public double getOrderPrice(){
        double totalPrice = 0.0;
        for(int i=0;i<orderItems.size();i++){
            totalPrice += orderItems.get(i).getPrice();
        }
        return totalPrice;
    }
    /**
     * The method is to print out information of all order items in this order
     */
    public void displayAllItems(){
        System.out.println("   Item\t                                        Quantity    Price\n");
        for(int i=0;i<orderItems.size();i++){
            System.out.println((i+1) + " " + String.format("%-50s",orderItems.get(i).getName())+  String.format("%-5d",orderItems.get(i).getQuantity())+"   "+ String.format("%.2f",orderItems.get(i).getPrice()) );
        }
        System.out.println("-------------------------------------------");
    }
    /**
     * The method is to print out information about this order
     */
    public void displayOrder(){
        System.out.println("-------------------------------------------");
        System.out.println("Order ID " + orderID+
                "  Staff ID " + staffID+
                "  Table ID "+tableId+
                "  Number of pax " + pax +"\n");
        displayAllItems();
    }

    /**
     * The method is to return information about this order
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(OrderItem item : orderItems){
            sb.append("\n"+ String.format("%-50s",item.getName())  + String.format("%-5d",item.getQuantity() )+ "      " + String.format("%.2f", item.getPrice() ));
        }
        return sb.toString();
    }

}
