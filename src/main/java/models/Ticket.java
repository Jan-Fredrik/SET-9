package models;
import java.time.LocalDateTime;

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
}
