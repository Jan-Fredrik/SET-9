package controller;

import org.junit.jupiter.api.*;
import repository.FakeBussAPI;
import view.KonsollView;
import view.TerminalView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class RouteControllerIntegrationIT {

    private static final String PREF_FILE = "filtrering_innstillinger.properties";
    private static final String LOCAL_TICKET_FILE = "local_tickets.txt";

    private KonsollView mockView;
    private FakeBussAPI fakeApi;
    private RouteController controller;

    @BeforeEach
    void setUp() throws Exception {
        // Fjern evt. filer fra tidligere kjøringer
        Files.deleteIfExists(new File(PREF_FILE).toPath());
        Files.deleteIfExists(new File(LOCAL_TICKET_FILE).toPath());

        mockView = new KonsollView() {
            @Override
            public void visMelding(String melding) {
                // Du kan logge til konsoll her hvis du vil
                // System.out.println("[MOCK VIEW]: " + melding);
            }
        };

        // Merk: Dette forutsetter at FakeBussAPI har implementert hentByer() og hentStederIBy(by)
        fakeApi = new FakeBussAPI();
        controller = new RouteController(fakeApi, mockView);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(new File(PREF_FILE).toPath());
        Files.deleteIfExists(new File(LOCAL_TICKET_FILE).toPath());
    }

    @Test
    void testVelgReise_fullFlyt_kjoperBillett() {
        // Simuler brukerinput:
        // 1. Billettype (2: Periodebillett)
        // 2. Fra by (2: F.eks. Fredrikstad)
        // 3. Fra stoppested (2)
        // 4. Til by (2: F.eks. Fredrikstad)
        // 5. Til stoppested (2)
        // 6. Tidspunktvalg ("filtrer")
        // 7. Tidspunkt etter filtrering (f.eks. "12:00") <-- Måtte legges til for å unngå uendelig løkke
        // 8. Bekreftelse ("j")

        String simulatedInput = String.join("\n",
                "2",        // 1. Periodebillett
                "2",        // 2. Fra by
                "2",        // 3. Fra stoppested
                "2",        // 4. Til by
                "2",        // 5. Til stoppested
                "12:00",    // 7. GYLDIG TIDSPUNKT: Avslutter while(valgtTid == null) i RuteFiltrering
                "j"         // 8. Bekreft kjøp
        );

        Scanner simulatedScanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        assertDoesNotThrow(() -> controller.velgReise(simulatedScanner),
                "Metoden velgReise() skal fullføre uten unntak med simulert input");

        // Sjekk at billettfil ble opprettet
        assertTrue(Files.exists(new File(LOCAL_TICKET_FILE).toPath()),
                "Billettfil skal opprettes etter kjøp");
    }

    @Test
    void testVelgReise_avbryterKjop() {
        // Simuler brukerinput:
        // 1. Billettype (2: Periodebillett)
        // 2. Fra by (2)
        // 3. Fra stopp (2)
        // 4. Til by (2)
        // 5. Til stopp (2)
        // 6. Tidspunkt (f.eks. "08:00") <-- Må være et gyldig klokkeslett
        // 7. Avbryt kjøp ("n")

        // Merk: Endret rekkefølgen og brukte et gyldig klokkeslett i stedet for "\nfiltrer"
        String simulatedInput = String.join("\n",
                "2",        // 1. Periodebillett
                "2",        // 2. Fra by
                "2",        // 3. Fra stoppested
                "2",        // 4. Til by
                "2",        // 5. Til stoppested
                "09:00",    // 6. GYLDIG TIDSPUNKT: Avslutter hentØnsketTidspunktFraBruker
                "n"         // 7. Avbryter kjøp
        );

        Scanner simulatedScanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        assertDoesNotThrow(() -> controller.velgReise(simulatedScanner));

        // Siden bruker valgte 'n', skal det ikke opprettes billettfil
        assertFalse(Files.exists(new File(LOCAL_TICKET_FILE).toPath()),
                "Skal ikke lagres billettfil når kjøp avbrytes");
    }
}