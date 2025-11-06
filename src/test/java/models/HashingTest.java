package models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    public class HashingTest {

        @Test
        void hashPassword_ikkenullogikkelikpassord() {
            String pass = "hemmelig123";
            String hash = Hashing.hashPassword(pass);
            assertNotNull(hash);
            assertNotEquals(pass, hash);
        }

        @Test
        void checkPassword_trueforriktigpassord() {
            String pass = "abc123";
            String hash = Hashing.hashPassword(pass);
            boolean resultat = Hashing.checkPassword(pass,hash);
            assertTrue(resultat);
        }

        @Test
        void checkPassword_Falseforfeilpassord() {
            String pass = "abc123";
            String hash = Hashing.hashPassword(pass);

            boolean resultat = Hashing.checkPassword("feilPassord", hash);
            assertFalse(resultat);

        }

        @Test
        void sjekkomsammepassordetfaartoulikehash() {
            String pass = "abc123";
            String hash1 = Hashing.hashPassword(pass);
            String hash2 = Hashing.hashPassword(pass);

            assertNotEquals(hash1, hash2);
        }
    }

