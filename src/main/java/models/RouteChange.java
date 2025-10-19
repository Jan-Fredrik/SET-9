package models;

import java.util.Random;

public class RouteChange {
    private final Random random = new Random();

    public void triggerRandomRouteChange() {
        String[] events = {
                "Melding: Din rute er forsinket grunnet høy trafikk.\n Forventet ventetid: 5 minutter",
                "Melding: Din rute er kansellert",
                "Melding: Platform endret fra 2B til 6A",
                "Melding: Ruten du har valgt er utsolgt. Forvent lang ventetid"
        };
        String chosen = events[random.nextInt(events.length)];
        System.out.println("[RouteManager] " + chosen);
    }
}

