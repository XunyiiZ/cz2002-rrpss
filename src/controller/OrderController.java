package controller;
import Entity.*;

public class OrderController {
    private static OrderController OrderController = null;

    public static OrderController getInstance(){
        if(OrderController == null){
            OrderController = new OrderController();
        }
        return OrderController;
    }

    public Order getOrderById(int id){
        Order order = new Order();
        return order;
    }
}
