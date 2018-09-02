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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.DaoService;
import service.ServiceFactory;
import entity.Visitor;
import local.LocaleHelper;
import manager.Config;
import persistence.daoImpl.VisitorDao;

public class RegisterButton implements IButton {
	private DaoService daoService;
	private static final Logger LOGGER = LogManager.getLogger(VisitorDao.class.getName());
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String NAME = "name";
	private static final String REGISTER_SUCCESS = "REGISTER_SUCCESS";
	private static final String REGISTER_ERROR = "REGISTER_ERROR";
	private static final String USER_EXISTS = "USER_EXISTS";
	
	public RegisterButton() {
	}
	
	public RegisterButton(DaoService daoService) {
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
			
		
		if(login =="" || password == "" || name == "") {
			request.setAttribute("message", bundle.getString(REGISTER_ERROR));
            page = Config.getInstance().getProperty(Config.REGISTER);
		}
		else if (daoService.isVisitor(login, password)) {
			request.setAttribute("message", login + bundle.getString(USER_EXISTS));
            page = Config.getInstance().getProperty(Config.LOGIN);
           
        } else {
        	Visitor visitor = new Visitor();
        	visitor.setName(name);
        	System.out.print("in RegisterButton "+name);
        	visitor.setLogin(login);
        	visitor.setPass(password);
        	daoService.addVisitor(visitor);
        	LOGGER.info("new visitor " + visitor.getLogin() + " registered");
			request.setAttribute("message", bundle.getString(REGISTER_SUCCESS));
            page = Config.getInstance().getProperty(Config.LOGIN);
        }
	return page;
	}
}
