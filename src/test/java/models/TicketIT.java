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
    private static final String PREF_FILE = "preferanser.properties";

    @BeforeEach
    void setUp() throws IOException {
        // Rydd opp i filer før hver test
        Files.deleteIfExists(Path.of(TEST_FILE));
        Files.deleteIfExists(Path.of(PREF_FILE));

        // Lag en "ren" preferansefil uten student/honnør-rabatt
        FiltreringInnstillingHandler pref = new FiltreringInnstillingHandler(PREF_FILE);
        pref.setPrefValue("student", "false");
        pref.setPrefValue("honner", "false");
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
        new Ticket("Halden-Oslo");

        assertTrue(Files.exists(Path.of(TEST_FILE)), "local_tickets.txt skal være opprettet");
        String innhold = Files.readString(Path.of(TEST_FILE));
        assertTrue(innhold.contains("Reise: Halden-Oslo"), "Fil skal inneholde valgt reise");
    }

    // -------------------------------------------------------
    // Tester at student/honnør gir rabatt på 50 % (internt, ikke i fil)
    // -------------------------------------------------------
    @Test
    void testSaveTicketLocally_appliesDiscountFromPreferences() {
        FiltreringInnstillingHandler pref = new FiltreringInnstillingHandler(PREF_FILE);
        pref.setPrefValue("student", "true");
        pref.saveTo();

        Ticket t = new Ticket("Moss-Fredrikstad");
        assertEquals(25.0, t.getPrice(), "Studentrabatt skal gi halv pris (25 kr)");
    }

    // -------------------------------------------------------
    // Tester at getTicketsByDate finner riktig dato
    // -------------------------------------------------------
    @Test
    void testGetTicketsByDate_returnsTicketsForToday() {
        new Ticket("Sarpsborg-Oslo");

        List<Ticket> result = Ticket.getTicketsByDate(LocalDate.now());
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Skal finne minst én billett for dagens dato");
    }

    // -------------------------------------------------------
    // Tester at clearAllTickets faktisk tømmer fila
    // -------------------------------------------------------
    @Test
    void testClearAllTickets_emptiesFile() throws IOException {
        new Ticket("Fredrikstad-Oslo");
        Ticket.clearAllTickets();

        String innhold = Files.readString(Path.of(TEST_FILE));
        assertTrue(innhold.isEmpty(), "local_tickets.txt skal være tom etter clearAllTickets()");
    }
}
