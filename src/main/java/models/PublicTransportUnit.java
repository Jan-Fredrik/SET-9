package models;

import java.util.ArrayList;

public class PublicTransportUnit {

    // Attributes
    private int id;
    private int totalAvailableSeats;
    private int totalAvailableSeatsForDisabled;
    private ArrayList<Route> routes;


    // Constructors

    public PublicTransportUnit() {}


    public PublicTransportUnit(int totalAvailableSeats, int totalAvailableSeatsForDisabled, ArrayList<Route> routes) {
        this.totalAvailableSeats = totalAvailableSeats;
        this.totalAvailableSeatsForDisabled = totalAvailableSeatsForDisabled;
        this.routes = routes;
    }

    public PublicTransportUnit(int id, int totalAvailableSeats, int totalAvailableSeatsForDisabled) {
        this.id = id;
        this.totalAvailableSeats = totalAvailableSeats;
        this.totalAvailableSeatsForDisabled = totalAvailableSeatsForDisabled;
    }


    // Methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalAvailableSeats() {
        return totalAvailableSeats;
    }

    public void setTotalAvailableSeats(int totalAvailableSeats) {
        this.totalAvailableSeats = totalAvailableSeats;
    }

    public int getTotalAvailableSeatsForDisabled() {
        return totalAvailableSeatsForDisabled;
    }

    public void setTotalAvailableSeatsForDisabled(int totalAvailableSeatsForDisabled) {
        this.totalAvailableSeatsForDisabled = totalAvailableSeatsForDisabled;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }
}
