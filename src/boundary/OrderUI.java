import java.util.InputMismatchException;
import java.util.Scanner;

public class OrderUI {

    private OrderController oc = OrderController.getInstance();
    private static Scanner sc = new Scanner(System.in);

    public void run() {
        int option;

        option = choose();
        while(option<=5){
            switch(option){
                case 1: //create order
                    oc.createOrder();
                    System.out.println("Order has been created.");
                    break;
                case 2: //display
                    //check order
                    System.out.println("Enter OrderID: ");
                    int orderID = sc.nextInt();
                    System.out.println("Orders:");
                    oc.getOrderByID(orderID).printOrders();
                    break;
                case 3: //add order item, KIV
                    System.out.println("Enter OrderID.");
                    System.out.println("Enter the ID of item to add.");
                    int addChoice = sc.nextInt();
                    //find order

                    Order.addOrderItem(addChoice);
                    break;
                case 4: //remove order, KIV
                    System.out.println("Enter the ID of item to remove.");
                    int removeChoice = sc.nextInt();
                    //code for removing here
                    break;
                case 5:
                    //display all orders
                    oc.getOrders();
                    break;
            }
        }
    }
    private static int choose(){
        System.out.println("Select option: ");
        System.out.println("1. Create Order");
        System.out.println("2. View Order Items");
        System.out.println("3. Add Order Item to Order");
        System.out.println("4. Remove Order Item from Order");
        System.out.println("5. Display all orders");
        System.out.println("6. Exit");

        try{
            System.out.println("Enter the option: ");
            return sc.nextInt();
        } catch(InputMismatchException e) {
            System.out.println("Input mismatch.");
            sc.nextLine();
            return choose();
        }
    }
}

