package models;

import java.util.Random;

public class RouteChange {
    private final Random random = new Random();

    public void triggerRandomRouteChange() {
        String[] events = {
                "Melding: Din rute er forsinket grunnet høy trafikk.\n Forventet ventetid: 5 minutter",
                "Melding: Din rute er forsinket grunnet ukjent årsak.",
                "Melding: Buss ute av drift. Informasjon om transport erstatning underveis"
        };
        String chosen = events[random.nextInt(events.length)];
        System.out.println("[RouteChange] " + chosen);
    }
}

