<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
 <link rel = "stylesheet" href="style3.css"/>
   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edytowanie danych czytelnika</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="http://jquery.bassistance.de/validate/jquery.validate.js"></script>

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
<div style="margin-left:40px;">
	<h2>Edytowanie danych czytelnika</h2>
	
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
	
	int id=0;
	if(id_czytelnik!=null && id_czytelnik!=""){
	id=Integer.parseInt(id_czytelnik);
	}
	String sql = "select id_czytelnik, imie, nazwisko, data_urodzenia,pesel,ulica,nr_domu,kod_pocztowy,miejscowosc,email,nr_tel \r\n"
			+"from czytelnik \r\n"
			+"where id_czytelnik="+id+" or email='"+email+"'";
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

<br>
	<form name="formularz" method="post" action="http://localhost:8090/Biblioteka/edytujCzytelnika">
	<input type="hidden" id="id_czytelnik" name="id_czytelnik" value=<%=id_czytelnik %>>
	<input type="hidden" id="email" name="email" value=<%=email%>>
	<label for="imie">Imię</label>
	<input type="text" name="imie" value=<%=imie%>> <br>
	<label for="nazwisko">Nazwisko</label>
	<input type="text" name="nazwisko" value=<%=nazwisko%>> <br>
	<label for="data_urodzenia">Data urodzenia</label>
	<input type="date" id="data_urodzenia" name="data_urodzenia" 
	min="1900/01/01" max="2023/12/31" value=<%=data_urodzenia%>> <br>
	<label for="pesel">Pesel</label>
	<input type="text" name="pesel" value=<%=pesel%>> <br>
	<label for="ulica">Ulica</label>
	<input type="text" name="ulica" value=<%=ulica%>> <br>
	<label for="nrDomu">Nr domu</label>
	<input type="text" name="nrDomu" value=<%=nrDomu%>> <br>
	<label for="kodPocztowy">Kod pocztowy</label>
	<input type="text" name="kodPocztowy" value=<%=kodPocztowy%>> <br>
	<label for="miejscowosc">Miejscowość</label>
	<input type="text" name="miejscowosc" value=<%=miejscowosc%>> <br>
	<label for="email2">Adres E-mail</label>
	<input type="text" name="email2" value=<%=email2%>> <br>
	<label for="telefon">Numer telefonu</label>
	<input type="text" name="telefon" value=<%=telefon%>> <br>

	<div>
		<input type="submit" value="Zapisz" name="save" onclick="" />

	</div> 
<script>
data_urodzenia.max = new Date().toISOString().split("T")[0];
</script>
</form>
</div>

</div>
</div>
</div>
</body>
</html>