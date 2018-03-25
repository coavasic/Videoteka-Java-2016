package guiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.ZaposleniProzor;
import korisnik.Pol;
import korisnik.TipZaposlenog;
import korisnik.Zaposleni;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class ZaposeniDodIzmDijalog extends JFrame {
	private Videoteka videoteka;
	private Zaposleni zaposleni;
	private ZaposleniProzor zaposleniProzor;

	private JLabel lblIme;
	private JTextField tfIme;
	private JLabel lblPrezime;
	private JTextField tfPrezime;
	private JLabel lblJMBG;
	private JTextField tfJMBG;
	private JLabel lblAdresa;
	private JTextField tfAdresa;
	private JLabel lblPol;
	private ButtonGroup btnPolGrupa;
	private JRadioButton rbtnMuski;
	private JRadioButton rbtnZenski;
	private JLabel lblPlata;
	private JTextField tfPlata;
	private JLabel lblTipRadnika;
	private ButtonGroup btnTipGrupa;
	private JRadioButton rbtnUrednik;
	private JRadioButton rbtnRadnik;
	private JLabel lblKorIme;
	private JTextField tfKorIme;
	private JLabel lblSifra;
	private JPasswordField pfSifra;
	private JButton btnOk;

	public ZaposeniDodIzmDijalog(Videoteka videoteka, Zaposleni zaposleni, ZaposleniProzor zaposleniProzor) {
		this.videoteka = videoteka;
		this.zaposleni = zaposleni;
		this.zaposleniProzor = zaposleniProzor;
		setTitle("Dodavanje Zaposlenog");
		if (zaposleni != null) {
			setTitle("Izmena Zaposlenog");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
		initAkcije();
		pack();

	}

	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][]15[][]15[][][]15[][]15[]");
		setLayout(layout);

		lblIme = new JLabel("Ime");
		tfIme = new JTextField(20);
		lblPrezime = new JLabel("Prezime");
		tfPrezime = new JTextField(20);
		lblAdresa = new JLabel("Adresa");
		tfAdresa = new JTextField(20);
		lblJMBG = new JLabel("JMBG");
		tfJMBG = new JTextField(20);
		lblPol = new JLabel("Pol");
		rbtnMuski = new JRadioButton("Muški");
		rbtnZenski = new JRadioButton("Ženski");
		btnPolGrupa = new ButtonGroup();
		btnPolGrupa.add(rbtnMuski);
		btnPolGrupa.add(rbtnZenski);
		lblPlata = new JLabel("Plata");
		tfPlata = new JTextField(20);
		lblTipRadnika = new JLabel("Tip Radnika");
		rbtnUrednik = new JRadioButton("Urednik");
		rbtnRadnik = new JRadioButton("Radnik");
		btnTipGrupa = new ButtonGroup();
		btnTipGrupa.add(rbtnUrednik);
		btnTipGrupa.add(rbtnRadnik);
		lblKorIme = new JLabel("Korisničko Ime");
		tfKorIme = new JTextField(20);
		lblSifra = new JLabel("Šifra");
		pfSifra = new JPasswordField(20);
		btnOk = new JButton("Potvrdi");

		this.getRootPane().setDefaultButton(btnOk);

		if (zaposleni != null) {
			stareVrednosti();
		}

		add(lblKorIme);
		add(tfKorIme);
		add(lblSifra);
		add(pfSifra);
		add(lblIme);
		add(tfIme);
		add(lblPrezime);
		add(tfPrezime);
		add(lblJMBG);
		add(tfJMBG);
		add(lblAdresa);
		add(tfAdresa);
		add(lblPlata);
		add(tfPlata);
		add(lblPol);
		add(rbtnZenski, "split 2");
		add(rbtnMuski);
		add(lblTipRadnika);
		add(rbtnUrednik, "split 2");
		add(rbtnRadnik);
		add(new JLabel());
		add(btnOk, "gapleft 167");

	}

	private void initAkcije() {
		this.btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				double plata = 0;
				boolean format = true;
				int jmbgDuzina = 13;
				boolean postojiZaposleni = false;

				String korIme = tfKorIme.getText().trim();
				String lozinka = new String(pfSifra.getPassword());
				String ime = tfIme.getText().trim();
				String prezime = tfPrezime.getText().trim();
				String adresa = tfAdresa.getText().trim();
				String jmbg = tfJMBG.getText();
				String plataString = tfPlata.getText();
				Pol pol = rbtnZenski.isSelected() ? Pol.ŽENSKI : Pol.MUŠKI;
				TipZaposlenog tip = rbtnRadnik.isSelected() ? TipZaposlenog.RADNIK : TipZaposlenog.UREDNIK;

				if (korIme.equals("") || lozinka.equals("") || ime.equals("") || prezime.equals("") || adresa.equals("")
						|| jmbg.equals("") || plataString.equals("")) {
					JOptionPane.showMessageDialog(ZaposeniDodIzmDijalog.this, "Niste uneli sve podatke!");
				}

				else {
					

					for (int i = 0; i < videoteka.getListaZaposleni().size(); i++) {
						Zaposleni zaposleni = videoteka.getListaZaposleni().get(i);
						if (zaposleni.getKorisnickoIme().equals(korIme)) {
							postojiZaposleni = true;
						}
					}

					if (!(jmbg.length() == jmbgDuzina)) {
						format = false;
					}

					try {
						plata = Double.parseDouble(plataString);
					} catch (Exception e1) {
						format = false;
					}

					if (format && plata >= 0 && jmbg.length() == 13) {
						int red = zaposleniProzor.getZaposleniTabela().getSelectedRow();
						DefaultTableModel model = (DefaultTableModel) zaposleniProzor.getZaposleniTabela().getModel();

						if (!postojiZaposleni) {
							if (zaposleni == null) {
								zaposleni = new Zaposleni(ime, prezime, jmbg, adresa, pol, plata, korIme, lozinka, tip);
								videoteka.dodajZaposlenog(zaposleni);
								Object[] noviRed = new Object[] { ime, prezime, jmbg, adresa, pol, plata, korIme, tip };
								model.addRow(noviRed);

							} else {
								zaposleni.setKorisnickoIme(korIme);
								zaposleni.setLozinka(lozinka);
								zaposleni.setIme(ime);
								zaposleni.setPrezime(prezime);
								zaposleni.setJmbg(jmbg);
								zaposleni.setAdresa(adresa);
								zaposleni.setPlata(plata);
								zaposleni.setPol(pol);
								zaposleni.setTipZaposlenog(tip);

								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(ime, red, 0);
								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(prezime, red,
										1);
								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(jmbg, red,
										2);
								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(adresa, red,
										3);
								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(pol, red, 4);
								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(plata, red,
										5);
								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(korIme, red,
										6);
								ZaposeniDodIzmDijalog.this.zaposleniProzor.getZaposleniTabela().setValueAt(tip, red, 7);

							}

							videoteka.sacuvajZaposlene();
							ZaposeniDodIzmDijalog.this.dispose();
							ZaposeniDodIzmDijalog.this.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(ZaposeniDodIzmDijalog.this,
									"Uneto korisničko ime već postoji!");
						}
					} else {
						JOptionPane.showMessageDialog(ZaposeniDodIzmDijalog.this,
								"Format unetih podataka nije dobar! Proverite podatke.");
					}
				}
			}
		});

	}

	private void stareVrednosti() {
		tfKorIme.setText(zaposleni.getKorisnickoIme());
		pfSifra.setText(zaposleni.getLozinka());
		tfIme.setText(zaposleni.getIme());
		tfPrezime.setText(zaposleni.getPrezime());
		tfJMBG.setText(String.valueOf(zaposleni.getJmbg()));
		tfAdresa.setText(zaposleni.getAdresa());
		tfPlata.setText(String.valueOf(zaposleni.getPlata()));
		if (zaposleni.getPol() == Pol.ŽENSKI) {
			rbtnZenski.setSelected(true);
		} else {
			rbtnMuski.setSelected(true);
		}
		if (zaposleni.getTipZaposlenog() == TipZaposlenog.UREDNIK) {
			rbtnUrednik.setSelected(true);
		} else {
			rbtnRadnik.setSelected(true);
		}

	}

}
