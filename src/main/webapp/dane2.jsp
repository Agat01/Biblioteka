<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<title>Dane</title>
<link rel="stylesheet" href="style3.css" />
</head>
<body>
<div id="kontener">
<div id="left">
 <jsp:include page="menu.jsp"></jsp:include>
</div>
<div id="right">
<div class="header" id="header">
Biblioteka
</div>

<div class="mainContent">
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.*" %>

<%@ page import="biblioteka.javaee.encje.*" %>
<%@ page import="biblioteka.javaee.utils.*" %>
<%
response.setContentType("text/html; charset=utf-8");

	Driver sterownik = new oracle.jdbc.OracleDriver();
	DriverManager.registerDriver(sterownik);
	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");
	Uzytkownik zalogowanyU = AppUtils.getLoginedUser(request.getSession());
	String nazwa_uzytkownika = zalogowanyU.getNazwa_uzytkownika();
	
	String imie = "";
	String nazwisko = "";
	Date data_urodzenia=null;
	String pesel="";
	String ulica = "";
	String nrDomu = "";
	String kodPocztowy = "";
	String miejscowosc = "";
	String email2 = "";
	String telefon = "";
	String isbn = "";
	String tytul = "";
	String autor="";
	Date data_wypozyczenia;
	Date data_plan_zwrotu;
	Date data_zwrotu;
	int czas_wypozyczenia=0;
	int przetrzymanie=0;
	int kara=0;
	int suma=0;
	int zaplacono=0;
	int doZaplacenia=0;
	int id=0;
	String sql = "select id_czytelnik, imie, nazwisko, data_urodzenia,pesel,ulica,nr_domu,kod_pocztowy,miejscowosc,email,nr_tel \r\n"
			+"from czytelnik \r\n"
			+"where email='"+nazwa_uzytkownika+"'";
	Statement stmt = conn.createStatement();
	ResultSet rs=stmt.executeQuery(sql);
	while(rs.next()){
		id=rs.getInt(1);
		imie=rs.getString(2);
		nazwisko=rs.getString(3);
		data_urodzenia=rs.getDate(4);
		pesel=rs.getString(5);
		ulica=rs.getString(6);
		nrDomu=rs.getString(7);
		kodPocztowy=rs.getString(8);
		miejscowosc=rs.getString(9);
		email2=rs.getString(10);
		telefon=rs.getString(11);
	}
	rs.close();
	stmt.close();
%>
<h3>Dane:</h3>
<table id="tabela" style="background-color:rgba(0, 0, 0, 0.4);" border=1>
<% 
out.println("<tr><td>Id czytelnika: </td><td>"+id+"</td></tr>"); 
out.println("<tr><td>Imię i nazwisko: </td><td>"+imie+" "+nazwisko+"</td></tr>"); 
out.println("<tr><td>Data urodzenia: </td><td>"+data_urodzenia+"</td></tr>"); 
out.println("<tr><td>Pesel: </td><td>"+pesel+"</td></tr>"); 
out.println("<tr><td>Adres: </td><td>"+ulica+" "+nrDomu+"<br>"+kodPocztowy+" "+miejscowosc+"</td></tr>"); 
out.println("<tr><td>Adres e-mail: </td><td>"+email2+"</td></tr>"); 
out.println("<tr><td>Numer telefonu: </td><td>"+telefon+"</td></tr>"); 
%>
</table>

<h3>Książki aktualnie wypożyczone:</h3>
<table id="tabela" border="1">
			<tr>
				<td>ISBN</td>
				<td>Tytuł książki</td>
				<td>Autor</td>
				<td>Data wypożyczenia</td>
				<td>Data planowanego zwrotu</td>
				<td>Czas wypożyczenia</td>
				<td>Czas przetrzymania książki</td>
			</tr>
<% 	String sql1 = "select w.isbn, k.tytul, a.imie || ' ' || a.nazwisko, w.data_wypozyczenia, w.data_plan_zwrotu, trunc(sysdate-w.data_wypozyczenia),trunc(sysdate-w.data_plan_zwrotu)\r\n"
		+"from wypozyczenie w join egzemplarz e on(w.isbn=e.isbn) \r\n"
		+"join ksiazka k on(k.id_ksiazka=e.id_ksiazka) \r\n"
		+"join ksiazka_autor ka on(ka.id_ksiazka=k.id_ksiazka) \r\n"
		+"join autor a on(a.id_autor=ka.id_autor)\r\n"
		+"where w.data_zwrotu is null and id_czytelnik="+id;
Statement stmt1 = conn.createStatement();
ResultSet rs1=stmt1.executeQuery(sql1);
while(rs1.next()){
	isbn=rs1.getString(1);
	tytul=rs1.getString(2);
	autor=rs1.getString(3);
	data_wypozyczenia=rs1.getDate(4);
	data_plan_zwrotu=rs1.getDate(5);
	czas_wypozyczenia=rs1.getInt(6);
	przetrzymanie=rs1.getInt(7);
	if(przetrzymanie<0) przetrzymanie=0;
	
	out.println("<tr>"); 
	out.println("<td>"+isbn+"</td><td>"+tytul+"</td><td>"+autor+"</td>");
    out.println("<td>"+data_wypozyczenia+"</td><td>"+data_plan_zwrotu+"</td>");
    out.println("<td>"+czas_wypozyczenia+"</td><td>"+przetrzymanie+"</td>");
    out.println("</tr>"); 
}
rs1.close();
stmt1.close();
%>
</table>
<br>
<h3>Kary:</h3>
<table id="tabela" border="1">
			<tr>
				<td>ISBN</td>
				<td>Tytuł książki</td>
				<td>Autor</td>
				<td>Data zwrotu</td>
				<td>Czas przetrzymania książki</td>
				<td>Koszt kary</td>
			</tr>
<% 	String sql2 = "select w.isbn, ks.tytul, a.imie || ' ' || a.nazwisko, w.data_zwrotu, trunc(w.data_zwrotu-w.data_plan_zwrotu), k.kwota \r\n"
		+"from wypozyczenie w join egzemplarz e on(w.isbn=e.isbn) \r\n"
		+"join ksiazka ks on(ks.id_ksiazka=e.id_ksiazka) \r\n"
		+"join ksiazka_autor ka on(ka.id_ksiazka=ks.id_ksiazka) \r\n"
		+"join autor a on(a.id_autor=ka.id_autor)\r\n"
		+"join kara k on(k.id_wypozyczenie=w.id_wypozyczenie)\r\n"
		+"where w.data_zwrotu is not null and id_czytelnik="+id;
Statement stmt2 = conn.createStatement();
ResultSet rs2=stmt2.executeQuery(sql2);
while(rs2.next()){
	isbn=rs2.getString(1);
	tytul=rs2.getString(2);
	autor=rs2.getString(3);
	data_zwrotu=rs2.getDate(4);
	przetrzymanie=rs2.getInt(5);
	kara=rs2.getInt(6);
	
	out.println("<tr>"); 
	out.println("<td>"+isbn+"</td><td>"+tytul+"</td><td>"+autor+"</td>");
    out.println("<td>"+data_zwrotu+"</td><td>"+przetrzymanie+"</td>");
    out.println("<td>"+kara+"</td>");
    out.println("</tr>"); 
}
rs2.close();
stmt2.close();
%>
</table>
<br>
<table id="tabela" border="1">
<% 	
String sql3="SELECT SUM(kwota) \r\n"
		+ "FROM czytelnik cz JOIN wypozyczenie w ON(cz.id_czytelnik=w.id_czytelnik)\r\n"
		+ "						JOIN kara k ON(w.id_wypozyczenie=k.id_wypozyczenie)\r\n"
		+ "WHERE cz.id_czytelnik="+id;
String sql4="SELECT SUM(kwota) \r\n"
		+ "FROM czytelnik cz JOIN wypozyczenie w ON(cz.id_czytelnik=w.id_czytelnik)\r\n"
		+ "						JOIN kara k ON(w.id_wypozyczenie=k.id_wypozyczenie)\r\n"
		+ "WHERE k.data_zaplaty is null and cz.id_czytelnik="+id;
String sql5="SELECT SUM(kwota) \r\n"
		+ "FROM czytelnik cz JOIN wypozyczenie w ON(cz.id_czytelnik=w.id_czytelnik)\r\n"
		+ "						JOIN kara k ON(w.id_wypozyczenie=k.id_wypozyczenie)\r\n"
		+ "WHERE k.data_zaplaty is not null and cz.id_czytelnik="+id;
Statement stmt3 = conn.createStatement();
ResultSet rs3=stmt3.executeQuery(sql3);
while(rs3.next()){
	suma=rs3.getInt(1);
	out.println("<tr><td>Suma kar:</td><td>"+suma+"</td></tr>"); 
}
rs3.close();
stmt3.close();

Statement stmt4 = conn.createStatement();
ResultSet rs4=stmt4.executeQuery(sql4);
while(rs4.next()){
zaplacono=rs4.getInt(1);
out.println("<tr><td>Zapłacono:</td><td>"+zaplacono+"</td></tr>"); 
}
rs4.close();
stmt4.close();

Statement stmt5 = conn.createStatement();
ResultSet rs5=stmt5.executeQuery(sql5);
while(rs5.next()){
doZaplacenia=rs5.getInt(1);
out.println("<tr><td>Pozostało do zapłaty:</td><td>"+doZaplacenia+"</td></tr>"); 
}
rs5.close();
stmt5.close();
%>
</table>

</div>

</div>
</div>
</body>
</html>