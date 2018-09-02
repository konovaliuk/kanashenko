/**
 * It is a Singleton class which provides DB Connection Pool
 *
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConPool {
	private static ConPool uniquePool = null;
	private DataSource pool;
	private ConPool() {initPool();};

	public static synchronized ConPool getConPool() {
		if (uniquePool == null) {
			uniquePool = new ConPool();
		}
		return uniquePool;
	}

	private  void initPool() {
		try {
			Context context = new InitialContext();
			pool = (DataSource) context.lookup("java:comp/env/dsExpo");
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
	
	public synchronized Connection getConnectionFromPool() throws SQLException{
        return pool.getConnection();
    }
	
	 public void closeConnection(Connection connection){
	        try{
	            if(connection != null){
	                connection.close();
	            }
	        }catch(SQLException e){
	            e.printStackTrace();
	        }
	    }
}
