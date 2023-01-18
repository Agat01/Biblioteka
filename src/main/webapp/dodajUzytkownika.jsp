<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
 <link rel = "stylesheet" href="style3.css"/>
   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dodaj użytkownika</title>
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
	<h2>Dodaj użytkownika</h2>

	<form name="formularz" method="post" action="http://localhost:8090/Biblioteka/dodajUzytkownika">
	
	<label for="nazwa_uzytkownika">Nazwa użytkownika</label>
	<input type="text" name="nazwa_uzytkownika" required /> <br>
	<label for="haslo">Hasło</label>
	<input type="password" id="haslo" name="haslo" required /> <br>
	<label for="id_rola">Id roli</label>
	<input type="text" id="id_rola" name="id_rola" required /> <br>

	<div>
		<input type="submit" value="Zapisz" name="save" onclick="" />

	</div> 

</form>
</div>
<footer>

</footer>
</div>
</div>
</div>
</body>
</html>