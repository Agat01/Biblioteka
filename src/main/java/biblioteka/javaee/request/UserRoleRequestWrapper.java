package biblioteka.javaee.request;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * An extension for the HTTPServletRequest that overrides the getUserPrincipal()
 * and isUserInRole(). We supply these implementations here, where they are not
 * normally populated unless we are going through the facility provided by the
 * container.
 * <p>
 * If he user or roles are null on this wrapper, the parent request is consulted
 * to try to fetch what ever the container has set for us. This is intended to
 * be created and used by the UserRoleFilter.
 * 
 * @author thein
 *
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

	private String uzytkownik;
	private List<String> role = null;
	private HttpServletRequest realRequest;

	public UserRoleRequestWrapper(String uzytkownik, List<String> role, HttpServletRequest request) {
		super(request);
		this.uzytkownik = uzytkownik;
		this.role = role;
		this.realRequest = request;
	}

	@Override
	public boolean isUserInRole(String rola) {
		if (role == null) {
			return this.realRequest.isUserInRole(rola);
		}
		return role.contains(rola);
	}

	@Override
	public Principal getUserPrincipal() {
		if (this.uzytkownik == null) {
			return realRequest.getUserPrincipal();
		}

		// Make an anonymous implementation to just return our user
		return new Principal() {
			@Override
			public String getName() {
				return uzytkownik;
			}
		};
	}
}
