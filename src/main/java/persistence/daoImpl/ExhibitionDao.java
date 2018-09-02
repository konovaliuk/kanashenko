/**
 *This is DAO, which provides methods for manipulating
 *<code>Exhibition</code> entity.
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
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import connection.ConPool;
import entity.Exhibition;
import persistence.IDAOExhibition;

public class ExhibitionDao implements IDAOExhibition {
		
	public static final String ADD_EXHIBITION = "insert into Exhibition (exhibitionName, price, exhibStart, exhibEnd) values (?,?,?,?)";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final Logger log = LogManager.getLogger(ExhibitionDao.class.getName());
	protected final ConPool conpool = ConPool.getConPool();
	protected final String SQL_SELECT = "SELECT exhibitionId, exhibitionName, price, exhibStart, exhibEnd FROM " + getTableName() + "";
	private ExhibitionDaoHelper exhibitionHelper = new ExhibitionDaoHelper();
		
	public String getTableName() {
        return "Exhibition";
    }
	
	public Exhibition[] findAllExhibition() {
		return exhibitionHelper.findMultiple(SQL_SELECT);
	}
	
	public Exhibition findExhibitionbyName(String name) {
		return exhibitionHelper.findSingle(SQL_SELECT + " WHERE exhibitionName = ?", new Object[]{new String(name)});
	}
	
	public void addExhibition(Exhibition exhibition) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			try {
				con = conpool.getConnectionFromPool();		
				st = con.prepareStatement(ADD_EXHIBITION);
				st.setString(1, exhibition.getExhibitionName());
				st.setInt(2, exhibition.getPrice());
				st.setString(3, exhibition.getExhibStart().format(formatter));
				st.setString(4, exhibition.getExhibEnd().format(formatter));
				st.executeUpdate();
			} finally {
				exhibitionHelper.closeResourses(con, st);
			}
		}catch (SQLException e) {
			log.error(e,e);
		}
	}
}
