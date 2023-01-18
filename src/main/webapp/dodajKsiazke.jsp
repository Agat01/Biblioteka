<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
 <link rel = "stylesheet" href="style3.css"/>
   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dodaj książkę</title>
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
	<h2>Dodaj książkę</h2>
	
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
List<String> autorzy = new ArrayList<String>();
List<String> wydawnictwa = new ArrayList<String>();
List<String> kategorie = new ArrayList<String>();
String autor="";
String autorS="";
String wydawnictwo="";
String wydawnictwoS="";
String kategoria="";
String kategoriaS="";
String sql = "select imie || ' ' || nazwisko from autor";
Statement stmt = conn.createStatement();
ResultSet rs=stmt.executeQuery(sql);
while(rs.next()){
	autor=rs.getString(1);
autorzy.add(autor);
}
rs.close();
stmt.close();

String sql2 = "select nazwa from wydawnictwo";
Statement stmt2 = conn.createStatement();
ResultSet rs2=stmt2.executeQuery(sql2);
while(rs2.next()){
	wydawnictwo=rs2.getString(1);
wydawnictwa.add(wydawnictwo);
}
rs2.close();
stmt2.close();

String sql3 = "select nazwa from kategoria";
Statement stmt3 = conn.createStatement();
ResultSet rs3=stmt3.executeQuery(sql3);
while(rs3.next()){
	kategoria=rs3.getString(1);
kategorie.add(kategoria);
}
rs3.close();
stmt3.close();
%>	

	<form name="formularz" method="post" action="http://localhost:8090/Biblioteka/dodajKsiazke">
	
	<label for="tytul">Tytuł książki:</label>
	<input type="text" name="tytul"/> <br>
	<label for="autor">Autor:</label>
	<input type="text" list="autorzy" id="autor" name="autor" onchange="get_data()" size="100"/>
		<datalist id="autorzy">
					  <%
        for(int i=0;i<autorzy.size();i++) {
            autorS=autorzy.get(i).toString();
        %>
        <option value="<%=autorS %>"><%=autorS %></option>
        <%} %>
		</datalist>
		<input type="hidden" id="aut" name="aut" value="">
	<label for="rok">Rok wydania:</label>
	<input type="text" name="rok" required /> <br>
	<label for="miejsce">Miejsce wydania:</label>
	<input type="text" name="miejsce" required /> <br>
	<label for="wydawnictwo">Wydawnictwo:</label>
	<input type="text" list="wydawnictwa" id="wydawnictwo" name="wydawnictwo" onchange="get_data()" size="100"/>
		<datalist id="wydawnictwa">
					  <%
        for(int i=0;i<wydawnictwa.size();i++) {
        	wydawnictwoS=wydawnictwa.get(i).toString();
        %>
        <option value="<%=wydawnictwoS %>"><%=wydawnictwoS %></option>
        <%} %>
		</datalist>
		<input type="hidden" id="wyd" name="wyd" value="">
	<label for="kategoria">Kategoria:</label>
	<input type="text" list="kategorie" id="kategoria" name="kategoria" onchange="get_data()" size="100"/>
		<datalist id="kategorie">
					  <%
		 for(int i=0;i<kategorie.size();i++) {
			 kategoriaS=kategorie.get(i).toString();
        %>
        <option value="<%=kategoriaS %>"><%=kategoriaS %></option>
        <%} %>
		</datalist>
		<input type="hidden" id="kat" name="kat" value="">
	<label for="opis">Opis:</label>
	<input type="text" name="opis"/> <br>
	
					
	
	<div>
		<input type="submit" value="Zapisz" name="save" onclick="" />

	</div> 
<script type="text/javascript">
function get_data(){
	  var val = document.getElementById("autor").value;
	  var x = document.getElementById("aut").value=val;
	  
	  var val2 = document.getElementById("wydawnictwo").value;
	  var x2 = document.getElementById("wyd").value=val2;
	  
	  var val3 = document.getElementById("kategoria").value;
	  var x3 = document.getElementById("kat").value=val3;
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