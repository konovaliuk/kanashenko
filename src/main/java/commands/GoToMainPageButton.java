/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entity.Exhibition;
import manager.Config;
import service.DaoService;
import service.ServiceFactory;

public class GoToMainPageButton implements IButton  {
	
	private DaoService daoService;
	
	public GoToMainPageButton() {
	}
	
	public GoToMainPageButton(DaoService daoService) {
		this.daoService = daoService;
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(daoService == null) {
			ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
			daoService = serviceFactory.getDaoService();
		}
		
		String page;
		HttpSession session = request.getSession(true);
				
		Collection<Integer> maxTicketsList = new ArrayList<Integer>();
		//update tickets
		for (Exhibition item : daoService.findAllExhibition()) {
			// fetch tickets
			int tickets = daoService.howManyTickets(item.getExhibId());
			maxTicketsList.add(tickets);
		}
		int[] maxTicketsArray = maxTicketsList.stream().filter(i -> i != null).mapToInt(i -> i).toArray();

		session.setAttribute("maxTicketsList", maxTicketsList);
		session.setAttribute("maxTicketsArray", maxTicketsArray);
		
		page = Config.getInstance().getProperty(Config.MAIN);
		return page;
	}
}
