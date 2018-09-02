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
import service.DaoService;
import service.PaymentService;
import service.ServiceFactory;
import entity.Exhibition;
import local.LocaleHelper;
import manager.Config;

public class BuyButton implements IButton {
	private DaoService daoService;
	PaymentService paymentService;
	private static final String LOGIN = "username";
	private static final String TOTAL_PRICE = "totalPrice";
	private static final String EXHIBITIONS = "choosenexhibs";
	private static final String AMOUNT_TICKETS = "choosenTickets";
	private static final String PURCHASE_SUCCESS = "PURCHASE_SUCCESS";
	private static final String PURCHASE_ERROR = "PURCHASE_ERROR";
	
	public BuyButton() {
	}
	
	public BuyButton(DaoService daoService, PaymentService paymentService) {
		this.daoService = daoService;
		this.paymentService = paymentService;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		if(daoService == null || paymentService == null) {
			ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
			daoService = serviceFactory.getDaoService();
			paymentService = serviceFactory.getPaymentService();
		}
		
		String page = null;
		HttpSession session = request.getSession(true);
		ResourceBundle bundle = LocaleHelper.getBundle(session.getAttribute("language"));
		int visitorId= daoService.findVisitorId((String)session.getAttribute(LOGIN));
		int totalPrice = (Integer) session.getAttribute(TOTAL_PRICE);
		@SuppressWarnings("unchecked")
		Collection<Exhibition> choosenexhibs = (ArrayList<Exhibition>) session.getAttribute(EXHIBITIONS);
		@SuppressWarnings("unchecked")
		Collection<Integer> choosenTickets = (ArrayList<Integer>) session.getAttribute(AMOUNT_TICKETS);

		if (paymentService.performTransaction(visitorId, totalPrice, choosenexhibs, choosenTickets)) {
			request.setAttribute("message", bundle.getString(PURCHASE_SUCCESS));
			page = Config.getInstance().getProperty(Config.RESULT);	
		} else {
			request.setAttribute("message", bundle.getString(PURCHASE_ERROR));
			page = Config.getInstance().getProperty(Config.RESULT);		
		}
		return page;
	}
}