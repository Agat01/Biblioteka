<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Witaj</title>
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
<h3>Witaj, ${zalogowanyU.nazwa_uzytkownika} !</h3>

</div>
</div>
</div>
</body>
</html>