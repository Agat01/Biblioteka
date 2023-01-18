package biblioteka.javaee.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import biblioteka.javaee.config.SecurityConfig;
import biblioteka.javaee.encje.Uzytkownik;

public class UzytkownikDAO {

	public static String getMd5(String input)
    {
        try {
 
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	 private static final Map<String, Uzytkownik> mapUzytkownicy = new HashMap<String, Uzytkownik>();

	   static {
	      initUzytkownicy();
	   }

	private static void initUzytkownicy() {
		
		String nazwa_uzytkownika="";
		String hashHaslo="";
		int id_rola=0;
		String rola="";
		
		try {
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

			String sql = "select nazwa_uzytkownika, hashHaslo, id_rola from uzytkownik";
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				nazwa_uzytkownika=rs.getString(1);
				hashHaslo=rs.getString(2);
				id_rola=rs.getInt(3);
				if(id_rola==1){
					rola=SecurityConfig.ROLA_ADMIN;
				}
				else if(id_rola==2) {
					rola=SecurityConfig.ROLA_BIBLIOTEKARZ;
				}
				else if(id_rola==3) {
					rola=SecurityConfig.ROLA_CZYTELNIK;
				}
				
				Uzytkownik uzytkownik=new Uzytkownik(nazwa_uzytkownika,hashHaslo,rola);
				mapUzytkownicy.put(uzytkownik.getNazwa_uzytkownika(),uzytkownik);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Uzytkownik admin=new Uzytkownik("admin",hashHaslo=getMd5("admin"),SecurityConfig.ROLA_ADMIN);
		mapUzytkownicy.put(admin.getNazwa_uzytkownika(), admin);
	
	}
	public static Uzytkownik znajdzUzytkownika(String nazwa_uzytkownika,String haslo) {
		Uzytkownik u=mapUzytkownicy.get(nazwa_uzytkownika);
		if(u!=null&&u.getHaslo().equals(haslo)) {
			return u;
		}
		return null;
	}
}
