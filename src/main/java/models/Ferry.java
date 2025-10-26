package models;

import java.util.ArrayList;

public class Ferry extends PublicTransportUnit{

    // Attributes

    // Constructors
    public Ferry() {
    }

    public Ferry(int totalAvailableSeats, int totalAvailableSeatsForDisabled, ArrayList<Route> routes) {
        super(totalAvailableSeats, totalAvailableSeatsForDisabled, routes);
    }

    public Ferry(int id, int totalAvailableSeats, int totalAvailableSeatsForDisabled) {
        super(id, totalAvailableSeats, totalAvailableSeatsForDisabled);
    }
}
