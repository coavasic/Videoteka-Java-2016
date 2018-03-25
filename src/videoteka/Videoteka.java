package videoteka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import film.Film;
import film.Iznajmljivanje;
import film.Kopija;
import film.Medijum;
import film.Zanr;
import korisnik.Aktivnost;
import korisnik.Clan;
import korisnik.Pol;
import korisnik.TipZaposlenog;
import korisnik.Zaposleni;

public class Videoteka {

	private int pib;
	private String naziv;
	private String adresa;
	private ArrayList<Zaposleni> listaZaposleni;
	private ArrayList<Clan> listaClan;
	private ArrayList<Iznajmljivanje> listaIznajmljivanje;
	private ArrayList<Film> listaFilm;
	private ArrayList<Kopija> listaKopija;
	private ArrayList<Zanr> listaZanr;
	private ArrayList<Medijum> listaMedijum;
	private String ulogovan;
	private ArrayList<Kopija> korpa;
	private double ukupnaCenaKorpe;

	public Videoteka() {
		super();
		this.pib = 0;
		this.naziv = "";
		this.adresa = "";
		this.listaZaposleni = new ArrayList<Zaposleni>();
		this.listaClan = new ArrayList<Clan>();
		this.listaIznajmljivanje = new ArrayList<Iznajmljivanje>();
		this.listaFilm = new ArrayList<Film>();
		this.listaKopija = new ArrayList<Kopija>();
		this.listaZanr = new ArrayList<Zanr>();
		this.listaMedijum = new ArrayList<Medijum>();
		this.ulogovan = "";
		this.korpa = new ArrayList<Kopija>();
		this.ukupnaCenaKorpe = 0;
	}

	public Videoteka(int pib, String naziv, String adresa, ArrayList<Zaposleni> listaZaposleni,
			ArrayList<Clan> listaClan, ArrayList<Iznajmljivanje> listaIznajmljivanje, ArrayList<Film> listaFilm,
			ArrayList<Kopija> listaKopija, ArrayList<Zanr> listaZanr, ArrayList<Medijum> listaMedijum,
			ArrayList<Integer> brojKarataLista, ArrayList<Integer> oznakeKopijaLista,
			ArrayList<Integer> oznakeIznajmljivanjaLista, String ulogovan, ArrayList<Kopija> korpa,
			double ukupnaCenaKorpe) {
		super();
		this.pib = pib;
		this.naziv = naziv;
		this.adresa = adresa;
		this.listaZaposleni = listaZaposleni;
		this.listaClan = listaClan;
		this.listaIznajmljivanje = listaIznajmljivanje;
		this.listaFilm = listaFilm;
		this.listaKopija = listaKopija;
		this.listaZanr = listaZanr;
		this.listaMedijum = listaMedijum;
		this.ulogovan = ulogovan;
		this.korpa = korpa;
		this.ukupnaCenaKorpe = ukupnaCenaKorpe;
	}

	public void sacuvajSve() {
		sacuvajZaposlene();
		snimiClanove();
		snimiFilmove();
		snimiIznajmljivanja();
		snimiKopije();
		snimiMedijume();
		snimiVideoteku();
		snimiZanrove();
	}

	public int getPib() {
		return pib;
	}

	public void setPib(int pib) {
		this.pib = pib;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getUlogovan() {
		return ulogovan;
	}

	public void setUlogovan(String ulogovan) {
		this.ulogovan = ulogovan;
	}

	public double getUkupnaCenaKorpe() {
		return ukupnaCenaKorpe;
	}

	public void setUkupnaCenaKorpe(double ukupnaCenaKorpe) {
		this.ukupnaCenaKorpe = ukupnaCenaKorpe;
	}

	public ArrayList<Zaposleni> getListaZaposleni() {
		return listaZaposleni;
	}

	public ArrayList<Clan> getListaClan() {
		return listaClan;
	}

	public ArrayList<Iznajmljivanje> getListaIznajmljivanje() {
		return listaIznajmljivanje;
	}

	public ArrayList<Film> getListaFilm() {
		return listaFilm;
	}

	public ArrayList<Kopija> getListaKopija() {
		return listaKopija;
	}

	public ArrayList<Zanr> getListaZanr() {
		return listaZanr;
	}

	public ArrayList<Medijum> getListaMedijum() {
		return listaMedijum;
	}

	public ArrayList<Kopija> getKorpa() {
		return korpa;
	}

	public void dodajUKorpu(Kopija kopija) {
		this.korpa.add(kopija);
	}

	public void brisiIzKorpe(Kopija kopija) {
		this.korpa.remove(kopija);

	}

	public Zaposleni loginProvera(String korisnickoIme, String lozinka) {
		for (Zaposleni zaposleni : listaZaposleni) {
			if (zaposleni.getKorisnickoIme().equals(korisnickoIme) && zaposleni.getLozinka().equals(lozinka)) {
				return zaposleni;
			}
		}
		return null;
	}

	public void ucitajVideoteku() {
		try {
			File videotekaFajl = new File("src/fajlovi/videoteka.txt");
			BufferedReader br = new BufferedReader(new FileReader(videotekaFajl));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\\|");
				int pib = Integer.parseInt(split[0]);
				Videoteka.this.setPib(pib);
				String naziv = split[1];
				Videoteka.this.setNaziv(naziv);
				String ulica = split[2];
				Videoteka.this.setAdresa(ulica);
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void snimiVideoteku() {
		try {
			File videotekaFajl = new File("src/fajlovi/videoteka.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(videotekaFajl));
			String sadrzaj = "";
			sadrzaj += Videoteka.this.getPib() + "|" + Videoteka.this.getNaziv() + "|" + Videoteka.this.getAdresa();
			bw.write(sadrzaj);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ucitajZaposlene() {
		try {
			File zaposleniFile = new File("src/fajlovi/zaposleni.txt");
			BufferedReader br = new BufferedReader(new FileReader(zaposleniFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\\|");
				String ime = split[0];
				String prezime = split[1];
				String jmbg = split[2];
				String adresa = split[3];
				int polInt = Integer.parseInt(split[4]);
				Pol pol = Pol.fromInt(polInt);
				double plata = Double.parseDouble(split[5]);
				String korisnickoIme = split[6];
				String lozinka = split[7];
				int tipInt = Integer.parseInt(split[8]);
				TipZaposlenog tipZaposlenog = TipZaposlenog.fromInt(tipInt);
				Zaposleni zaposlen = new Zaposleni(ime, prezime, jmbg, adresa, pol, plata, korisnickoIme, lozinka,
						tipZaposlenog);
				listaZaposleni.add(zaposlen);

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sacuvajZaposlene() {
		try {
			File zaposleniFajl = new File("src/fajlovi/zaposleni.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(zaposleniFajl));
			String sadrzaj = "";
			for (Zaposleni zaposleni : listaZaposleni) {

				sadrzaj += zaposleni.getIme() + "|" + zaposleni.getPrezime() + "|" + zaposleni.getJmbg() + "|"
						+ zaposleni.getAdresa() + "|" + Pol.toInt(zaposleni.getPol()) + "|" + zaposleni.getPlata() + "|"
						+ zaposleni.getKorisnickoIme() + "|" + zaposleni.getLozinka() + "|"
						+ TipZaposlenog.toInt(zaposleni.getTipZaposlenog()) + "\n";

			}
			bw.write(sadrzaj);
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Zaposleni pronadjiZaposlenog(String ime) {
		for (Zaposleni zaposleni : listaZaposleni) {
			if (zaposleni.getIme().equals(ime)) {
				return zaposleni;
			}
		}
		return null;
	}

	public void obrisiZaposlenog(Zaposleni zaposleni) {
		this.listaZaposleni.remove(zaposleni);

	}

	public void dodajZaposlenog(Zaposleni zaposleni) {
		this.listaZaposleni.add(zaposleni);
	}

	public void ucitajClanove() {
		try {
			File clanoviFajl = new File("src/fajlovi/clanovi.txt");
			BufferedReader br = new BufferedReader(new FileReader(clanoviFajl));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\\|");
				String ime = split[0];
				String prezime = split[1];
				String jmbg = split[2];
				String adresa = split[3];
				int polInt = Integer.parseInt(split[4]);
				Pol pol = Pol.fromInt(polInt);
				int brkarte = Integer.parseInt(split[5]);
				int aktivnostInt = Integer.parseInt(split[6]);
				Aktivnost aktivnost = Aktivnost.fromInt(aktivnostInt);
				Clan clan = new Clan(ime, prezime, jmbg, adresa, pol, brkarte, aktivnost);
				listaClan.add(clan);

			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void snimiClanove() {
		try {
			File clanoviFajl = new File("src/fajlovi/clanovi.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(clanoviFajl));
			String sadrzaj = "";
			for (Clan clan : listaClan) {
				sadrzaj += clan.getIme() + "|" + clan.getPrezime() + "|" + clan.getJmbg() + "|" + clan.getAdresa() + "|"
						+ Pol.toInt(clan.getPol()) + "|" + clan.getBrojKarte() + "|"
						+ Aktivnost.toInt(clan.getAktivnost()) + "\n";
			}
			bw.write(sadrzaj);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Clan nadjiClana(String ime) {
		for (Clan clan : listaClan) {
			if (clan.getIme().equals(ime)) {
				return clan;
			}
		}
		return null;
	}

	public void obrisiClana(Clan clan) {
		this.listaClan.remove(clan);
	}

	public void dodajClana(Clan clan) {
		this.listaClan.add(clan);
	}

	public void ucitajFilmove() {
		try {
			File filmoviFajl = new File("src/fajlovi/filmovi.txt");
			BufferedReader br = new BufferedReader(new FileReader(filmoviFajl));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\\|");
				String naslovSrpski = split[0];
				String naslovOriginal = split[1];
				int godinaIzdavanja = Integer.parseInt(split[2]);
				String zanrovi = split[3];
				String imeRezisera = split[4];
				String prezimeRezisera = split[5];
				String opis = split[6];
				int trajanje = Integer.parseInt(split[7]);
				Film film = new Film(naslovSrpski, naslovOriginal, godinaIzdavanja, zanrovi, imeRezisera,
						prezimeRezisera, opis, trajanje);
				listaFilm.add(film);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void snimiFilmove() {
		try {
			File filmoviFajl = new File("src/fajlovi/filmovi.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(filmoviFajl));
			String sadrzaj = "";
			for (Film film : listaFilm) {
				sadrzaj += film.getNaslovSrb() + "|" + film.getNaslovOrg() + "|" + film.getGodinaIzdavanja() + "|"
						+ film.getZanrovi() + "|" + film.getImeRezisera() + "|" + film.getPrezimeRezisera() + "|"
						+ film.getOpis() + "|" + film.getTrajanje() + "\n";
			}
			bw.write(sadrzaj);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Film nadjiFilm(String naslov) {

		for (Film film : listaFilm) {

			if (film.getNaslovSrb().equals(naslov)) {

				return film;
			}
		}
		return null;

	}

	public void obrisiFilm(Film film) {

		this.listaFilm.remove(film);
	}

	public void dodajFilm(Film film) {

		this.listaFilm.add(film);
	}

	public void ucitajKopije() {
		try {

			File kopijeFajl = new File("src/fajlovi/kopije.txt");
			BufferedReader br = new BufferedReader(new FileReader(kopijeFajl));
			String line = null;
			while ((line = br.readLine()) != null) {

				String[] split = line.split("\\|");
				int oznaka = Integer.parseInt(split[0]);
				String film = split[1];
				String oznakaMedijuma = split[2];
				int brPrimeraka = Integer.parseInt(split[3]);
				Kopija kopija = new Kopija(oznaka, film, oznakaMedijuma, brPrimeraka);
				listaKopija.add(kopija);

			}
			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void snimiKopije() {
		try {
			File kopijeFajl = new File("src/fajlovi/kopije.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(kopijeFajl));
			String sadrzaj = "";
			for (Kopija kopija : listaKopija) {
				sadrzaj += kopija.getOznaka() + "|" + kopija.getFilm() + "|" + kopija.getOznakaMedijuma() + "|"
						+ kopija.getBrojPrimeraka() + "\n";
			}
			bw.write(sadrzaj);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Kopija nadjiKopiju(int oznaka) {

		for (Kopija kopija : listaKopija) {

			if (kopija.getOznaka() == oznaka) {

				return kopija;
			}
		}
		return null;
	}

	public void obrisiKopiju(Kopija kopija) {

		this.listaKopija.remove(kopija);
	}

	public void dodajKopiju(Kopija kopija) {

		this.listaKopija.add(kopija);
	}

	public void ucitajZanrove() {

		try {

			File zanrFajl = new File("src/fajlovi/zanrovi.txt");
			BufferedReader br = new BufferedReader(new FileReader(zanrFajl));
			String line = null;
			while ((line = br.readLine()) != null) {

				String[] split = line.split("\\|");
				String oznaka = split[0];
				String naziv = split[1];
				Zanr zanr = new Zanr(oznaka, naziv);
				listaZanr.add(zanr);
			}
			br.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void snimiZanrove() {

		try {

			File zanrFajl = new File("src/fajlovi/zanrovi.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(zanrFajl));
			String sadrzaj = "";
			for (Zanr zanr : listaZanr) {

				sadrzaj += zanr.getOznaka() + "|" + zanr.getNaziv() + "\n";
			}
			bw.write(sadrzaj);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Zanr nadjiZanr(String naziv) {

		for (Zanr zanr : listaZanr) {

			if (zanr.getNaziv().equals(naziv)) {

				return zanr;
			}
		}
		return null;

	}

	public void obrisiZanr(Zanr zanr) {
		this.listaZanr.remove(zanr);
	}

	public void dodajZanr(Zanr zanr) {
		this.listaZanr.add(zanr);
	}

	public void ucitajIznajmljivanja() {
		try {
			File IznajmljivanjeFajl = new File("src/fajlovi/iznajmljivanje.txt");
			BufferedReader br = new BufferedReader(new FileReader(IznajmljivanjeFajl));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\\|");
				int oznakaPaketa = Integer.parseInt(split[0]);
				int oznaka = Integer.parseInt(split[1]);
				String zaposleni = split[2];
				String clan = split[3];
				String datumIznajmljivanja = split[4];
				String datumVracanja = split[5];
				String kopija = split[6];
				double cena = Double.parseDouble(split[7]);
				int brojDana = Integer.parseInt(split[8]);
				Iznajmljivanje iznajmljivanje = new Iznajmljivanje(oznaka, oznakaPaketa, zaposleni, clan,
						datumIznajmljivanja, datumVracanja, kopija, cena, brojDana);
				listaIznajmljivanje.add(iznajmljivanje);

			}
			br.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void snimiIznajmljivanja() {
		try {
			File IznajmljivanjeFajl = new File("src/fajlovi/iznajmljivanje.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(IznajmljivanjeFajl));
			String sadrzaj = "";
			for (Iznajmljivanje iznajmljivanje : listaIznajmljivanje) {
				sadrzaj += iznajmljivanje.getOznakaPaketa() + "|" + iznajmljivanje.getOznaka() + "|"
						+ iznajmljivanje.getZaposleni() + "|" + iznajmljivanje.getClan() + "|"
						+ iznajmljivanje.getDatumIznajmljivanja() + "|" + iznajmljivanje.getDatumVracanja() + "|"
						+ iznajmljivanje.getKopija() + "|" + iznajmljivanje.getCena() + "|"
						+ iznajmljivanje.getBrojDana() + "\n";
			}
			bw.write(sadrzaj);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Iznajmljivanje nadjiIznajmljivanje(int oznaka) {

		for (Iznajmljivanje iznajmljivanje : listaIznajmljivanje) {

			if (iznajmljivanje.getOznaka() == oznaka) {

				return iznajmljivanje;
			}
		}
		return null;
	}

	public void obrisiIznajmljivanje(Iznajmljivanje iznajmljivanje) {

		this.listaIznajmljivanje.remove(iznajmljivanje);
	}

	public void dodajIznajmljivanje(Iznajmljivanje iznajmljivanje) {

		this.listaIznajmljivanje.add(iznajmljivanje);
	}

	public void ucitajMedijume() {

		try {

			File medijumFajl = new File("src/fajlovi/medijumi.txt");
			BufferedReader br = new BufferedReader(new FileReader(medijumFajl));
			String line = null;
			while ((line = br.readLine()) != null) {

				String[] split = line.split("\\|");
				String oznaka = split[0];
				int cena = Integer.parseInt(split[1]);
				Medijum medijum = new Medijum(oznaka, cena);
				listaMedijum.add(medijum);

			}
			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void snimiMedijume() {
		try {
			File medijumFajl = new File("src/fajlovi/medijumi.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(medijumFajl));
			String sadrzaj = "";
			for (Medijum medijum : listaMedijum) {
				sadrzaj += medijum.getOznaka() + "|" + medijum.getCena() + "\n";
			}
			bw.write(sadrzaj);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Medijum nadjiMedijum(String oznaka) {
		for (Medijum medijum : listaMedijum) {
			if (medijum.getOznaka().equals(oznaka)) {
				return medijum;
			}
		}
		return null;
	}

}
