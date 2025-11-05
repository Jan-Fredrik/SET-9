package models;

import controller.BetalingController;
import controller.BrukerController;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BetalingControllerTest {
    @Test
    void gjennomforKjop() throws SQLException {
        BrukerController brukerCtrl = new BrukerController();
        BetalingController betalingCtrl = new BetalingController();

        String epost = "betaling_" + System.currentTimeMillis() + "@gmail.com";
        brukerCtrl.registrerBruker(
                "Betaling1 Kunde",
                epost,
                "pass123",
                "kunde",
                "99998832",
                "1999-09-09"
        );

        Bruker kunde = brukerCtrl.hentBruker(epost);
        assertNotNull(kunde);

        assertDoesNotThrow(() -> {
            betalingCtrl.gjennomforKjop(kunde);
        });
    }
}
