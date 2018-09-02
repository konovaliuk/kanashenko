/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */

package commands;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.DaoService;
import service.ServiceFactory;
import entity.Visitor;
import local.LocaleHelper;
import manager.Config;

public class RegisterAdminButton implements IButton {
	private DaoService daoService;
	
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String NAME = "name";
	private static final String ADMIN_KEY = "adminKey";
	private static final String REGISTER_SUCCESS = "REGISTER_SUCCESS";
	private static final String REGISTER_ERROR = "REGISTER_ERROR";
	
	public RegisterAdminButton() {
	}
	
	public RegisterAdminButton(DaoService daoService) {
		this.daoService = daoService;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		if(daoService == null) {
			ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();
			daoService = serviceFactory.getDaoService();
		}
		
		String page = null;
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);
		String name = request.getParameter(NAME);
		HttpSession session = request.getSession(true);
		ResourceBundle bundle = LocaleHelper.getBundle(session.getAttribute("language"));
		
		if (login == "" || password == "" || name == "") {
			request.setAttribute("message", bundle.getString(REGISTER_ERROR));
			page = Config.getInstance().getProperty(Config.ADMIN_REGISTER);			
		}else if(!adminKey.equals(Config.getInstance().getProperty(Config.ADMINKEY))) {		
			request.setAttribute("message", bundle.getString(ADMIN_KEY_ERROR));
			page = Config.getInstance().getProperty(Config.ADMIN_REGISTER);
		} else {
			if (daoService.isVisitor(login, password)) {
				request.setAttribute("message", "User with name " + "'" + login + "'" + " already exists");
				page = Config.getInstance().getProperty(Config.LOGIN);
			} else {
				Visitor visitor = new Visitor();
				visitor.setName(name);
				visitor.setLogin(login);
				visitor.setPass(password);
				visitor.setIsAdmin(true);			
				daoService.addVisitor(visitor);
				request.setAttribute("message", bundle.getString(REGISTER_SUCCESS));
				page = Config.getInstance().getProperty(Config.LOGIN);
			}
		}	
		return page;
	}
}
