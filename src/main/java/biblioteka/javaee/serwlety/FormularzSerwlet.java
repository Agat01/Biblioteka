package biblioteka.javaee.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/formularz")
public class FormularzSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/plain; charset=utf-8");
		PrintWriter out = res.getWriter();
		res.getWriter().append("Served at: ").append(req.getContextPath());
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
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");;
		PrintWriter out = res.getWriter();
	
		String imie = req.getParameter("imie");
		String nazwisko = req.getParameter("nazwisko");
		String data=req.getParameter("data_urodzenia");
		
		String pesel=req.getParameter("pesel");
		String ulica = req.getParameter("ulica");
		String nrDomu = req.getParameter("nrDomu");
		String kodPocztowy = req.getParameter("kodPocztowy");
		String miejscowosc = req.getParameter("miejscowosc");
		String email = req.getParameter("email");
		String telefon = req.getParameter("telefon");
		
		String haslo = req.getParameter("haslo");
		String haslo2 = req.getParameter("powtorzHaslo");

		String hashHaslo = "";
		int idRola=3;

		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//			String data_urodzenia = sdf.format(data);
			java.util.Date data_urodzenia = new SimpleDateFormat("yyyy-MM-dd").parse(data);
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

			if (haslo.equals(haslo2)) {
			PreparedStatement stmt = conn.prepareStatement("Insert into czytelnik values(null,?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement stmt2 = conn.prepareStatement("Insert into uzytkownik values(null,?,?,?)");

				stmt.setString(1, imie);
				stmt.setString(2, nazwisko);
				stmt.setDate(3,new java.sql.Date(data_urodzenia.getTime()));
				stmt.setString(4, pesel);
				stmt.setString(5, ulica);
				stmt.setString(6, nrDomu);
				stmt.setString(7, kodPocztowy);
				stmt.setString(8, miejscowosc);
				stmt.setString(9, email);
				stmt.setString(10, telefon);
			
				if (stmt.executeUpdate() > 0) {
					res.getWriter().println("Czytelnik został dodany");
					out.println("Dane :");
					out.println(imie);
					out.println(nazwisko);
					out.println(data_urodzenia);
					out.println(pesel);				
					out.println(ulica);
					out.println(nrDomu);
					out.println(kodPocztowy);
					out.println(miejscowosc);
					out.println(email);
					out.println(telefon);	
				} else
					res.getWriter().println("nie udało się");
				
				hashHaslo=getMd5(haslo);
				stmt2.setString(1, email);
				stmt2.setString(2, hashHaslo);
				stmt2.setInt(3, idRola);

				if (stmt2.executeUpdate() > 0) {
				
					out.println("-----------------------------------------------------------");
					res.getWriter().println("Dane logowania:");
					out.println(email);
					out.println(haslo);
					out.println(idRola);
					
					out.println("hashHaslo:"+hashHaslo);
				
				} else
					res.getWriter().println("nie udało się");
				
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
}
}
