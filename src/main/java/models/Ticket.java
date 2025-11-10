package models;

import controller.FiltreringInnstillingHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ticket {

    private int id;
    private int orderId;
    private LocalDateTime isValidStart;
    private LocalDateTime isValidEnd;
    private double price = 50;

    private String ticketID;
    private String route;
    private LocalDateTime purchaseTime;

    private static final String FILE_NAME = "local_tickets.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ---------- KONSTRUKTØRER ----------
    public Ticket() {}

    public Ticket(LocalDateTime isValidStart, LocalDateTime isValidEnd, int orderId, double price) {
        this.isValidStart = isValidStart;
        this.isValidEnd = isValidEnd;
        this.orderId = orderId;
        this.price = price;
    }

    public Ticket(int id, LocalDateTime isValidStart, LocalDateTime isValidEnd, int orderId, double price) {
        this.id = id;
        this.isValidStart = isValidStart;
        this.isValidEnd = isValidEnd;
        this.orderId = orderId;
        this.price = price;
    }

    // brukes i getTicketsByDate()
    private Ticket(String ticketID, String route, double price, LocalDateTime purchaseTime) {
        this.ticketID = ticketID;
        this.route = route;
        this.price = price;
        this.purchaseTime = purchaseTime;
    }

    // oppretter og lagrer billett
    public Ticket(String route) {
        Random rand = new Random();
        int ticketNumber = 10000 + rand.nextInt(90000);

        this.orderId = ticketNumber;
        this.route = route;
        this.price = price;
        this.purchaseTime = LocalDateTime.now();

        saveTicketLocally();
    }

    // ---------- METODER ----------

    private void saveTicketLocally() {
        FiltreringInnstillingHandler pref = new FiltreringInnstillingHandler("filtrering_innstillinger.properties");
        pref.loadFrom();

        boolean erStudent = Boolean.parseBoolean(pref.getPrefValue("student", "false"));
        boolean erHonnør = Boolean.parseBoolean(pref.getPrefValue("honnør", "false"));

        double prisJustering = price;
        if (erStudent || erHonnør) {
            prisJustering = price * 0.5;
            setPrice(prisJustering);
        }

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write("\n==== BUSS BILLETT ====\n");
            writer.write("Kjøps-ID: " + orderId + "\n");
            writer.write("Reise: " + route + "\n");
            writer.write("Pris: " + prisJustering + " kr\n");
            writer.write("Kjøpt: " + purchaseTime.format(FORMATTER) + "\n");
            writer.write("=======================\n");
        } catch (IOException e) {
            System.out.println("Kunne ikke lagre billetten lokalt: " + e.getMessage());
        }
    }

    public static List<Ticket> getTicketsByDate(LocalDate date) {
        List<Ticket> tickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            String id = null, route = null;
            double price = 0;
            LocalDateTime time = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Kjøps-ID: ")) {
                    id = line.substring(9).trim();
                } else if (line.startsWith("Rute: ")) {
                    route = line.substring(6).trim();
                } else if (line.startsWith("Pris: ")) {
                    String prisTekst = line.substring(6).replace(" kr", "").trim();
                    price = Double.parseDouble(prisTekst);
                } else if (line.startsWith("Kjøpt: ")) {
                    time = LocalDateTime.parse(line.substring(7).trim(), FORMATTER);

                    if (time.toLocalDate().equals(date)) {
                        tickets.add(new Ticket(id, route, price, time));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke lese billetter: " + e.getMessage());
        }

        return tickets;
    }

    public static void clearAllTickets() {
        try (FileWriter writer = new FileWriter(FILE_NAME, false)) {
            writer.write("");
            System.out.println("Alle billettene er slettet.");
        } catch (IOException e) {
            System.out.println("Feil ved sletting: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "\n==== BUSS BILLETT ====\n" +
                "Kjøps-ID: " + ticketID + "\n" +
                "Reise: " + route + "\n" +
                "Pris: " + price + " kr\n" +
                "Kjøpt: " + purchaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" +
                "=======================\n";
    }

    // ---------- GETTERS/SETTERS ----------
    public int getTicketId() { return id; }
    public LocalDateTime getIsValidStart() { return isValidStart; }
    public LocalDateTime getIsValidEnd() { return isValidEnd; }
    public void setTicketId(int ticketId) { this.id = ticketId; }
    public void setIsValidStart(LocalDateTime isValidStart) { this.isValidStart = isValidStart; }
    public void setIsValidEnd(LocalDateTime isValidEnd) { this.isValidEnd = isValidEnd; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getTicketID() { return ticketID; }
    public void setTicketID(String ticketID) { this.ticketID = ticketID; }
}
