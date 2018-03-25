package film;

public class Medijum {

	private String oznaka;
	private int cena;

	public Medijum() {
		super();
		this.oznaka = "";
		this.cena = 0;
	}

	public Medijum(String oznaka, int cena) {
		super();
		this.oznaka = oznaka;
		this.cena = cena;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

}
