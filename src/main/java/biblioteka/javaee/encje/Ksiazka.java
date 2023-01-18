package biblioteka.javaee.encje;

public class Ksiazka {
String tytul;
String autor;
String wydawnictwo;
public String getTytul() {
	return tytul;
}
public void setTytul(String tytul) {
	this.tytul = tytul;
}
public String getAutor() {
	return autor;
}
public void setAutor(String autor) {
	this.autor = autor;
}
public String getWydawnictwo() {
	return wydawnictwo;
}
public void setWydawnictwo(String wydawnictwo) {
	this.wydawnictwo = wydawnictwo;
}
public Ksiazka(String tytul, String autor, String wydawnictwo) {
	super();
	this.tytul = tytul;
	this.autor = autor;
	this.wydawnictwo = wydawnictwo;
}


}
