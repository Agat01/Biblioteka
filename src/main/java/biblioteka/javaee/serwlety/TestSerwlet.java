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
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test.pdf")
public class TestSerwlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	//	PrintWriter out = res.getWriter();
		Date data_urodzenia;
		int idSzkola = 416;
		int idTyp = 0;
		int idNauczycielk = 0;
		int idNauczyciel = 0;
		int idJezyk=0;
		int idKlasa=0;
		String liczba_klas="";
		if(req.getParameter("liczba_klas")!=null && req.getParameter("liczba_klas")!="") {
		liczba_klas=req.getParameter("liczba_klas");
	//	out.println("liczba klas:"+liczba_klas);
		}
		String[] typ = req.getParameterValues("typ");
	
		String[] nazwaKlasy = req.getParameterValues("nazwaKlasy");
		String[] ntytul = req.getParameterValues("ntytul");
		String[] nimie = req.getParameterValues("nimie");
		String[] nnazwisko = req.getParameterValues("nnazwisko");
		String[] liczbaUczniow = req.getParameterValues("liczbaUczniow");
		String[] jezyk = req.getParameterValues("jezyk");
	//	out.println(jezyk);
		
		try {
			Driver sterownik = new oracle.jdbc.OracleDriver();
			DriverManager.registerDriver(sterownik);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "agata", "agata");
			
			Statement id_sz = conn.createStatement();
			Statement id_t = conn.createStatement();
			Statement id_nk = conn.createStatement();
			Statement id_j = conn.createStatement();
			Statement id_k = conn.createStatement();
			
			id_nk.execute("SELECT nauczyciel_seq.NEXTVAL+1 as idNauczyciela FROM DUAL");
			ResultSet rs5 = id_nk.getResultSet();
			while (rs5.next()) {
				idNauczycielk = rs5.getInt(1);
				data_urodzenia=rs5.getDate(2);
	//			out.println("id nauczyciela koordynujacego: "+idNauczycielk);
			}
			idNauczyciel=idNauczycielk;
	//		idNauczyciel2=idNauczyciel+1;
	//		out.println("id nauczyciela: "+idNauczyciel);
			
			id_k.execute("SELECT klasa_seq.NEXTVAL+1 as idKlasy FROM DUAL");
			ResultSet rs6 = id_k.getResultSet();
			while (rs6.next()) {
				idKlasa = rs6.getInt(1);
	//			 out.println(idKlasa);
			}
			/*
			PreparedStatement stmt5 = conn.prepareStatement("Insert into nauczyciel (id_nauczyciel,tytul,imie,nazwisko,email,id_szkola,czy_koordynuje) values(null,?,?,?,null,?,0)");
			PreparedStatement stmt6 = conn.prepareStatement("Insert into klasa (id_klasa,nazwa,liczba_uczniow,pref_jezyk,id_szkola,id_typ_klasy) values(null,?,?,?,?,?)");
			PreparedStatement stmt7 = conn.prepareStatement("Insert into klasa_nauczyciel (id_klasa_nauczyciel,id_klasa,id_nauczyciel) values(null,?,?)");
*/
			int liczba=Integer.parseInt(liczba_klas);
			
			for (int i = 0; i <liczba ; i++) {
				/*
					stmt5.setString(1, ntytul[i]);
					stmt5.setString(2, nimie[i]);
					stmt5.setString(3, nnazwisko[i]);
					stmt5.setInt(4, idSzkola);
	//				out.println("i="+i);
	//				out.println("typ["+i+"]= "+typ[i]);
	//				out.println("jezyk["+i+"]= "+jezyk[i]);
					idNauczyciel=idNauczyciel+i;
					idKlasa=idKlasa+i;
	//				out.println(idNauczyciel);

					if (stmt5.executeUpdate() > 0) {
						/*
						res.getWriter().println("Nauczyciel został dodany");
						out.println("Dane nauczyciela:");
						out.println(idNauczyciel);
						out.println(ntytul[i]);
						out.println(nimie[i]);
						out.println(nnazwisko[i]);
						out.println("i="+i);
*/
		//			} else
		//				out.println("nie udało się");
					
					id_t.execute("SELECT id_typ_klasy FROM typ_klasy where nazwa='" + typ[i] + "'");
					ResultSet rs4 = id_t.getResultSet();
					while (rs4.next()) {
						idTyp = rs4.getInt(1);
		//			out.println(idTyp);
					}
			
					id_j.execute("SELECT id_jezyk FROM jezyk where nazwa='" + jezyk[i] + "'");
					ResultSet rs7 = id_j.getResultSet();
					while (rs7.next()) {
						idJezyk = rs7.getInt(1);
		//				out.println(idJezyk);
					}
/*
					stmt6.setString(1, nazwaKlasy[i]);
					stmt6.setString(2, liczbaUczniow[i]);
					stmt6.setInt(3, idJezyk);
					stmt6.setInt(4, idSzkola);
					stmt6.setInt(5, idTyp);
		//			stmt6.addBatch();
		//			out.println("i="+i);

					if (stmt6.executeUpdate() > 0) {
						/*
						res.getWriter().println("Klasa została dodana");
						out.println("Dane klasy:");
						out.println(typ[i]);
						out.println(nazwaKlasy[i]);
						out.println(liczbaUczniow[i]);
						out.println(jezyk[i]);
						out.println("i="+i);
						*/
		//			} else
		//				out.println("nie udało się");
/*
					stmt7.setInt(1, idKlasa);
					stmt7.setInt(2, idNauczyciel);
		//			out.println("i="+i);
					
					if (stmt7.executeUpdate() > 0) {
						/*
						res.getWriter().println("id klasy i nauczyciela:");
						out.println(idKlasa);
						out.println(idNauczyciel);
						out.println("i="+i);
						*/
					} 
					//else
		//				out.println("nie udało się");
		//			out.println("i="+i);
		//			int[] count = stmt.executeBatch();
		//	}
		
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
