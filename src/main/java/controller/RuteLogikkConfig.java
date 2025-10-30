package controller;

import java.io.*;
import java.util.Properties;

class RuteLogikkConfig {
    private final Properties props = new Properties();
    private final String fileName;

    public RuteLogikkConfig(String fileName) {
        this.fileName = fileName;
    }

    // Lese fra fil (hvis den finnes)
    public void loadFrom() {
        try (FileInputStream in = new FileInputStream(fileName)) {
            props.load(in);
            System.out.println("models.Config lastet fra " + fileName);
        } catch (IOException e) {
            System.out.println("Ingen eksisterende config, starter med tom.");
        }
    }

    // Lagre til fil
    public void saveTo() {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            props.store(out, "App Settings");
            System.out.println("models.Config lagret til " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hente verdi med fallback
    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    // Sette verdi
    public void set(String key, String value) {
        props.setProperty(key, value);
    }
}
