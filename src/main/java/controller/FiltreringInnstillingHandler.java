package controller;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class FiltreringInnstillingHandler {
    private final Properties props = new Properties();
    private final String fileName;

    public FiltreringInnstillingHandler(String fileName) {
        this.fileName = fileName;
    }

    // Lese fra fil
    public void loadFrom() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Opprettet ny preferansefil: " + fileName);

                    // Oppretter standardverdie hvis ikke fila er der og en ny må opprette
                    setPrefValue("hund", String.valueOf(false));
                    setPrefValue("rullestol", String.valueOf(false));
                    setPrefValue("student", String.valueOf(false));
                    setPrefValue("honner", String.valueOf(false));
                    saveTo(); // lagrer tomt sett med defaults
                }
            } catch (IOException e) {
                System.out.println("Kunne ikke opprette ny fil: " + e.getMessage());
            }
        } else {
            try (FileInputStream in = new FileInputStream(file)) {
                props.load(in);
            } catch (IOException e) {
                System.out.println("Feil ved lasting av preferansefil: " + e.getMessage());
            }
        }
    }

    // Lagre til fil
    public void saveTo() {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            props.store(out, "App Settings");
            System.out.println("Filtrerings-preferanse lagret til " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endrePreferanser() {

        loadFrom();
        Scanner brukerInput = new Scanner(System.in);


        System.out.print("Har du hund? (ja/nei): ");
        String hund = brukerInput.nextLine().trim().toLowerCase();
        setPrefValue("hund", String.valueOf(hund.equals("ja")));

        System.out.print("Trenger du rullestoltilgang? (ja/nei): ");
        String rullestol = brukerInput.nextLine().trim().toLowerCase();
        setPrefValue("rullestol", String.valueOf(rullestol.equals("ja")));

        System.out.print("Er du student? (ja/nei): ");
        String student = brukerInput.nextLine().trim().toLowerCase();
        setPrefValue("student", String.valueOf(student.equals("ja")));

        System.out.print("Har du honnørkort? (ja/nei): ");
        String honnor = brukerInput.nextLine().trim().toLowerCase();
        setPrefValue("honner", String.valueOf(honnor.equals("ja")));

        saveTo();
        System.out.println("Preferanser oppdatert og lagret.");
    }




    // Hente verdi med fallback
    public String getPrefValue(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    // Sette verdi
    public void setPrefValue(String key, String value) {
        props.setProperty(key, value);
    }
}
