package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ticketTest {
    public static void main(String[] args) {

        Ticket.clearAllTickets();


        new Ticket("Karin Olsen", "Oslo - Drammen", 75);
        new Ticket("Erik Hansen", "Oslo - Lillestrøm", 65);

        // Henter billetter kjøpt i dag
        List<Ticket> todayTickets = Ticket.getTicketsByDate(LocalDate.now());

        for (Ticket t : todayTickets) {
            System.out.println(t);
        }





    }
}
