package biblioteka.javaee.utils;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import biblioteka.javaee.config.SecurityConfig;

public class SecurityUtils {
	public static boolean isSecurityPage(HttpServletRequest request) {
		String urlPattern = UrlPatternUtils.getUrlPattern(request);

		Set<String> role = SecurityConfig.getAllAppRoles();

		for (String rola : role) {
			List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(rola);
			if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}
	public static boolean hasPermission(HttpServletRequest request) {
		String urlPattern = UrlPatternUtils.getUrlPattern(request);

		Set<String> wszystkieRole = SecurityConfig.getAllAppRoles();

		for (String rola : wszystkieRole) {
			if (!request.isUserInRole(rola)) {
				continue;
			}
			List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(rola);
			if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}
}
