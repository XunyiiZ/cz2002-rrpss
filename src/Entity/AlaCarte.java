package Entity;

public class AlaCarte extends MenuItem{

    Category type;

    public enum Category {
        MAINCOURSE, DRINKS, DESSERTS
    }

    public AlaCarte(String name, String description, double price, Category type) {
        super(name, description, price);
        this.type = type;
    }
}
