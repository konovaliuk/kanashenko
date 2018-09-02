/**
 *This is DAO, which provides methods for manipulating
 *<code>Ticket</code> entity.
 *
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package persistence.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import connection.ConPool;
import entity.Ticket;
import persistence.IDAOTicket;

public class TicketDao implements IDAOTicket  {

	public static final int TICKETID = 1;
	public static final int TICKETSOLD = 2;
	public static final int TICKET_EXHIB_ID = 3;
	public static final String MAKE_TICKET_SOLD = "UPDATE Ticket set Ticket.ticketIsSold = true where Ticket.ticketId =? ";
	public static final String ADDTICKET = "insert into Ticket (ticket_exhibitionId) values (?)";
	public static final String TICKET_AMOUNT = "select count(*) from( select *from Ticket where ticketIsSold = false and ticket_exhibitionId = ?) as subquery";
	private static final Logger log = LogManager.getLogger(TicketDao.class.getName());
	protected Connection userCon;
	protected final ConPool conpool = ConPool.getConPool();
	protected final String SQL_SELECT = "SELECT ticketId, ticketIsSold, ticket_exhibitionId FROM " + getTableName() + "";
	private TicketDaoHelper ticketHelper = new TicketDaoHelper();
	TicketDao() {
	}

	TicketDao(Connection con) {
		this.userCon = con;
	}
	
	public String getTableName() {
        return "Ticket";
    }

	public Ticket findTicket(int exhibId) {
		return ticketHelper.findSingle(SQL_SELECT + " WHERE ticketIsSold = false and ticket_exhibitionId = ?", new Object[]{new Integer(exhibId)});
	}
	
	public List<Ticket> findTickets(int exhibId, int amount) {
		return ticketHelper.findMultiple(SQL_SELECT + " WHERE ticketIsSold = false and ticket_exhibitionId = ?", new Object[]{new Integer(exhibId)}, amount);
	}

	public void makeTicketSold(int id) {
		try {
			PreparedStatement st = null;
			try {
				st = userCon.prepareStatement(MAKE_TICKET_SOLD);
				st.setInt(1, id);
				st.executeUpdate();
				log.info("ticket has been sold");
			} finally {
				ticketHelper.closeResourses(st);
			}
		} catch (SQLException e) {
			log.error(e, e);
		}
	}

	public void addTicket(Ticket ticket) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			try {	
				con = conpool.getConnectionFromPool();	
				st = con.prepareStatement(ADDTICKET);
				st.setInt(1, ticket.getTicketExhib());
				st.executeUpdate();
				log.info("new ticket has been added");				
			} finally {
				ticketHelper.closeResourses(con, st);
			}
		} catch (SQLException e) {
			log.error(e, e);
		}
	}

	public int howManyTickets(int exhibId) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				con = conpool.getConnectionFromPool();			
				st = con.prepareStatement(TICKET_AMOUNT);
				st.setInt(1, exhibId);					
				rs = st.executeQuery();
				rs.next();
				return (rs.getInt(1));
			}finally {
				ticketHelper.closeResourses(con, st, rs);
			}
		} catch (SQLException e) {
			log.error(e, e);
			return 0;
		}
	}
}
