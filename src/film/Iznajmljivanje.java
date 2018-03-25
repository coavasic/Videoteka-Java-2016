package film;

public class Iznajmljivanje {

	private int oznaka;
	private int oznakaPaketa;
	private String zaposleni;
	private String clan;
	private String datumIznajmljivanja;
	private String datumVracanja;
	private String kopija;
	private double cena;
	private int brojDana;

	public Iznajmljivanje() {
		super();
		this.oznaka = 0;
		this.oznakaPaketa = 0;
		this.zaposleni = "";
		this.clan = "";
		this.datumIznajmljivanja = "";
		this.datumVracanja = "";
		this.kopija = "";
		this.cena = 0;
		this.brojDana = 0;
	}

	public Iznajmljivanje(int oznaka, int oznakaPaketa, String zaposleni, String clan, String datumIznajmljivanja,
			String datumVracanja, String kopija, double cena, int brojDana) {
		super();
		this.oznaka = oznaka;
		this.oznakaPaketa = oznakaPaketa;
		this.zaposleni = zaposleni;
		this.clan = clan;
		this.datumIznajmljivanja = datumIznajmljivanja;
		this.datumVracanja = datumVracanja;
		this.kopija = kopija;
		this.cena = cena;
		this.brojDana = brojDana;
	}

	public int getOznaka() {
		return oznaka;
	}

	public void setOznaka(int oznaka) {
		this.oznaka = oznaka;
	}

	public int getOznakaPaketa() {
		return oznakaPaketa;
	}

	public void setOznakaPaketa(int oznakaPaketa) {
		this.oznakaPaketa = oznakaPaketa;
	}

	public String getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(String zaposleni) {
		this.zaposleni = zaposleni;
	}

	public String getClan() {
		return clan;
	}

	public void setClan(String clan) {
		this.clan = clan;
	}

	public String getDatumIznajmljivanja() {
		return datumIznajmljivanja;
	}

	public void setDatumIznajmljivanja(String datumIznajmljivanja) {
		this.datumIznajmljivanja = datumIznajmljivanja;
	}

	public String getDatumVracanja() {
		return datumVracanja;
	}

	public void setDatumVracanja(String datumVracanja) {
		this.datumVracanja = datumVracanja;
	}

	public String getKopija() {
		return kopija;
	}

	public void setKopija(String kopija) {
		this.kopija = kopija;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public int getBrojDana() {
		return brojDana;
	}

	public void setBrojDana(int brojDana) {
		this.brojDana = brojDana;
	}

}
