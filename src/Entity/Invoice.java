package Entity;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Invoice{
    private int invoiceID;
    private int orderID;
    private Order order;
    private LocalDate date;   // LocalDateTime data type yyyy-MM-dd-HH-mm-ss-ns
    private LocalTime time;
    private double subtotal;
    private double serviceCharge;
    private double GST;
    private double total;
    private boolean isMember;
    private double afterDiscount;

//    public Invoice(int id, int OrderId, Order order, double subtotal, double afterDiscount, double serviceCharge, double GST, double total){
//        invoiceID = id;
//        orderID = OrderId;
//        date = LocalDate.now();
//        time = LocalTime.now();
//        this.order = order;   //order cannot be saved in the txt file
//        this.subtotal = subtotal;
//        this.afterDiscount = afterDiscount;
//        this.serviceCharge = serviceCharge;
//        this.GST = GST;
//        this.total = total;
//    }

//    public String toString(){
//
//        return(
//                "\t\t\tOODP RESTAUTRANT\t\t\t"+
//              "\n\tINVOICE #: "  + invoiceID + " \t\t\t"+
//              "\n\tServer: " +order.getStaff() + "\t\tTable: " + order.getTableId() +
//              "\n\tDate Time: " + date+time+
//              "\n\tClient: " + order.getNumber()+
//              "\n--------------------------------------------------------------" +
//              order.toString()+
//              "\n--------------------------------------------------------------" +
//              "\n\t\t\t\t\t\t\t\t\t\tSubTotal:  " + subtotal+
//              "\n\t\t\t\t\t\t\t\t\tafter discount :   " + afterDiscount+
//              "\n\t\t\t\t\t\t\t\t\t\tGST:        " + GST+
//              "\n\t\t\t\t\t\t\t\t\t10% SERVICE CHARGE " + serviceCharge+
//              "\n--------------------------------------------------------------" +
//              "\n\t\t\t\t\t\t\t\t\t\tTOTAL: " + total +
//              "\n\n=============================================================="  +
//              "\n\t\t Thank you for dining with us! "
//                );
//    }

    //test code
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

    public int getInvoiceID(){return invoiceID;}
    public int getOrderID(){return orderID;}
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getTime(){ return time;}
    public double getSubtotal(){return subtotal;}
    public double getServiceCharge(){return serviceCharge;}
    public double getGST(){return GST;}
    public double getTotal(){
        return total;
    }

    public String toString(){

        return(

                "=========================RESTAURANT=========================="+
                        "\nINVOICE #: "  + invoiceID + " \t\t\t"+
                        "\nServer: " + "order.getStaff()" + "\t\tTable: " + "order.getTableId()" +
                        "\nDate Time: " + date+"    "+time.truncatedTo(ChronoUnit.SECONDS)+
                        "\nClient: " + "order.getNumber()"+
                        "\n--------------------------------------------------------------" +
                        "\norder.toString()"+
                        "\n--------------------------------------------------------------" +
                        "\n\t\t\t\t\t\t\t\t\t\tSubTotal:  " + subtotal+
                        "\n\t\t\t\t\t\t\t\t\t\tafter discount :   " + afterDiscount+
                        "\n\t\t\t\t\t\t\t\t\t\tGST:        " +  String.format("%.2f",GST)+
                        "\n\t\t\t\t\t\t\t\t\t\t10% SERVICE CHARGE " + serviceCharge+
                        "\n--------------------------------------------------------------" +
                        "\n\t\t\t\t\t\t\t\t\t\tTOTAL: " + total +
                        "\n\n===========================END===============================\n\n"

        );
    }



}