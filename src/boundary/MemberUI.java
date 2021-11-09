package boundary;
import controller.*;

import java.io.IOException;
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
        int choice = displayOptions();
        while (choice != 0) {
            switch (choice) {
                case 1:
                    System.out.println("Enter member name: ");
                    String n = sc.nextLine();
                    System.out.println("Enter member contact no.: ");
                    String c = sc.nextLine();
                    memberController.createMember(n, c);

                    break;

                case 2:
                    System.out.println("Enter member's contact no.: ");
                    String n2 = sc.nextLine();
                    if (memberController.checkIsMember(n2)) {
                        System.out.println("Customer is a member");
                    }
                    else {
                        System.out.println("Customer is not a member");
                    }

                    break;

                case 3:
                    System.out.println("Enter member's contact no.: ");
                    String n3 = sc.nextLine();
                    memberController.removeMember(n3);

                    break;

                case 4:
//                    for (Member m : memberController.getAllMembers()) {
//                        System.out.println(m.toString());
//                    }
//                    break;
                    memberController.displayAllMembers();
                    break;

                default:
                    break;
            }
            choice = this.displayOptions();
        }
    }

    private int displayOptions() {
        System.out.println("0. Go back to MainUI");
        System.out.println("1. Create a new member");
        System.out.println("2. Check for membership");
        System.out.println("3. Remove member");
        System.out.println("4. Get member list");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

}
