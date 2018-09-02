/**
 * @version 1.0
 *
 * @date Aug 30, 2018
 *
 * Copyright by Mykyta Kanashchenko
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(servletNames = "Controller",  urlPatterns = "/*")
public class EncodingFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(EncodingFilter.class);
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
	public void init(FilterConfig arg0) throws ServletException {
    	LOGGER.info("EncodingFilter init");
	}
    
	@Override
	public void destroy() {
		LOGGER.info("EncodingFilter destroy");
	}	
}

