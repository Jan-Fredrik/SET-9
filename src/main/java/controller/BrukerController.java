package controller;

import models.*;
import java.sql.*;
import java.time.LocalDate;

/**
 * BrukerController
 * ----------------
 * Denne klassen håndterer alt som har med brukere å gjøre:
 * - Registrering av nye brukere
 * - Henting av brukerdata fra databasen
 * - Oppdatering av eksisterende brukere
 * Den kobler seg til databasen via Database.getConnection()
 * og bruker PreparedStatement for å unngå SQL-injeksjon.
 */
public class BrukerController {

    /**
     * Registrerer en ny bruker i databasen.
     * Rollen bestemmer hvilke rettigheter brukeren får.
     *
     * @param navn Navn på bruker
     * @param epost E-postadresse (unik)
     * @param passord Rå passord som hashes før lagring
     * @param rolleInput Tekst for rolle (kunde/admin/utvikler)
     * @param telefon Telefonnummer (kun siffer)
     * @param fodselsdatoStr Fødselsdato som tekst (valgfri)
     * @return Bruker-objekt med registrert data
     */
    public Bruker registrerBruker(String navn, String epost, String passord, String rolleInput,
                                  String telefon, String fodselsdatoStr) throws SQLException {
        // Sørger for at rollen er gyldig og konverterer til små bokstaver
        String rolle = normalizeRole(rolleInput);

        // Henter rettigheter basert på rolle
        Rights r = policyForRole(rolle);

        // Prøver å tolke fødselsdato (kan være null)
        LocalDate fd = parseDateOrNull(fodselsdatoStr);

        // SQL-setning for å legge inn ny bruker
        String sql = "INSERT INTO Bruker (navn, epost, passord_hash, rolle, telefon, fodselsdato, kan_se, kan_endre, kan_slette) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Prøver å koble til databasen og utføre spørringen
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setter inn verdiene i spørringen
            stmt.setString(1, navn);
            stmt.setString(2, epost);
            stmt.setString(3, Hashing.hashPassword(passord)); // hasher passordet
            stmt.setString(4, rolle);
            stmt.setString(5, telefon);
            if (fd == null) stmt.setNull(6, Types.DATE);
            else stmt.setDate(6, Date.valueOf(fd));
            stmt.setBoolean(7, r.kanSe);
            stmt.setBoolean(8, r.kanEndre);
            stmt.setBoolean(9, r.kanSlette);

            // Utfører INSERT
            stmt.executeUpdate();

            // Henter automatisk generert bruker-ID
            ResultSet keys = stmt.getGeneratedKeys();
            int id = 0;
            if (keys.next()) id = keys.getInt(1);

            // Returnerer et nytt Bruker-objekt (uten ekte hash-streng for sikkerhet)
            return new Bruker(id, navn, epost, "<hash>", rolle, telefon, fd, r.kanSe, r.kanEndre, r.kanSlette);
        }
    }

    /**
     * Henter en bruker basert på e-postadresse.
     * Returnerer null hvis ingen bruker finnes.
     */
    public Bruker hentBruker(String epost) throws SQLException {
        String sql = "SELECT * FROM Bruker WHERE epost = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, epost);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return toBruker(rs); // konverterer til Bruker-objekt
            return null;
        }
    }

    /**
     * Henter en bruker basert på bruker-ID.
     * Returnerer null hvis brukeren ikke finnes.
     */
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

    /**
     * Oppdaterer en brukers informasjon.
     * Brukeren kan endre seg selv, eller en admin kan endre alle.
     */
    public boolean oppdaterBruker(Bruker requester, int targetId,
                                  String nyttNavn, String nyEpost, String nyttPassord,
                                  String nyTelefon, String nyFodselsdatoStr) throws SQLException {
        // Sjekker at brukeren har lov til å endre (admin eller seg selv)
        if (!(requester.getRolle().equalsIgnoreCase("admin") ||
                (requester.getBrukerId() == targetId && requester.getKanEndre()))) {
            System.out.println("Du har ikke rettighet til å endre denne brukeren.");
            return false;
        }

        // Henter eksisterende brukerdata
        Bruker eksisterende = hentBrukerById(targetId);
        if (eksisterende == null) return false;

        // SQL for å oppdatere bruker
        String sql = "UPDATE Bruker SET navn=?, epost=?, passord_hash=?, telefon=?, fodselsdato=? WHERE bruker_id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Beholder gammelt passord hvis nytt ikke er oppgitt
            String passHash = (nyttPassord == null || nyttPassord.isBlank())
                    ? eksisterende.getPassordHash()
                    : Hashing.hashPassword(nyttPassord);

            // Beholder gammel fødselsdato hvis ny ikke er oppgitt
            LocalDate fd = (nyFodselsdatoStr == null || nyFodselsdatoStr.isBlank())
                    ? eksisterende.getFodselsdato()
                    : parseDateOrNull(nyFodselsdatoStr);

            // Setter oppdaterte verdier
            stmt.setString(1, (nyttNavn == null || nyttNavn.isBlank()) ? eksisterende.getNavn() : nyttNavn);
            stmt.setString(2, (nyEpost == null || nyEpost.isBlank()) ? eksisterende.getEpost() : nyEpost);
            stmt.setString(3, passHash);
            stmt.setString(4, (nyTelefon == null || nyTelefon.isBlank()) ? eksisterende.getTelefon() : nyTelefon);

            if (fd == null) stmt.setNull(5, Types.DATE);
            else stmt.setDate(5, Date.valueOf(fd));

            stmt.setInt(6, targetId);

            // Returnerer true hvis minst én rad ble endret
            return stmt.executeUpdate() > 0;
        }
    }

    // =====================
    //    HJELPEMETODER
    // =====================

    /**
     * Sørger for at rollen er gyldig og skriver den med små bokstaver.
     * Kaster feil hvis rollen ikke er "admin", "kunde" eller "utvikler".
     */
    private static String normalizeRole(String r) {
        String x = r == null ? "" : r.trim().toLowerCase();
        if (x.equals("admin") || x.equals("utvikler") || x.equals("kunde")) return x;
        throw new IllegalArgumentException("Ugyldig rolle: " + r);
    }

    /**
     * Bestemmer hvilke rettigheter en rolle skal ha.
     * (Her kunne man utvide systemet senere hvis flere roller legges til.)
     */
    private static Rights policyForRole(String rolle) {
        switch (rolle) {
            case "admin":    return new Rights(true,  true,  false);
            case "utvikler": return new Rights(true,  true,  true);
            case "kunde":    return new Rights(true,  true,  false);
            default:         return new Rights(false, false, false);
        }
    }

    /**
     * Enkel klasse som representerer rettigheter (kan se, endre, slette).
     */
    private static class Rights {
        boolean kanSe, kanEndre, kanSlette;
        Rights(boolean s, boolean e, boolean d) {
            kanSe = s;
            kanEndre = e;
            kanSlette = d;
        }
    }

    /**
     * Prøver å tolke en streng som dato.
     * Returnerer null hvis tom eller ugyldig.
     */
    private static LocalDate parseDateOrNull(String s) {
        if (s == null || s.isBlank()) return null;
        return LocalDate.parse(s.trim()); // forventer format YYYY-MM-DD
    }

    /**
     * Lager et Bruker-objekt ut fra et database-resultat (ResultSet).
     */
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
