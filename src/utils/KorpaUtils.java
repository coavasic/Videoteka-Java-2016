package utils;

import java.util.ArrayList;

import film.Iznajmljivanje;
import film.Kopija;
import film.Medijum;
import videoteka.Videoteka;

public class KorpaUtils {
	public static double izracunajCenu(ArrayList<Medijum> medijumi, Videoteka videoteka, ArrayList<Kopija> korpa,
			int brDana, String zaposleni, String clan, String datumIzn, String datumVracanj, int oznaka) {
		int cenaVHS = 0;
		int cenaDVD = 0;
		int cenaDVDBR = 0;

		for (Medijum medijum : medijumi) {
			Medijum objMedijum = videoteka.nadjiMedijum(medijum.getOznaka());
			if (objMedijum.getOznaka().equals("VHS")) {
				cenaVHS += objMedijum.getCena();
			}
			if (objMedijum.getOznaka().equals("DVD")) {
				cenaDVD += objMedijum.getCena();
			}
			if (objMedijum.getOznaka().equals("DVD BR")) {
				cenaDVDBR += objMedijum.getCena();
			}
		}

		double ukupnaCena = 0;
		double cena = 0;
		for (Kopija kopija : korpa) {
			cena = 0;
			String oznakaMedijum = kopija.getOznakaMedijuma();
			if (oznakaMedijum.equals("VHS")) {
				cena += cenaVHS;
				ukupnaCena += cenaVHS;
			}
			if (oznakaMedijum.equals("DVD")) {
				cena += cenaDVD;
				ukupnaCena += cenaDVD;
			}
			if (oznakaMedijum.equals("DVD BR")) {
				cena += cenaDVDBR;
				ukupnaCena += cenaDVDBR;
			}
			snimanjeInzajmljivanja(videoteka, zaposleni, clan, datumIzn, datumVracanj, kopija.getFilm(), cena, brDana,
					oznaka);
		}
		videoteka.setUkupnaCenaKorpe(ukupnaCena * brDana);
		return cena * brDana;

	}

	public static void snimanjeInzajmljivanja(Videoteka videoteka, String zaposleni, String clan, String datumIzn,
			String datumVracanj, String film, double cenaJednog, int brDana, int oznaka) {
		int oznaka1 = videoteka.getKorpa().size() + 1001;

		Iznajmljivanje iznajmljivanje = new Iznajmljivanje(oznaka, oznaka1, zaposleni, clan, datumIzn, datumVracanj,
				film, cenaJednog, brDana);
		videoteka.dodajIznajmljivanje(iznajmljivanje);
		videoteka.snimiIznajmljivanja();

	}

	public static void azuriranjeBrojaKopija(int oznakaKopije, Videoteka videoteka) {
		Kopija kopija = videoteka.nadjiKopiju(oznakaKopije);
		int stariBrojKopija = (int) kopija.getBrojPrimeraka();
		kopija.setBrojPrimeraka(stariBrojKopija - 1);
		videoteka.snimiKopije();

	}

}
