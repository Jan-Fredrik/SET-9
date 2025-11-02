package view;

import controller.RouteController;
import repository.FakeBussAPI;

import java.util.Scanner;

public class TerminalView {

    private final Scanner brukerInput = new Scanner(System.in);
    private final RouteController routeController;


    public void visMelding(String melding) {
        System.out.println(melding);
    }

    public TerminalView() {
        this.routeController = new RouteController(new FakeBussAPI(), this);
    }

    public void start() {
        visMelding("Velkommen til billettsystemet!");
        routeController.velgReise(brukerInput);
    }

   






}
