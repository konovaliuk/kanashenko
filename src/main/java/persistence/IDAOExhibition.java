/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package persistence;

import entity.Exhibition;

public interface IDAOExhibition {
	public Exhibition[] findAllExhibition();
	public Exhibition findExhibitionbyName(String name);
	public void addExhibition(Exhibition exhibition);
	public String getTableName();
}
