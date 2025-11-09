import controller.FiltreringInnstillingHandler;

public class configMain {
    public static void main(String[] args) {

        // Opprette et nytt konfigurasjonsobjekt
        FiltreringInnstillingHandler config = new FiltreringInnstillingHandler("filtrering_innstillinger.properties");

        // Laste inn eksisterende config (eller starte ny)
        config.loadFrom();

        // Sette noen verdier
        config.setPrefValue("language", "no");
        config.setPrefValue("font.size", "14");


        // Lese verdier (med fallback dersom nøkkelen ikke finnes)
        String lang = config.getPrefValue("language", "ukjent");
        String font = config.getPrefValue("font.size", "12");

        System.out.println("Språk: " + lang);
        System.out.println("Skriftstørrelse: " + font);

        // Lagre tilbake til fil
        config.saveTo();
    }
}
