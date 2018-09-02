/**
 *This is DAO, which provides methods for manipulating
 *<code>ExpoHall</code> entity.
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
import entity.ExpoHall;
import persistence.IDAOExpoHall;

public class ExpoHallDao implements IDAOExpoHall {
	
	public static final int EXPOHALLID = 1;
	public static final int EXPOHALLNAME = 2;
	public static final int EXPOHALL_EXHIB_ID = 3;
	public static final String ADD_EXPO_HALL = "insert into ExpoHall (expoHallName, expo_exhibitionId ) values (?,?)";
	private static final Logger log = LogManager.getLogger(ExpoHallDao.class.getName());
	protected final ConPool conpool = ConPool.getConPool();
	protected final String SQL_SELECT = "SELECT expoHallId, expoHallName, expo_exhibitionId FROM " + getTableName() + "";
	private ExpoHallDaoHelper expoHelper = new ExpoHallDaoHelper();

	public String getTableName() {
        return "ExpoHall";
    }
	
	public ExpoHall[] findExpoHallsbyId(int exhibitionId ) {
		return expoHelper.findMultiple(SQL_SELECT + " WHERE expo_exhibitionId = ?", new Object[]{new Integer(exhibitionId )});
	}
		
	public void addExpoHall(ExpoHall expoHall) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			try {
				con = conpool.getConnectionFromPool();				
				st = con.prepareStatement(ADD_EXPO_HALL);
				st.setString(1, expoHall.getExpoHallName());
				st.setInt(2, expoHall.getExpo_exhibitionId());
				st.executeUpdate();
			} finally {
				expoHelper.closeResourses(con, st);
			}						
		} catch (SQLException e) {
			log.error(e,e);
		}
	}
}
