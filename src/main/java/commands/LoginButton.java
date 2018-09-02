/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.DaoService;
import service.ServiceFactory;
import entity.Exhibition;
import entity.ExpoHall;
import local.LocaleHelper;
import manager.Config;

public class LoginButton implements IButton {
	private DaoService daoService;
	
	private static final Logger LOGGER = LogManager.getLogger(LoginButton.class);
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String LOGIN_ERROR = "LOGIN_ERROR";
	
	public LoginButton() {
	}
	
	public LoginButton(DaoService daoService) {
		this.daoService = daoService;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		if(daoService == null) {
			ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
			daoService = serviceFactory.getDaoService();
		}
		
		HttpSession session = request.getSession(true);
		String page = null;
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);

		if (daoService.isVisitor(login, password)) {
			session.setAttribute("username", login);
			session.setAttribute("isAdmin", daoService.isAdmin(login, password));
			LOGGER.debug("Validation was done successfully");
			if (daoService.isAdmin(login, password)) {
				page = Config.getInstance().getProperty(Config.ADMIN_PANEL);
				LOGGER.debug("User logged in as administrator");
			} else {
				page = Config.getInstance().getProperty(Config.MAIN);
				LOGGER.debug("User " +login+" logged in as visitor");
				String visitorName = daoService.findVisitorName(login);
				session.setAttribute("visitorName", visitorName);
				
				session.setAttribute("exhibitions", daoService.findAllExhibition());
				Collection<ExpoHall[]> expoarrays = new ArrayList<ExpoHall[]>();
				Collection<Integer> maxTicketsList = new ArrayList<Integer>();
				for (Exhibition item : daoService.findAllExhibition()) {
					// fetch expohalls from each exhibition
					expoarrays.add(daoService.findExpoHall(item.getExhibId()));
					// fetch tickets
					int tickets = daoService.howManyTickets(item.getExhibId());
					maxTicketsList.add(tickets);
				}
				int[] maxTicketsArray = maxTicketsList.stream().filter(i -> i != null).mapToInt(i -> i).toArray();
				session.setAttribute("expohalls", expoarrays);
				session.setAttribute("maxTicketsList", maxTicketsList);
				session.setAttribute("maxTicketsArray", maxTicketsArray);
			}
		} else {
			ResourceBundle bundle = LocaleHelper.getBundle(session.getAttribute("language"));
			request.setAttribute("message", bundle.getString(LOGIN_ERROR));
			page = Config.getInstance().getProperty(Config.LOGIN);
			LOGGER.debug("Validation failed");
		}
		return page;
	}
}
