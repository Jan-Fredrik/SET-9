package models;

import java.util.ArrayList;

public class Train extends PublicTransportUnit{
    // Attributes

    // Constructors
    public Train() {
    }

    public Train(int totalAvailableSeats, int totalAvailableSeatsForDisabled, ArrayList<Route> routes) {
        super(totalAvailableSeats, totalAvailableSeatsForDisabled, routes);
    }


}
