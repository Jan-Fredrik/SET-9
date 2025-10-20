package models;

import java.util.ArrayList;

public class Bus extends PublicTransportUnit {

    // Attributes


    // Constructors
    public Bus () {}

    public Bus(int id, int totalAvailableSeats, int totalAvailableSeatsForDisabled) {
        super(id, totalAvailableSeats, totalAvailableSeatsForDisabled);
    }

    public Bus(int totalAvailableSeats, int totalAvailableSeatsForDisabled, ArrayList<Route> routes) {
        super(totalAvailableSeats, totalAvailableSeatsForDisabled, routes);
    }
}
