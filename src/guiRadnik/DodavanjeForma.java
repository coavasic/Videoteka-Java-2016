package guiRadnik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import film.Kopija;
import korisnik.Aktivnost;
import korisnik.Clan;
import net.miginfocom.swing.MigLayout;
import utils.KorpaUtils;
import utils.NumberUtils;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class DodavanjeForma extends JFrame {
	private Videoteka videoteka;

	private JButton btnPotvrdi;
	private JLabel lblOznaka;
	private JTextField tfOznaka;
	private JLabel lblZaposleni;
	private JTextField tfZaposleni;
	private JLabel lblClan;
	private JComboBox<Integer> cbClanovi;
	private JLabel lblDatumSad;
	private JTextField tfDatumSad;
	private JLabel lblDatumVracaja;
	private JTextField tfDatumVracanja;
	private JLabel lblFilmovi;
	private JButton btnKorpa;
	private JButton btnDodaj;

	public DodavanjeForma(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Novo Iznajmljivanje");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		izlazDijalog();
		initGui();
		initAction();
		pack();

	}

	private void initGui() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][]20[]");
		setLayout(layout);
		String oznaka1 = Integer.toString(videoteka.getListaIznajmljivanje().size() + 101);

		DateFormat formatDatuma = new SimpleDateFormat("dd.MM.yyyy");
		Date datum = new Date();
		String datumSadString = formatDatuma.format(datum);

		cbClanovi = new JComboBox<Integer>();
		cbClanovi.addItem(null);
		for (int i = 0; i < this.videoteka.getListaClan().size(); i++) {
			Clan clan = this.videoteka.getListaClan().get(i);
			if (clan.getAktivnost().equals(Aktivnost.AKTIVAN)) {
				cbClanovi.addItem(clan.getBrojKarte());
			}
		}

		btnPotvrdi = new JButton("Potvrdi");
		lblOznaka = new JLabel("Oznaka");
		tfOznaka = new JTextField(10);
		tfOznaka.setText(oznaka1);
		tfOznaka.setEnabled(false);
		lblZaposleni = new JLabel("Zaposleni");
		tfZaposleni = new JTextField(20);
		tfZaposleni.setText(this.videoteka.getUlogovan());
		tfZaposleni.setEnabled(false);
		lblClan = new JLabel("Clan");
		lblDatumSad = new JLabel("Datum Iznajmljivanja");
		tfDatumSad = new JTextField(20);
		tfDatumSad.setText(datumSadString);
		tfDatumSad.setEnabled(false);
		lblDatumVracaja = new JLabel("Datum Vracanja");
		tfDatumVracanja = new JTextField(20);
		tfDatumVracanja.setText(datumSadString);
		tfDatumVracanja.setToolTipText("Format datuma -> dd.mm.gggg");
		lblFilmovi = new JLabel("Filmovi");
		btnKorpa = new JButton("Korpa");
		btnDodaj = new JButton("Dodaj");

		this.getRootPane().setDefaultButton(btnPotvrdi);

		add(lblOznaka);
		add(tfOznaka);
		add(lblZaposleni);
		add(tfZaposleni);
		add(lblClan);
		add(cbClanovi);
		add(lblDatumSad);
		add(tfDatumSad);
		add(lblDatumVracaja);
		add(tfDatumVracanja);
		add(lblFilmovi);
		add(btnDodaj, "split 2, align right");
		add(btnKorpa, "align right");
		add(new JLabel());
		add(btnPotvrdi, "align right");

	}

	private void initAction() {

		this.btnDodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DodavanjeUKorpu doduk = new DodavanjeUKorpu(DodavanjeForma.this.videoteka);
				WindowUtils.centirarnjeProzora(doduk);
				doduk.setVisible(true);
				doduk.setAlwaysOnTop(true);

			}
		});
		//
		this.btnKorpa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KorpaProzor korpa = new KorpaProzor(DodavanjeForma.this.videoteka);
				WindowUtils.centirarnjeProzora(korpa);
				korpa.setVisible(true);
				korpa.setAlwaysOnTop(true);

			}
		});

		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean formatDatuma = true;
				Date date = null;
				int brDana = 0;

				int oznaka = Integer.parseInt(tfOznaka.getText());
				String zaposleni = tfZaposleni.getText().trim();
				String datumIzn = tfDatumSad.getText().trim();
				String datumVracanja = tfDatumVracanja.getText();

				if (!(videoteka.getKorpa().isEmpty())) {

					if (cbClanovi.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(DodavanjeForma.this, "Morate odabrati clana");
					} else {
						int clan = (Integer) cbClanovi.getSelectedItem();
						String clanString = String.valueOf(clan);

						Calendar cal1 = new GregorianCalendar();
						Calendar cal2 = new GregorianCalendar();

						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

						try {
							date = sdf.parse(datumIzn);
						} catch (ParseException e) {
						}
						cal2.setTime(date);

						try {
							date = sdf.parse(datumVracanja);
						} catch (ParseException e) {
							formatDatuma = false;
							tfDatumVracanja.setText(datumIzn);
						}

						if (!(formatDatuma)) {
							JOptionPane.showMessageDialog(DodavanjeForma.this, "Format datuma nije dobar");
						} else {
							cal1.setTime(date);
							brDana = NumberUtils.daniIzmedju(cal1.getTime(), cal2.getTime());
							if (brDana < 0) {
								JOptionPane.showMessageDialog(DodavanjeForma.this,
										"Datum vracanja mora biti isti ili posle datuma iznajmljivanja.");
							} else {

								int odgovor = JOptionPane.showConfirmDialog(DodavanjeForma.this, "Da li ste sigurni?",
										"Potvrda iznajmljivanja", JOptionPane.YES_NO_OPTION);

								if (odgovor == JOptionPane.YES_OPTION) {
									if (datumVracanja.equals(datumIzn)) {
										datumVracanja = "Nije Odredjen";
									}

									KorpaUtils.izracunajCenu(DodavanjeForma.this.videoteka.getListaMedijum(),
											DodavanjeForma.this.videoteka, DodavanjeForma.this.videoteka.getKorpa(),
											brDana, zaposleni, clanString, datumIzn, datumVracanja, oznaka);
									JOptionPane.showMessageDialog(DodavanjeForma.this,
											"Ukupna cena iznajmljivanja iznosi: " + videoteka.getUkupnaCenaKorpe());

									for (int i = 0; i < videoteka.getKorpa().size(); i++) {
										Kopija kopija = videoteka.getKorpa().get(i);
										KorpaUtils.azuriranjeBrojaKopija(kopija.getOznaka(),
												DodavanjeForma.this.videoteka);
									}
									videoteka.getKorpa().clear();

									DodavanjeForma.this.setVisible(false);
									DodavanjeForma.this.dispose();
									IznajmljivanjeProzor izp = new IznajmljivanjeProzor(DodavanjeForma.this.videoteka);
									WindowUtils.centirarnjeProzora(izp);
									izp.setVisible(true);
								}

							}

						}

					}
				} else {
					JOptionPane.showMessageDialog(DodavanjeForma.this, "Korpa je prazna");
				}

			}
		});
	}

	
	private void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				int odgovor = JOptionPane.showConfirmDialog(DodavanjeForma.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					DodavanjeForma.this.setVisible(false);
					DodavanjeForma.this.dispose();
					IznajmljivanjeProzor izf = new IznajmljivanjeProzor(DodavanjeForma.this.videoteka);
					WindowUtils.centirarnjeProzora(izf);
					izf.setVisible(true);
					videoteka.getKorpa().clear();

				}
			}
		});

	}
}
