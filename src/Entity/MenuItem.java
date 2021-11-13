package Entity;
public class MenuItem {

    private int menuItemId;
    private String name;
    private String description;
    private double price;

    public MenuItem(int menuItemId, String name, String description, double price) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.description = description;
        this.price = Math.round(price*10.0)/10.0;
    }

    public int getMenuItemId(){
        return menuItemId;
    }

    public double getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public void setPrice(double price){
        this.price = Math.round(price*10.0)/10.0;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String toString(){
        String statement = String.format("Name: %s \nDescription %s \nPrice: %.1f0 \n", name, description, price);
        return statement;
    }
}
