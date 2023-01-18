package biblioteka.javaee.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dodajUzytkownika")
public class DodajUzytkownikaSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/dodajUzytkownika.jsp");

		dispatcher.forward(req, res);
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
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");;
		PrintWriter out = res.getWriter();

		String nazwa_uzytkownika = req.getParameter("nazwa_uzytkownika");
		String haslo=req.getParameter("haslo");
		String rola = req.getParameter("id_rola");
		int id_rola=Integer.parseInt(rola);
		
		String hashHaslo = getMd5(haslo);
		try {
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

			PreparedStatement stmt = conn.prepareStatement("Insert into uzytkownik values(null,?,?,?)");
			
				stmt.setString(1, nazwa_uzytkownika);
				stmt.setString(2, hashHaslo);
				stmt.setInt(3,id_rola);
				
				if (stmt.executeUpdate() > 0) {
					res.getWriter().println("Użytkownik został dodany");
					out.println("Dane :");
					out.println(nazwa_uzytkownika);
					out.println(hashHaslo);
					out.println(id_rola);				
						
				} else
					res.getWriter().println("nie udało się");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

}
