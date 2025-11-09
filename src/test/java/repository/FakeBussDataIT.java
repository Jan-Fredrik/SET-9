package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FakeBussDataIT {

    private FakeBussData fakeBussData;

    @BeforeEach
    void setUp() {
        fakeBussData = new FakeBussData();
    }

    @Test
    void hentAvganger_returnererListeForEksisterendeBy() {
        List<FakeBussAPI> result = fakeBussData.hentAvganger("Halden");
        assertNotNull(result, "Metoden skal aldri returnere null");
        assertFalse(result.isEmpty(), "Listen skal ikke være tom for Halden");
    }

    @Test
    void hentAvganger_erCaseInsensitive() {
        List<FakeBussAPI> result = fakeBussData.hentAvganger("FREDRIKSTAD");
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Metoden skal håndtere store bokstaver");
    }

    @Test
    void hentAvganger_returnererTomListeForUkjentBy() {
        List<FakeBussAPI> result = fakeBussData.hentAvganger("Bergen");
        assertNotNull(result, "Metoden skal ikke returnere null");
        assertTrue(result.isEmpty(), "Listen skal være tom for byer som ikke finnes");
    }

    @Test
    void hentAvganger_returnererForskjelligeListerForUlikeByer() {
        List<FakeBussAPI> halden = fakeBussData.hentAvganger("Halden");
        List<FakeBussAPI> moss = fakeBussData.hentAvganger("Moss");
        assertNotEquals(halden, moss, "Ulike byer skal ha ulike lister");
    }
}
