import java.sql.*;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class profil {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/profiloppretting?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "datahaha";

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {


            System.out.print("Skriv inn navnet ditt: ");
            String navn = input.nextLine().trim();

            System.out.print("Skriv inn e-post: ");
            String epost = input.nextLine().trim();

            System.out.print("Skriv inn telefonnummer: ");
            String telefon = input.nextLine().trim();

            System.out.print("Velg et passord: ");
            String passord = input.nextLine();


            if (navn.isEmpty() || epost.isEmpty() || telefon.isEmpty() || passord.isEmpty()) {
                System.out.println("Alle felter må fylles ut.");
                return;
            }


            String passordHash = BCrypt.hashpw(passord, BCrypt.gensalt(12));


            String sql = "INSERT INTO Profil (navn, epost, telefon, passord) VALUES (?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, navn);
                ps.setString(2, epost);
                ps.setString(3, telefon);
                ps.setString(4, passordHash);

                int rows = ps.executeUpdate();
                if (rows == 0) {
                    System.out.println("Kunne ikke lagre bruker (ingen rader påvirket).");
                    return;
                }

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        System.out.println("\n--- Profil lagret ---");
                        System.out.println("ID: " + id);
                        System.out.println("Navn: " + navn);
                        System.out.println("E-post: " + epost);
                        System.out.println("Telefon: " + telefon);
                        System.out.println("(Passord er trygt lagret som hash.)");
                    } else {
                        System.out.println("Bruker lagret, men fikk ikke generert ID.");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("DB-feil: " + e.getMessage());
        }
    }
}
