package film;

public class Film {

	private String naslovSrb;
	private String naslovOrg;
	private int godinaIzdavanja;
	private String zanrovi;
	private String imeRezisera;
	private String prezimeRezisera;
	private String opis;
	private int trajanje;

	public Film() {
		super();
		this.naslovSrb = "";
		this.naslovOrg = "";
		this.godinaIzdavanja = 1900;
		this.zanrovi = "";
		this.imeRezisera = "";
		this.prezimeRezisera = "";
		this.opis = "";
		this.trajanje = 0;
	}

	public Film(String naslovSrb, String naslovOrg, int godinaIzdavanja, String zanrovi, String imeRezisera,
			String prezimeRezisera, String opis, int trajanje) {
		super();
		this.naslovSrb = naslovSrb;
		this.naslovOrg = naslovOrg;
		this.godinaIzdavanja = godinaIzdavanja;
		this.zanrovi = zanrovi;
		this.imeRezisera = imeRezisera;
		this.prezimeRezisera = prezimeRezisera;
		this.opis = opis;
		this.trajanje = trajanje;
	}

	public String getNaslovSrb() {
		return naslovSrb;
	}

	public void setNaslovSrb(String naslovSrb) {
		this.naslovSrb = naslovSrb;
	}

	public String getNaslovOrg() {
		return naslovOrg;
	}

	public void setNaslovOrg(String naslovOrg) {
		this.naslovOrg = naslovOrg;
	}

	public int getGodinaIzdavanja() {
		return godinaIzdavanja;
	}

	public void setGodinaIzdavanja(int godinaIzdavanja) {
		this.godinaIzdavanja = godinaIzdavanja;
	}

	public String getZanrovi() {
		return zanrovi;
	}

	public void setZanrovi(String zanrovi) {
		this.zanrovi = zanrovi;
	}

	public String getImeRezisera() {
		return imeRezisera;
	}

	public void setImeRezisera(String imeRezisera) {
		this.imeRezisera = imeRezisera;
	}

	public String getPrezimeRezisera() {
		return prezimeRezisera;
	}

	public void setPrezimeRezisera(String prezimeRezisera) {
		this.prezimeRezisera = prezimeRezisera;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

}
