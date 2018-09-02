/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package entity;

public class Ticket {
	private int ticketId;
	private boolean ticketIsSold;
	private int ticket_exhibitionId;
	
	public Ticket(){		
	}
	
	public Ticket(int ticketId) {
		super();
		this.ticketId = ticketId;
	}
	
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public boolean isTicketIsSold() {
		return ticketIsSold;
	}
	public void setTicketIsSold(boolean ticketIsSold) {
		this.ticketIsSold = ticketIsSold;
	}
	public int getTicketExhib() {
		return ticket_exhibitionId;
	}
	public void setTicketExhib(int ticketExhibId) {
		this.ticket_exhibitionId = ticketExhibId;
	}
	
	@Override
	public int hashCode() {
		int result = 31 * Integer.hashCode(ticketId);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Ticket))
			return false;
		Ticket other = (Ticket) obj;
		return this.ticketId == other.ticketId;
	}
	
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", ticketIsSold=" + ticketIsSold + "]";
	}
	
	
	
	
}
