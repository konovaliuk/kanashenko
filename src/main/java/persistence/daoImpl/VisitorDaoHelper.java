/**
 * Utilized by <code>VisitorDao</code>
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
import entity.Visitor;

public class VisitorDaoHelper extends DaoHelper {
	public static final int VISITORID = 1;
	public static final int VISITORNAME = 2;
	public static final int LOGIN = 3;
	public static final int PASSWORD = 4;
	public static final int ISADMIN = 5;
	protected final ConPool conpool = ConPool.getConPool();
	private static final Logger log = LogManager.getLogger(TicketDaoHelper.class.getName());

	public  Visitor findSingle(String sql, Object[] sqlParams) {
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
		}catch (SQLException ex) {
			log.error(ex, ex);
			return null;
		}
	}
	
	public  List<Visitor> findMultiple(String sql, Object[] sqlParams, int amount){
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
		}catch (SQLException ex) {
			log.error(ex, ex);
			return null;
		}
	}
	
	protected List<Visitor> fetchMultiResults(ResultSet rs, int amount) throws SQLException {
		List<Visitor> resultList = new ArrayList<>();
		for(int i = 0; i<amount; i++) {
			if (rs.next()) {
				Visitor dto = new Visitor();
	            populateDto(dto, rs);
	            resultList.add(dto);
			}
		}
        return resultList;
    }
	
	protected Visitor fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			Visitor dto = new Visitor();
			populateDto(dto, rs);	
			return dto;
		}
		else {
			return null;
		}		
	}

	protected void populateDto(Visitor visitor, ResultSet rs) throws SQLException {
		visitor.setVisitorId(rs.getInt(VISITORID));
		visitor.setName(rs.getString(VISITORNAME));
		visitor.setLogin(rs.getString(LOGIN));
		visitor.setPass(rs.getString(PASSWORD));
		visitor.setIsAdmin(rs.getBoolean(ISADMIN));
	}	
	
	protected Object findField(String sql, Object[] sqlParams, int field) {
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
				rs.next();
				return rs.getObject(field);
			} finally {
				closeResourses(con, st, rs);			
			}			
		} catch (SQLException e) {
			log.error(e, e);
			return null;
		}
	}
}
