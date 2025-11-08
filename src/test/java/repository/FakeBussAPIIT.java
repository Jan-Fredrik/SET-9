package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FakeBussAPITest {

    private FakeBussAPI api;

    @BeforeEach
    void setUp() {
        api = new FakeBussAPI();
    }

    // -----------------------------------------------------------
    // Tester hentByer()
    // -----------------------------------------------------------
    @Test
    void hentByer_returnererListeMedByer() {
        List<String> byer = api.hentByer();
        assertNotNull(byer, "Listen skal ikke være null");
        assertTrue(byer.contains("Oslo"), "Oslo skal være med i lista");
        assertTrue(byer.size() >= 5, "Skal være flere enn 5 byer tilgjengelig");
    }

    // -----------------------------------------------------------
    // Tester hentStederIBy()
    // -----------------------------------------------------------
    @Test
    void hentStederIBy_returnererListeMedStoppesteder() {
        List<String> steder = api.hentStederIBy("Halden");
        assertNotNull(steder);
        assertTrue(steder.contains("Halden stasjon"), "Halden stasjon skal finnes");
    }

    @Test
    void hentStederIBy_returnererTomListeForUkjentBy() {
        List<String> steder = api.hentStederIBy("Bergen");
        assertNotNull(steder);
        assertTrue(steder.isEmpty(), "Ukjent by skal gi tom liste");
    }

    // -----------------------------------------------------------
    // Tester isHundevennligString()
    // -----------------------------------------------------------
    @Test
    void isHundevennligString_returnererKorrektTekst() {
        FakeBussAPI buss1 = new FakeBussAPI("08:00", true, false);
        FakeBussAPI buss2 = new FakeBussAPI("09:00", false, false);

        assertEquals("Hundevennlig", buss1.isHundevennligString());
        assertEquals("Ikke hundevennlig", buss2.isHundevennligString());
    }

    // -----------------------------------------------------------
    // Tester isRullestolvennligString()
    // -----------------------------------------------------------
    @Test
    void isRullestolvennligString_returnererKorrektTekst() {
        FakeBussAPI buss1 = new FakeBussAPI("10:00", false, true);
        FakeBussAPI buss2 = new FakeBussAPI("11:00", false, false);

        assertEquals("Rullestolvennlig", buss1.isRullestolvennligString());
        assertEquals("Ikke rullestolvennlig", buss2.isRullestolvennligString());
    }

    // -----------------------------------------------------------
    // (Valgfritt) Tester den private metoden velgFraListe() via refleksjon
    // -----------------------------------------------------------
    @Test
    void velgFraListe_returnererKorrektIndeks() throws Exception {
        // Simuler brukerinput "2" (velger element nummer 2)
        String simulatedInput = "2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        List<String> alternativer = List.of("A", "B", "C");

        // Hent metoden via refleksjon
        Method method = FakeBussAPI.class.getDeclaredMethod(
                "velgFraListe", String.class, List.class, Scanner.class
        );
        method.setAccessible(true);

        int result = (int) method.invoke(null, "Velg test", alternativer, scanner);
        assertEquals(1, result, "Skal returnere indeks 1 når bruker velger 2");
    }

    @Test
    void velgFraListe_kasterExceptionNarIngenInput() throws Exception {
        Scanner emptyScanner = new Scanner(new ByteArrayInputStream(new byte[0]));
        List<String> alternativer = List.of("A", "B");

        Method method = FakeBussAPI.class.getDeclaredMethod(
                "velgFraListe", String.class, List.class, Scanner.class
        );
        method.setAccessible(true);

        Exception exception = assertThrows(Exception.class, () ->
                method.invoke(null, "Velg test", alternativer, emptyScanner)
        );

        // Sjekk at "årsaken" inni InvocationTargetException er IllegalStateException
        Throwable cause = exception.getCause();
        assertNotNull(cause, "Forventet at exceptionen har en årsak");
        assertEquals(IllegalStateException.class, cause.getClass(),
                "Forventet IllegalStateException som årsak");
        assertTrue(cause.getMessage().contains("Ingen input"),
                "Meldingen skal indikere at det ikke finnes input");
    }
}
