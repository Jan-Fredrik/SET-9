package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ticketTest {
    public static void main(String[] args) {

        Ticket.clearAllTickets();

        new Ticket("Ole Edvard Antonsen",
                "Oslo - Drammen", 75);

        new Ticket("Erik Hansen",
                "Oslo - Lillestrøm", 65);

        List<Ticket> todayTickets = Ticket.getTicketsByDate(LocalDate.now());
        for (Ticket t : todayTickets) {
            System.out.println(t);
        }





    }
}
