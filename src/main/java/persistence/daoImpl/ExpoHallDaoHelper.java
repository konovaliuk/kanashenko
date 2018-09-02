/**
 * Utilized by <code>ExpoHallDao</code>
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
import entity.ExpoHall;

public class ExpoHallDaoHelper extends DaoHelper {
	public static final int EXPOHALLID = 1;
	public static final int EXPOHALLNAME = 2;
	public static final int EXPOHALL_EXHIB_ID = 3;
	protected final ConPool conpool = ConPool.getConPool();
	private static final Logger log = LogManager.getLogger(ExpoHallDaoHelper.class.getName());
		
	public  ExpoHall[] findMultiple(String sql, Object[] sqlParams){
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
				return fetchMultiResults(rs);
			} finally {
				closeResourses( con, st, rs );
			}
		}catch (SQLException ex) {
			log.error(ex, ex);
			return null;
		}
	}
	
	protected ExpoHall[] fetchMultiResults(ResultSet rs) throws SQLException {
		List<ExpoHall> resultList = new ArrayList<>();
			while (rs.next()) {
				ExpoHall dto = new ExpoHall();
	            populateDto(dto, rs);
	            resultList.add(dto);
			}
		ExpoHall arr[] = new ExpoHall[resultList.size()];
		resultList.toArray(arr);
        return arr;
    }
	
	protected void populateDto(ExpoHall expoHall, ResultSet rs) throws SQLException {
		expoHall.setExpoHallId(rs.getInt(EXPOHALLID));
		expoHall.setExpoHallName(rs.getString(EXPOHALLNAME));
		expoHall.setExpoHallId(rs.getInt(EXPOHALL_EXHIB_ID));
		
	}	
}
