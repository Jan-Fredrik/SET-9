package controller;

import models.*;
import java.sql.*;
import java.time.LocalDate;

public class BrukerController {

    public Bruker registrerBruker(String navn, String epost, String passord, String rolleInput,
                                  String telefon, String fodselsdatoStr) throws SQLException {
        String rolle = normalizeRole(rolleInput);
        Rights r = policyForRole(rolle);

        LocalDate fd = parseDateOrNull(fodselsdatoStr);

        String sql = "INSERT INTO Bruker (navn, epost, passord_hash, rolle, telefon, fodselsdato, kan_se, kan_endre, kan_slette) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, navn);
            stmt.setString(2, epost);
            stmt.setString(3, Hashing.hashPassword(passord));
            stmt.setString(4, rolle);
            stmt.setString(5, telefon);
            if (fd == null) stmt.setNull(6, Types.DATE);
            else stmt.setDate(6, Date.valueOf(fd));
            stmt.setBoolean(7, r.kanSe);
            stmt.setBoolean(8, r.kanEndre);
            stmt.setBoolean(9, r.kanSlette);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            int id = 0; if (keys.next()) id = keys.getInt(1);

            return new Bruker(id, navn, epost, "<hash>", rolle, telefon, fd, r.kanSe, r.kanEndre, r.kanSlette);
        }
    }

    public Bruker hentBruker(String epost) throws SQLException {
        String sql = "SELECT * FROM Bruker WHERE epost = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, epost);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return toBruker(rs);
            return null;
        }
    }

    public Bruker hentBrukerById(int id) throws SQLException {
        String sql = "SELECT * FROM Bruker WHERE bruker_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return toBruker(rs);
            return null;
        }
    }

    public boolean oppdaterBruker(Bruker requester, int targetId,
                                  String nyttNavn, String nyEpost, String nyttPassord,
                                  String nyTelefon, String nyFodselsdatoStr) throws SQLException {
        if (!(requester.getRolle().equalsIgnoreCase("admin") || (requester.getBrukerId() == targetId && requester.getKanEndre()))) {
            System.out.println("Du har ikke rettighet til å endre denne brukeren.");
            return false;
        }

        Bruker eksisterende = hentBrukerById(targetId);
        if (eksisterende == null) return false;

        String sql = "UPDATE Bruker SET navn=?, epost=?, passord_hash=?, telefon=?, fodselsdato=? WHERE bruker_id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String passHash = (nyttPassord == null || nyttPassord.isBlank())
                    ? eksisterende.getPassordHash()
                    : Hashing.hashPassword(nyttPassord);

            LocalDate fd = (nyFodselsdatoStr == null || nyFodselsdatoStr.isBlank())
                    ? eksisterende.getFodselsdato()
                    : parseDateOrNull(nyFodselsdatoStr);

            stmt.setString(1, (nyttNavn == null || nyttNavn.isBlank()) ? eksisterende.getNavn() : nyttNavn);
            stmt.setString(2, (nyEpost == null || nyEpost.isBlank()) ? eksisterende.getEpost() : nyEpost);
            stmt.setString(3, passHash);
            stmt.setString(4, (nyTelefon == null || nyTelefon.isBlank()) ? eksisterende.getTelefon() : nyTelefon);

            if (fd == null) stmt.setNull(5, Types.DATE);
            else stmt.setDate(5, Date.valueOf(fd));

            stmt.setInt(6, targetId);
            return stmt.executeUpdate() > 0;
        }
    }

    // --- hjelpere ---
    private static String normalizeRole(String r) {
        String x = r == null ? "" : r.trim().toLowerCase();
        if (x.equals("admin") || x.equals("utvikler") || x.equals("kunde")) return x;
        throw new IllegalArgumentException("Ugyldig rolle: " + r);
    }

    private static Rights policyForRole(String rolle) {
        switch (rolle) {
            case "admin":    return new Rights(true,  true,  false);
            case "utvikler": return new Rights(true,  true,  true);
            case "kunde":    return new Rights(true,  true,  false);
            default:         return new Rights(false, false, false);
        }
    }

    private static class Rights {
        boolean kanSe, kanEndre, kanSlette;
        Rights(boolean s, boolean e, boolean d) { kanSe=s; kanEndre=e; kanSlette=d; }
    }

    private static LocalDate parseDateOrNull(String s) {
        if (s == null || s.isBlank()) return null;
        return LocalDate.parse(s.trim()); // forventer YYYY-MM-DD
    }

    private Bruker toBruker(ResultSet rs) throws SQLException {
        Date d = rs.getDate("fodselsdato");
        return new Bruker(
                rs.getInt("bruker_id"),
                rs.getString("navn"),
                rs.getString("epost"),
                rs.getString("passord_hash"),
                rs.getString("rolle"),
                rs.getString("telefon"),
                d == null ? null : d.toLocalDate(),
                rs.getBoolean("kan_se"),
                rs.getBoolean("kan_endre"),
                rs.getBoolean("kan_slette")
        );
    }
}
