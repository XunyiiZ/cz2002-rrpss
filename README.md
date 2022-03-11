# Restaurant Reservation and Point of Sale System (RRPSS).
___

 RRPSS is an application to computerize the processes of making reservation, recording of orders and displaying of sale records. It will be solely used by the restaurant staff.

## The following are information about the application:
___
a) Menu items should be categorized according to its type, eg, Main course, drinks,
dessert, etc.
<br>b) Menu items can be added with details like name, description, price.
<br>c) Promotional set package comes in a single package price with descriptions of the
items to be served.
<br>d) A customer may order a set package and ala carte menu items as well.
<br>e) An order should indicate the staff who created the order.
<br>f) Staff information can be in the form of name, gender, employee ID and job title.
<br>g) Reservation is made by providing details like date, time, #pax, name, contact, etc.
The system should check availability and allocate a suitable table.
<br>h) When a reservation is made, a table is reserved till the reservation booking is
removed.
<br>i) Table comes in different seating capacity, in even sizes, with minimum of 2 and
maximum of 10 pax ("Persons At Table").
<br>j) Order invoice can be printed to list the order details (eg, table number, timestamp)
and a complete breakdown of order items details with taxes details.
<br>k) Discounts can be given to customers who hold membership of the restaurant or other
entities.
<br>l) Sale revenue report will detail the period, individual sale items (either ala carte or
promotional items) and total revenue.

## Functional Requirements:
___
1. Create/Update/Remove menu item
2. Create/Update/Remove promotion
3. Create order
   CE/CZ2002 Object-Oriented Design & Programming Assignment
   Page 2
4. View order
5. Add/Remove order item/s to/from order
6. Create reservation booking
7. Check/Remove reservation booking
8. Check table availability
9. Print order invoice
10. Print sale revenue report by period (eg day or month)
    (Note : you may re-order or re-phrase the above functionalities when displaying your
    application menu)
    The application is to be developed as a Console-based application (non-Graphical UI).
    Data should be stored in flat file format, either in text or binary. No database application (eg
    MySQL, MS Access, etc) is to be used.
    You may populate your menu items with data collected from the internet.
    You will create your own test cases and data to test your application thoroughly. However,
    you should also create test cases to test for cases* of full reservation, releasing of table/s upon
    payment and removing of reservation/s upon ‘period expiry’.
    Assumptions :
    (1) Reservation can only be made in advance. Reservation will be automatically removed XX
    minutes after the actual booking time*.
    (2) The currency will be in Singapore Dollar (SGD) and Good and Services Tax (GST) and
    service charge must be included in the order invoice.
    (3) Once an order invoice is printed*, it is assumed that payment has been made and the table
    is vacated*.
    (4) Customer with membership card will be entitled to discount.
    (5) There is no requirement for access control and there is no need for authentication
    (login/logout) in order to use the application.
    (6) There is no need to interface with external system, eg Payment, printer, etc.