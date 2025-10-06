package models;

import java.util.ArrayList;

public class Order {

    // Attributes
    private int orderID;
    private int userId;
    private ArrayList<Ticket> ticketsInOrder = new ArrayList<>();



    // Constructors
    public Order () {}

    public Order(int orderID, int userId, ArrayList<Ticket> ticketsInOrder) {
        this.orderID = orderID;
        this.userId = userId;
        this.ticketsInOrder = ticketsInOrder;
    }

    public Order(int orderID, ArrayList<Ticket> ticketsInOrder) {
        this.orderID = orderID;
        this.ticketsInOrder = ticketsInOrder;
    }

    // Getter-setter
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public ArrayList<Ticket> getTicketsInOrder() {
        return ticketsInOrder;
    }

    public void setTicketsInOrder(ArrayList<Ticket> ticketsInOrder) {
        this.ticketsInOrder = ticketsInOrder;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
