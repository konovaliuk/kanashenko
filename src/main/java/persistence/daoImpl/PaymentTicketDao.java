/**
 *This is DAO, which provides methods for manipulating
 *<code>PaymentTicket</code> entity.
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
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connection.ConPool;
import entity.PaymentTicket;
import persistence.IDAOPayTicket;

public class PaymentTicketDao implements IDAOPayTicket {

	public static final int PAY_TICKET_ID = 1;
	public static final int PAY_TICKET_TICKETID = 2;
	public static final int PAY_TICKET_PAYMENTID = 3;
	public static final String ADD_PAY_TICKET = "INSERT INTO PaymentTicket (payTicket_TicketId, payTicket_PaymentId) values (?,?)";
	protected Connection userCon;
	protected final ConPool conpool = ConPool.getConPool();
	private static final Logger log = LogManager.getLogger(PaymentTicketDao.class.getName());

	PaymentTicketDao(Connection con) {
		this.userCon = con;
	}

	public void addPayTicket(PaymentTicket payticket) {
		try {
			PreparedStatement st = null;
			try {
				st = userCon.prepareStatement(ADD_PAY_TICKET);
				st.setInt(1, payticket.getPayTicket_TicketId());
				st.setInt(2, payticket.getPayTicket_PaymentId());
				st.executeUpdate();
				log.info("new payment-ticket has been added");
			} finally {
				if (st != null) {
					st.close();
				}
			}
		} catch (SQLException e) {
			log.error(e, e);
		}
	}
}
