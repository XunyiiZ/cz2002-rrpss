package Entity;
/** Entity class that contains the attributes, setter and getter methods of an AlaCarte which is a menu item 
 * @author YiXuan 
 */

public class AlaCarte extends MenuItem{

    private Category type;

    /**
     * Enum with 3 elements to idetify the type of the AlaCarte menu item
     */
    public enum Category {
        MAINCOURSE, DRINKS, SIDES
    }

    /**
     * Constructor to initialises an AlaCarte with the details of the parameters entered 
     * @param id id of menu item
     * @param name name of AlaCarte item
     * @param description description of the AlaCarte item
     * @param price price of the AlaCarte item
     * @param type type of the AlaCarte item
     */
    public AlaCarte(int id, String name, String description, double price, Category type) {
        super(id, name, description, price);
        this.type = type;
    }

    /**
     * Method that returns the string data type of the enum category type 
     * @return
     */
    public String getStringType(){
        if (type == Category.MAINCOURSE)
            return "maincourse";
        else if (type == Category.DRINKS)
            return "drinks";
        else
            return "sides";
    }
}
