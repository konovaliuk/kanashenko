/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package persistence;

import entity.ExpoHall;

public interface IDAOExpoHall {
	public ExpoHall[] findExpoHallsbyId(int exhibitionId);
	public void addExpoHall(ExpoHall expoHall);
	public String getTableName();
}
