package biblioteka.javaee.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SecurityConfig {
	public static final String ROLA_ADMIN = "ADMIN";
	public static final String ROLA_BIBLIOTEKARZ = "BIBLIOTEKARZ";
	public static final String ROLA_CZYTELNIK = "CZYTELNIK";
	
//	public static final String ROLA_ADMIN2 = "ADMIN2";
	
	private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

	static {
		init();
	}
	private static void init() {
		mapConfig.clear();
		
		List<String> urlPatterns1 = new ArrayList<String>();
		urlPatterns1.add("/wypozycz");
		urlPatterns1.add("/zwrot");
		urlPatterns1.add("/czytelnik");
		urlPatterns1.add("/czytelnik2");

		mapConfig.put(ROLA_BIBLIOTEKARZ, urlPatterns1);

		List<String> urlPatterns2 = new ArrayList<String>();
		urlPatterns2.add("/wypozycz");
		urlPatterns2.add("/zwrot");
		urlPatterns2.add("/czytelnik");
		urlPatterns2.add("/czytelnik2");
		urlPatterns2.add("/dodajKsiazke");
		urlPatterns2.add("/dodajUzytkownika");
		urlPatterns2.add("/dodajEgzemplarz");

		mapConfig.put(ROLA_ADMIN, urlPatterns2);
		
		List<String> urlPatterns3 = new ArrayList<String>();
		urlPatterns3.add("/czytelnik");
		urlPatterns3.add("/dane2.jsp");
		
		mapConfig.put(ROLA_CZYTELNIK, urlPatterns3);
	}

	public static Set<String> getAllAppRoles() {
		return mapConfig.keySet();
	}

	public static List<String> getUrlPatternsForRole(String role) {
		return mapConfig.get(role);
	}


}
