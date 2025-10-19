package models;
import java.util.Random;

public class RouteTrigger {
    private final RouteChange routeChange;
    private final Random random = new Random();


    public RouteTrigger(RouteChange routeChange) {
        this.routeChange = routeChange;
    }

    public void wakeUpRouteChange() {
        if (random.nextInt(3) == 0) { //1/3 ganger vil meldingen vises
            routeChange.triggerRandomRouteChange();
        }
    }
}

