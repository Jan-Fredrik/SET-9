import controller.RouteController;
import repository.FakeBussAPI;
import view.TerminalView;

import java.util.Scanner;

public static class main {
    String commit = "Jan Fredrik er ekstremt kjekk";
    String takk_marius = "Takk marius, du er snill å god.";
    String bare_hyggerlig = "Bare hyggerlig, du er best";
    String sjekk = "JF sjekk";
}
public static void main(String[] args) {
    // Opprett nødvendige objekter
    FakeBussAPI api = new FakeBussAPI();
    TerminalView view = new TerminalView();
    RouteController controller = new RouteController(api, view);

    // Start test-sesjon
    Scanner scanner = new Scanner(System.in);
    controller.velgReise(scanner);
}