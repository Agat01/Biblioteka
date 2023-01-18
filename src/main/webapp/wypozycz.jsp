<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
 <link rel = "stylesheet" href="style3.css"/>
   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Wypożyczenie</title>
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
	<h2>Wypożyczenie książki</h2>

	<form name="formularz" method="post" action="http://localhost:8090/Biblioteka/wypozycz">
	
	<label for= "id_czytelnik">Podaj id czytelnika</label>
<input type="text" name="id_czytelnik"/>
lub
<br>
<label for= "email">Podaj adres e-mail czytelnika</label>
<input type="text" name="email"/>
	<label for="isbn">ISBN</label>
	<input type="text" name="isbn" required /> <br>
	<label for="data_wypozyczenia">Data wypożyczenia</label>
	<input type="date" id="data_wypozyczenia" name="data_wypozyczenia" required /> <br>

	<div>
		<input type="submit" value="Zapisz" name="save" onclick="" />

	</div> 
<script>

data_wypozyczenia.max = new Date().toISOString().split("T")[0];

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