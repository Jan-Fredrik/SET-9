package models;

public class StopPlace {

    // Attributes
    private int id;
    private String name;

    // Constructors


    public StopPlace(String name) {
        this.name = name;
    }

    public StopPlace(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
