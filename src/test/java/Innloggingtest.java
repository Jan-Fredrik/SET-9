import models.Innlogging;

public class Innloggingtest {
    public static void main(String[] args) {

        // registrerer en bruker, hvis vellykket skal det komme i terminalen "Bruker registrert"
        Innlogging.leggTilBruker("mahrukhh@hiof.no", "regnbue123");

        // registrerer en til bruker
        Innlogging.leggTilBruker("ida.k.thoresen@hiof.no", "vetikke123");



        // Test innlogging med feil passord
        Innlogging.sjekkInnlogging("mahrukhh@hiof.no", "abc123");

        // Tester innlogging uten å ha fyllt ut alle felter
        //Innlogging.sjekkInnlogging("", "regnbue123");

        Innlogging innlogging = new Innlogging();
        innlogging.startInnlogging();

    }
}
