package guiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.ClanoviProzor;
import korisnik.Aktivnost;
import korisnik.Clan;
import korisnik.Pol;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class ClanoviDodIzmDijalog extends JFrame {

	private Videoteka videoteka;
	private Clan clan;
	private ClanoviProzor clanoviProzor;

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
	private JLabel lblBrKarte;
	private JTextField tfBrKarte;
	private JButton btnPotvrdi;

	public ClanoviDodIzmDijalog(Videoteka videoteka, Clan clan, ClanoviProzor clanoviProzor) {
		this.videoteka = videoteka;
		this.clan = clan;
		this.clanoviProzor = clanoviProzor;
		setTitle("Dodavanje Clana");
		if (clan != null) {
			setTitle("Izmena Člana");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
		initAkcije();
		pack();
	}

	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[][]15[][]15[]15[]");
		setLayout(layout);

		int oznaka1 = videoteka.getListaClan().size() + 501;

		lblBrKarte = new JLabel("Broj Karte");
		tfBrKarte = new JTextField(20);
		tfBrKarte.setText(String.valueOf(oznaka1));
		tfBrKarte.setEnabled(false);
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
		btnPotvrdi = new JButton("Potvrdi");

		this.getRootPane().setDefaultButton(btnPotvrdi);

		if (clan != null) {
			stareVrednosti();
		}

		add(lblBrKarte);
		add(tfBrKarte);
		add(lblIme);
		add(tfIme);
		add(lblPrezime);
		add(tfPrezime);
		add(lblJMBG);
		add(tfJMBG);
		add(lblAdresa);
		add(tfAdresa);
		add(lblPol);
		add(rbtnZenski, "split 2");
		add(rbtnMuski);
		add(new JLabel());
		add(btnPotvrdi, "gapleft 167");

	}

	private void initAkcije() {
		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean format = true;
				String regex = "[0-9]+";

				int brKarte = Integer.parseInt(tfBrKarte.getText().trim());
				String ime = tfIme.getText().trim();
				String prezime = tfPrezime.getText().trim();
				String adresa = tfAdresa.getText().trim();
				String jmbg = tfJMBG.getText();
				Pol pol = rbtnZenski.isSelected() ? Pol.ŽENSKI : Pol.MUŠKI;
				Aktivnost aktivnost = Aktivnost.AKTIVAN;

				if (ime.equals("") || prezime.equals("") || adresa.equals("") || jmbg.equals("")) {
					JOptionPane.showMessageDialog(ClanoviDodIzmDijalog.this, "Niste uneli sve podatke!");
				}

				else {
					if (!(jmbg.matches(regex))) {
						format = false;
					}

					if ((format) && (jmbg.length() == 13)) {
						int red = clanoviProzor.getClanoviTabela().getSelectedRow();
						DefaultTableModel model = (DefaultTableModel) clanoviProzor.getClanoviTabela().getModel();

						if (clan == null) {
							clan = new Clan(ime, prezime, jmbg, adresa, pol, brKarte, aktivnost);
							videoteka.dodajClana(clan);
							Object[] noviRed = new Object[] { ime, prezime, jmbg, adresa, pol, brKarte, aktivnost };
							model.addRow(noviRed);
						} else {
							clan.setBrojKarte(brKarte);
							clan.setIme(ime);
							clan.setPrezime(prezime);
							clan.setJmbg(jmbg);
							clan.setAdresa(adresa);
							clan.setPol(pol);

							ClanoviDodIzmDijalog.this.clanoviProzor.getClanoviTabela().setValueAt(ime, red, 0);
							ClanoviDodIzmDijalog.this.clanoviProzor.getClanoviTabela().setValueAt(prezime, red, 1);
							ClanoviDodIzmDijalog.this.clanoviProzor.getClanoviTabela().setValueAt(jmbg, red, 2);
							ClanoviDodIzmDijalog.this.clanoviProzor.getClanoviTabela().setValueAt(adresa, red, 3);
							ClanoviDodIzmDijalog.this.clanoviProzor.getClanoviTabela().setValueAt(pol, red, 4);
							ClanoviDodIzmDijalog.this.clanoviProzor.getClanoviTabela().setValueAt(brKarte, red, 5);
							ClanoviDodIzmDijalog.this.clanoviProzor.getClanoviTabela().setValueAt(aktivnost, red, 6);
						}

						videoteka.snimiClanove();
						ClanoviDodIzmDijalog.this.dispose();
						ClanoviDodIzmDijalog.this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(ClanoviDodIzmDijalog.this, "Neispravan JMBG");
					}

				}

			}
		});
	}

	private void stareVrednosti() {
		tfBrKarte.setText(String.valueOf(clan.getBrojKarte()));
		tfIme.setText(clan.getIme());
		tfPrezime.setText(clan.getPrezime());
		tfJMBG.setText(String.valueOf(clan.getJmbg()));
		tfAdresa.setText(clan.getAdresa());
		if (clan.getPol() == Pol.ŽENSKI) {
			rbtnZenski.setSelected(true);
		} else {
			rbtnMuski.setSelected(true);
		}

	}

}
