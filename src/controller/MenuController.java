package controller;

import Entity.*;
import Entity.Set;
import Entity.AlaCarte.Category;

import java.io.File;
import java.io.IOException;
import java.security.DrbgParameters.Capability;
import java.util.*;

/**
 * @YiXuan
 * @14-11-2021
 * MenuController allows the staff to manage the menu and it's various functions such as updating a menu item or removing an item. The MenuController also implements the fileIO handling methods
 * */

public class MenuController extends AbstractController {

    private static MenuController menuController = null;
    private static Scanner sc = new Scanner(System.in);
    private static final String dir = "src/data/menu.txt";
    private ArrayList<MenuItem> menuList;

    private static int menuItemId = 0;

    /**
     * Method that creates an menuController object if it doesn't exist.
     * If it exists, it returns the object reference of the current menuController
     * @return an instance of menuController
     */
    public static MenuController getInstance() {
        if (menuController == null) {
            menuController = new MenuController();
        }
        return menuController;
    }

    /**
     * Constructor of MenuController aims to load all the exisiting menu items from the menu.txt file if it exists.
     * If the text file does not exist. It creates a new text file, as well as menu item objects, saving it into the text file created
     */
    public MenuController() {
        try {
            File file = new File(dir);
            if (file.exists()) {
                menuList = load(dir);
            } else {
                file.getParentFile().mkdir();
                file.createNewFile();
                menuList = new ArrayList<MenuItem>();

                // AlaCarte 1-3 and Set 1
                AlaCarte aCarte1 = new AlaCarte(1, "Steak", "juicy, tender, loaded with flavor", 24.90, AlaCarte.Category.MAINCOURSE);
                menuList.add(aCarte1);
                AlaCarte aCarte2 = new AlaCarte(2, "Fries", "crunchy exterior with a light, fluffy interior", 4.90, AlaCarte.Category.SIDES);
                menuList.add(aCarte2);
                AlaCarte aCarte3 = new AlaCarte(3, "Coca-cola", "carbonated soft drink flavored with vanilla, cinnamon, citrus oils and other flavorings", 1.50, AlaCarte.Category.DRINKS);
                menuList.add(aCarte3);

                Set set1 = new Set(4, "London-Famous Ribeye", "A scrumptious ribeye steak, served along with a side of fries and a bottle of coke", 0);
                set1.addAlaCarte(aCarte1);
                set1.addAlaCarte(aCarte2);
                set1.addAlaCarte(aCarte3);
                menuList.add(set1);

                // AlaCarte 4-6 and Set 2
                AlaCarte aCarte4 = new AlaCarte(5, "Aglio Olio", "traditional pasta recipe made by sautéing sliced garlic in olive oil", 14.90, AlaCarte.Category.MAINCOURSE);
                menuList.add(aCarte4);
                AlaCarte aCarte5 = new AlaCarte(6, "Onion Rings", " deep fried onion dipped in batter and bread crumbs", 4.90, AlaCarte.Category.SIDES);
                menuList.add(aCarte5);
                AlaCarte aCarte6 = new AlaCarte(7, "Lemonade", "homemade drink using lemon juice, water, and a sweetener such as cane sugar, simple syrup or honey.", 3, AlaCarte.Category.DRINKS);
                menuList.add(aCarte6);

                Set set2 = new Set(8, "Chef's Recommended Italian Aglio Olio", "A delicious bowl of pasta, served along with a side of onion rings and a special homemade lemonade", 0);
                set2.addAlaCarte(aCarte4);
                set2.addAlaCarte(aCarte5);
                set2.addAlaCarte(aCarte6);
                menuList.add(set2);

                // AlaCarte 7-9 and Set 3
                AlaCarte aCarte7 = new AlaCarte(9, "Chicken-chop", "piece of de-boned chicken thigh, breaded and deep-fried or just plain grilled or pan-fried", 19.90, AlaCarte.Category.MAINCOURSE);
                menuList.add(aCarte7);
                AlaCarte aCarte8 = new AlaCarte(10, "Mash potato", "Cooked potatoes, with a small amount of milk added, that have been mashed to a smooth fluffy consistency", 5.90, AlaCarte.Category.SIDES);
                menuList.add(aCarte8);
                AlaCarte aCarte9 = new AlaCarte(11, "Green Tea", "fresh, lively and nourishing homed-brewed tea", 3.50, AlaCarte.Category.DRINKS);
                menuList.add(aCarte9);

                Set set3 = new Set(12, "Chicken Supremo", "A hearty cut of chicken, served with smooth fluffy mash potato and a cup of homed-brewed tea", 0);
                set3.addAlaCarte(aCarte7);
                set3.addAlaCarte(aCarte8);
                set3.addAlaCarte(aCarte9);
                menuList.add(set3);

                save(dir, menuList);
            }
        } catch (IOException e) {
            System.out.println("getting menu list unsuccessful");
            e.printStackTrace();
        }
    }

    /**
     * Method to display all the items on the Menu
     */
    public void displayMenu(){
        int count = 1;
        System.out.println("Displaying all menu items");
        for(MenuItem item : menuList){
            System.out.println("============== Menu Item " + count++ + " ==============");
            if (item instanceof Set)
                System.out.println("Menu Type: Set");
            else if (item instanceof AlaCarte)
                System.out.println("Menu Type: AlaCarte");
            System.out.println(item.toString());
        }
        return;
    }

    /**
	   * Creates a new Ala Carte and add it into the menu
	   * @param name the name of new Ala Carte
       * @param price the price of the new Ala Carte
	   * @param description the words used to describe the new Ala Carte
	   * @param cat the category of the new Ala Carte
	   */
    public void addAlaCarte(String name, double price, String description, AlaCarte.Category cat ){

        menuItemId = menuList.get(menuList.size()-1).getMenuItemId()+1;
        menuList.add(new AlaCarte(menuItemId,name,description,price,cat));
        save(dir, menuList);
    }

    /**
	 * Creates a new set and add it to the menu
	 * @param name the name of the new Set
	 * @param description the words used to describe the new set
	 * @return the the new object reference of the set created
	 */
    public Set addSet(String name, String description){
        menuItemId = menuList.get(menuList.size()-1).getMenuItemId()+1;
        Set set = new Set(menuItemId, name, description, 0.0);
        menuList.add(set);
        try {
            save(dir, menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
        //add alaCarte in it
    }

    /**
     * Method that allows the staff the manage the internal functions related to a set.
     * For example, this method allows the staff to add item into a set or to remove an item from a set.
     * @param setItem the set to be managed
     */
    public void manageSet(Set setItem){
        int choice=0;
        int idx;
        while (choice != 4 || setItem.getItemList().size() < 2) {
            System.out.println("============== Manage this Set ==============\n1. Add item \n2. Remove item \n3. Display set \n4. Finish");
            while (true) {
                try {
                    System.out.println("Enter choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();

                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Invalid input");
                    continue;
                }
                break;
            }
            switch(choice){
                case 1:
                    menuController.displayAlaCarte();
                    while (true) {
                        try {
                            System.out.println("Which AlaCarte item would you like to add? ");
                            idx = sc.nextInt();
                            sc.nextLine();
                            /* if (idx > menuController.getSizeOfMenu()) {
                                throw new Exception("Invalid AlaCarte number");
                            }  */
                            MenuItem aCarte = menuController.getItemByIndex(idx-1);
                            if  (!(aCarte instanceof AlaCarte)) {
                                throw new Exception("Menu Item is not an AlaCarte Item");
                            }
                            setItem.addAlaCarte( (AlaCarte) aCarte);
                            System.out.println("AlaCarte item added: ");
                            System.out.println(aCarte.toString());
                            System.out.println("AlaCarte item successfully added");
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            System.out.println("Invalid input");
                            continue;
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Invalid AlaCarte number");
                            continue;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    }
                    break;
                case 2:
                    setItem.displayItemsInSet();
                    while (true) {
                        try {
                            System.out.println("Enter AlaCarte Item to remove:");
                            idx = sc.nextInt();
                            sc.nextLine();
                            if (idx > setItem.getItemList().size()) {
                                throw new Exception("Invalid Menu Item number");
                            }
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            System.out.println("Invalid input");
                            continue;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    }
                    setItem.deleteFromSet(idx-1);
                    System.out.println("AlaCarte Item successfully removed!");
                    break;
                case 3:
                    System.out.println("\n============== Displaying Set ==============");
                    System.out.println(setItem.toString());
                    setItem.displayItemsInSet();
                    break;
                case 4:
                    if (setItem.getItemList().size() < 2) {
                        System.out.println("Set must have at least 2 AlaCarte Items");
                    }
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
        try {
            save(dir, menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that allows the staff to make changes to a set, for example, the name and the description.
     * This method allows the staff to also manage the internal functionalities of the set like adding and removing items from a set
     * @param mItem the menuItem object which should be set 
     */
    public void updateSet(MenuItem mItem) {
        int choice = 0;
        while (choice != 5) {
            System.out.println("============== Update this set ==============\n1. Update name \n2. Update description \n3. Display set \n4. Manage set \n5. Finish");
            while (true) {
                try {
                    System.out.println("Enter choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();

                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Invalid input");
                    continue;
                }
                break;
            }
            switch(choice){
                case 1:
                    System.out.println("Enter new name: ");
                    mItem.setName(sc.nextLine());                    
                    System.out.println();
                    System.out.println("Updated Set: ");
                    System.out.println(mItem.toString());
                    break;
                case 2:
                    System.out.println("Enter new description: ");
                    mItem.setDescription(sc.nextLine());
                    System.out.println();
                    System.out.println("Updated Set: ");
                    System.out.println(mItem.toString());
                    break;
                case 3:
                    System.out.println(mItem.toString());
                    break;
                case 4:
                    manageSet((Set) mItem);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
        try {
            save(dir, menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that allows the staff to display all AlaCarte items - useful when staff wants to add AlaCarte items into a set 
     */
    public void displayAlaCarte(){
        System.out.println("Displaying all AlaCarte items");
        int count = 1;
        for(MenuItem item : menuList){
            if (item instanceof AlaCarte)
            {
                System.out.println("============== AlaCarte Item " + count + " ==============");
                System.out.println(item.toString());
            }
            count++;
        }
        return;
    }


    /**
     * Method that allows the staff to update an AlaCarte item, for example, the name, description or price 
     */
    public void updateAlaCarte(MenuItem aCarte){
        int choice = 0;
        while (choice != 5) {
            System.out.println("============== Update this AlaCarte ==============\n1. Update name \n2. Update description \n3. Update price \n4. Display ala carte \n5. Finish");
            while (true) {
                try {
                    System.out.println("Enter choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();

                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Invalid input");
                    continue;
                }
                break;
            }
            switch(choice){
                case 1:
                    System.out.println("Enter new name: ");
                    aCarte.setName(sc.nextLine());
                    System.out.println();
                    System.out.println("Updated AlaCarte: ");
                    System.out.println(aCarte.toString());
                    break;
                case 2:
                    System.out.println("Enter new description: ");
                    aCarte.setDescription(sc.nextLine());
                    System.out.println();
                    System.out.println("Updated AlaCarte: ");
                    System.out.println(aCarte.toString());
                    break;
                case 3:
                    while (true) {
                        try {
                            System.out.println("Enter new price:");
                            aCarte.setPrice(sc.nextDouble());
                        } catch (InputMismatchException e) {
                            sc.nextLine();
                            System.out.println("Invalid input");
                            continue;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    }
                    System.out.println();
                    System.out.println("Updated ala carte: ");
                    System.out.println(aCarte.toString());
                    for (MenuItem m: menuList) {
                        if (m instanceof Set) {
                            m.setPrice(((Set) m).setSetPrice());
                        }
                    }
                    break;
                case 4:
                    System.out.println(aCarte.toString());
                    break;
                case 5: 
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
        try {
            save(dir, menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that allows us to get a menuItem from its ID attribute 
     * @param id menuItem attribute id
     * @return the object reference of the menu item 
     */
    public MenuItem getItemById(int id){

        for (MenuItem item : menuList)
        {
            if(item.getMenuItemId() == id)
                System.out.println("Item found!");
                return item;
        }
        System.out.println("Id entered is incorrect!");
        return null;
    }

    /**
     * Method that allows us to get a menuItem from its printed index 
     * @param index index(Menu Item displayed when menu is printed) of the menuItem shown
     * @return the object reference of the menu item
     */
    public MenuItem getItemByIndex(int index){
        return menuList.get(index);
    }

    /**
	 * Deletes the menuItem from the menuList by its index
	 * @param index the index of the menuItem to be deleted
	 */
    public void removeMenuItem(int index) {
        try {
            MenuItem mItem = menuList.get(index);
            if (mItem instanceof AlaCarte) {                
                ArrayList<MenuItem> toRemove = new ArrayList<>();
                for (MenuItem m: menuList) {
                    if (m instanceof Set) {
                        for (AlaCarte aCarte: ((Set) m).getItemList()) {
                            if (mItem.getMenuItemId() == aCarte.getMenuItemId())
                                toRemove.add(m);
                        }
                    }
                }                
                menuList.removeAll(toRemove);
            }
            menuList.remove(index);
            System.out.println("Menu Item removed");
            save(dir, menuList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Menu Item not removed");
        }
    }

    /**
     * This method is to save current menu items to external files.
     * @param filename
     *          specifies where the data to be stored
     * @param al
     *          specifies the list to be saved to the file
     */
    @Override
    public  void save(String filename, List al)  {
        List alw = new ArrayList();  //to store data
        for (int i = 0; i < al.size(); i++) {
            StringBuilder st = new StringBuilder();
            if (al.get(i) instanceof AlaCarte)
            {
                AlaCarte aCarte = (AlaCarte) al.get(i);
                st.append(1); // To identify that it is an AlaCarte
                st.append("|");
                st.append(aCarte.getMenuItemId());
                st.append("|");
                st.append(aCarte.getName());
                st.append("|");
                st.append(aCarte.getDescription()); 
                st.append("|");
                st.append(aCarte.getPrice());  
                st.append("|");
                st.append(aCarte.getStringType());
            }
            else
            {
                Set set = (Set) al.get(i);
                st.append(0); // To identify that it is a set
                st.append("|");
                st.append(set.getMenuItemId());
                st.append("|");
                st.append(set.getName());
                st.append("|");
                st.append(set.getDescription()); 
                st.append("|");
                st.append(set.getPrice());  
                st.append("|");
               
                ArrayList<AlaCarte> itemList = set.getItemList();
                int size = itemList.size();
                st.append(size);
                st.append("|");

                for (int j = 0; j<size; j++)
                {
                    st.append(itemList.get(j).getMenuItemId());
                    if (j < size-1)
                        st.append("|");
                }
            }

            alw.add(st.toString());

            write(filename, alw);
        }
    }

    /**
     * This method is to load menu items from external files
     * @param filename
     *            specifies where the external files stored
     * @return all reservations read from the file
     */
    @Override
    public ArrayList<MenuItem> load(String filename) {
        ArrayList stringArray = (ArrayList) read(filename);
        ArrayList<MenuItem> alr = new ArrayList<MenuItem>();

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, "|");

            int type = Integer.parseInt(star.nextToken().trim());
            int menuItemId;
            String name;
            String description;
            double price;

            if (type == 1) // AlaCarte
            {
                menuItemId = Integer.parseInt(star.nextToken().trim());
                name = star.nextToken().trim();
                description = star.nextToken().trim();
                price = Double.parseDouble(star.nextToken().trim());
                String category = star.nextToken().trim();

                Category cat = null;
                switch (category){
                    case "maincourse":
                        cat = Category.MAINCOURSE;
                        break;
                    case "drinks":
                        cat = Category.DRINKS;
                        break;
                    case "sides":
                        cat = Category.SIDES;
                        break;
                }
                AlaCarte aCarte = new AlaCarte(menuItemId, name, description, price, cat);
                alr.add(aCarte);
            }
            else //Set
            {
                menuItemId = Integer.parseInt(star.nextToken().trim());
                name = star.nextToken().trim();
                description = star.nextToken().trim();
                price = Double.parseDouble(star.nextToken().trim());
                Set set = new Set(menuItemId, name, description, 0);

                int size = Integer.parseInt(star.nextToken().trim());
                for (int j = 0; j < size; j++)
                {
                    int aCarteId = Integer.parseInt(star.nextToken().trim());

                    int index = 0;
                    for (MenuItem item: alr){
                        if (item.getMenuItemId() == aCarteId)
                        {
                            set.addAlaCarte((AlaCarte) alr.get(index));
                        }
                        index++;
                    }
                }
                alr.add(set);
            }
        }
        return alr;
    }

    /**
     * Method that returns the size of the menu 
     * @return size of the Menu 
     */
    public int getSizeOfMenu(){
        return menuList.size();
    }

    /**
     * Method that returns the object reference of the menuList.
     * @return
     */
    public ArrayList<MenuItem> getMenuList() {
        return menuList;
    }

    /**
     * Method that checks whether a item id is an actual menu item in the list
     * @param id id of the menu item that needs to be check
     * @return returns true if id corresponding to a valid menu item, else return false
     */
    public boolean isValidMenuItemId(int id){
        for(MenuItem item : menuList){
            if(item.getMenuItemId() == id) return true;
        }
        return false;
    }

}

