package models;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Klassen skal opprette objekter av typen billett, og skal holde informasjon om hver enkelt billett som
// kjøpes gjennom app'en. Kobles til en bruker, må kunne vise hvilken type billett, hvor lenge den er gyldig
// og hva den er gyldig for

public class Ticket {

    // Attributes
    private int id;
    private int orderId;
    private LocalDateTime isValidStart;
    private LocalDateTime isValidEnd;
    private double price;

    // Attributter JF la til ift. billett-lagring
    private String ticketID;
    private String passengerName;
    private String route;
    private LocalDateTime purchaseTime;
    private static final String FILE_NAME = "local_tickets.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //attributter for å holde en bruker, kanskje en billettype (Vy, Østfold Kollektiv osv)
    //attributt for om en billett er gyldig, et objekt av Order, holde på rabbatt (student/honnør)


    // Constructors
    public Ticket () {}

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


    // VIKTIG
    // Konstruktør som brukes av metoden: getTicketsByDate
    private Ticket(String ticketID, String passengerName, String route, double price, LocalDateTime purchaseTime) {
        this.ticketID = ticketID;
        this.passengerName = passengerName;
        this.route = route;
        this.price = price;
        this.purchaseTime = purchaseTime;
    }


    // Konstruerer Ticket-objekt som skrives rett til fil "local-tickets.txt"
    public Ticket(String passengerName, String route, double price) {
        this.orderId = orderId;
        this.passengerName = passengerName;
        this.route = route;
        this.price = price;
        this.purchaseTime = LocalDateTime.now();

        saveTicketLocally(); // lagre automatisk ved opprettelse
    }
                                // METODER //

    // ////////////////////////////////////////////////
    // Lagrer billetten til local_tickets.txt
    //

    private void saveTicketLocally() {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write("\n==== BUSS BILLETT ====\n");
            writer.write("Kjøps-ID: " + ticketID + "\n");
            writer.write("Navn: " + passengerName + "\n");
            writer.write("Rute:" + route + "\n");
            writer.write("Pris:" + price + "\n");
            writer.write("Kjøpt:" + purchaseTime.format(FORMATTER) + "\n");
            writer.write("=======================\n");
        } catch (IOException e) {
            System.out.println("Kunne ikke lagre billetten lokalt: " + e.getMessage());
        }
    }

    // ///////////////////////////////////////////////
    // Henter alle billetter kjøpt på en spesifikk dato
    //

    public static List<Ticket> getTicketsByDate(LocalDate date) {
        List<Ticket> tickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            String id = null, name = null, route = null;
            double price = 0;
            LocalDateTime time = null;


            while ((line = reader.readLine()) != null) {

                if (line.startsWith("ID:")) {
                    id = line.substring(3).trim();
                } else if (line.startsWith("Navn:")) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("Rute:")) {
                    route = line.substring(5).trim();
                } else if (line.startsWith("Pris:")) {
                    price = Double.parseDouble(line.substring(5).trim());
                } else if (line.startsWith("Kjøpt:")) {
                    time = LocalDateTime.parse(line.substring(6).trim(), FORMATTER);


                    if (time.toLocalDate().equals(date)) {
                        tickets.add(new Ticket(id, name, route, price, time));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke lese billetter: " + e.getMessage());
        }

        return tickets;
    }

    // //////////////////////////////////////////////////////////////////////
    // Metoden her er bare lagd for å renske local_tickets.txt under testing
    //
    public static void clearAllTickets() {
        try (FileWriter writer = new FileWriter(FILE_NAME, false)) { // false = overskriv fil
            writer.write(""); // skriver ingenting, bare tømmer
            System.out.println("Alle billettan e sletta JF");
        } catch (IOException e) {
            System.out.println("nope" + e.getMessage());
        }
    }




    @Override
    public String toString() {
        return "\n==== BUSS BILLETT ====\n" +
                "ID: " + ticketID + "\n" +
                "Navn: " + passengerName + "\n" +
                "Rute: " + route + "\n" +
                "Pris: " + price + " kr\n" +
                "Kjøpt: " + purchaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" +
                "=======================\n";
    }



    // Getter-setter
    public int getTicketId() {
        return id;
    }

    public LocalDateTime getIsValidStart() {
        return isValidStart;
    }

    public LocalDateTime getIsValidEnd() {
        return isValidEnd;
    }

    public void setTicketId(int ticketId) {
        this.id = ticketId;
    }

    public void setIsValidStart(LocalDateTime isValidStart) {
        this.isValidStart = isValidStart;
    }

    public void setIsValidEnd(LocalDateTime isValidEnd) {
        this.isValidEnd = isValidEnd;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }
}
