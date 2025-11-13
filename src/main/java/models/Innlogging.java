package models;

import java.sql.*;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class Innlogging {

    private static final String URL = "jdbc:mysql://itstud.hiof.no:3306/se25_G9?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "gruppe9";
    private static final String PASSWORD = "Summer28";


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Fant ikke MySQL-driveren: " + e.getMessage());
        }
    }

    // --- Registrer ny bruker med kun epost og passord ---
    public static void leggTilBruker(String epost, String passord) {
        String sql = "INSERT INTO Innlogging (epost, passord) VALUES (?, ?)";
        String hash = BCrypt.hashpw(passord, BCrypt.gensalt());

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, epost);
            stmt.setString(2, hash);
            stmt.executeUpdate();
            System.out.println("Bruker er nå registrert!");
        } catch (SQLException e) {
            System.err.println("Feil ved lagring: " + e.getMessage());
        }
    }
    // --- Leser input fra terminal ---
    public String[] hentInnloggingsdata() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("--- INNLOGGING ----");
            System.out.print("E-post: ");
            String epost = input.nextLine().trim();

            System.out.print("Passord: ");
            String passord = input.nextLine();

            if (epost.isBlank() || passord.isBlank()) {
                System.out.println("Både e-post og passord må fylles ut.");
                return null;
            }
            return new String[]{epost, passord};
        }
    }

    // --- Kjør full innlogging  ---
    public void startInnlogging() {
        String[] data = hentInnloggingsdata();
        if (data == null) {
            System.out.println("Innlogging avbrutt på grunn av manglende data.");
            return;
        }
        String epost = data[0];
        String passord = data[1];

        if (sjekkInnlogging(epost, passord)) {
            System.out.println("Innlogging vellykket!");
        } else {
            System.out.println(" Innlogging feilet. Ugyldig e-post eller passord.");
        }
    }

    // --- Sjekk epost + passord (bcrypt) ---
    public static boolean sjekkInnlogging(String epost, String passord) {
        final String sql = "SELECT passord FROM Innlogging WHERE epost = ? LIMIT 1";

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, epost);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false; // fant ikke epost
                String lagretHash = rs.getString("passord");
                if (lagretHash == null) return false;
                return BCrypt.checkpw(passord, lagretHash);
            }
        } catch (SQLException e) {
            System.err.println("DB-feil: " + e.getMessage());
            return false;
        }
    }
}
