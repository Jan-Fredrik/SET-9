package models;

import java.util.ArrayList;

public class Route {

    // Attributes
    private int id;
    private String name;
    private ArrayList<StopPlace> listOfStops;
    private String typeOfPublicTransport;

    // Constructors

    public Route () {}

    public Route(int id, String name, ArrayList<StopPlace> listOfStops, String typeOfPublicTransport) {
        this.id = id;
        this.name = name;
        this.listOfStops = listOfStops;
        this.typeOfPublicTransport = typeOfPublicTransport;
    }

    public Route(String name, ArrayList<StopPlace> listOfStops, String typeOfPublicTransport) {
        this.name = name;
        this.listOfStops = listOfStops;
        this.typeOfPublicTransport = typeOfPublicTransport;
    }

    // Methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<StopPlace> getListOfStops() {
        return listOfStops;
    }

    public void setListOfStops(ArrayList<StopPlace> listOfStops) {
        this.listOfStops = listOfStops;
    }

    public String getTypeOfPublicTransport() {
        return typeOfPublicTransport;
    }

    public void setTypeOfPublicTransport(String typeOfPublicTransport) {
        this.typeOfPublicTransport = typeOfPublicTransport;
    }
}
