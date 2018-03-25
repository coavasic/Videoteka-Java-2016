package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import guiDialogs.VideotekaIzmenaDijalog;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class UrednikProzor extends JFrame {
	private Videoteka videoteka;

	private JButton btnVideoteka;
	private JButton btnZaposleni;
	private JButton btnFilmovi;
	private JButton btnKopije;
	private JButton btnZanrovi;
	private JButton btnMedijum;
	private JButton btnOdjava;

	public UrednikProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle(videoteka.getNaziv() + " " + videoteka.getUlogovan());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(330, 500);
		setResizable(false);
		izlazDijalog();
		initGui();
		initAction();
	}

	private void initGui() {
		GridLayout layout = new GridLayout(3, 2);
		setLayout(layout);

		this.btnVideoteka = new JButton("Videoteka");
		this.btnZaposleni = new JButton("Zaposleni");
		this.btnFilmovi = new JButton("Filmovi");
		this.btnKopije = new JButton("Kopije");
		this.btnZanrovi = new JButton("Žanrovi");
		this.btnMedijum = new JButton("Medijum");
		this.btnOdjava = new JButton("Odjava");

		add(btnVideoteka);
		add(btnZaposleni);
		add(btnFilmovi);
		add(btnKopije);
		add(btnZanrovi);
		add(btnMedijum);
		add(btnOdjava);
	}

	private void initAction() {
		btnVideoteka.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UrednikProzor.this.dispose();
				UrednikProzor.this.setVisible(false);
				VideotekaIzmenaDijalog vid = new VideotekaIzmenaDijalog(videoteka);
				WindowUtils.centirarnjeProzora(vid);
				vid.setVisible(true);

			}
		});

		btnZaposleni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UrednikProzor.this.dispose();
				UrednikProzor.this.setVisible(false);
				ZaposleniProzor zp = new ZaposleniProzor(videoteka);
				WindowUtils.centirarnjeProzora(zp);
				zp.setVisible(true);

			}
		});

		btnFilmovi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UrednikProzor.this.dispose();
				UrednikProzor.this.setVisible(false);
				FilmProzor fp = new FilmProzor(videoteka);
				WindowUtils.centirarnjeProzora(fp);
				fp.setVisible(true);

			}
		});

		btnKopije.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UrednikProzor.this.dispose();
				UrednikProzor.this.setVisible(false);
				KopijeProzor kp = new KopijeProzor(videoteka);
				WindowUtils.centirarnjeProzora(kp);
				kp.setVisible(true);

			}
		});

		btnZanrovi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UrednikProzor.this.setVisible(false);
				UrednikProzor.this.dispose();
				ZanrProzor zp = new ZanrProzor(videoteka);
				WindowUtils.centirarnjeProzora(zp);
				zp.setVisible(true);

			}
		});

		btnMedijum.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UrednikProzor.this.setVisible(false);
				UrednikProzor.this.dispose();
				MedijumProzor mp = new MedijumProzor(videoteka);
				WindowUtils.centirarnjeProzora(mp);
				mp.setVisible(true);

			}
		});

		btnOdjava.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UrednikProzor.this.setVisible(false);
				UrednikProzor.this.dispose();
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

			}
		});

	}

	public void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrda izlaza iz aplikacije",
						JOptionPane.YES_NO_OPTION);
				videoteka.sacuvajSve();

				if (odgovor == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
	}
}
