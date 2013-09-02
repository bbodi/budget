package hu.nevermind.budget;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// do nothing
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws ServletException, IOException {
		final Boolean isLogined = (Boolean)((HttpServletRequest)request).getSession().getAttribute("isLogined");

		final boolean isLoginPage = ((HttpServletRequest) request).getRequestURI().endsWith("login.xhtml");
		final boolean isResourceRequest = ((HttpServletRequest) request).getRequestURI().contains("javax.faces.resource");
		if ((isLogined == null || !isLogined) && !isLoginPage && !isResourceRequest) {
			((HttpServletRequest) request).getSession().setAttribute("isLogined", true);
			final String contextPath = ((HttpServletRequest)request).getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(final FilterConfig config) throws ServletException {
		// do nothing
	}

}
