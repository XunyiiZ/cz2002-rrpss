package Entity;

public class Member {
    private String name;
    private String contact;

    public Member(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }


    public String getName() {
        return name;
    }


    public String getContact() {
        return contact;
    }

    public String toString() {
        return "Name: " + name + " Contact: " + contact;
    }

}
