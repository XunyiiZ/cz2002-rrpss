package controller;

/**
    InvoiceController
    @Xunyi Zeng
    1. make use of InvoiceList
 */

//import FileReadWrite.SerializeDB;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import Entity.*;

public class InvoiceController extends AbstractController{
    //    private ArrayList<Invoice> invoices;
    private MemberController memberController = MemberController.getInstance();
    private OrderController orderController = OrderController.getInstance();
    private TableController tableController = TableController.getInstance();
    private final double GST_RATE = 0.07;
    private final double SERVICE_RATE = 0.1;

    /**
     * new feature
     */
    private static InvoiceController InvoiceController = null;
    private static final String dir = "src/data/invoice.txt";
    private ArrayList<Invoice> invoiceList;


    /**
     * add a getInstance() method
     */
    public static InvoiceController getInstance() throws IOException {
        if (InvoiceController == null) {
            InvoiceController = new InvoiceController();
        }
        return InvoiceController;
    }

    public InvoiceController() throws IOException {
        /** using serialization method, have an error */
//        File file = new File(dir);
//        if(file.exists()){
//            System.out.println("file exist");
//            invoiceList = (ArrayList) SerializeDB.readSerializedObject(dir);
//        }
//        else{
//            file.getParentFile().mkdir();
//            invoiceList = new ArrayList<Invoice>();
//            SerializeDB.writeSerializedObject(dir,invoiceList);
//        }
        /** using text method */
        File file = new File(dir);
        if (file.exists()) {
            invoiceList = load(dir);
        } else {
            file.getParentFile().mkdir();
            file.createNewFile();
            invoiceList = new ArrayList<Invoice>();
            save(dir, invoiceList);
        }

//      invoices = new ArrayList<>(); // need to have file
//      memberController = MemberController.getInstance();
    }

    public void printDailyReport(LocalDate reportDate) {

    //    ZoneId defaultZoneId = ZoneId.systemDefault();
        double overallRevenue = 0.0;
            for (Invoice i : invoiceList) {
                LocalDate invoiceDate = i.getDate();
                if (invoiceDate.equals(reportDate)) {
                    System.out.println(i);
                    overallRevenue += i.getTotal();
                }
            }
            System.out.println("|||"+reportDate + ":  Total for the day is $" + String.format("%.2f", overallRevenue)+"|||");
    }
    
    public void printMonthlyReport(String dateStr) {

        double totalRev = 0.0;

        String[] date = dateStr.split("/");

        int month = Integer.parseInt(date[0]);
        int year = Integer.parseInt(date[1]);

        Calendar cal = new GregorianCalendar(year, month, 1);  // why do we need to -1？
        int length = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        double[] revenue = new double[length];

        for (int i = 0; i < length; i++) {
            revenue[i] = 0;
        }

        for (Invoice i : invoiceList) {

            int invoice_d = i.getDate().getDayOfMonth() - 1;   //minus one as a index in the array
            int invoice_m = i.getDate().getMonthValue();
            int invoice_y = i.getDate().getYear();

            //           System.out.println("invoice: " + invoice_d + invoice_m+ invoice_y +"\n");

            if (invoice_m == month && invoice_y == year) {
                revenue[invoice_d] += i.getTotal();
                totalRev += i.getTotal();
            }
        }

        // need to implement with max and min revenue of the month

        int minDay = 1, maxDay = 1;
        double minRev = 999999;
        double maxRev = 0;

        for (int i = 0; i < length; i++) {
            if (revenue[i] < minRev) {
                minRev = revenue[i];
                minDay = i + 1;
            }

            if (revenue[i] > maxRev) {
                maxRev = revenue[i];
                maxDay = i + 1;
            }
        }

        System.out.println("total revenue for the month is " + String.format("%.2f", totalRev));
        System.out.println("Highest revenue is $" + String.format("%.2f", maxRev) + " on " + maxDay + "-" + month + "-" + year);        //没有算重复的
        System.out.println("Lowest revenue is $" + String.format("%.2f", minRev) + " on " + minDay + "-" + month + "-" + year);

    }

    /**
    * prints invoice by passing in int id and String contact
    */
    public void printInvoice(int id, String contact) {
        Invoice invoice = addInvoice(id, contact);
        System.out.println(invoice.toString());
    }

    private Invoice addInvoice(int id, String number) {
        try {
            /** +1 make id start from 1 */
            int invoiceId = invoiceList.size() + 1;       // USE arrayList size to get the invoice ID  /
            Order order = orderController.getOrderByID(id);
            double subtotal = order.getOrderPrice();
            double afterDiscount = subtotal;
            double GST;
            double serviceCharge;
            double total;
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

//            String name = memberController.getMember(number).getName();

            if (memberController.checkIsMember(number)) {
                System.out.println(" (" + number + ") is a member");
                System.out.println("Member discount rate is "+String.format("%.2f",memberController.getDiscountRate()));
                afterDiscount = subtotal * memberController.getDiscountRate();
            } else; //System.out.println(name + " (" + number + ") is not a member");

            GST = subtotal * GST_RATE;
            serviceCharge = subtotal * SERVICE_RATE;
            total = afterDiscount + GST + serviceCharge;

            Invoice invoice = new Invoice(invoiceId, id, date, time, order, subtotal, afterDiscount, serviceCharge, GST, total);
            invoiceList.add(invoice);
            save(dir, invoiceList);

            tableController.setUnoccupied(order.getTableId());
            return invoice;

        } catch (IOException e) {
            System.out.println("shen me error? ");
            e.printStackTrace();
            return null;
        }

    }


    public void printAll() {
        for (Invoice i : invoiceList) {
            System.out.println(i);
        }
    }



    //   load method will be different between different controller
    public ArrayList load(String filename) throws IOException {
        ArrayList stringArray = (ArrayList) read(filename);
        ArrayList alr = new ArrayList();  // to store invoices data
        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, "|");


            int invoiceId = Integer.parseInt(star.nextToken().trim());
            int orderId = Integer.parseInt(star.nextToken().trim());
            Order order = orderController.getOrderByID(orderId);
            LocalDate date = LocalDate.parse(star.nextToken().trim()); // 神奇
            LocalTime time = LocalTime.parse(star.nextToken().trim());
            double subtotal = Double.parseDouble(star.nextToken().trim());
            double afterDiscount = Double.parseDouble(star.nextToken().trim());
            double serviceCharge = Double.parseDouble(star.nextToken().trim());
            double GST = Double.parseDouble(star.nextToken().trim());
            double total = Double.parseDouble(star.nextToken().trim());

            // create Invoice object from file data
            Invoice invoice = new Invoice(invoiceId, orderId, date, time, order, subtotal, afterDiscount, serviceCharge, GST, total);

            //add to Invoice List
            alr.add(invoice);
        }
        return alr;
    }



    /**
     * save method
     * save method will be different with different controller
     */

    public void save(String filename, List al) throws IOException {
        List alw = new ArrayList();  //to store data

        for (int i = 0; i < al.size(); i++) {
            Invoice invoice = (Invoice) al.get(i);
            StringBuilder st = new StringBuilder();
            st.append(invoice.getInvoiceID()); // trim() ??
            st.append("|");
            st.append(invoice.getOrderID());
            st.append("|");
            st.append(invoice.getDate());  // ke yi ma?
            st.append("|");
            st.append(invoice.getTime().truncatedTo(ChronoUnit.SECONDS));
            st.append("|");
            st.append(Math.round(invoice.getSubtotal()*100)/100.0);
            st.append("|");
            st.append(Math.round(invoice.getAfterDiscount()*100)/100.0);
            st.append("|");
            st.append(Math.round(invoice.getServiceCharge()*100)/100.0);
            st.append("|");
            st.append(Math.round(invoice.getGST()*100)/100.0);
            st.append("|");
            st.append(Math.round(invoice.getTotal()*100)/100.0);
            alw.add(st.toString());
        }
            write(filename, alw);
        }
    }





/** test code */
//    public static void main(String[] args) throws IOException {
//        InvoiceController controller = new InvoiceController();
////       controller.printInvoice(1,"12345678");
////      controller.printInvoice(2,"12345678");
////        controller.printInvoice(3,"12345678");
////         controller.printAll();
////        controller.printDailyReport("05/11/2021");
////        controller.printMonthlyReport("11/2021");
//        controller.printInvoice(5,"12345678");
//    }
//
//}

/**write new*/
//    public boolean writeNew(String fileName, Serializable obj){
//        try {
//            FileOutputStream fos = new FileOutputStream(fileName);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(obj);
//
//            oos.close();
//            fos.close();
//            return true;
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//            return false;
//        } catch (IOException e){
//            e.printStackTrace();
//            return false;
//        }
//
//    }


//    public void saveNew(String filename, Serializable obj){
//        if( !writeNew(filename,obj)){
//            System.out.println("Error saving menuItems to file!");
//        }
//    }

/**read new*/
//    public static Object readNew(String filename){
//        try{
//            FileInputStream fis = new FileInputStream(filename);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//
//            Object obj = ois.readObject();
//
//            ois.close();
//            fis.close();
//
//            return obj;
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            System.out.println("error reading file");
//            e.printStackTrace();
//            return null;
//        } catch (ClassNotFoundException e) {
//            System.out.println("class cannot be found");
//            e.printStackTrace();
//            return null;
//        }
//    }

/**load new*/
//    public ArrayList<Invoice> loadNew(String filename) throws IOException {
//        ArrayList<Invoice> invoices = (ArrayList<Invoice>) readNew(filename);
//        if (invoices == null) {
//            invoices = new ArrayList<Invoice>();
//        }
//        return invoices;
//    }
