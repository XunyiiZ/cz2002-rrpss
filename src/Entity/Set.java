package Entity;



import java.util.ArrayList;

public class Set extends MenuItem{

    private ArrayList<AlaCarte> item;
    private static double SET_DISCOUNT = 0.95;


    public Set(String name, String description, double price)
    {
        super(name, description,price);
    }

//    public void addAlaCarte(MenuItem fooditem1, MenuItem fooditem2, MenuItem fooditem3)
//    {
//        item.add((AlaCarte) fooditem1);
//        item.add((AlaCarte) fooditem2);
//        item.add((AlaCarte) fooditem3);
//    }


    /** new one */
    public void addAlaCarte(AlaCarte newItem){
        item.add(newItem);                             // after add a new item in the set, the price of the set will change
        super.setPrice(setSetPrice());
    }

    private double setSetPrice(){
        double total =0.0;
        for(AlaCarte alaCarte : item){
            total += alaCarte.getPrice();
        }
        return total*SET_DISCOUNT;
    }

}