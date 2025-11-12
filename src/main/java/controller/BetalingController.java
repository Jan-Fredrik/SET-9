package controller;

import models.*;
import java.sql.*;
import java.util.UUID;

public class BetalingController {

    public void gjennomforKjop(Bruker bruker) throws SQLException {
        if (!bruker.getRolle().equalsIgnoreCase("kunde")) {
            System.out.println("Kun brukere med rolle 'kunde' kan kjøpe billetter.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            String transaksjonId = UUID.randomUUID().toString();
            String kortToken = "tok_" + UUID.randomUUID().toString().substring(0,8);
            double pris = 59.00;

            String sql = "INSERT INTO Bestilling (bruker_id, rute, avreise_tid, destinasjon, pris, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, bruker.getBrukerId());
            stmt.setString(2, "Tog 17");
            stmt.setTimestamp(3, Timestamp.valueOf("2025-10-23 12:00:00"));
            stmt.setString(4, "Oslo S");
            stmt.setDouble(5, pris);
            stmt.setString(6, "PENDING");
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            int bestillingId = 0;
            if (keys.next()) bestillingId = keys.getInt(1);

            System.out.println("\n💳 Simulerer betaling...");
            System.out.println("Korttoken: " + kortToken);
            System.out.println("Transaksjons-ID: " + transaksjonId);

            String sqlUpdate = "UPDATE Bestilling SET status = ? WHERE bestilling_id = ?";
            try (PreparedStatement u = conn.prepareStatement(sqlUpdate)) {
                u.setString(1, "PAID");
                u.setInt(2, bestillingId);
                u.executeUpdate();
            }

            System.out.println("✅ Betaling fullført. Bestilling-ID: " + bestillingId + ", Status: PAID\n");
        }
    }
}
