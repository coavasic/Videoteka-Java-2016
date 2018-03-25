package film;

public class Zanr {

	private String oznaka;
	private String naziv;

	public Zanr() {
		super();
		this.oznaka = "";
		this.naziv = "";
	}

	public Zanr(String oznaka, String naziv) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

}
