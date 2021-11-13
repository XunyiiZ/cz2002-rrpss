package Entity;

/** Entity class that contains the attributes, setter and getter methods of a Menu Item
 * @author YiXuan 
 */

public class MenuItem {

    private int menuItemId;
    private String name;
    private String description;
    private double price;

    /**
     * Constructor of Menu Item which initialises a menu items with the details of the parameters entered 
     * @param menuItemId menu item unique id
     * @param name name of the menu item
     * @param description short description of what the menu item is 
     * @param price price of the menu item
     */
    public MenuItem(int menuItemId, String name, String description, double price) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.description = description;
        this.price = Math.round(price*10.0)/10.0;
    }

    /**
     * Method to get a menu item id 
     * @return menu item id
     */
    public int getMenuItemId(){
        return menuItemId;
    }

    /**
     * Method to get the price of a menu item
     * @return
     */
    public double getPrice(){
        return price;
    }

    /**
     * Method that allows us to get the name of the menu item 
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Method to get the description of a menu item
     * @return
     */
    public String getDescription(){
        return description;
    }

    /**
     * Method that allows us to change the price of a menu item
     * @param price price that the staff would like the menu item to be changed to 
     */
    public void setPrice(double price){
        this.price = Math.round(price*10.0)/10.0;
    }

    /**
     * Method to change the name of the menu item
     * @param name new name of the menu item 
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method to change the description of the menu item   
     * @param description new description of the menu item
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Method that returns a printed statement of the attributes of a menu item 
     */
    public String toString(){
        String statement = String.format("Name: %s \nDescription %s \nPrice: %.1f0 \n", name, description, price);
        return statement;
    }
}
