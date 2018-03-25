package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import film.Film;
import guiDialogs.FilmDodIzmDijalog;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class FilmProzor extends JFrame {

	private Videoteka videoteka;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenuItem dodaj;
	private JMenuItem izmeni;
	private JMenuItem obrisi;

	private JScrollPane skrolTabele;
	private JTable filmoviTabela;

	public FilmProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Filmovi");
		setSize(700, 450);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		izlazDijalog();
		initGui();
		initAkcije();

	}

	private void initGui() {
		this.mainMenu = new JMenuBar();
		this.file = new JMenu("File");
		this.dodaj = new JMenuItem("Dodaj");
		this.izmeni = new JMenuItem("Izmeni");
		this.obrisi = new JMenuItem("Izbrisi");

		this.file.add(dodaj);
		this.file.add(izmeni);
		this.file.add(obrisi);

		this.mainMenu.add(file);

		setJMenuBar(mainMenu);

		String[] zaglavlje = new String[] { "Srpski naslov", "Original Naslov", "Godina Izadavanja", "Zanr",
				"Ime Rezisera", "Prezime Rezisera", "Opis", "Trajanje(minuti)" };
		Object[][] podaci = new Object[this.videoteka.getListaFilm().size()][zaglavlje.length];

		for (int i = 0; i < this.videoteka.getListaFilm().size(); i++) {
			Film film = this.videoteka.getListaFilm().get(i);
			podaci[i][0] = film.getNaslovSrb();
			podaci[i][1] = film.getNaslovOrg();
			podaci[i][2] = film.getGodinaIzdavanja();
			podaci[i][3] = film.getZanrovi();
			podaci[i][4] = film.getImeRezisera();
			podaci[i][5] = film.getPrezimeRezisera();
			podaci[i][6] = film.getOpis();
			podaci[i][7] = film.getTrajanje();
		}

		DefaultTableModel modelTabele = new DefaultTableModel(podaci, zaglavlje);
		filmoviTabela = new JTable(modelTabele);
		filmoviTabela.setColumnSelectionAllowed(false);
		filmoviTabela.setRowSelectionAllowed(true);
		filmoviTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filmoviTabela.setDefaultEditor(Object.class, null);
		filmoviTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(filmoviTabela);
		add(skrolTabele, BorderLayout.CENTER);

	}

	public JTable getFilmoviTabela() {
		return filmoviTabela;
	}

	private void initAkcije() {
		this.dodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FilmDodIzmDijalog fdid = new FilmDodIzmDijalog(FilmProzor.this.videoteka, null, FilmProzor.this);
				WindowUtils.centirarnjeProzora(fdid);
				fdid.setVisible(true);
				fdid.setAlwaysOnTop(true);

			}
		});

		this.izmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selektovaniRed = FilmProzor.this.filmoviTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(FilmProzor.this, "Niste nista izabrali!");

				} else {
					String naslov = (String) FilmProzor.this.filmoviTabela.getValueAt(selektovaniRed, 0);
					Film film = FilmProzor.this.videoteka.nadjiFilm(naslov);
					FilmDodIzmDijalog fdid = new FilmDodIzmDijalog(FilmProzor.this.videoteka, film, FilmProzor.this);
					WindowUtils.centirarnjeProzora(fdid);
					fdid.setVisible(true);
					fdid.setAlwaysOnTop(true);

				}

			}
		});

		this.obrisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selektovaniRed = FilmProzor.this.filmoviTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(FilmProzor.this, "Niste ništa izabrali!");

				} else {
					int potvrda = JOptionPane.showConfirmDialog(FilmProzor.this, "Da li ste sigurni?", "Brisanje Filma",
							JOptionPane.YES_NO_OPTION);
					if (potvrda == JOptionPane.YES_OPTION) {
						String naslov = (String) FilmProzor.this.filmoviTabela.getValueAt(selektovaniRed, 0);
						Film film = FilmProzor.this.videoteka.nadjiFilm(naslov);
						if (film != null) {
							DefaultTableModel modelTabele = (DefaultTableModel) FilmProzor.this.filmoviTabela
									.getModel();
							modelTabele.removeRow(selektovaniRed);
							FilmProzor.this.videoteka.obrisiFilm(film);
							FilmProzor.this.videoteka.snimiFilmove();
						} else {
							JOptionPane.showMessageDialog(FilmProzor.this, "Greska prilikom pronalazenja filma!");
						}
					}
				}
			}
		});

	}

	private void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(FilmProzor.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					FilmProzor.this.setVisible(false);
					FilmProzor.this.dispose();
					UrednikProzor up = new UrednikProzor(videoteka);
					WindowUtils.centirarnjeProzora(up);
					up.setVisible(true);

				}
			}
		});

	}

}
