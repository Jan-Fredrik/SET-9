
package models;

import controller.BrukerController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class BrukerControllerTest {
    @Test
    void registrerBruker() throws SQLException {
        BrukerController ctrl = new BrukerController();

        String unikEpost = "testuser_" + System.currentTimeMillis() + "@gmail.com";

        Bruker ny = ctrl.registrerBruker(
                "Test Bruker",
                unikEpost,
                "hemmelig123",
                "kunde",
                "23456789",
                "1990-01-01"
        );

        assertNotNull(ny);
        assertTrue(ny.getBrukerId() > 0, "brukerid skal være generert og større enn 0");
        assertEquals("Test Bruker", ny.getNavn());
        assertEquals("kunde", ny.getRolle());

        Bruker fraDb = ctrl.hentBruker(unikEpost);

        assertNotNull(fraDb,"Brukeren skal finnes i databasen");
        assertEquals(ny.getBrukerId(), fraDb.getBrukerId());
        assertEquals("Test Bruker", fraDb.getNavn());
        assertEquals("kunde", fraDb.getRolle());
        assertEquals("23456789", fraDb.getTelefon());

    }
}
