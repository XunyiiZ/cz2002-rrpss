package Entity;

import java.util.ArrayList;

public class Set extends MenuItem{

    private ArrayList<AlaCarte> itemList;
    private static double SET_DISCOUNT = 0.95;


    public Set(int id, String name, String description, double price)
    {
        super(id, name, description, price);
        itemList = new ArrayList<AlaCarte>();
    }

//    public void addAlaCarte(MenuItem fooditem1, MenuItem fooditem2, MenuItem fooditem3)
//    {
//        item.add((AlaCarte) fooditem1);
//        item.add((AlaCarte) fooditem2);
//        item.add((AlaCarte) fooditem3);
//    }


    /** new one */
    public void addAlaCarte(AlaCarte newItem){
        itemList.add(newItem);                             // after add a new item in the set, the price of the set will change
        super.setPrice(setSetPrice());
    }

    // public void removeAlaCarte(int id){
    //     itemList
    //     itemList.remove(item);
    // }

    public double setSetPrice(){
        double total = 0.0;
        for(AlaCarte alaCarte :itemList){
            total += alaCarte.getPrice();
        }
        
        if (itemList.size() > 1)
            return total*SET_DISCOUNT;
        else 
            return total;
    }

    public ArrayList<AlaCarte> getItemList(){
        return itemList;
    }

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

    public void displayItemsInSet(){
        int count = 1;
        for (MenuItem item: itemList){
            System.out.println("============== AlaCarte Item " + count++ + " ==============");
            System.out.println(item.toString());
        }
    }

}