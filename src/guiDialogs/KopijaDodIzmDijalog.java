package guiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import film.Film;
import film.Kopija;
import gui.KopijeProzor;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class KopijaDodIzmDijalog extends JFrame {
	private Videoteka videoteka;
	private Kopija kopija;
	private KopijeProzor kopijeProzor;

	private JLabel lblOznaka;
	private JTextField tfOznaka;
	private JLabel lblFilm;
	private JComboBox<String> cbFilm;
	private JLabel lblOznakaMedijuma;
	private ButtonGroup btnMedijumGrupa;
	private JRadioButton rbtnVHS;
	private JRadioButton rbtnDVD;
	private JRadioButton rbtnDVDBR;
	private JLabel lblBrPrimeraka;
	private JTextField tfBrPrimeraka;
	private JButton btnPotvrdi;

	public KopijaDodIzmDijalog(Videoteka videoteka, Kopija kopija, KopijeProzor kopijeProzor) {
		this.videoteka = videoteka;
		this.kopija = kopija;
		this.kopijeProzor = kopijeProzor;
		setTitle("Dodavanje Kopije");
		if (kopija != null) {
			setTitle("Izmena Kopije");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
		initAkcije();
		pack();

	}

	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][]20[]");
		setLayout(layout);

		String oznaka1 = "K" + Integer.toString(videoteka.getListaKopija().size() + 1);

		cbFilm = new JComboBox<>();
		cbFilm.addItem("");
		for (int i = 0; i < videoteka.getListaFilm().size(); i++) {
			Film film = this.videoteka.getListaFilm().get(i);
			cbFilm.addItem(film.getNaslovSrb());
		}

		lblOznaka = new JLabel("Oznaka");
		tfOznaka = new JTextField(20);
		tfOznaka.setText(oznaka1);
		tfOznaka.setEnabled(false);
		lblFilm = new JLabel("Film");
		lblOznakaMedijuma = new JLabel("Medijum");
		rbtnVHS = new JRadioButton("VHS");
		rbtnVHS.setActionCommand("VHS");
		rbtnDVD = new JRadioButton("DVD");
		rbtnDVD.setActionCommand("DVD");
		rbtnDVDBR = new JRadioButton("DVD BR");
		rbtnDVDBR.setActionCommand("DVD BR");
		btnMedijumGrupa = new ButtonGroup();
		btnMedijumGrupa.add(rbtnVHS);
		btnMedijumGrupa.add(rbtnDVD);
		btnMedijumGrupa.add(rbtnDVDBR);
		lblBrPrimeraka = new JLabel("Broj Primeraka");
		tfBrPrimeraka = new JTextField(20);
		btnPotvrdi = new JButton("Potvrdi");

		this.getRootPane().setDefaultButton(btnPotvrdi);

		if (kopija != null) {
			stareVrednosti();
		}

		add(lblOznaka);
		add(tfOznaka);
		add(lblFilm);
		add(cbFilm);
		add(lblOznakaMedijuma);
		add(rbtnVHS, "split 3");
		add(rbtnDVD);
		add(rbtnDVDBR);
		add(lblBrPrimeraka);
		add(tfBrPrimeraka);
		add(new JLabel());
		add(btnPotvrdi, "gapleft 167");

	}

	private void initAkcije() {
		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean medijumPrazan = false;
				boolean brPrimerakaOK = true;
				String medijum = "";
				long brojPrimeraka = 0;

				int oznaka = Integer.parseInt(tfOznaka.getText());
				String film = (String) cbFilm.getSelectedItem();
				String brojPrimerakaString = tfBrPrimeraka.getText();
				try {
					medijum = btnMedijumGrupa.getSelection().getActionCommand();
				} catch (Exception e1) {
					medijumPrazan = true;
				}

				if (brojPrimerakaString.equals("") || film.equals("") || medijumPrazan == true) {
					JOptionPane.showMessageDialog(KopijaDodIzmDijalog.this, "Niste uneli sve podatke!");
				} else {
					try {
						brojPrimeraka = Long.parseLong(brojPrimerakaString);
					} catch (Exception e2) {
						brPrimerakaOK = false;
					}

					if (brojPrimeraka < 0) {
						brPrimerakaOK = false;
					}

					if (brPrimerakaOK) {
						int red = kopijeProzor.getKopijeTabela().getSelectedRow();
						DefaultTableModel model = (DefaultTableModel) kopijeProzor.getKopijeTabela().getModel();

						if (kopija == null) {
							kopija = new Kopija(oznaka, film, medijum, brojPrimeraka);
							videoteka.dodajKopiju(kopija);
							Object[] noviRed = new Object[] { oznaka, film, medijum, brojPrimeraka };
							model.addRow(noviRed);

						} else {
							kopija.setOznaka(oznaka);
							kopija.setBrojPrimeraka(brojPrimeraka);
							kopija.setFilm(film);
							kopija.setOznakaMedijuma(medijum);

							KopijaDodIzmDijalog.this.kopijeProzor.getKopijeTabela().setValueAt(oznaka, red, 0);
							KopijaDodIzmDijalog.this.kopijeProzor.getKopijeTabela().setValueAt(film, red, 1);
							KopijaDodIzmDijalog.this.kopijeProzor.getKopijeTabela().setValueAt(medijum, red, 2);
							KopijaDodIzmDijalog.this.kopijeProzor.getKopijeTabela().setValueAt(brojPrimeraka, red, 3);

						}
						videoteka.snimiKopije();
						KopijaDodIzmDijalog.this.dispose();
						KopijaDodIzmDijalog.this.setVisible(false);

					} else {
						JOptionPane.showMessageDialog(KopijaDodIzmDijalog.this,
								"Format broja primeraka nije dobar. Mora biti broj veci ili jednak nuli!");
					}

				}

			}
		});

	}

	private void stareVrednosti() {
		tfOznaka.setText(String.valueOf(kopija.getOznaka()));
		tfBrPrimeraka.setText(String.valueOf(kopija.getBrojPrimeraka()));
		cbFilm.setSelectedItem(kopija.getFilm());
		cbFilm.setEnabled(false);

		if (kopija.getOznakaMedijuma().equals("VHS")) {
			rbtnVHS.setSelected(true);
		} else if (kopija.getOznakaMedijuma().equals("DVD")) {
			rbtnDVD.setSelected(true);
		} else {
			rbtnDVDBR.setSelected(true);
		}

	}

}
