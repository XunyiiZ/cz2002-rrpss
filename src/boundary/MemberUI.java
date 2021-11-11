package boundary;
import controller.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MemberUI {
    private static MemberUI memberUI = null;
    private MemberController memberController = MemberController.getInstance();
    private static Scanner sc = new Scanner(System.in);

    private MemberUI() throws IOException {};

    public static MemberUI getInstance() throws IOException {
        if (memberUI == null) {
            memberUI = new MemberUI();
        }
        return memberUI;
    }

    public void run() {
        String name;
        String contact;

        int choice = displayOptions();
        while (choice != 0) {
            switch (choice) {
                case 1:
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

                    break;

                case 2:
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

                    break;

                case 3:
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

                    break;

                case 4:
//                    for (Member m : memberController.getAllMembers()) {
//                        System.out.println(m.toString());
//                    }
//                    break;
                    memberController.displayAllMembers();
                    break;
                case 5:
                    System.out.println("Enter new member discount rate: ");
                    double rate = sc.nextDouble();
                    if (rate > 1)
                        memberController.setDiscountRate(rate/100);
                    else{
                        memberController.setDiscountRate(rate);
                    }
                    System.out.println("New discount rate set at: " + memberController.getDiscountRate());
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            choice = this.displayOptions();
        }
    }

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

}
