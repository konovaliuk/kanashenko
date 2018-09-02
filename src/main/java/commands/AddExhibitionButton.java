/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.DaoService;
import service.ServiceFactory;
import entity.Exhibition;
import manager.Config;

public class AddExhibitionButton implements IButton {
	
	private DaoService daoService;	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final Logger LOGGER = LogManager.getLogger(AddExhibitionButton.class);
	private static final String NAME = "name";
	private static final String PRICE = "price";
	private static final String STARTDATE = "startDate";
	private static final String ENDDATE = "endDate";
	
	
	public AddExhibitionButton() {
	}
	
	public AddExhibitionButton(DaoService daoService) {
		this.daoService = daoService;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		if(daoService == null) {
			ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
			daoService = serviceFactory.getDaoService();
		}
		
		String page = null;
		Exhibition exhibition = new Exhibition();
		exhibition.setExhibitionName(request.getParameter(NAME));
		exhibition.setPrice(Integer.parseInt(request.getParameter(PRICE)));
		exhibition.setExhibStart(LocalDate.parse((request.getParameter(STARTDATE)), formatter));		
		exhibition.setExhibEnd(LocalDate.parse((request.getParameter(ENDDATE)), formatter));
		daoService.addExhibition(exhibition);
		LOGGER.info("new "+ request.getParameter(NAME)+" exhibition was added");
		page = Config.getInstance().getProperty(Config.EXHIBS);
		return page;
	}
}
