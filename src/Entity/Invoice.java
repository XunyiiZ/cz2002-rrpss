package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
/**
 * This entity class defines Order Class
 *
 @author Zeng Xunyi
 @version 1.0
 @since 2021-11
 */
public class Invoice{
    /**
     * The invoice ID
     */
    private final int invoiceID;
    /**
     * The corresponding order ID
     */
    private final int orderID;
    /**
     * The corresponding order
     */
    private final Order order;
    /**
     * The date when making this invoice
     */
    private final LocalDate date;
    /**
     * The time when making this invoice
     */
    private final LocalTime time;
    /**
     * The total charge of the order (excluding gst and service charge)
     */
    private final double subtotal;
    /**
     * The total charge of the order after discount (excluding gst and service charge)
     */
    private final double afterDiscount;
    /**
     * The service charge of the order (10% of the total charge)
     */
    private final double serviceCharge;
    /**
     * The gst of the order (7% of the total charge)
     */
    private final double GST;
    /**
     * The total charge of the order (order charge + gst + service charge)
     */
    private final double total;

    /**
     * Constructor of Invoice which initialises a invoice object with the details of the parameters entered
     */
    public Invoice(int id, int OrderId, LocalDate date, LocalTime time,Order order, double subtotal, double afterDiscount, double serviceCharge, double GST, double total){
        invoiceID = id;
        orderID = OrderId;
        this.date = date;
        this.time = time;
        this.order = order;   //order cannot be saved in the txt file
        this.subtotal = subtotal;
        this.afterDiscount = afterDiscount;
        this.serviceCharge = serviceCharge;
        this.GST = GST;
        this.total = total;
    }
    /**
     * This is the accessor method of invoiceID field.
     * @return invoice ID
     */
    public int getInvoiceID(){return invoiceID;}
    /**
     * This is the accessor method of orderID field.
     * @return order ID
     */
    public int getOrderID(){return orderID;}
    /**
     * This is the accessor method of date field.
     * @return date when making this invoice
     */
    public LocalDate getDate(){
        return date;
    }
    /**
     * This is the accessor method of time field.
     * @return time when making this invoice
     */
    public LocalTime getTime(){ return time;}
    /**
     * This is the accessor method of subtotal field.
     * @return total charge by adding the price of all order item
     */
    public double getSubtotal(){return subtotal;}
    /**
     * This is the accessor method of serviceCharge field.
     * @return service charge of corresponding order
     */
    public double getServiceCharge(){return serviceCharge;}
    /**
     * This is the accessor method of gst field.
     * @return gst charge of corresponding order
     */
    public double getGST(){return GST;}
    /**
     * This is the accessor method of total field.
     * @return total charge of the order
     */
    public double getTotal(){
        return total;
    }
    /**
     * This is the accessor method of afterDiscount field.
     * @return subtotal charge after discount
     */
    public double getAfterDiscount(){return afterDiscount;}
    /**
     * The method is to return the information about this invoice in string format
     */
    @Override
    public String toString(){

        return(

                "=========================RESTAURANT=============================="+
                        "\nINVOICE #: "  + invoiceID + " \t\t\t"+
                        "\nServer: " + order.getStaffID() + "\t\tTable: " + order.getTableId() +
                        "\nDate Time: " + date+"    "+time.truncatedTo(ChronoUnit.SECONDS)+
                        "\nClient: " + order.getPax()+
                        "\n------------------------------------------------------------------" +
                        "\n   Item\t                                        Quantity    Price"+
                         order+
                        "\n------------------------------------------------------------------" +
                        "\n" + String.format("%58s: ","SubTotal") + String.format("%.2f",subtotal)+
                        "\n" + String.format("%58s: ","After Discount") + String.format("%.2f",afterDiscount)+
                        "\n" + String.format("%58s: ","GST") +  String.format("%.2f",GST)+
                        "\n" + String.format("%58s: ","10% SERVICE CHARGE") + String.format("%.2f",serviceCharge)+
                        "\n------------------------------------------------------------------" +
                        "\n" + String.format("%58s: ","Total") + String.format("%.2f",total) +
                        "\n\n===========================END===================================\n\n"

        );
    }


}