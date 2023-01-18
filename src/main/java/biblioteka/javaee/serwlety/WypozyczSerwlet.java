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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/wypozycz")
public class WypozyczSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/wypozycz.jsp");

		dispatcher.forward(req, res);
	}
	 public static Date addDays(Date date, int days) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.DATE, days);
	        return new Date(c.getTimeInMillis());
	    }


	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");;
		PrintWriter out = res.getWriter();
		String id_czytelnik=req.getParameter("id_czytelnik");
		String email=req.getParameter("email");
		String isbn = req.getParameter("isbn");
		String data=req.getParameter("data_wypozyczenia");
		int id=0;
		String isbn2="";
		List<String> wypozyczone = new ArrayList<String>();
		try {
			java.util.Date data_wypozyczenia = new SimpleDateFormat("yyyy-MM-dd").parse(data);
			java.util.Date data_plan_zwrotu=this.addDays(new java.sql.Date(data_wypozyczenia.getTime()), 30);
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

			if(id_czytelnik!=null && id_czytelnik!="")
				id=Integer.parseInt(id_czytelnik);
			else {
				String sql = "select id_czytelnik \r\n"
						+"from czytelnik \r\n"
						+"where email='"+email+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				id=rs.getInt(1);
			}
			rs.close();
			stmt.close();
			}
			
			String sql="select isbn\r\n"
					+ "from wypozyczenie\r\n"
					+ "where data_zwrotu is null";
			Statement stmt2 = conn.createStatement();
			ResultSet rs2=stmt2.executeQuery(sql);
			while(rs2.next()){
				isbn2=rs2.getString(1);
				wypozyczone.add(isbn2);
			}
			rs2.close();
			stmt2.close();
			
			if(!wypozyczone.contains(isbn)) {
			PreparedStatement stmt = conn.prepareStatement("Insert into wypozyczenie values(null,?,?,?,?,null)");
			
				stmt.setInt(1, id);
				stmt.setString(2, isbn);
				stmt.setDate(3,new java.sql.Date(data_wypozyczenia.getTime()));
				stmt.setDate(4,(Date) data_plan_zwrotu);
			
				if (stmt.executeUpdate() > 0) {
					res.getWriter().println("Wypożczenie zostało dodane");
					out.println("Dane :");
					out.println(id);
					out.println(isbn);
					out.println(data_wypozyczenia);
					out.println(data_plan_zwrotu);				
						
				} else
					res.getWriter().println("nie udało się");
			} else
				res.getWriter().println("Ten egzemplarz jest już wypożyczony");
			
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
}

}
