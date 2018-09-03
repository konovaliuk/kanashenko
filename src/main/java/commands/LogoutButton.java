/**
 * @version 1.0
 *
 * @date Sep 2, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.Config;

public class LogoutButton implements IButton {
	
	private static final Logger LOGGER = LogManager.getLogger(LogoutButton.class);
	private static final String LOGIN = "username";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page;
		HttpSession session = request.getSession(true);
		String login = (String) session.getAttribute(LOGIN);
		session.invalidate();
		page = Config.getInstance().getProperty(Config.LOGIN);
		LOGGER.info("User "+login+" logged out");
		return page;
	}

}
