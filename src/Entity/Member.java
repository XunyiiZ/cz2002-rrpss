package Entity;

/** Entity class that contains the attributes, setter and getter methods of a Menu Item
 * @author YiXuan 
 */

public class Member {
    private String name;
    private String contact;

    /**
     * Constructor of Member which initialises a member object with the details of the parameters entered
     * @param name name of the member
     * @param contact contact number of the member
     */
    public Member(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    /**
     * Method to get the name of the member
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the contact number of a member
     * @return
     */
    public String getContact() {
        return contact;
    }

    /**
     * Method that returns a printed statement of the details of a Member
     */
    public String toString() {
        return "Name: " + name + " Contact: " + contact;
    }

}
