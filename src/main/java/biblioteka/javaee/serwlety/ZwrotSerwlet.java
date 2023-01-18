package biblioteka.javaee.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/zwrot")
public class ZwrotSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/zwrot.jsp");
		dispatcher.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");;
		PrintWriter out = res.getWriter();

		String id_czytelnik = req.getParameter("id_czytelnik");
		String isbn = req.getParameter("isbn");
//		String data=req.getParameter("data_zwrotu");

		int id_wypozyczenie=0;
		
		try {
		//	java.util.Date data_zwrotu = new SimpleDateFormat("yyyy-MM-dd").parse(data);
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

			String sql = "SELECT id_wypozyczenie\r\n"
					+ "FROM wypozyczenie\r\n"
					+ "WHERE data_zwrotu IS NULL and id_czytelnik="+id_czytelnik+" and isbn='"+isbn+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				id_wypozyczenie=rs.getInt(1);
			}
			rs.close();
			stmt.close();
			
			
			PreparedStatement stmt2 = conn.prepareStatement("UPDATE wypozyczenie \r\n"
					+ "SET data_zwrotu = current_date \r\n"
					+ "WHERE id_wypozyczenie=? AND data_zwrotu IS NULL");
			
				stmt2.setInt(1, id_wypozyczenie);
		
				if (stmt2.executeUpdate() > 0) {
					out.println("Książka została zwrócona :");
					out.println(id_wypozyczenie);
				
				} else
					res.getWriter().println("nie udało się");
				
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	
}

}
