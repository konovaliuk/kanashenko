/**
 *This is DAO, which provides methods for manipulating
 *<code>Visitor</code> entity.
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
import connection.ConPool;
import entity.Visitor;
import persistence.IDAOVisitor;

public class VisitorDao implements IDAOVisitor {
	
	public static final int VISITORID = 1;
	public static final int VISITORNAME = 2;
	public static final int LOGIN = 3;
	public static final int PASSWORD = 4;
	public static final int ISADMIN = 5;
	public static final String ADDVISITOR = "insert into Visitor (visitorName, login, password, isAdmin) values (?,?,?,?)";
	public static final String ISVISITOR = "select *  from Visitor where login = ? and password = ?";
	private static final Logger log = LogManager.getLogger(VisitorDao.class.getName());
	protected final ConPool conpool = ConPool.getConPool();
	protected final String SQL_SELECT = "SELECT visitorId, visitorName, login, password, isAdmin FROM " + getTableName() + "";
	private VisitorDaoHelper visitorHelper = new VisitorDaoHelper();
	
	public String getTableName() {
        return "Visitor";
    }
		
	public Visitor findVisitor(String username) {
		return visitorHelper.findSingle(SQL_SELECT + " WHERE login = ?", new Object[]{new String(username)});
	}

	public void addVisitor(Visitor visitor) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			try {
				con = conpool.getConnectionFromPool();
				st = con.prepareStatement(ADDVISITOR);
				st.setString(1, visitor.getName());
				st.setString(2, visitor.getLogin());
				st.setString(3, visitor.getPass());
				st.setBoolean(4, visitor.isAdmin());
				st.executeUpdate();				
			} finally {
				visitorHelper.closeResourses(con, st);
			}			
		} catch (SQLException e) {
			log.error(e, e);
		}
	}

	public boolean isVisitor(String login, String password) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				con = conpool.getConnectionFromPool();
				st = con.prepareStatement(ISVISITOR);
				st.setString(1, login);
				st.setString(2, password);			
				rs = st.executeQuery();
				return rs.next();
			} finally {
				visitorHelper.closeResourses(con, st, rs);		
			}				
		} catch (SQLException e) {
			log.error(e, e);
			return false;
		}
	}

	public boolean isAdmin(String login, String password) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				con = conpool.getConnectionFromPool();
				st = con.prepareStatement(ISVISITOR);
				st.setString(1, login);
				st.setString(2, password);
				rs = st.executeQuery();
				rs.next();
				return rs.getBoolean(5);
			} finally {
				visitorHelper.closeResourses(con, st, rs);	
			}				
		} catch (SQLException e) {
			log.error(e, e);
			return false;
		}
	}

	public int findVisitorId(String username) {
		return (int) visitorHelper.findField(SQL_SELECT + " WHERE login = ?", new Object[]{new String(username)}, VISITORID);
	}

	public String findVisitorName(String username) {
		return (String) visitorHelper.findField(SQL_SELECT + " WHERE login = ?", new Object[]{new String(username)}, VISITORNAME);
	}
}
