package biblioteka.javaee.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biblioteka.javaee.encje.Uzytkownik;
import biblioteka.javaee.utils.AppUtils;

@WebServlet("/czytelnik")
public class CzytelnikSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Uzytkownik zalogowanyU = AppUtils.getLoginedUser(req.getSession());
		String nazwa_uzytkownika = zalogowanyU.getNazwa_uzytkownika();
		int id_rola=0;
		try {
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

		String sql="select id_rola \r\n"
				+ "from uzytkownik \r\n"
				+ "where nazwa_uzytkownika='"+nazwa_uzytkownika+"'";
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		while(rs.next()){
			id_rola=rs.getInt(1);
		}
			rs.close();
			stmt.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(id_rola==3) {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/dane2.jsp");
		dispatcher.forward(req, res);
		}
		else {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/czytelnik.jsp");
			dispatcher.forward(req, res);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();

		String id_czytelnik=req.getParameter("id_czytelnik");
		String email=req.getParameter("email");
		out.println(id_czytelnik);
		out.println(email);
		req.setAttribute("id_czytelnik", "id_czytelnik");
		req.setAttribute("email", "email");
		req.getRequestDispatcher("dane.jsp").forward(req, res);

	}

}
