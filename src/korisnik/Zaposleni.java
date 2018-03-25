package korisnik;

public class Zaposleni extends Korisnik {

	private double plata;
	private String korisnickoIme;
	private String lozinka;
	private TipZaposlenog tipZaposlenog;

	public Zaposleni() {
		super();
		this.plata = 0;
		this.korisnickoIme = "";
		this.lozinka = "";
		this.tipZaposlenog = TipZaposlenog.RADNIK;
	}

	public Zaposleni(String ime, String prezime, String jmbg, String adresa, Pol pol, double plata,
			String korisnickoIme, String lozinka, TipZaposlenog tipZaposlenog) {
		super(ime, prezime, jmbg, adresa, pol);
		this.plata = plata;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.tipZaposlenog = tipZaposlenog;
	}

	public double getPlata() {
		return plata;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public TipZaposlenog getTipZaposlenog() {
		return tipZaposlenog;
	}

	public void setTipZaposlenog(TipZaposlenog tipZaposlenog) {
		this.tipZaposlenog = tipZaposlenog;
	}

}
