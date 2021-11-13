package controller;

import java.io.*;
import java.util.*;

import Entity.*;

public class MemberController extends AbstractController{
    /**
     * An ArrayList of members holding the member instances to mimic the behavior of a database
     * Each query to this list is equivalent to a query to a database
     */
    private ArrayList<Member> members;
    private static MemberController memberController = null;
    private double discountRate = 0.95;
    private static final String dir = "src/data/member.txt";


    public static MemberController getInstance() throws IOException {
        if (memberController == null) {
            memberController = new MemberController();
        }
        return memberController;
    }

    public MemberController() throws IOException {
        /** using text method */
        File file = new File(dir);
        if (file.exists()) {
            members = load(dir);
        } else {
            file.getParentFile().mkdir();
            file.createNewFile();
            members = new ArrayList<Member>();
            save(dir, members);
        }

//      invoices = new ArrayList<>(); // need to have file
//      memberController = MemberController.getInstance();
    }

    /**
     * Create a new member and add into the member list
     *  @param name the name of the new member
     *  @param contact the contact of the new member
     */
    public void createMember(String name, String contact) {
        try {
            if (contact.length() != 8)
                throw new Exception("Invalid contact number!");
            if(checkIsMember(contact)){
                System.out.println("the memmber already existed");
                return;
            }
            Member m = new Member(name, contact);
            members.add(m);
            save(dir,members);
            System.out.println("New member created:\n" + m.toString());
        } catch (Exception e) {
            System.out.print("Member not created: ");
            System.out.println(e.getMessage());
        }

    }


    public boolean checkIsMember(String contact) {
        for (Member m : members) {
            if (m.getContact().contains(contact)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete the member with the given contact
     *
     * @param contact the contact of the member to be deleted
     */
    public void removeMember(String contact) {
        try {
            if (contact.length() != 8)
                throw new Exception("Invalid contact number!");
            for (Member m : members) {
                if (m.getContact().contains(contact)) {
                    members.remove(m);
                    save(dir,members);
                    System.out.println("Member removed!");
                    return;
                }
            }
            System.out.println("Member not found!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    public ArrayList<Member> getAllMembers() {
        return members;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double rate){
        this.discountRate = rate;
    }

    /**
     * Print a list of Members
     */
    public void displayAllMembers(){
        for (Member m : memberController.getAllMembers()) {
           System.out.println(m.toString());
       }
    }

    public ArrayList load(String filename) throws IOException {
        ArrayList stringArray = (ArrayList) read(filename);
        ArrayList alr = new ArrayList();  // to store invoices data

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, "|");


            String name = star.nextToken().trim();
            String contact = star.nextToken().trim();

            // create Invoice object from file data
            Member member = new Member(name, contact);
            //add to Invoice List
            alr.add(member);
        }
        return alr;
    }



    /**
     * save method
     * save method will be different with different controlelr
     */

    public void save(String filename, List al) throws IOException {
        List alw = new ArrayList();  //to store data

        for (int i = 0; i < al.size(); i++) {
            Member member = (Member) al.get(i);
            StringBuilder st = new StringBuilder();
            st.append(member.getName()); // trim() ??
            st.append("|");
            st.append(member.getContact());
            alw.add(st.toString());
        }
            write(filename, alw);
        }
    }

