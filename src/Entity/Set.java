package Entity;

/** Entity class that contains the attributes, setter and getter methods of a Set which is a menu item. A Set also consists of at least 2 AlaCarte items
 * @author YiXuan 
 */

import java.util.ArrayList;

public class Set extends MenuItem{

    private ArrayList<AlaCarte> itemList;
    private static double SET_DISCOUNT = 0.95;

    /**
     * Constructor to initialises a Set with the details of the parameters entered 
     * @param id id the menu item
     * @param name name of the set 
     * @param description description of the set
     * @param price default price entered will be 0 (this is because the price of the set is generated from the price of )
     */
    public Set(int id, String name, String description, double price)
    {
        super(id, name, description, price);
        itemList = new ArrayList<AlaCarte>();
    }

    /**
     * Method that allows us to add an AlaCarte item into a set
     * @param newItem the AlaCarte item to be added into the set
     */
    public void addAlaCarte(AlaCarte newItem){
        itemList.add(newItem);  // after add a new item in the set, the price of the set will change
        super.setPrice(setSetPrice());
    }

    /**
     * Method that allows us to set the price of the Set. The price of the set is derived from the sum of the prices of its AlaCarte items multiplied by the set discount
     * @return the price of the set
     */
    public double setSetPrice(){
        double total = 0.0;
        for(AlaCarte alaCarte :itemList){
            total += alaCarte.getPrice();
        }
        
        if (itemList.size() > 1)
            return Math.round(total*SET_DISCOUNT*10.0)/10.0;
        else 
            return Math.round(total*10.0)/10.0;
    }

    /**
     * Method that returns the array list object reference of the items in the set 
     * @return
     */
    public ArrayList<AlaCarte> getItemList(){
        return itemList;
    }

    /**
     * Method to remove an AlaCarte item from the set
     * @param itemIndex the index of the AlaCarte item to be removed from the array list 
     */
    public void deleteFromSet(int itemIndex){
        if(itemList.size() == 0){
            System.out.println("No more Ala Carte to delete!");
        }
        else{
            while(true){
                try{
                    itemList.remove(itemIndex);
                    super.setPrice(setSetPrice());
                    return;
                }catch(IndexOutOfBoundsException e){
                    System.out.println("Invalid ID please input again!");
                    return;
                }
            }
        }
    }   

    /**
     * Method that prints the attributes of the AlaCarte items in the set
     */
    public void displayItemsInSet(){
        int count = 1;
        for (MenuItem item: itemList){
            System.out.println("============== AlaCarte Item " + count++ + " ==============");
            System.out.println(item.toString());
        }
    }

}