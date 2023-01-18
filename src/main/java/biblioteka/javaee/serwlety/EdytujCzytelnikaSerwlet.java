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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/edytujCzytelnika")
public class EdytujCzytelnikaSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");;
		PrintWriter out = res.getWriter();
	
		String id_czytelnik = req.getParameter("id_czytelnik");
		String email=req.getParameter("email");
		String imie = req.getParameter("imie");
		String nazwisko = req.getParameter("nazwisko");
		String data=req.getParameter("data_urodzenia");
		
		String pesel=req.getParameter("pesel");
		String ulica = req.getParameter("ulica");
		String nrDomu = req.getParameter("nrDomu");
		String kodPocztowy = req.getParameter("kodPocztowy");
		String miejscowosc = req.getParameter("miejscowosc");
		String email2 = req.getParameter("email2");
		String telefon = req.getParameter("telefon");
		
		Date data_ur=null;
		out.println(id_czytelnik);
		out.println(email);
		out.println("Dane :");
		out.println(imie);
		out.println(nazwisko);
		out.println(data);
		out.println(pesel);				
		out.println(ulica);
		out.println(nrDomu);
		out.println(kodPocztowy);
		out.println(miejscowosc);
		out.println(email2);
		out.println(telefon);	
		
		String imieS = "";
		String nazwiskoS = "";
		Date data_urodzeniaS=null;
		String peselS="";
		String ulicaS = "";
		String nrDomuS = "";
		String kodPocztowyS = "";
		String miejscowoscS = "";
		String email2S = "";
		String telefonS = "";
		
		int id=0;
		int idS=0;

		try {
			java.util.Date data_urodzenia = new SimpleDateFormat("yyyy-MM-dd").parse(data);
			out.println(data_urodzenia);
			
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
			
			String sqlS = "select id_czytelnik, imie, nazwisko, data_urodzenia,pesel,ulica,nr_domu,kod_pocztowy,miejscowosc,email,nr_tel \r\n"
					+"from czytelnik \r\n"
					+"where id_czytelnik="+id+" or email='"+email+"'";
			Statement stmtS = conn.createStatement();
			ResultSet rsS=stmtS.executeQuery(sqlS);
			while(rsS.next()){
				idS=rsS.getInt(1);
				imieS=rsS.getString(2);
				nazwiskoS=rsS.getString(3);
				data_urodzeniaS=rsS.getDate(4);
				peselS=rsS.getString(5);
				ulicaS=rsS.getString(6);
				nrDomuS=rsS.getString(7);
				kodPocztowyS=rsS.getString(8);
				miejscowoscS=rsS.getString(9);
				email2S=rsS.getString(10);
				telefonS=rsS.getString(11);
			}
			rsS.close();
			stmtS.close();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE czytelnik\r\n"
					+ "SET imie=?,\r\n"
					+ "    nazwisko=?,\r\n"
					+ "    data_urodzenia=?,\r\n"
					+ "    pesel=?,\r\n"
					+ "    ulica=?,\r\n"
					+ "    nr_domu=?,\r\n"
					+ "    kod_pocztowy=?,\r\n"
					+ "    miejscowosc=?,\r\n"
					+ "    email=?,\r\n"
					+ "    nr_tel=? \r\n"
					+ "where id_czytelnik="+id+" or email='"+email+"'");

				stmt.setString(1, imie);
				stmt.setString(2, nazwisko);
				stmt.setDate(3,new java.sql.Date(data_urodzenia.getTime()));
				stmt.setString(4, pesel);
				stmt.setString(5, ulica);
				stmt.setString(6, nrDomu);
				stmt.setString(7, kodPocztowy);
				stmt.setString(8, miejscowosc);
				stmt.setString(9, email2);
				stmt.setString(10, telefon);
			
				if (stmt.executeUpdate() > 0) {
					res.getWriter().println("Dane czytelnika zostały poprawione");
					out.println("Dane :");
					out.println(imieS+" -> "+imie);
					out.println(nazwiskoS+" -> "+nazwisko);
					out.println(data_urodzeniaS+" -> "+data_urodzenia);
					out.println(peselS+" -> "+pesel);				
					out.println(ulicaS+" -> "+ulica);
					out.println(nrDomuS+" -> "+nrDomu);
					out.println(kodPocztowyS+" -> "+kodPocztowy);
					out.println(miejscowoscS+" -> "+miejscowosc);
					out.println(email2S+" -> "+email2);
					out.println(telefonS+" -> "+telefon);	
				} else
					res.getWriter().println("nie udało się");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}

}
