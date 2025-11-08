package controller;

import org.junit.jupiter.api.*;
import repository.FakeBussAPI;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class RuteFiltreringTest {

    private RuteFiltrering filtrering;
    private List<FakeBussAPI> avganger;

    @BeforeEach
    void setUp() {
        filtrering = new RuteFiltrering();

        avganger = new ArrayList<>();
        avganger.add(new FakeBussAPI("06:00", true, false));   // hundevennlig
        avganger.add(new FakeBussAPI("07:00", false, true));   // rullestolvennlig
        avganger.add(new FakeBussAPI("08:00", true, true));    // begge
        avganger.add(new FakeBussAPI("09:00", false, false));  // ingen
    }

    // -------------------------------------------------------
    // TEST: FiltrerAvgangerEtterPreferanser
    // -------------------------------------------------------
    @Test
    void testFiltrerAvgangerEtterPreferanser_skriverUtAlle() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        filtrering.FiltrerAvgangerEtterPreferanser(avganger);

        String konsoll = output.toString();
        assertTrue(konsoll.contains("Filtrerte avganger"));
        assertTrue(konsoll.contains("06:00"));
        assertTrue(konsoll.contains("Rullestolvennlig"));
    }

    // -------------------------------------------------------
    // TEST: visBareFiltrerteAvganger – har treff
    // -------------------------------------------------------
    @Test
    void testVisBareFiltrerteAvganger_medTreff() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Scanner dummyInput = new Scanner(new ByteArrayInputStream(new byte[0]));

        filtrering.visBareFiltrerteAvganger(avganger, true, false, dummyInput);

        String out = output.toString();
        assertTrue(out.contains("06:00"), "06:00 skal vises fordi den matcher preferansene");
        assertFalse(out.contains("07:00"), "07:00 skal ikke vises fordi den ikke matcher");
    }

    // -------------------------------------------------------
    // TEST: visBareFiltrerteAvganger – ingen treff, bruker velger j (avbryt)
    // -------------------------------------------------------
    @Test
    void testHentØnsketTidspunktFraBruker_skriverFiltrerOgAvbryter() {
        // bruker skriver "filtrer" først, så "j" for å avbryte
        String input = "filtrer\nj\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // avganger som ikke matcher preferanser
        List<FakeBussAPI> avganger = new ArrayList<>();
        avganger.add(new FakeBussAPI("06:00", true, false));
        avganger.add(new FakeBussAPI("07:00", false, true));

        // forvent at metoden kaster RuntimeException når "j" skrives
        assertThrows(RuntimeException.class, () ->
                        filtrering.hentØnsketTidspunktFraBruker(avganger, false, false, scanner),
                "Forventet at bruker skriver 'filtrer' og deretter 'j' -> RuntimeException");
    }

    // -------------------------------------------------------
    // TEST: visBareFiltrerteAvganger – ingen treff, bruker velger n (fortsetter)
    // -------------------------------------------------------
    @Test
    void testVisBareFiltrerteAvganger_ingenTreff_ogFortsetter() {
        // Sett opp avganger som IKKE matcher preferanser
        List<FakeBussAPI> avganger = new ArrayList<>();
        avganger.add(new FakeBussAPI("08:00", true, true)); // hundevennlig OG rullestolvennlig
        avganger.add(new FakeBussAPI("09:00", true, true)); // alle matcher ikke false,false

        // Bruker svarer "n" for å fortsette
        String input = "n\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        RuteFiltrering filtrering = new RuteFiltrering();

        // Kjør metoden
        assertDoesNotThrow(() ->
                filtrering.visBareFiltrerteAvganger(avganger, false, false, scanner)
        );

        String out = output.toString();

        // Bekreft forventet flyt
        assertTrue(out.contains("Ånei! Ingen avganger matcher dine preferanser"),
                "Skal vise melding om at ingen avganger matcher");
        assertTrue(out.contains("Fortsetter med dagens innstillinger"),
                "Skal vise melding om at man fortsetter");
    }


    // -------------------------------------------------------
    // TEST: hentØnsketTidspunktFraBruker – gyldig tidspunkt
    // -------------------------------------------------------
    @Test
    void testHentØnsketTidspunktFraBruker_gyldigTidspunkt() {
        String input = "08:00\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        LocalTime valgt = filtrering.hentØnsketTidspunktFraBruker(avganger, false, false, scanner);

        assertEquals(LocalTime.of(8, 0), valgt);
    }

    // -------------------------------------------------------
    // TEST: hentØnsketTidspunktFraBruker – ugyldig input, så gyldig
    // -------------------------------------------------------
    @Test
    void testHentØnsketTidspunktFraBruker_ugyldigDeretterGyldig() {
        String input = "abc\n07:30\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        LocalTime valgt = filtrering.hentØnsketTidspunktFraBruker(avganger, false, false, scanner);

        String out = output.toString();
        assertTrue(out.contains("Ugyldig format"));
        assertEquals(LocalTime.of(7, 30), valgt);
    }

    // -------------------------------------------------------
    // TEST: hentØnsketTidspunktFraBruker – bruker skriver "filtrer"
    // -------------------------------------------------------
    @Test
    void testHentØnsketTidspunktFraBruker_skriverFiltrer() {
        String input = "filtrer\n08:00\n"; // skriver "filtrer" først, så velger tidspunkt
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        LocalTime valgt = filtrering.hentØnsketTidspunktFraBruker(avganger, true, true, scanner);

        String out = output.toString();
        assertTrue(out.contains("Filtrert etter preferanser"));
        assertEquals(LocalTime.of(8, 0), valgt);
    }
}
