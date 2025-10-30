package controller;

import repository.FakeBussAPI;
import view.TerminalView;
import java.util.*;

public class RouteController {

    private final FakeBussAPI api;
    private final TerminalView view;

    public RouteController(FakeBussAPI api, TerminalView view) {
        this.api = api;
        this.view = view;
    }

    public void velgReise(Scanner sc) {



        // Velg avreisested
        String fraBy = velgBy("Velg byen du reiser FRA", sc);
        String fraStopp = velgStoppested(fraBy, "Velg stoppested du reiser FRA", sc);

        // Velg destinasjon
        String tilBy = velgBy("Velg byen du skal reise TIL", sc);
        String tilStopp = velgStoppested(tilBy, "Velg stoppested du skal reise TIL", sc);

        // Vis resultatet
        view.visMelding("\nDu valgte å reise fra:");
        view.visMelding(fraBy + " - " + fraStopp);
        view.visMelding("Til:");
        view.visMelding(tilBy + " - " + tilStopp);
    }





    // --- Hjelpemetoder ---

    private String velgBy(String tittel, Scanner sc) {
        List<String> byer = new ArrayList<>(api.hentByer());
        Collections.sort(byer);
        return velgFraListe(tittel, byer, sc);
    }

    private String velgStoppested(String by, String tittel, Scanner sc) {
        List<String> stoppesteder = new ArrayList<>(api.hentStederIBy(by));
        Collections.sort(stoppesteder);
        return velgFraListe(tittel + " i " + by, stoppesteder, sc);
    }

    private String velgFraListe(String tittel, List<String> alternativer, Scanner sc) {
        while (true) {
            view.visMelding("\n" + tittel + ":");
            for (int i = 0; i < alternativer.size(); i++) {
                view.visMelding((i + 1) + ". " + alternativer.get(i));
            }
            System.out.print("Skriv tallet til valget ditt: ");

            String input = sc.nextLine().trim();
            try {
                int valg = Integer.parseInt(input);
                if (valg >= 1 && valg <= alternativer.size()) {
                    return alternativer.get(valg - 1);
                }
            } catch (NumberFormatException ignored) {}
            view.visMelding("Ugyldig valg – prøv igjen.");
        }
    }
}