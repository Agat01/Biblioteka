package biblioteka.javaee.serwlety;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biblioteka.javaee.encje.Ksiazka;

@WebServlet("/dodajEgzemplarz")
public class DodajEgzemplarzSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/dodajEgzemplarz.jsp");
		dispatcher.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		res.setCharacterEncoding("UTF-8");
	
		List<String> stany = new ArrayList<String>();
		List<String> egzemplarze = new ArrayList<String>();
		String isbn = req.getParameter("isbn");
		String ksiazka = req.getParameter("ks");
		String opis=req.getParameter("opis");
		String stan =req.getParameter("st");
		out.println(isbn);
		out.println(ksiazka);
		out.println(opis);
		out.println(stan);
	
		String isbnS="";
		int idKsiazka=0;
		int idStan=0;
		String stanS="";
		
		String[] arrOfStr = ksiazka.split(", ");
		  
		String tytul=arrOfStr[0];
		String autor=arrOfStr[1];
		String wydawnictwo=arrOfStr[2];
		out.println(tytul);
		out.println(autor);
		out.println(wydawnictwo);
		
		
		try {
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

			String sqlKs = "select isbn from egzemplarz";
			Statement stmtKs = conn.createStatement();
			ResultSet rsKs=stmtKs.executeQuery(sqlKs);
			while(rsKs.next()){
				isbnS=rsKs.getString(1);
				egzemplarze.add(isbnS);
			}
			rsKs.close();
			stmtKs.close();
			
			String sqlSt = "select nazwa from stan_egzemplarza";
			Statement stmtSt = conn.createStatement();
			ResultSet rsSt=stmtSt.executeQuery(sqlSt);
			while(rsSt.next()){
				stanS=rsSt.getString(1);
				stany.add(stanS);
			}
			rsSt.close();
			stmtSt.close();
			
			if(egzemplarze.contains(isbn)) {
				out.println("Podany egzemplarz jest już w bazie");
			}
			else {		
			String sql= "select k.id_ksiazka \r\n"
					+ "from ksiazka k join ksiazka_autor ka on(ka.id_ksiazka=k.id_ksiazka)\r\n"
					+ "            join autor a on(a.id_autor=ka.id_autor)\r\n"
					+ "            join wydawnictwo w on(w.id_wydawnictwo=k.id_wydawnictwo)\r\n"
					+ "where tytul='"+tytul+"' and a.imie || ' ' || a.nazwisko= '"+autor+"' and w.nazwa='"+wydawnictwo+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				idKsiazka=rs.getInt(1);
			}
			rs.close();
			stmt.close();
			
			
			if(stany.contains(stan)) {
				Statement id_s = conn.createStatement();

				id_s.execute("SELECT id_stan from stan_egzemplarza where nazwa='"+stan+"'");
				ResultSet rs2 = id_s.getResultSet();
				while (rs2.next()) {
					idStan = rs2.getInt(1);
					out.println("idStan:"+idStan);
				}
				rs.close();
				id_s.close();
			}
			else{
				Statement id_s = conn.createStatement();

				id_s.execute("SELECT stan_egzemplarza_seq.NEXTVAL+1 as idStan FROM DUAL");
				ResultSet rs2 = id_s.getResultSet();
				while (rs2.next()) {
					idStan = rs2.getInt(1);
					out.println("idStan:"+idStan);
				}
				rs2.close();
				id_s.close();
				
				
			PreparedStatement stmt2 = conn.prepareStatement("Insert into stan_egzemplarza(id_stan,nazwa) values(null,?)");
				stmt2.setString(1, stan);
				
				if (stmt2.executeUpdate() > 0) {
					res.getWriter().println("Stan "+stan+" został dodany");
				} else
					res.getWriter().println("nie udało się");
				
			}	
		
			
			PreparedStatement stmt3 = conn.prepareStatement("Insert into egzemplarz values(?,?,?,?)");
			stmt3.setString(1, isbn);
			stmt3.setInt(2, idKsiazka);
			stmt3.setString(3, opis);
			stmt3.setInt(4, idStan);
			
			if (stmt3.executeUpdate() > 0) {
				res.getWriter().println("Egzemplarz "+isbn+" został dodany");
				out.println("Dane egzemplarza:");
				out.println(isbn);
				out.println(tytul);
				out.println("autor: "+autor);
				out.println(opis);
				out.println(stan);
			} else
				res.getWriter().println("nie udało się");
			}	
			conn.close();
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
	}

}
