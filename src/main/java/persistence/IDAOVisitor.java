/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package persistence;

import entity.Visitor;

public interface IDAOVisitor {
	  Visitor findVisitor(String uname);  
	  int findVisitorId(String uname);	  
	  void addVisitor(Visitor visitor);	  
	  boolean isVisitor(String login, String password);	  
	  public boolean isAdmin(String login, String password);	  
	  String findVisitorName(String uname);
	  public String getTableName();
	
}
