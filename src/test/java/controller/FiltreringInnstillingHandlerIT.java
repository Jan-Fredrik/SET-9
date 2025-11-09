package controller;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class FiltreringInnstillingHandlerIT {

    private static final String TEST_FILE = "test_prefs.properties";
    private FiltreringInnstillingHandler handler;

    @BeforeEach
    void setUp() {
        handler = new FiltreringInnstillingHandler(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        File f = new File(TEST_FILE);
        if (f.exists()) f.delete(); // rydder opp etter testen
    }

    @Test
    void testSaveAndLoadFromFile() {
        handler.setPrefValue("tema", "mørk");
        handler.saveTo();

        // Opprett ny instans som skal lese samme fil
        FiltreringInnstillingHandler leser = new FiltreringInnstillingHandler(TEST_FILE);
        leser.loadFrom();

        assertEquals("mørk", leser.getPrefValue("tema", "lys"));
    }

    @Test
    void testLoadFrom_nonExistingFile() {
        FiltreringInnstillingHandler leser = new FiltreringInnstillingHandler("finnes_ikke.properties");

        assertDoesNotThrow(leser::loadFrom, "Skal ikke kaste exception selv om filen mangler");
    }
}
