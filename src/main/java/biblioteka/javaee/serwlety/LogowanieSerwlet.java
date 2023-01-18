package biblioteka.javaee.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biblioteka.javaee.encje.Uzytkownik;
import biblioteka.javaee.utils.AppUtils;
import biblioteka.javaee.utils.UzytkownikDAO;

@WebServlet("/login")
public class LogowanieSerwlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/strona.jsp");
			dispatcher.forward(request, response);
	}

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
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String nazwa_uzytkownika = request.getParameter("nazwa_uzytkownika");
		String haslo = request.getParameter("haslo");
		String hashHaslo="";
		hashHaslo=getMd5(haslo);
		Uzytkownik u = UzytkownikDAO.znajdzUzytkownika(nazwa_uzytkownika, hashHaslo);

		if (u == null) {
			String errorMessage = "Nieprawidłowa nazwa użytkownika lub hasło";

			request.setAttribute("errorMessage", errorMessage);

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/strona.jsp");

			dispatcher.forward(request, response);
			return;
		}

		AppUtils.storeLoginedUser(request.getSession(), u);

		int redirectId = -1;
		try {
			redirectId = Integer.parseInt(request.getParameter("redirectId"));
		} catch (Exception e) {
		}
		String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
		if (requestUri != null) {
			response.sendRedirect(requestUri);
		} else {
			// Default after successful login
			// redirect to /userInfo page
			response.sendRedirect(request.getContextPath() + "/witaj.jsp");
		}

	}

}