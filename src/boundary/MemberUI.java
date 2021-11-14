package boundary;
import controller.*;

/**
* Boundary class for the Member User Interface that allows staff to retrieve and access member particulars
* @author Timothy
* @version 1.0
* @since 2021-11-13
*/

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This boundary class provides users with access to various functionalities of
 * member management.
 *
 * @author
 * @version 1.0
 * @Date 2021-11
 */
public class MemberUI {
    private static MemberUI memberUI = null;
    private MemberController memberController = MemberController.getInstance();
    private static Scanner sc = new Scanner(System.in);

    private MemberUI() {};

    /**
    * Method that creates an MemberUI object if it doesn't exist. If it exists, it returns the object reference of the current MemberUI
    * @return The instance of the memberUI object
    */
    public static MemberUI getInstance(){
        if (memberUI == null) {
            memberUI = new MemberUI();
        }
        return memberUI;
    }

    /**
     * Display options for users to access control of member details
     */
    public void run() {
        int choice = displayOptions();
        while (choice != 0) {
            switch (choice) {
                case 1:
                    createMember();
                    break;
                case 2:
                    checkForMembership();
                    break;
                case 3:
                    removeMember();
                    break;
                case 4:
                    displayAllMembers();
                    break;
                case 5:
                    changeDiscountRate();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            choice = this.displayOptions();
        }
    }

    /**
    * Method to display options available for interface
    * @return the integer choice of method to call
    */
    private int displayOptions() {
        System.out.println("--------Member System--------");
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create a new member");
        System.out.println("2. Check for membership");
        System.out.println("3. Remove member");
        System.out.println("4. Get member list");
        System.out.println("5. Change discount rate");
        int choice;        
        while (true) {
            try {      
                System.out.println("Your choice: ");                      
                choice = sc.nextInt(); 
                sc.nextLine();   
                        
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input");
                return displayOptions();
            }
            break;
        }
        return choice;
    }
    /**
     * Creates a new member in the member list.
     */
    private void createMember(){
        String name;
        String contact;
        System.out.println("Enter member name: ");
        name = sc.nextLine();
        while (true)
        {
            try {
                System.out.println("Enter member contact no.: ");
                contact = sc.nextLine();
                if (contact.length() != 8)
                    throw new Exception("Invalid contact number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        memberController.createMember(name, contact);
    }
    /**
     * check for membership of the given contact
     */
    private void checkForMembership(){
        String contact;
        while (true)
        {
            try {
                System.out.println("Enter member contact no.: ");
                contact = sc.nextLine();
                if (contact.length() != 8)
                    throw new Exception("Invalid contact number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        if (memberController.checkIsMember(contact)) {
            System.out.println("Customer is a member");
        }
        else {
            System.out.println("Customer is not a member");
        }
    }
    /**
     * Remove the member from the member list
     */
    private void removeMember(){
        String contact;
        while (true)
        {
            try {
                System.out.println("Enter member contact no.: ");
                contact = sc.nextLine();
                if (contact.length() != 8)
                    throw new Exception("Invalid contact number!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        memberController.removeMember(contact);
    }
    /**
     * display all members in the member list
     */
    private void displayAllMembers(){
        memberController.displayAllMembers();
    }
    /**
     * change member discount rate
     */
    private void changeDiscountRate(){
        System.out.println("Enter new member discount rate: ");
        double rate = sc.nextDouble();
        if (rate > 1)
            memberController.setDiscountRate(rate/100);
        else{
            memberController.setDiscountRate(rate);
        }
        System.out.println("New discount rate set at: " + memberController.getDiscountRate());
    }

}
