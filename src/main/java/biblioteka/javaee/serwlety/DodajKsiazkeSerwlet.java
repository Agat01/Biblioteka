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
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dodajKsiazke")
public class DodajKsiazkeSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/dodajKsiazke.jsp");
		dispatcher.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		res.setCharacterEncoding("UTF-8");
		
		List<String> autorzy = new ArrayList<String>();
		List<String> wydawnictwa = new ArrayList<String>();
		List<String> kategorie = new ArrayList<String>();
		List<String> ksiazki = new ArrayList<String>();
		String tytul = req.getParameter("tytul");
		String autor = req.getParameter("aut");
		String rok =req.getParameter("rok");
		String miejsce =req.getParameter("miejsce");
		String wydawnictwo =req.getParameter("wyd");
		String kategoria =req.getParameter("kat");
		String opis =req.getParameter("opis");
		
		String autorA="";
		String wydawnictwoW="";
		String kategoriaK="";
		String ksiazkaK="";
		int idAutor=0;
		int idWydawnictwo=0;
		int idKategoria=0;
		int idKsiazka=0;
		String[] arrOfStr = autor.split(" ", 2);
		  
		String imie=arrOfStr[0];
		String nazwisko=arrOfStr[1];
		
		try {
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");

			String sqlKs = "select tytul from ksiazka";
			Statement stmtKs = conn.createStatement();
			ResultSet rsKs=stmtKs.executeQuery(sqlKs);
			while(rsKs.next()){
				ksiazkaK=rsKs.getString(1);
				ksiazki.add(ksiazkaK);
			}
			rsKs.close();
			stmtKs.close();
			if(ksiazki.contains(tytul)) {
				out.println("Podana książka jest już w bazie");
			}
			else {		
			String sqlA = "select imie || ' ' || nazwisko from autor";
			Statement stmtA = conn.createStatement();
			ResultSet rsA=stmtA.executeQuery(sqlA);
			while(rsA.next()){
				autorA=rsA.getString(1);
			autorzy.add(autorA);
			}
			rsA.close();
			stmtA.close();
			if(autorzy.contains(autor)) {
				Statement id_a = conn.createStatement();

				id_a.execute("SELECT id_autor from autor where imie='"+imie+"' and nazwisko='"+nazwisko+"'");
				ResultSet rs = id_a.getResultSet();
				while (rs.next()) {
					idAutor = rs.getInt(1);
					out.println("idAutor:"+idAutor);
				}
				rs.close();
				id_a.close();
			}
			else{
				Statement id_a = conn.createStatement();

				id_a.execute("SELECT autor_seq.NEXTVAL+1 as idAutor FROM DUAL");
				ResultSet rs = id_a.getResultSet();
				while (rs.next()) {
					idAutor = rs.getInt(1);
					out.println("idAutor:"+idAutor);
				}
				rs.close();
				id_a.close();
				
			PreparedStatement stmt = conn.prepareStatement("Insert into autor(id_autor,imie,nazwisko,data_urodzenia) values(null,?,?,null)");
				stmt.setString(1, imie);
				stmt.setString(2, nazwisko);
				
				if (stmt.executeUpdate() > 0) {
					res.getWriter().println("Autor "+imie+" "+nazwisko+" został dodany");
				} else
					res.getWriter().println("nie udało się");
				
			}	
		
			String sqlW = "select nazwa from wydawnictwo";
			Statement stmtW = conn.createStatement();
			ResultSet rsW=stmtW.executeQuery(sqlW);
			while(rsW.next()){
				wydawnictwoW=rsW.getString(1);
				wydawnictwa.add(wydawnictwoW);
			}
			rsW.close();
			stmtW.close();
			if(wydawnictwa.contains(wydawnictwo)) {
				Statement id_w = conn.createStatement();

				id_w.execute("SELECT id_wydawnictwo from wydawnictwo where nazwa='"+wydawnictwo+"'");
				ResultSet rs = id_w.getResultSet();
				while (rs.next()) {
					idWydawnictwo = rs.getInt(1);
					out.println("idWydawnictwo:"+idWydawnictwo);
				}
				rs.close();
				id_w.close();
			}
			else{
				Statement id_w = conn.createStatement();

				id_w.execute("SELECT wydawnictwo_seq.NEXTVAL+1 as idWydawnictwo FROM DUAL");
				ResultSet rs = id_w.getResultSet();
				while (rs.next()) {
					idWydawnictwo = rs.getInt(1);
					out.println("idWydawnictwo:"+idWydawnictwo);
				}
				rs.close();
				id_w.close();
				
				PreparedStatement stmt2 = conn.prepareStatement("Insert into wydawnictwo values(null,?)");
				stmt2.setString(1, wydawnictwo);
				
				if (stmt2.executeUpdate() > 0) {
					res.getWriter().println("Wydawnictwo "+wydawnictwo+" zostało dodane");
				} else
					res.getWriter().println("nie udało się");			
			}	
			
			String sqlK = "select nazwa from kategoria";
			Statement stmtK = conn.createStatement();
			ResultSet rsK=stmtK.executeQuery(sqlK);
			while(rsK.next()){
				kategoriaK=rsK.getString(1);
				kategorie.add(kategoriaK);
			}
			rsK.close();
			stmtK.close();
			if(kategorie.contains(kategoria)) {
				Statement id_k = conn.createStatement();

				id_k.execute("SELECT id_kategoria from kategoria where nazwa='"+kategoria+"'");
				ResultSet rs = id_k.getResultSet();
				while (rs.next()) {
					idKategoria = rs.getInt(1);
					out.println("idKategoria:"+idKategoria);
				}
				rs.close();
				id_k.close();
			}
			else{
				Statement id_k = conn.createStatement();

				id_k.execute("SELECT kategoria_seq.NEXTVAL+1 as idKategoria FROM DUAL");
				ResultSet rs = id_k.getResultSet();
				while (rs.next()) {
					idKategoria = rs.getInt(1);
					out.println("idKategoria:"+idKategoria);
				}
				rs.close();
				id_k.close();
				
				PreparedStatement stmt2 = conn.prepareStatement("Insert into kategoria values(null,?)");
				stmt2.setString(1, kategoria);
				
				if (stmt2.executeUpdate() > 0) {
					res.getWriter().println("Kategoria "+kategoria+" została dodana");
				} else
					res.getWriter().println("nie udało się");
				
			}	
			
			PreparedStatement stmt3 = conn.prepareStatement("Insert into ksiazka values(null,?,?,?,?,?,?)");
			stmt3.setString(1, tytul);
			stmt3.setString(2, rok);
			stmt3.setString(3, miejsce);
			stmt3.setInt(4, idWydawnictwo);
			stmt3.setInt(5, idKategoria);
			stmt3.setString(6, opis);
			
			if (stmt3.executeUpdate() > 0) {
				res.getWriter().println("Książka "+tytul+" została dodana");
				out.println("Dane książki:");
				out.println(tytul);
				out.println("autor: "+imie+" "+nazwisko);
				out.println(rok);
				out.println(miejsce);
				out.println(wydawnictwo);
				out.println(kategoria);
			} else
				res.getWriter().println("nie udało się");
				
			Statement id_ks = conn.createStatement();

			id_ks.execute("SELECT ksiazka_seq.NEXTVAL-1 as idKsiazka FROM DUAL");
			ResultSet rs = id_ks.getResultSet();
			while (rs.next()) {
				idKsiazka = rs.getInt(1);
				out.println("idKsiazka:"+idKsiazka);
			}
			rs.close();
			id_ks.close();
			
			PreparedStatement stmt4 = conn.prepareStatement("Insert into ksiazka_autor values(null,?,?)");
			stmt4.setInt(1, idKsiazka);
			stmt4.setInt(2, idAutor);
			if (stmt4.executeUpdate() > 0) {
				res.getWriter().println("ksiazka i autor zostaly dodane");
				out.println(tytul);
				out.println("autor: "+imie+" "+nazwisko);
			} else
				res.getWriter().println("nie udało się");
		}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

}
