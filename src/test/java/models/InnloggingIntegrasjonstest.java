package models;

import models.Innlogging;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InnloggingIntegrasjonstest {

    private static final String TEST_EMAIL = "bruker123@hiof.no";
    private static final String TEST_PASSORD = "hemmelig123";


    @BeforeEach
    void setUp(){
        System.out.println("Starter integrasjonstester for innlogging...");
    }
    // ----------------------------------------------------
    // Tester registrering og innlogging med riktig passord
    // ----------------------------------------------------
    @Test


    //
    // ---------------- MAHRUKH SIN TEST ---------------
    //
    void RegistreringogInnlogging(){


        // Arrange - registrer ny bruker i databasen
        Innlogging.leggTilBruker(TEST_EMAIL, TEST_PASSORD);

        // Act - forsøk å logge inn med riktig passord
        boolean resultat = Innlogging.sjekkInnlogging(TEST_EMAIL, TEST_PASSORD);

        // Assert - bekreft at innloggingen lykkes
        assertTrue(resultat, "Innlogging lykkes for riktig e-post og passord");
    }

    // ----------------------------------
    // Innlogging feiler med feil passord
    // ----------------------------------
    @Test
    void testFeilPassord(){
        //Arrange - sørg for at brukeren finnes fra før
        Innlogging.leggTilBruker("mahrukhh@hiof.no", "riktigpassord");

        // Act - forsøk med feil passord
        boolean resultat = Innlogging.sjekkInnlogging("mahrukhh@hiof.no", "feilpassord");

        // Assert - innlogging skal feile
        assertFalse(resultat, "Innlogging skal feile for feil passord");
        
    }



    
}
