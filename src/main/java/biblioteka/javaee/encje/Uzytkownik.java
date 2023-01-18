package biblioteka.javaee.encje;

import java.util.ArrayList;
import java.util.List;

public class Uzytkownik {
int id_uzytkownik;
String nazwa_uzytkownika;
String haslo;
private List<String> role;

public int getId_uzytkownik() {
	return id_uzytkownik;
}
public void setId_uzytkownik(int id_uzytkownik) {
	this.id_uzytkownik = id_uzytkownik;
}
public String getNazwa_uzytkownika() {
	return nazwa_uzytkownika;
}
public void setNazwa_uzytkownika(String nazwa_uzytkownika) {
	this.nazwa_uzytkownika = nazwa_uzytkownika;
}
public String getHaslo() {
	return haslo;
}
public void setHaslo(String haslo) {
	this.haslo = haslo;
}
public List<String> getRole() {
    return role;
 }

 public void setRole(List<String> role) {
    this.role = role;
 }

public Uzytkownik() {
}
public Uzytkownik(String nazwa_uzytkownika, String haslo, String... role) {
	this.nazwa_uzytkownika = nazwa_uzytkownika;
	this.haslo = haslo;
	this.role = new ArrayList<String>();
    if (role != null) {
        for (String r : role) {
           this.role.add(r);
        }
     }
}

}
