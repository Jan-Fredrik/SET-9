package controller;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.Properties;
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

    @Test
    void testEndrePreferanser_lagrerKorrekteVerdier() {
        // Simulerer brukerinput: hund=ja, rullestol=nei, student=ja, honner=nei
        String simulatedInput = "ja\nnei\nja\nnei\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Kjør metoden
        handler.endrePreferanser();

        // Les preferanser fra fil
        Properties loaded = new Properties();
        try (FileInputStream in = new FileInputStream(TEST_FILE)) {
            loaded.load(in);
        } catch (IOException e) {
            fail("Kunne ikke lese fra testfil: " + e.getMessage());
        }

        // Bekreft at verdiene ble lagret riktig
        assertEquals("true", loaded.getProperty("hund"), "Hund skal være true");
        assertEquals("false", loaded.getProperty("rullestol"), "Rullestol skal være false");
        assertEquals("true", loaded.getProperty("student"), "Student skal være true");
        assertEquals("false", loaded.getProperty("honner"), "Honnør skal være false");

        // Sjekk at utskrift inneholder bekreftelse
        String out = output.toString();
        assertTrue(out.contains("Preferanser oppdatert og lagret."),
                "Skal vise melding om at preferanser ble lagret");
    }
}
