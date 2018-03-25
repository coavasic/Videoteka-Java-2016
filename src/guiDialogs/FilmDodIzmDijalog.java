package guiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import film.Film;
import film.Zanr;
import gui.FilmProzor;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class FilmDodIzmDijalog extends JFrame {

	private Videoteka videoteka;
	private Film film;
	private FilmProzor filmProzor;

	private JLabel lblNaslovSrpski;
	private JTextField tfNaslovSrpski;
	private JLabel lblNaslovOriginal;
	private JTextField tfNaslovOriginal;
	private JLabel lblGodIzadavanja;
	private JTextField tfGodIzdavanja;
	private JLabel lblImeRezisera;
	private JTextField tfImeRezisera;
	private JLabel lblPrezimeRezisera;
	private JTextField tfPrezimeRezisera;
	private JLabel lblOpis;
	private JTextField tfOpis;
	private JLabel lblTrajanje;
	private JTextField tfTrajanje;
	private JButton btnPotvrdi;
	private JLabel lblZanr;
	private JComboBox<String> cbZanr;

	public FilmDodIzmDijalog(Videoteka videoteka, Film film, FilmProzor filmProzor) {
		this.videoteka = videoteka;
		this.film = film;
		this.filmProzor = filmProzor;
		setTitle("Dodavanje filma");
		if (film != null) {
			setTitle("Izmena filma");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initKomponente();
		initAkcije();
		pack();

	}

	private void initKomponente() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][][][]");
		setLayout(layout);

		lblNaslovSrpski = new JLabel("Srpski Naslov");
		tfNaslovSrpski = new JTextField(20);
		lblNaslovOriginal = new JLabel("Originalni Naslov");
		tfNaslovOriginal = new JTextField(20);
		lblGodIzadavanja = new JLabel("Godina izdavanja");
		tfGodIzdavanja = new JTextField(20);
		lblImeRezisera = new JLabel("Ime Režisera");
		tfImeRezisera = new JTextField(20);
		lblPrezimeRezisera = new JLabel("Prezime Režisera");
		tfPrezimeRezisera = new JTextField(20);
		lblOpis = new JLabel("Opis");
		tfOpis = new JTextField(20);
		lblTrajanje = new JLabel("Trajanje (minuti)");
		tfTrajanje = new JTextField(20);
		btnPotvrdi = new JButton("Potvrdi");
		lblZanr = new JLabel("Zanr");
		cbZanr = new JComboBox<>();
		cbZanr.addItem("");
		for (int i = 0; i < videoteka.getListaZanr().size(); i++) {
			Zanr zanr = this.videoteka.getListaZanr().get(i);
			cbZanr.addItem(zanr.getNaziv());
		}

		this.getRootPane().setDefaultButton(btnPotvrdi);

		if (film != null) {
			stareVrednosti();
		}

		add(lblNaslovSrpski);
		add(tfNaslovSrpski);
		add(lblNaslovOriginal);
		add(tfNaslovOriginal);
		add(lblGodIzadavanja);
		add(tfGodIzdavanja);
		add(lblImeRezisera);
		add(tfImeRezisera);
		add(lblPrezimeRezisera);
		add(tfPrezimeRezisera);
		add(lblOpis);
		add(tfOpis);
		add(lblTrajanje);
		add(tfTrajanje);
		add(lblZanr);
		add(cbZanr);
		add(btnPotvrdi, "gapleft 167");

	}

	private void initAkcije() {
		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int godIzdavanja = 0;
				int trajanje = 0;
				boolean format = true;

				String srpskiNaslov = tfNaslovSrpski.getText().trim();
				String originalNaslov = tfNaslovOriginal.getText().trim();
				String godIzdavanjaString = tfGodIzdavanja.getText().trim();
				String imeRezisera = tfImeRezisera.getText().trim();
				String prezimeRezisera = tfPrezimeRezisera.getText().trim();
				String opis = tfOpis.getText().trim();
				String trajanjeString = tfTrajanje.getText().trim();
				String zanr = (String) cbZanr.getSelectedItem();

				if (srpskiNaslov.equals("") || originalNaslov.equals("") || godIzdavanjaString.equals("")
						|| imeRezisera.equals("") || prezimeRezisera.equals("") || opis.equals("")
						|| trajanjeString.equals("") || zanr.equals("")) {
					JOptionPane.showMessageDialog(FilmDodIzmDijalog.this, "Niste uneli sve podatke!");
				} else {

					try {
						godIzdavanja = Integer.parseInt(godIzdavanjaString);
						trajanje = Integer.parseInt(trajanjeString);
					} catch (Exception e2) {
						format = false;
					}

					if (format) {
						int red = filmProzor.getFilmoviTabela().getSelectedRow();
						DefaultTableModel model = (DefaultTableModel) filmProzor.getFilmoviTabela().getModel();

						if (film == null) {
							film = new Film(srpskiNaslov, originalNaslov, godIzdavanja, zanr, imeRezisera,
									prezimeRezisera, opis, trajanje);
							videoteka.dodajFilm(film);
							Object[] noviRed = new Object[] { srpskiNaslov, originalNaslov, godIzdavanja, zanr,
									imeRezisera, prezimeRezisera, opis, trajanje };
							model.addRow(noviRed);
						} else {
							film.setNaslovSrb(srpskiNaslov);
							film.setNaslovOrg(originalNaslov);
							film.setGodinaIzdavanja(godIzdavanja);
							film.setImeRezisera(imeRezisera);
							film.setPrezimeRezisera(prezimeRezisera);
							film.setOpis(opis);
							film.setTrajanje(trajanje);
							film.setZanrovi(zanr);

							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(srpskiNaslov, red, 0);
							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(originalNaslov, red, 1);
							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(godIzdavanja, red, 2);
							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(zanr, red, 3);
							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(imeRezisera, red, 4);
							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(prezimeRezisera, red, 5);
							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(opis, red, 6);
							FilmDodIzmDijalog.this.filmProzor.getFilmoviTabela().setValueAt(trajanje, red, 7);

						}
						videoteka.snimiFilmove();
						FilmDodIzmDijalog.this.dispose();
						FilmDodIzmDijalog.this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(FilmDodIzmDijalog.this,
								"Uneti podaci nisu ispravni! Morate uneti broj kod trajanja i godine izadavanja!");
					}
				}
			}
		});

	}

	private void stareVrednosti() {
		tfNaslovSrpski.setText(film.getNaslovSrb());
		tfNaslovOriginal.setText(film.getNaslovOrg());
		tfGodIzdavanja.setText(String.valueOf(film.getGodinaIzdavanja()));
		tfImeRezisera.setText(film.getImeRezisera());
		tfPrezimeRezisera.setText(film.getPrezimeRezisera());
		tfOpis.setText(film.getOpis());
		tfTrajanje.setText(String.valueOf(film.getTrajanje()));
		cbZanr.setSelectedItem(film.getZanrovi());

	}

}
