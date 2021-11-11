package Entity;

public class AlaCarte extends MenuItem{

    private Category type;

    public enum Category {
        MAINCOURSE, DRINKS, SIDES
    }

    public AlaCarte(int id, String name, String description, double price, Category type) {
        super(id, name, description, price);
        this.type = type;
    }

    public String getStringType(){
        if (type == Category.MAINCOURSE)
            return "maincourse";
        else if (type == Category.DRINKS)
            return "drinks";
        else
            return "sides";
    }
}
