/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package persistence;

import java.util.List;
import entity.Ticket;
import exception.SoldOutException;

public interface  IDAOTicket {
	public Ticket findTicket(int exhibId) throws SoldOutException;
	public List<Ticket> findTickets(int exhibId, int amount) throws SoldOutException;
	public void makeTicketSold(int id);
	public void addTicket(Ticket ticket);
	public int howManyTickets(int exhibId);
	public String getTableName();
}
