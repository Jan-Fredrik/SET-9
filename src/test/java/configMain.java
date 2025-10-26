package models;

public class configMain {

        public static void main(String[] args) {
            Config config = new Config("config.properties");

            // Laste inn eksisterende config eller starte ny
            config.loadFrom();

            // Sette noen verdier
            config.set("language", "no");
            config.set("font.size", "14");

            // Lese verdier
            String lang = config.get("language", "snørr");
            String font = config.get("font.size", "12000");

            System.out.println("Språk: " + lang);
            System.out.println("Skriftstørrelse: " + font);

            // Lagre tilbake til fil
            config.saveTo();
        }
    }

