package korisnik;

public abstract class Korisnik {
	private String ime;
	private String prezime;
	private String jmbg;
	private String adresa;
	private Pol pol;

	public Korisnik() {
		this.ime = "";
		this.prezime = "";
		this.jmbg = "";
		this.adresa = "";
		this.pol = Pol.ŽENSKI;
	}

	public Korisnik(String ime, String prezime, String jmbg, String adresa, Pol pol) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.adresa = adresa;
		this.pol = pol;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}

}