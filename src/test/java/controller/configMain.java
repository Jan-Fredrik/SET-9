package controller;

public class configMain {
    public static void main(String[] args) {

        // Opprette et nytt konfigurasjonsobjekt
        RuteLogikkConfig config = new RuteLogikkConfig("config.properties");

        // Laste inn eksisterende config (eller starte ny)
        config.loadFrom();

        // Sette noen verdier
        config.set("language", "no");
        config.set("font.size", "14");

        // Lese verdier (med fallback dersom nøkkelen ikke finnes)
        String lang = config.get("language", "ukjent");
        String font = config.get("font.size", "12");

        System.out.println("Språk: " + lang);
        System.out.println("Skriftstørrelse: " + font);

        // Lagre tilbake til fil
        config.saveTo();
    }
}
