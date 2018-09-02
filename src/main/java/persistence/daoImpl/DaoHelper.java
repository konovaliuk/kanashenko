/**
 * DaoHelper.java contains methods common for all DAO classes
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DaoHelper {
	private static final Logger log = LogManager.getLogger(DaoHelper.class.getName());
	protected void closeResourses(Connection con,PreparedStatement st,ResultSet rs ) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}catch (SQLException ex) {
			log.error(ex, ex);
		}
	}
	
	protected void closeResourses(Connection con,PreparedStatement st) {
		try {
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}catch (SQLException ex) {
			log.error(ex, ex);
		}
	}
	
	protected void closeResourses(PreparedStatement st) {
		try {
			if (st != null) {
				st.close();
			}
		}catch (SQLException ex) {
			log.error(ex, ex);
		}
	}
}
