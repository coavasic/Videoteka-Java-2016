package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import guiRadnik.IznajmljivanjeProzor;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class RadnikProzor extends JFrame {
	private Videoteka videoteka;

	private JButton btnIznajmljivanje;
	private JButton btnClanarine;
	private JButton btnOdjava;

	public RadnikProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle(videoteka.getNaziv() + " " + videoteka.getUlogovan());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(450, 150);
		setResizable(false);
		izlazDijalog();
		initGui();
		initAction();
	}

	private void initGui() {
		GridLayout layout = new GridLayout(1, 3);
		setLayout(layout);

		this.btnIznajmljivanje = new JButton("Iznajmljivanje");
		this.btnClanarine = new JButton("Clanarine");
		this.btnOdjava = new JButton("Odjava");

		add(btnIznajmljivanje);
		add(btnClanarine);
		add(btnOdjava);

	}

	private void initAction() {
		btnIznajmljivanje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RadnikProzor.this.setVisible(false);
				RadnikProzor.this.dispose();
				IznajmljivanjeProzor izp = new IznajmljivanjeProzor(videoteka);
				WindowUtils.centirarnjeProzora(izp);
				izp.setVisible(true);

			}
		});

		btnClanarine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RadnikProzor.this.setVisible(false);
				RadnikProzor.this.dispose();
				ClanoviProzor cp = new ClanoviProzor(videoteka);
				WindowUtils.centirarnjeProzora(cp);
				cp.setVisible(true);

			}
		});

		btnOdjava.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RadnikProzor.this.setVisible(false);
				RadnikProzor.this.dispose();
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
