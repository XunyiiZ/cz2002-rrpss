package Entity;

public class AlaCarte extends MenuItem{

    private Category type;

    public enum Category {
        MAINCOURSE, DRINKS, SIDES
    }

    public AlaCarte(String name, String description, double price, Category type) {
        super(name, description, price);
        this.type = type;
    }
}
