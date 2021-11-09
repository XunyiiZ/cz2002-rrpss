package controller;

import Entity.*;
import Entity.Set;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Xunyi
 * @09-11-2021
 * MenuController manages the menu implementing the fileIO handling
 *
 * */

public class MenuController extends AbstractController {

    private static MenuController MenuController = null;
    private static final String dir = "src/data/menu.txt";
    private ArrayList<MenuItem> menuList;

    public static MenuController getInstance() throws IOException {
        if (MenuController == null) {
            MenuController = new MenuController();
        }
        return MenuController;
    }

    public MenuController() throws IOException {
        /** using text method */
        File file = new File(dir);
        if (file.exists()) {
            System.out.println("file exist");
            menuList = load(dir);
        } else {
            System.out.println("not exist");
            file.getParentFile().mkdir();
            file.createNewFile();
            menuList = new ArrayList<MenuItem>();

            // AlaCarte 1-3 and Set 1
            AlaCarte aCarte1 = new AlaCarte("Steak", "juicy, tender, loaded with flavor", 24.90, AlaCarte.Category.MAINCOURSE);
            menuList.add(aCarte1);
            AlaCarte aCarte2 = new AlaCarte("Fries", "crunchy exterior with a light, fluffy interior", 4.90, AlaCarte.Category.SIDES);
            menuList.add(aCarte2);
            AlaCarte aCarte3 = new AlaCarte("Coca-cola", "carbonated soft drink flavored with vanilla, cinnamon, citrus oils and other flavorings", 1.50, AlaCarte.Category.DRINKS);
            menuList.add(aCarte3);

            Set set1 = new Set("London-Famous Ribeye", "A scrumptious ribeye steak, served along with a side of fries and a bottle of coke", 0);
            set1.addAlaCarte(aCarte1);
            set1.addAlaCarte(aCarte2);
            set1.addAlaCarte(aCarte3);
            menuList.add(set1);

            // AlaCarte 4-6 and Set 2
            AlaCarte aCarte4 = new AlaCarte("Aglio Olio", "traditional pasta recipe made by sautéing sliced garlic in olive oil", 14.90, AlaCarte.Category.MAINCOURSE);
            menuList.add(aCarte4);
            AlaCarte aCarte5 = new AlaCarte("Onion Rings", " deep fried onion dipped in batter and bread crumbs", 4.90, AlaCarte.Category.SIDES);
            menuList.add(aCarte5);
            AlaCarte aCarte6 = new AlaCarte("Lemonade", "homemade drink using lemon juice, water, and a sweetener such as cane sugar, simple syrup or honey.", 3, AlaCarte.Category.DRINKS);
            menuList.add(aCarte6);

            Set set2 = new Set("Chef's Recommended Italian Aglio Olio", "A delicious bowl of pasta, served along with a side of onion rings and a special homemade lemonade", 0);
            set2.addAlaCarte(aCarte4);
            set2.addAlaCarte(aCarte5);
            set2.addAlaCarte(aCarte6);
            menuList.add(set2);

            // AlaCarte 7-9 and Set 3
            AlaCarte aCarte7 = new AlaCarte("Chicken-chop", "piece of de-boned chicken thigh, breaded and deep-fried or just plain grilled or pan-fried", 19.90, AlaCarte.Category.MAINCOURSE);
            menuList.add(aCarte7);
            AlaCarte aCarte8 = new AlaCarte("Mash potato", "Cooked potatoes, with a small amount of milk added, that have been mashed to a smooth fluffy consistency", 5.90, AlaCarte.Category.SIDES);
            menuList.add(aCarte8);
            AlaCarte aCarte9 = new AlaCarte("Green Tea", "fresh, lively and nourishing homed-brewed tea", 3.50, AlaCarte.Category.DRINKS);
            menuList.add(aCarte9);

            Set set3 = new Set("Chicken Supremo", "A hearty cut of chicken, served with smooth fluffy mash potato and a cup of homed-brewed tea", 0);
            set3.addAlaCarte(aCarte7);
            set3.addAlaCarte(aCarte8);
            set3.addAlaCarte(aCarte9);
            menuList.add(set3);

            save(dir, menuList);
        }
    }

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

    public void addAlaCarte(String name, double price, String description, AlaCarte.Category cat ){
        
        try {
            menuList.add(new AlaCarte(name,description,price,cat));
            save(dir, menuList);
        } catch (IOException e) {
            System.out.println("shen me error? ");
            e.printStackTrace();
        }
    }

    public void addSet(String name, String description){
        menuList.add(new Set(name, description, 0.0));
        // add alaCarte in it
    }

    public void displayAlaCarte(){
            }

    public MenuItem getItemById(){
        return (new MenuItem("name","description", 0.0));
    }

    public static void save(String filename, List al) throws IOException {
        List alw = new ArrayList();  //to store data

        for (int i = 0; i < al.size(); i++) {
            MenuItem menuItem = (MenuItem) al.get(i);
            StringBuilder st = new StringBuilder();
            st.append(menuItem.getName());
            st.append("|");
            st.append(menuItem.getDescription()); 
            st.append("|");
            st.append(menuItem.getPrice());  
            st.append("|");
            alw.add(st.toString());

            write(filename, alw);
        }
    }

    public ArrayList load(String filename) throws IOException {

        ArrayList stringArray = (ArrayList) read(filename);
        ArrayList alr = new ArrayList();  

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, "|");

            String name = star.nextToken().trim();
            String description = star.nextToken().trim();
            int price = Integer.parseInt(star.nextToken().trim());

            // create MenuItem object from file data
            MenuItem reservation = new MenuItem(name, description, price);

            //add to Invoice List
            alr.add(reservation);
        }
        return alr;
    }

    public int getItemById(int id)
    {
        return 0;
    }
}

