package Entity;



import java.util.ArrayList;

public class Set extends MenuItem{

    ArrayList<AlaCarte> item;

    public Set(String name, String description, double price)
    {
        super(name, description,price);
    }

    public void addAlaCarte(MenuItem fooditem1, MenuItem fooditem2, MenuItem fooditem3)
    {
        item.add((AlaCarte) fooditem1);
        item.add((AlaCarte) fooditem2);
        item.add((AlaCarte) fooditem3);
    }


}