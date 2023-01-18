<nav>
<div class="topnav">
<ul id="menu">
<li><span style="color:#c9a227"><center>[${zalogowanyU.nazwa_uzytkownika}]</center></span></li>
<li><div class="logowanie">
<button id="log" class="button" onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Logowanie</button>
<div id="id01" class="modal">
  <form class="modal-content animate" method="post" action="${pageContext.request.contextPath}/login">
  <input type="hidden" name="redirectId" value="${param.redirectId}" />
    
    <div class="container">
    <span class="close">&times;</span>
      <label for="nazwa_uzytkownika"><b>Nazwa uzytkownika</b></label>
      <input type="text" placeholder="Podaj nazwę użytkownika" id="nazwa_uzytkownika" name="nazwa_uzytkownika" value= "${uzytkownik.nazwa_uzytkownika}" required>

      <label for="haslo"><b>Hasło</b></label>
      <input type="password" placeholder="Podaj hasło" id="haslo" name="haslo" value= "${uzytkownik.haslo}" required>
        
      <button class="button" id="log" type="submit">Zaloguj</button>
      
    </div>
    <div class="container" style="background-color:#f1f1f1">
      
    </div>
  </form>
</div>
</div>
<script>
// Get the modal
var modal = document.getElementById('id01');
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
    var span = document.getElementsByClassName("close")[0];
    span.onclick = function() {
      modal.style.display = "none";
    }
}
</script></li>
<li><a href="${pageContext.request.contextPath}/wylogowanie">Wyloguj</a></li>
  <li><a href="strona.jsp">Strona główna</a></li>
  <li><a href="formularz.jsp">Formularz rejestracyjny</a></li>
  <li><a href="${pageContext.request.contextPath}/czytelnik">Dane czytelnika</a></li>
  <li><a href="${pageContext.request.contextPath}/czytelnik2">Edytuj dane czytelnika</a></li>
  <li><a href="${pageContext.request.contextPath}/zwrot">Zwrot książki</a></li>
  <li><a href="${pageContext.request.contextPath}/wypozycz">Wypożycz książkę</a></li>
  <li><a href="${pageContext.request.contextPath}/dodajUzytkownika">Dodaj użytkownika</a></li>
  <li><a href="${pageContext.request.contextPath}/dodajKsiazke">Dodaj książkę</a></li>
  <li><a href="${pageContext.request.contextPath}/dodajEgzemplarz">Dodaj egzemplarz</a></li>
  <a href="ksiazki.jsp"><script src="https://cdn.lordicon.com/fudrjiwc.js"></script>
<lord-icon
    src="https://cdn.lordicon.com/hursldrn.json"
    trigger="morph"
    colors="primary:#e88c30,secondary:#5c330a"
    style="margin-left:30px;width:130px;height:130px">
</lord-icon>
</a>

</ul>
</div>
</nav>