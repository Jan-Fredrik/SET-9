package controller;

import models.Ticket;
import repository.FakeBussAPI;
import repository.FakeBussData;
import view.TerminalView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class RouteController {

    private final FakeBussAPI api;
    private final TerminalView view;

    public RouteController(FakeBussAPI api, TerminalView view) {
        this.api = api;
        this.view = view;
    }

    public void velgReise(Scanner brukerInput) {

        // ////////////////////
        // Viktige variabler

        boolean enkeltBillett = false;
        boolean periodeBillett = false;

        //
        // /////////////////////

        view.visMelding("\n--- Billettkjøp ---\n");

        // //////////////////////
        // Velg billettype
        //
        view.visMelding("Velg billettype:");
        view.visMelding("1. Enkeltbillett");
        view.visMelding("2. Periodebillett");
        System.out.print("Skriv tallet til valget ditt: ");

        String valg = brukerInput.nextLine().trim();
        switch (valg) {
            case "1" -> {
                enkeltBillett = true;
                view.visMelding("Du har valgt ENKELTBILLETT.\n");
            }
            case "2" -> {
                periodeBillett = true;
                view.visMelding("Du har valgt PERIODEBILLETT.\n");
            }
            default -> {
                view.visMelding("Ugyldig valg – standard settes til ENKELTBILLETT.\n");
                enkeltBillett = true;
            }
        }


        // Velg reiser FRA
        String fraBy = velgBy("Velg byen du reiser FRA", brukerInput);
        String fraStopp = velgStoppested(fraBy, "Velg stoppested du reiser FRA", brukerInput);

        // Velg DESTINASJON
        String tilBy = velgBy("Velg byen du skal reise TIL", brukerInput);
        String tilStopp = velgStoppested(tilBy, "Velg stoppested du skal reise TIL", brukerInput);

        // resultat
        view.visMelding("\nDu valgte å reise fra:");
        view.visMelding(fraBy + " - " + fraStopp);
        view.visMelding("Til:");
        view.visMelding(tilBy + " - " + tilStopp);


        // //////////////////////////////////////////////////
        //
        //  AVGANGER - Det er her avgangene skal filtreres basert på
        //             hvilke tilgjengeligheter bussene har.
        //

        FiltreringInnstillingHandler pref = new FiltreringInnstillingHandler("filtrering_innstillinger.properties");
        pref.loadFrom();

        boolean vilHaHund = Boolean.parseBoolean(pref.getPrefValue("hund", "false"));
        boolean vilHaRullestol = Boolean.parseBoolean(pref.getPrefValue("rullestol", "false"));


        FakeBussData api = new FakeBussData();
        List<FakeBussAPI> Avganger = api.hentAvganger(fraBy);


        RuteFiltrering filtrering = new RuteFiltrering();

        filtrering.FiltrerAvgangerEtterPreferanser(Avganger);


        LocalTime valgtTidspunkt = filtrering.hentØnsketTidspunktFraBruker(Avganger, vilHaHund, vilHaRullestol);

        System.out.println("\n Du valgte tidspunkt: " + valgtTidspunkt + "\n");

        view.visMelding("\n----------------------------------------------------");
        view.visMelding(" --------- REISEOPPSUMMERING ---------");
        view.visMelding("Fra: " + fraBy + " - " + fraStopp);
        view.visMelding("Til:  " + tilBy + " - " + tilStopp);
        view.visMelding("Avreise ønsket: " + valgtTidspunkt);
        view.visMelding("----------------------------------------------------");

        Scanner sc = new Scanner(System.in);
        System.out.print("\nVil du bekrefte og kjøpe billett? (j/n): ");
        String bekreft = sc.nextLine().trim().toLowerCase();

        if (bekreft.equals("j")) {
            view.visMelding("\n Billetten er kjøpt! God tur!");
            view.visMelding("\nBilletten din er nå lagret på enheten din!");

        } else {
            view.visMelding("\nStarter på nytt...\n");
            velgReise(new Scanner(System.in));
        }

        boolean erStudent = Boolean.parseBoolean(pref.getPrefValue("student", "false"));
        boolean erHonnoer = Boolean.parseBoolean(pref.getPrefValue("honnør", "false"));

        String routeString = fraBy + " - " + fraStopp + " -> " + tilBy + " - " + tilStopp;


        Ticket ticket = new Ticket(routeString);



        // Etter kjøpt billett, returnerer til hovedmenyen.





    }





    // --- Metoder ---

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

