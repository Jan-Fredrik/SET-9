package models;
import java.util.Random;

public class RouteTrigger {
    private final RouteChange routeChange;
    private final Random random = new Random();


    public RouteTrigger(RouteChange routeChange) {
        this.routeChange = routeChange;
    }

    // Kall denne når du vil at rute-endring skal skje
    public void wakeUpRouteChange() {
        if (random.nextInt(3) == 0) {
            routeChange.triggerRandomRouteChange();
        }
    }
}

