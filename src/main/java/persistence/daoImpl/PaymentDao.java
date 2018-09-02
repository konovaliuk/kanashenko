/**
 *This is DAO, which provides methods for manipulating
 *<code>Payment</code> entity.
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
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import entity.Payment;
import persistence.IDAOPayment;

public class PaymentDao implements IDAOPayment {

	public static final int PAYMENTT_ID = 1;
	public static final int PAYMENT_TIME = 2;
	public static final int PAYMENT_VISITOR_ID = 3;
	public static final int PAYMENT_TOTAL = 4;
	public static final String ADDPAYMENT = "INSERT INTO Payment (paymentTime, PaymentVisitorId, total) values (?,?,?)";
	public static final String FIND_LAST_ID = "select * from Payment";
	private static final Logger log = LogManager.getLogger(PaymentDao.class.getName());
	protected Connection userCon;

	PaymentDao(Connection con) {
		this.userCon = con;
	}

	public int addPayment(Payment payment) {
		try {
			PreparedStatement st = null;
			int newId = 0;
			try {
				st = userCon.prepareStatement(ADDPAYMENT, Statement.RETURN_GENERATED_KEYS);
				st.setTimestamp(1, payment.getPaymentTime());
				st.setInt(2, payment.getpaymentVisitorId());
				st.setInt(3, payment.getTotal());
				st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();			
				if (rs.next()) {
					newId = rs.getInt(1);
				}
				log.info("new payment has been added");
				return newId;
			} finally {
				if (st != null) {
					st.close();
				}
			}
		} catch (SQLException e) {
			log.error(e, e);
			return 0;
		}
	}				
}
