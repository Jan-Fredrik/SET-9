package models;

import java.util.ArrayList;

public class Tram extends PublicTransportUnit{

    // Attributes

    // Constructors
    public Tram() {
    }

    public Tram(int totalAvailableSeats, int totalAvailableSeatsForDisabled, ArrayList<Route> routes) {
        super(totalAvailableSeats, totalAvailableSeatsForDisabled, routes);
    }

    public Tram(int id, int totalAvailableSeats, int totalAvailableSeatsForDisabled) {
        super(id, totalAvailableSeats, totalAvailableSeatsForDisabled);
    }
}
