package models;

import controller.FiltreringInnstillingHandler;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketIT {

    private static final String TEST_FILE = "local_tickets.txt";
    private static final String PREF_FILE = "filtrering_innstillinger.properties";

    @BeforeEach
    void setUp() throws IOException {
        // Rydd opp i filer før hver test
        Files.deleteIfExists(Path.of(TEST_FILE));
        Files.deleteIfExists(Path.of(PREF_FILE));

        // Lag en "ren" preferansefil uten student/honnør-rabatt
        FiltreringInnstillingHandler pref = new FiltreringInnstillingHandler(PREF_FILE);
        pref.setPrefValue("student", "false");
        pref.setPrefValue("honnør", "false");
        pref.saveTo();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Rydd opp etter testene
        Files.deleteIfExists(Path.of(TEST_FILE));
        Files.deleteIfExists(Path.of(PREF_FILE));
    }

    // -------------------------------------------------------
    // Tester at billetten faktisk blir lagret i fil
    // -------------------------------------------------------
    @Test
    void testSaveTicketLocally_savesTicketToFile() throws IOException {
        // Opprett ny billett (lagres automatisk til fil)
        new Ticket("Halden-Oslo");

        // Sjekk at fila nå eksisterer og inneholder tekst
        assertTrue(Files.exists(Path.of(TEST_FILE)), "local_tickets.txt skal være opprettet");
        String innhold = Files.readString(Path.of(TEST_FILE));
        assertTrue(innhold.contains("Rute: Halden-Oslo"), "Fil skal inneholde valgt rute");
    }

    // -------------------------------------------------------
    // Tester at student/honnør gir rabatt på 50 %
    // -------------------------------------------------------
    @Test
    void testSaveTicketLocally_appliesDiscountFromPreferences() throws IOException {
        // Sett rabatt i preferansefil
        FiltreringInnstillingHandler pref = new FiltreringInnstillingHandler(PREF_FILE);
        pref.setPrefValue("student", "true");
        pref.saveTo();

        // Opprett ny billett med standardpris 50 kr
        new Ticket("Moss-Fredrikstad");

        // Les filen og sjekk at prisen er halvert
        String innhold = Files.readString(Path.of(TEST_FILE));
        assertTrue(innhold.contains("Pris: 25.0"), "Studentrabatt skal gi halv pris (25 kr)");
    }

    // -------------------------------------------------------
    // Tester at getTicketsByDate finner riktig dato
    // -------------------------------------------------------
    @Test
    void testGetTicketsByDate_returnsTicketsForToday() {
        // Opprett billett som lagres med dagens dato
        new Ticket("Sarpsborg-Oslo");

        // Hent billetter for i dag
        List<Ticket> result = Ticket.getTicketsByDate(LocalDate.now());
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Skal finne minst én billett for dagens dato");
    }

    // -------------------------------------------------------
    // Tester at clearAllTickets faktisk tømmer fila
    // -------------------------------------------------------
    @Test
    void testClearAllTickets_emptiesFile() throws IOException {
        new Ticket("Fredrikstad-Oslo"); // Opprett billett først
        Ticket.clearAllTickets();       // Tøm alt

        String innhold = Files.readString(Path.of(TEST_FILE));
        assertTrue(innhold.isEmpty(), "local_tickets.txt skal være tom etter clearAllTickets()");
    }
}
