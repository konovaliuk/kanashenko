/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */

package commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.DaoService;
import service.ServiceFactory;
import entity.ExpoHall;
import manager.Config;

public class AddExpoHallButton implements IButton {
	
	private DaoService daoService;	
	private static final Logger LOGGER = LogManager.getLogger(AddExpoHallButton.class);
	private static final String NAMES = "names";
	private static final String EXHIBITION = "exhibName";
	
	public AddExpoHallButton() {
	}
	
	public AddExpoHallButton(DaoService daoService) {
		this.daoService = daoService;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		if(daoService == null) {
			ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
			daoService = serviceFactory.getDaoService();
		}
		
		String page;
		String exhibition = request.getParameter(EXHIBITION);
		String names = request.getParameter(NAMES);
		String[] expoHalls =  names.split(",");
		
		for(int i = 0; i < expoHalls.length; i++ ) {
			ExpoHall expoHall = new ExpoHall();
			expoHall.setExpoHallName(expoHalls[i]);		
			expoHall.setExpo_exhibitionId(daoService.findExhibitionbyName(exhibition).getExhibId());
			daoService.addExpoHall(expoHall);
		}
		LOGGER.info("Expo Halls "+names+" were added for "+exhibition+" exhibition");
		page = Config.getInstance().getProperty(Config.EXPOS);
		return page;
	}
}
