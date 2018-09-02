/**
 * Factory for creating DAO (data access object)
 *
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package persistence.daoImpl;

import java.sql.Connection;
import persistence.IDAOExhibition;
import persistence.IDAOExpoHall;
import persistence.IDAOPayTicket;
import persistence.IDAOPayment;
import persistence.IDAOTicket;
import persistence.IDAOVisitor;

public class MySQLDaoFactory {
	
	public static IDAOVisitor createVisitorDao() {
		return new VisitorDao();
	}
	
	public static IDAOTicket createTicketDao() {
		return new TicketDao();
	}
	
	public static IDAOTicket createTransactionTicketDao(Connection con) {
		return new TicketDao(con);
	}
		
	public static IDAOPayment createTransactionPaymentDao(Connection con) {
		return new PaymentDao(con);
	}
	
	public static IDAOExhibition createExhibitionDao() {
		return new ExhibitionDao();
	}
	
	public static IDAOExpoHall createExpoHallDao() {
		return new ExpoHallDao();
	}
		
	public static IDAOPayTicket createTransactionPaymentTicketDao(Connection con) {
		return new PaymentTicketDao(con);
	}			
}
