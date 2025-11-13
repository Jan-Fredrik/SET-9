package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteChangeTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private RouteChange routeChange;
    private RouteTrigger routeTrigger;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        routeChange = new RouteChange();
        routeTrigger = new RouteTrigger(routeChange);
    }

    @Test
    public void testTriggerRandomRouteChangePrintsMessage() {
        // Trigger flere ganger for å dekke tilfeldighet
        boolean messageAppeared = false;
        for (int i = 0; i < 10; i++) {
            routeChange.triggerRandomRouteChange();
            String output = outContent.toString();
            if (output.contains("[RouteChange]")) {
                messageAppeared = true;
                break;
            }
            outContent.reset();
        }
        assertTrue(messageAppeared, "Meldingen skal skrives ut minst en gang.");
    }

    @Test
    public void testWakeUpRouteChangeMayTriggerMessage() {
        boolean messageAppeared = false;
        for (int i = 0; i < 30; i++) { // Øk antall forsøk pga. 1/3 sannsynlighet
            routeTrigger.wakeUpRouteChange();
            String output = outContent.toString();
            if (output.contains("[RouteChange]")) {
                messageAppeared = true;
                break;
            }
            outContent.reset();
        }

        assertTrue(messageAppeared, "RouteTrigger skal noen ganger trigge RouteChange.");
    }
}