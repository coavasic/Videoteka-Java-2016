package film;

public class Kopija {

	private int oznaka;
	private String film;
	private String oznakaMedijuma;
	private long brojPrimeraka;

	public Kopija() {
		super();
		this.oznaka = 0;
		this.film = "";
		this.oznakaMedijuma = "";
		this.brojPrimeraka = 0;
	}

	public Kopija(int oznaka, String film, String oznakaMedijuma, long brojPrimeraka) {
		super();
		this.oznaka = oznaka;
		this.film = film;
		this.oznakaMedijuma = oznakaMedijuma;
		this.brojPrimeraka = brojPrimeraka;
	}

	public int getOznaka() {
		return oznaka;
	}

	public void setOznaka(int oznaka) {
		this.oznaka = oznaka;
	}

	public String getFilm() {
		return film;
	}

	public void setFilm(String film) {
		this.film = film;
	}

	public String getOznakaMedijuma() {
		return oznakaMedijuma;
	}

	public void setOznakaMedijuma(String oznakaMedijuma) {
		this.oznakaMedijuma = oznakaMedijuma;
	}

	public long getBrojPrimeraka() {
		return brojPrimeraka;
	}

	public void setBrojPrimeraka(long brojPrimeraka) {
		this.brojPrimeraka = brojPrimeraka;
	}

}
