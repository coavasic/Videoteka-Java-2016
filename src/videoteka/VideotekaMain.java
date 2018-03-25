package videoteka;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.LoginProzor;
import utils.WindowUtils;

public class VideotekaMain {

	public static void main(String[] args) throws UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
		Videoteka videoteka = new Videoteka();

		videoteka.ucitajVideoteku();
		videoteka.ucitajFilmove();
		videoteka.ucitajKopije();
		videoteka.ucitajZanrove();
		videoteka.ucitajIznajmljivanja();
		videoteka.ucitajClanove();
		videoteka.ucitajZaposlene();
		videoteka.ucitajMedijume();

		LoginProzor loginProzor = new LoginProzor(videoteka);
		WindowUtils.centirarnjeProzora(loginProzor);
		loginProzor.setVisible(true);
		loginProzor.setAlwaysOnTop(true);

	}

}
