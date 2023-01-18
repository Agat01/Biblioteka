<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<div style="margin-left:40px;">
<h2>Wyświetlanie danych czytelnika, aktualnie wypożyczonych książek i kar</h2>
<form name="dane" method="post" action="http://localhost:8090/Biblioteka/czytelnik">
<label for= "id_czytelnik">Podaj id czytelnika</label>
<input type="text" name="id_czytelnik"/>
<p>lub</p>
<label for= "email">Podaj adres e-mail czytelnika</label>
<input type="text" name="email"/>
<div>			
<input type="submit" value="wyświetl dane" name="save" onclick=""/>
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