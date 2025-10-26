import models.Ticket;

import java.time.LocalDate;
import java.util.List;

public class ticketTest {
    public static void main(String[] args) {

     /*   Ticket.clearAllTickets();

        new Ticket("Ole Edvard Antonsen",
                "Oslo - Drammen", 75);

        new Ticket("Erik Hansen",
                "Oslo - Lillestrøm", 65);*/

//        List<Ticket> todayTickets =
//                Ticket.getTicketsByDate(LocalDate.now());
//
//        for (Ticket t : todayTickets) {
//            System.out.println(t);
//        }
//
//        List<Ticket> todayTickets =
//                Ticket.getTicketsByDate(
//                        LocalDate.of(2025,10,22));
//
//        for (Ticket t : todayTickets) {
//            System.out.println(t);
//        }

        List<Ticket> todayTickets =
                Ticket.getTicketsByDate(
                        LocalDate.of(2025, 10, 20));

        if (todayTickets.isEmpty()) {
            System.out.println("\nIngen billetta her gitt");
        } else {
            for (Ticket t : todayTickets) {
                System.out.println(t);
            }
        }





    }
}
