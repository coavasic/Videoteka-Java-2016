package korisnik;

public class Clan extends Korisnik {

	private int brojKarte;
	private Aktivnost aktivnost;

	public Clan() {
		super();
		this.brojKarte = 0;
		this.aktivnost = Aktivnost.NIJE_AKTIVAN;
	}

	public Clan(String ime, String prezime, String jmbg, String adresa, Pol pol, int brojKarte, Aktivnost aktivnost) {
		super(ime, prezime, jmbg, adresa, pol);
		this.brojKarte = brojKarte;
		this.aktivnost = aktivnost;
	}

	public int getBrojKarte() {
		return brojKarte;
	}

	public void setBrojKarte(int brojKarte) {
		this.brojKarte = brojKarte;
	}

	public Aktivnost getAktivnost() {
		return aktivnost;
	}

	public void setAktivnost(Aktivnost aktivnost) {
		this.aktivnost = aktivnost;
	}

}
