<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<title>Lista książek</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="http://jquery.bassistance.de/validate/jquery.validate.js"></script>
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
String id_czytelnik=request.getParameter("id_czytelnik");
String email=request.getParameter("email");
	Driver sterownik = new oracle.jdbc.OracleDriver();
	DriverManager.registerDriver(sterownik);
	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");
	
	String isbn = "";
	String tytul = "";
	String autor="";
	int liczba=0;
	int wypozyczone=0;
	int liczba2=0;
	
%>

<h3>Lista wszystkich książek:</h3>
<input type="text" id="szukaj" placeholder="wpisz tytuł lub autora">
<table id="tabela" border="1">
			<tr>
				<td>Tytuł książki</td>
				<td>Autor</td>
				<td>Liczba wszystkich egzemplarzy</td>
				<td>Liczba dostępnych egzemplarzy</td>
			</tr>
<%
String sql1 = "select k.tytul, a.imie || ' ' || a.nazwisko, count(e.id_ksiazka) \r\n"
		+"from egzemplarz e join ksiazka k on(k.id_ksiazka=e.id_ksiazka) \r\n"
		+"join ksiazka_autor ka on(ka.id_ksiazka=k.id_ksiazka) \r\n"
		+"join autor a on(a.id_autor=ka.id_autor) \r\n"
		+"group by k.tytul,a.imie,a.nazwisko";
Statement stmt1 = conn.createStatement();
ResultSet rs1=stmt1.executeQuery(sql1);
while(rs1.next()){
	tytul=rs1.getString(1);
	autor=rs1.getString(2);
	liczba=rs1.getInt(3);
	
	out.println("<tr>"); 
	out.println("<td>"+tytul+"</td><td>"+autor+"</td><td>"+liczba+"</td>");
	
	String sql2 = "select count(w.isbn)\r\n"
			+"from egzemplarz e join ksiazka k on(k.id_ksiazka=e.id_ksiazka)\r\n"
			+"join ksiazka_autor ka on(ka.id_ksiazka=k.id_ksiazka)\r\n"
			+"join autor a on(a.id_autor=ka.id_autor)\r\n"
			+" left join wypozyczenie w on(w.isbn=e.isbn)\r\n"
			+"where w.data_zwrotu is null and k.tytul='"+tytul+"'";
	Statement stmt2 = conn.createStatement();
	ResultSet rs2=stmt2.executeQuery(sql2);
	while(rs2.next()){
		wypozyczone=rs2.getInt(1);
		liczba2=liczba-wypozyczone;
		out.println("<td>"+liczba2+"</td>");
	}
	rs2.close();
	stmt2.close();
    out.println("</tr>"); 
}
rs1.close();
stmt1.close();
%>
</table>
<script type="text/javascript">
var $rows = $('#tabela tr');
$('#szukaj').keyup(function() {
	    
	    var val = '^(?=.*\\b' + $.trim($(this).val()).split(/\s+/).join('\\b)(?=.*\\b') + ').*$',
	        reg = RegExp(val, 'i'),
	        text;
	    
	    $rows.show().filter(function() {
	        text = $(this).text().replace(/\s+/g, ' ');
	        return !reg.test(text);
	    }).hide();
	});
</script>
</div>

</div>
</div>
</body>
</html>