package controller;

/**
 * @YiXuan
 * @14-11-2021
 * MemberController allows the staff to manage the various functions related to membership such as creating and removing a member.
 * The MemberController also implements the fileIO handling methods
 * */

import java.io.*;
import java.util.*;

import Entity.*;

public class MemberController extends AbstractController{
    
    private ArrayList<Member> members;
    private static MemberController memberController = null;
    private double discountRate = 0.95;
    private static final String dir = "src/data/member.txt";

    /**
     * Method that creates an memberController object if it doesn't exist.
     * If it exists, it returns the object reference of the current memberController
     * @return an instance of memberController
     */
    public static MemberController getInstance(){
        if (memberController == null) {
            memberController = new MemberController();
        }
        return memberController;
    }

    /**
     * Constructor of MemberController aims to load all the members from the member.txt file if it exists.
     * If the text file does not exist. It creates a new text file, to store the details of the new members
     */
    public MemberController() {
        try {
            File file = new File(dir);
            if (file.exists()) {
                members = load(dir);
            } else {
                file.getParentFile().mkdir();
                file.createNewFile();
                members = new ArrayList<Member>();
                save(dir, members);
            }

        } catch (IOException e) {
            System.out.println("load member list unsuccessful");
            e.printStackTrace();
        }
    }

    /**
     * Create a new member and adds into the member list
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

    /**
     * Method to check whether a customer is a member 
     * @param contact contact number of the customer
     * @return
     */
    public boolean checkIsMember(String contact) {
        for (Member m : members) {
            if (m.getContact().contains(contact)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the member with the given contact
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
    
    /**
     * Method that returns the array list of the members
     * @return array list of members
     */
    public ArrayList<Member> getAllMembers() {
        return members;
    }

    /**
     * Method to get the discount rate of membership 
     * @return the discount rate
     */
    public double getDiscountRate() {
        return discountRate;
    }

    /**
     * Method that allows the staff to change the membership discount rate
     * @param rate new discount rate
     */
    public void setDiscountRate(double rate){
        this.discountRate = rate;
    }

    /**
     * Print the list of Members
     */
    public void displayAllMembers(){
        for (Member m : memberController.getAllMembers()) {
           System.out.println(m.toString());
       }
    }

    /**
     * Method that creates the Member objects from the its details stored in member.txt file.
     * @param filename specifies the direction of external file
     * @return the list of contents read from the file
     */
    @Override
    public ArrayList load(String filename){
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
     * This method is to save current members to external files.
     * @param filename
     *          specifies where the data to be stored
     * @param al
     *          specifies the list to be saved to the file
     */
    @Override
    public void save(String filename, List al)  {
        List alw = new ArrayList();
        for (int i = 0; i < al.size(); i++) {
            Member member = (Member) al.get(i);
            StringBuilder st = new StringBuilder();
            st.append(member.getName());
            st.append("|");
            st.append(member.getContact());
            alw.add(st.toString());
        }
            write(filename, alw);
        }
    }

