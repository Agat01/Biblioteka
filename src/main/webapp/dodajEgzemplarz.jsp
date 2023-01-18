<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
 <link rel = "stylesheet" href="style3.css"/>
   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dodaj egzemplarz</title>
<script type="text/javascript" src="assets/bootstrap.js"></script>

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
	<h2>Dodaj egzemplarz</h2>
	
	<%@ page import="java.sql.*" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.*" %>

<%@ page import="biblioteka.javaee.encje.*" %>
<%@ page import="biblioteka.javaee.utils.*" %>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");
Driver sterownik = new oracle.jdbc.OracleDriver();
DriverManager.registerDriver(sterownik);
Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "biblioteka", "biblioteka");
List<Ksiazka> ksiazki = new ArrayList<Ksiazka>();
List<String> stany = new ArrayList<String>();
String ksiazka="";
String ksiazkaS="";
String tytul="";
String autor="";
String wydawnictwo="";
String stan="";
String stanS="";

String sql = "select k.tytul, a.imie || ' ' || a.nazwisko, w.nazwa \r\n"
		+ "from ksiazka k join ksiazka_autor ka on(ka.id_ksiazka=k.id_ksiazka)\r\n"
		+ "join autor a on(a.id_autor=ka.id_autor) \r\n"
		+ "join wydawnictwo w on(w.id_wydawnictwo=k.id_wydawnictwo)";
Statement stmt = conn.createStatement();
ResultSet rs=stmt.executeQuery(sql);
while(rs.next()){
	tytul=rs.getString(1);
	autor=rs.getString(2);
	wydawnictwo=rs.getString(3);
	Ksiazka k=new Ksiazka(tytul,autor,wydawnictwo);
ksiazki.add(k);
}
rs.close();
stmt.close();

String sql2 = "select nazwa from stan_egzemplarza";
Statement stmt2 = conn.createStatement();
ResultSet rs2=stmt2.executeQuery(sql2);
while(rs2.next()){
	stan=rs2.getString(1);
stany.add(stan);
}
rs2.close();
stmt2.close();


%>	

	<form name="formularz" method="post" action="http://localhost:8090/Biblioteka/dodajEgzemplarz">
	
	<label for="isbn">Isbn:</label>
	<input type="text" name="isbn"/> <br>
	<label for="ksiazka">Ksiazka (tytuł, autor, wydawnictwo):</label>
	<input type="text" list="ksiazki" id="ksiazka" name="ksiazka" onchange="get_data()" size="100"/>
		<datalist id="ksiazki">
					  <%
        for(int i=0;i<ksiazki.size();i++) {
        	ksiazkaS=ksiazki.get(i).getTytul().toString()+", "+ksiazki.get(i).getAutor()+", "+ksiazki.get(i).getWydawnictwo();
        %>
        <option value="<%=ksiazkaS %>"><%=ksiazkaS %></option>
        <%} %>
		</datalist>
		<input type="hidden" id="ks" name="ks" value="">
	
	<label for="opis">Opis:</label>
	<input type="text" name="opis"/> <br>
	<label for="stan">Stan egzemplarza:</label>
	<input type="text" list="stany" id="stan" name="stan" onchange="get_data()" size="100"/>
		<datalist id="stany">
					  <%
		 for(int i=0;i<stany.size();i++) {
			 stanS=stany.get(i).toString();
        %>
        <option value="<%=stanS %>"><%=stanS %></option>
        <%} %>
		</datalist>
	<input type="hidden" id="st" name="st" value="">			
	
	<div>
		<input type="submit" value="Zapisz" name="save" onclick="" />

	</div> 
<script type="text/javascript">
function get_data(){
	  var val = document.getElementById("ksiazka").value;
	  var x = document.getElementById("ks").value=val;
	  var val2 = document.getElementById("stan").value;
	  var x2 = document.getElementById("st").value=val2;
}
</script>

</form>
</div>
<footer>

</footer>
</div>
</div>
</div>
</body>
</html>