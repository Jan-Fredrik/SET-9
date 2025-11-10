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


    public boolean visBareFiltrerteAvganger(List<FakeBussAPI> avganger, boolean hundePref, boolean rullestolPref, Scanner brukerInput) {
        System.out.println("\nFiltrerte avganger som nøyaktig passer dine preferanser\n");
        System.out.println("Avgang");
        System.out.println("-----------------------------------------------");

        List<FakeBussAPI> bareFiltreteAvganger = new ArrayList<>();

        for (FakeBussAPI avgang : avganger) {
            if (hundePref == avgang.isHundevennlig() && rullestolPref == avgang.isRullestolvennlig()) {
                bareFiltreteAvganger.add(avgang);
                System.out.println(avgang.getAvgang());
            }
        }

        // Hvis ingen avganger matcher preferansene
        if (bareFiltreteAvganger.isEmpty()) {
            System.out.println("\nÅnei! Ingen avganger matcher dine preferanser.");
            System.out.print("Vil du gå ut av billettkjøpet for å endre preferansene dine? (j/n): ");

            String svar = brukerInput.nextLine().trim().toLowerCase();

            if (svar.equals("j")) {
                System.out.println("\nAvbryter billettkjøp slik at du kan oppdatere preferansene dine.");
                return true; // signaliserer at brukeren avbrøt
            } else {
                System.out.println("\nFortsetter med dagens innstillinger...");
                System.out.println("\nViser alle tilgjengelige avganger igjen:\n");
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
        }

        return false;
    }


    public LocalTime hentØnsketTidspunktFraBruker(List<FakeBussAPI> avganger, boolean hundePref, boolean rullestolPref, Scanner brukerInput) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime valgtTid = null;

        while (valgtTid == null) {
            System.out.print("\nSkriv inn tidspunktet du ønsker å reise (HH:mm), ");
            System.out.print("eller skriv \"filtrer\" for å se ruter som spesifikt passer for deg: ");
            String input = brukerInput.nextLine().trim().toLowerCase();

            // Hvis bruker skriver "filtrer" -> vis preferansebaserte avganger
            if (input.equals("filtrer")) {
                System.out.println("\n--------------- Filtrert etter preferanser ---------------");
                boolean avbrutt = visBareFiltrerteAvganger(avganger, hundePref, rullestolPref, brukerInput);
                System.out.println("----------------------------------------------------------");

                if (avbrutt) {
                    return null; // signal til RouteController den blir avbrutt, ellers kræsjer ting
                }

                continue;
            }

            // Hvis bruker skriver klokkeslett
            try {
                valgtTid = LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldig format, bruk HH:mm, for eksempel 18:00.");
            }
        }

        return valgtTid;
    }






}
