/**
 * Utilized by <code>TicketDao</code>
 * to reduce code repetition.
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
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import connection.ConPool;
import entity.Ticket;
import exception.SoldOutException;

public class TicketDaoHelper extends DaoHelper {
	public static final int TICKETID = 1;
	public static final int TICKETSOLD = 2;
	public static final int TICKET_EXHIB_ID = 3;
	protected final ConPool conpool = ConPool.getConPool();
	private static final Logger log = LogManager.getLogger(TicketDaoHelper.class.getName());

	public  Ticket findSingle(String sql, Object[] sqlParams) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				con = conpool.getConnectionFromPool();
				final String SQL = sql;
				st = con.prepareStatement(SQL);
				
				for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
					st.setObject(i + 1, sqlParams[i]);
				}
				rs = st.executeQuery();
				return fetchSingleResult(rs);
			} finally {
				closeResourses( con, st, rs );
			}
		} catch (SoldOutException ex) {
			log.error(ex, ex);
			return null;
		}
		catch (SQLException ex) {
			log.error(ex, ex);
			return null;
		}
	}
	
	public  List<Ticket> findMultiple(String sql, Object[] sqlParams, int amount){
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				con = conpool.getConnectionFromPool();
				final String SQL = sql;
				st = con.prepareStatement(SQL);
				
				for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
					st.setObject(i + 1, sqlParams[i]);
				}
				rs = st.executeQuery();
				return fetchMultiResults(rs, amount);
			} finally {
				closeResourses( con, st, rs );
			}
		}catch (SoldOutException ex) {
			log.error(ex, ex);
			return null;
		}
		catch (SQLException ex) {
			log.error(ex, ex);
			return null;
		}
	}
	
	protected List<Ticket> fetchMultiResults(ResultSet rs, int amount) throws SQLException, SoldOutException {
		List<Ticket> resultList = new ArrayList<>();
		for(int i = 0; i<amount; i++) {
			if (rs.next()) {
	        	Ticket dto = new Ticket();
	            populateDto(dto, rs);
	            resultList.add(dto);
	        }else {
				throw new SoldOutException("tickets are soldout");
			}
		}
        return resultList;
    }
	
	protected Ticket fetchSingleResult(ResultSet rs) throws SQLException, SoldOutException {
		if (!rs.next()) {
			throw new SoldOutException("tickets are soldout");							
		} else {
			Ticket dto = new Ticket();
			populateDto(dto, rs);
			return dto;
		}
	}

	protected void populateDto(Ticket ticket, ResultSet rs) throws SQLException {
		ticket.setTicketId(rs.getInt(TICKETID));
		ticket.setTicketIsSold(rs.getBoolean(TICKETSOLD));
		ticket.setTicketExhib(rs.getInt(TICKET_EXHIB_ID));
	}
}
