/**
 * Performs a transaction as a result of tickets purchase
 * Makes <code>Ticket</code> sold and adds new <code>Payment</code> and
 *  <code>PaymentTicket</code> entries to DB.
 *
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import connection.ConPool;
import entity.Exhibition;
import entity.Payment;
import entity.PaymentTicket;
import entity.Ticket;
import exception.SoldOutException;
import persistence.IDAOPayTicket;
import persistence.IDAOPayment;
import persistence.IDAOTicket;
import persistence.daoImpl.MySQLDaoFactory;

public class PaymentService {

	private static final Logger log = LogManager.getLogger(PaymentService.class.getName());
	private IDAOPayment paymentdao;
	private IDAOPayTicket payTicketdao;
	private IDAOTicket ticketdao;
	private ConPool conpool;
	private Connection con;

	public boolean performTransaction(int visitorId, int totalPrice, Collection<Exhibition> exhibitions,
			Collection<Integer> choosenTickets) {
		log.info("Transaction started");
		conpool = ConPool.getConPool();
		try {
			try {
				con = conpool.getConnectionFromPool();
				try {
					con.setAutoCommit(false);

					paymentdao = MySQLDaoFactory.createTransactionPaymentDao(con);
					payTicketdao = MySQLDaoFactory.createTransactionPaymentTicketDao(con);
					ticketdao = MySQLDaoFactory.createTransactionTicketDao(con);

					Integer[] amountTickets = new Integer[exhibitions.size()];
					choosenTickets.toArray(amountTickets);

					List<Ticket> allChoosenTickets = new ArrayList<>();
					int counter = 0;
					for (Exhibition item : exhibitions) {
						int exhibId = item.getExhibId();
						int amountTicket = amountTickets[counter];
						List<Ticket> oneEventTickets = ticketdao.findTickets(exhibId, amountTicket);
						for (Ticket ticket : oneEventTickets) {
							allChoosenTickets.add(ticket);
							ticketdao.makeTicketSold(ticket.getTicketId());
						}
						counter++;
					}

					Payment payment = new Payment();
					payment.setpaymentVisitorId(visitorId);
					Date date = new Date();
					Timestamp paymentTime = new Timestamp(date.getTime());
					payment.setPaymentTime(paymentTime);
					payment.setTotal(totalPrice);
					int newID = paymentdao.addPayment(payment);

					Collection<PaymentTicket> paymentTickets = new ArrayList<>();
					for (Ticket item : allChoosenTickets) {
						PaymentTicket paymentTicket = new PaymentTicket();
						paymentTicket.setPayTicket_TicketId(item.getTicketId());
						paymentTicket.setPayTicket_PaymentId(newID);
						paymentTickets.add(paymentTicket);
					}
					for (PaymentTicket item : paymentTickets) {
						payTicketdao.addPayTicket(item);
					}
					con.commit();
					log.info("Transaction success");
					return true;
				} catch (SQLException e) {
					log.error(e, e);
					try {
						if (con != null) {
							con.rollback();
							log.info("Transaction rolled back");
						}
					} catch (SQLException ex) {
						log.error(e, e);
					}
					return false;
				} catch (SoldOutException e) {
					log.error(e, e);
					try {
						if (con != null) {
							con.rollback();
							log.info("Transaction rolled back");
						}
					} catch (SQLException ex) {
						log.error(ex, ex);
					}
					return false;
				}
			} finally {
				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			}
		} catch (SQLException e) {
			log.error(e, e);
			return false;
		}
	}
}
