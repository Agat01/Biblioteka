package biblioteka.javaee.filtry;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biblioteka.javaee.encje.Uzytkownik;
import biblioteka.javaee.request.UserRoleRequestWrapper;
import biblioteka.javaee.utils.*;
@WebFilter("/*")
public class SecurityFilter implements Filter {

	public SecurityFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String servletPath = request.getServletPath();

		// User information stored in the Session.
		// (After successful login).
		Uzytkownik zalogowanyU = AppUtils.getLoginedUser(request.getSession());

		if (servletPath.equals("/strona.jsp")) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest wrapRequest = request;

		if (zalogowanyU != null) {
			// User Name
			String nazwa_uzytkownika = zalogowanyU.getNazwa_uzytkownika();

			// Roles
			List<String> role = zalogowanyU.getRole();

			// Wrap old request by a new Request with userName and Roles information.
			wrapRequest = new UserRoleRequestWrapper(nazwa_uzytkownika, role, request);
		}

		// Pages must be signed in.
		if (SecurityUtils.isSecurityPage(request)) {

			// If the user is not logged in,
			// Redirect to the login page.
			if (zalogowanyU == null) {

				String requestUri = request.getRequestURI();

				// Store the current page to redirect to after successful login.
				int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);

				response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
				return;
			}

			// Check if the user has a valid role?
			boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
			if (!hasPermission) {

				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/brakDostepu.jsp");

				dispatcher.forward(request, response);
				return;
			}
		}

		chain.doFilter(wrapRequest, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}
}
