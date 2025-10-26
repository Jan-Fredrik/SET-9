package models;

import java.util.Scanner;

public class Innlogging {

    private static final String URL = "jdbc:mysql://itstud.hiof.no:3306/se25_G9?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "gruppe9";
    private static final String PASSWORD = "Summer28";

    // Metode for å lese innloggingsdata fra bruker
    public String[] hentInnloggingsdata() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("--- INNLOGGING ---");
            System.out.print("E-post: ");
            String epost = input.nextLine().trim();

            System.out.print("Passord: ");
            String passord = input.nextLine();

            // Validerer input
            if (epost.isBlank() || passord.isBlank()) {
                System.out.println("Både e-post og passord må fylles ut.");
                return null;
            }

            return new String[]{epost, passord};
        }
    }

    // Metode for å validere brukerinformasjonen (placeholder)
    public boolean validerInnlogging(String epost, String passord) {
        // Her kan du senere legge inn kode for å sjekke brukeren i databasen
        System.out.println("Forsøker å logge inn med e-post: " + epost);
        return true; // midlertidig verdi
    }

    // Metode som kjører hele prosessen samlet
    public void startInnlogging() {
        String[] data = hentInnloggingsdata();
        if (data == null) {
            System.out.println("Innlogging avbrutt på grunn av manglende data.");
            return;
        }

        String epost = data[0];
        String passord = data[1];

        if (validerInnlogging(epost, passord)) {
            System.out.println("Innlogging vellykket!");
        } else {
            System.out.println("Innlogging feilet. Ugyldig e-post eller passord.");
        }
    }
}
