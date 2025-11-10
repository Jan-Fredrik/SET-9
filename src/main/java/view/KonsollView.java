package view;

import controller.AppController;

public class KonsollView {

    public void visMelding(String melding) {
        System.out.println(melding);
    }

    public static void main(String[] args) {
        AppController app = new AppController();
        app.run();  // EN kall-funksjon som starter hele systemet


    }
}