package controller;

import repository.FakeBussAPI;
import view.TerminalView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RuteFiltrering {

    // Viser avganger i konsollen basert på preferansan
    //

    public void FiltrerAvgangerEtterPreferanser(List<FakeBussAPI> avganger) {
        System.out.println("\n Filtrerte avganger:");
        System.out.printf("%-8s | %-18s | %-20s%n", "Avgang", "Hund", "Rullestol");
        System.out.println("-----------------------------------------------");
        for (FakeBussAPI avgang : avganger) {
            System.out.printf(
                    "%-8s | %-18s | %-20s%n",
                    avgang.getAvgang(),
                    avgang.isHundevennligString(),
                    avgang.isRullestolvennligString()
            );
        }
    }


    public void visBareFiltrerteAvganger(List<FakeBussAPI> avganger, boolean hundePref, boolean rullestolpref) {
        System.out.println("\n Filtrerte avganger som nøyaktig passer dine preferanser\n");
        System.out.println("Avganger");
        System.out.println("-----------------------------------------------");

        List<FakeBussAPI> bareFiltreteAvganger = new ArrayList<>();

        for (FakeBussAPI avgang : avganger) {
            if (hundePref == avgang.isHundevennlig() && rullestolpref == avgang.isRullestolvennlig() ) {

                System.out.println(avgang.getAvgang());
            }



        }

    }

    public LocalTime hentØnsketTidspunktFraBruker(List<FakeBussAPI> avganger, boolean hundePref, boolean rullestolPref) {

        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime valgtTid = null;

        while (valgtTid == null) {
            System.out.print("\nSkriv inn tidspunktet du ønsker å reise (HH:mm), ");
            System.out.print("eller skriv \"filtrer\" for å se ruter som spesifikt passer for deg: ");
            String input = sc.nextLine().trim().toLowerCase();

            // Hvis bruker skriver "filtrer" -> vis preferansebaserte avganger
            if (input.equals("filtrer")) {
                System.out.println("\n--------------- Filtrert etter preferanser ---------------");
                visBareFiltrerteAvganger(avganger, hundePref, rullestolPref);
                System.out.println("----------------------------------------------------------");

                continue;
            }

            // Hvis bruker skriver klokkeslett med fallback
            try {
                valgtTid = LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig format, bruk HH:mm, for eksempel 18:00.");
            }
        }

        return valgtTid;
    }





}
