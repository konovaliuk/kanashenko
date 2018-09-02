/**
 * Utilized by <code>ExhibitionDao</code>
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import connection.ConPool;
import entity.Exhibition;

public class ExhibitionDaoHelper extends DaoHelper {
	public static final int EXHIBITIONID = 1;
	public static final int EXHIBITIONRNAME = 2;
	public static final int EXHIBITIONPRICE = 3;
	public static final int EXHIBITION_START = 4;
	public static final int EXHIBITION_END = 5;
	protected final ConPool conpool = ConPool.getConPool();
	private static final Logger log = LogManager.getLogger(ExhibitionDaoHelper.class.getName());
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	public  Exhibition[] findMultiple(String sql){
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				con = conpool.getConnectionFromPool();
				final String SQL = sql;
				st = con.prepareStatement(SQL);				
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
	
	public  Exhibition findSingle(String sql, Object[] sqlParams){
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
	
	protected Exhibition[] fetchMultiResults(ResultSet rs) throws SQLException {
		List<Exhibition> resultList = new ArrayList<>();
			while (rs.next()) {
				Exhibition dto = new Exhibition();
	            populateDto(dto, rs);
	            resultList.add(dto);
			}
		Exhibition arr[] = new Exhibition[resultList.size()];
		resultList.toArray(arr);
		Arrays.sort(arr);	
        return arr;
    }
	
	protected Exhibition fetchSingleResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			Exhibition dto = new Exhibition();
			populateDto(dto, rs);	
			return dto;
		}
		else {
			return null;
		}		
	}
	
	protected void populateDto(Exhibition exhibition, ResultSet rs) throws SQLException {
		exhibition.setExhibId(rs.getInt(EXHIBITIONID));
		exhibition.setExhibitionName(rs.getString(EXHIBITIONRNAME));
		exhibition.setPrice(rs.getInt(EXHIBITIONPRICE));
		exhibition.setExhibStart(LocalDate.parse(rs.getString(EXHIBITION_START), formatter));		
		exhibition.setExhibEnd(LocalDate.parse(rs.getString(EXHIBITION_END), formatter));
	}	
}
