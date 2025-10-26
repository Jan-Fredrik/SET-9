package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

import static models.profil.*;

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

    public static boolean sjekkInnlogging(String epost, String passord) {
        final String sql = "SELECT passord_hash FROM Bruker WHERE epost = ? LIMIT 1";

        try(Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = c.prepareStatement(sql)){

            ps.setString(1, epost);

            try (ResultSet rs = ps.executeQuery()) {
                //Ingen brukere med denne e-posten
                if (!rs.next()) return false;

                String lagretHash = rs.getString("passord_hash");
                if (lagretHash == null) return false;

                // Returnerer sann hvis passord matcher med hashen
                return BCRYPT.checkpw(passord, lagretHash);
            }
        } catch (SQLException e) {
            System.err.println("DB-feil: " + e.getMessage());
            return false;
        }


    }


}
