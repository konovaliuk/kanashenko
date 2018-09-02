package commands;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import manager.Config;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;

public class LogoutButtonTest {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	@Test
	public void testExecute() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session = mock(HttpSession.class);
		LogoutButton logoutButton = new LogoutButton();
		
		when(request.getSession()).thenReturn(session);				
		String page = logoutButton.execute(request, response);
		
		assertThat(page, is(Config.getInstance().getProperty(Config.LOGIN)));		
		verify(session, times(1)).invalidate();
	}

}
