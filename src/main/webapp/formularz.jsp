<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
 <link rel = "stylesheet" href="style3.css"/>
   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Formularz rejestracyjny</title>
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
	<h2>Formularz rejestracyjny</h2>
	<form name="formularz" method="post" action="http://localhost:8090/Biblioteka/formularz">
	
	<label for="imie">Imię</label>
	<input type="text" name="imie" required /> <br>
	<label for="nazwisko">Nazwisko</label>
	<input type="text" name="nazwisko" required /> <br>
	<label for="data_urodzenia">Data urodzenia</label>
	<input type="date" id="data_urodzenia" name="data_urodzenia" 
	min="1900/01/01" max="2023/12/31" required /> <br>
	<label for="pesel">Pesel</label>
	<input type="text" name="pesel"/> <br>
	<label for="ulica">Ulica</label>
	<input type="text" name="ulica" required /> <br>
	<label for="nrDomu">Nr domu</label>
	<input type="text" name="nrDomu" required /> <br>
	<label for="kodPocztowy">Kod pocztowy</label>
	<input type="text" name="kodPocztowy" required /> <br>
	<label for="miejscowosc">Miejscowość</label>
	<input type="text" name="miejscowosc" required /> <br>
	<label for="email">Adres E-mail</label>
	<input type="text" name="email" required /> <br>
	<label for="telefon">Numer telefonu</label>
	<input type="text" name="telefon" /> <br>

	<p>Dane potrzebne do logowania się na stronę.</p>
	<p>Nazwą użytkownika jest podany wyżej adres E-mail</p>
	<label for="haslo">Podaj hasło:</label>
	<input type="password" name="haslo" required /> <br>
	<label for="powtorzHaslo">Powtórz hasło:</label>
	<input type="password" name="powtorzHaslo" required /> <br>

	<div>
		<input type="submit" value="Zapisz" name="save" onclick="" />

	</div> 
<script>
data_urodzenia.max = new Date().toISOString().split("T")[0];
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