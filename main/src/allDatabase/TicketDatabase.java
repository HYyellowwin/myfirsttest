package allDatabase;

import bacsta.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketDatabase {
    public static List<Ticket> tickets = new ArrayList<>();

    public boolean addTicket(Ticket ticket) {
        tickets.add(ticket);
        return true;
    }

    public boolean updateTicket(Ticket ticket) {
        int index = -1;
        if (tickets.contains(ticket)) {
            index = tickets.indexOf(ticket);
            tickets.set(index, ticket);
            System.out.println("电影票信息更新成功！");
            return true;
        } else {
            System.out.println("未找到该电影票");
            return false;
        }
    }

    public Ticket findTicketByID(String ticketID) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketID() == null);
            else if (ticket.getTicketID().equals(ticketID)) {
                return ticket;
            }
        }
        return null;
    }
}
