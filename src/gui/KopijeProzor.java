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

import film.Kopija;
import guiDialogs.KopijaDodIzmDijalog;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class KopijeProzor extends JFrame {

	private Videoteka videoteka;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenuItem dodaj;
	private JMenuItem izmeni;
	private JMenuItem obrisi;

	private JScrollPane skrolTabele;
	private JTable kopijeTabela;

	public KopijeProzor(Videoteka videoteka) {
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

		String[] zaglavlje = new String[] { "Oznaka Kopije", "Film", "Medijum", "Broj Primeraka" };
		Object[][] podaci = new Object[this.videoteka.getListaKopija().size()][zaglavlje.length];

		for (int i = 0; i < videoteka.getListaKopija().size(); i++) {
			Kopija kopija = this.videoteka.getListaKopija().get(i);
			podaci[i][0] = kopija.getOznaka();
			podaci[i][1] = kopija.getFilm();
			podaci[i][2] = kopija.getOznakaMedijuma();
			podaci[i][3] = kopija.getBrojPrimeraka();
		}

		DefaultTableModel modelTabele = new DefaultTableModel(podaci, zaglavlje);
		kopijeTabela = new JTable(modelTabele);
		kopijeTabela.setColumnSelectionAllowed(false);
		kopijeTabela.setRowSelectionAllowed(true);
		kopijeTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		kopijeTabela.setDefaultEditor(Object.class, null);
		kopijeTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(kopijeTabela);
		add(skrolTabele, BorderLayout.CENTER);

	}

	public JTable getKopijeTabela() {
		return kopijeTabela;

	}

	private void initAkcije() {
		this.dodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				KopijaDodIzmDijalog kdid = new KopijaDodIzmDijalog(KopijeProzor.this.videoteka, null,
						KopijeProzor.this);
				WindowUtils.centirarnjeProzora(kdid);
				kdid.setVisible(true);
				kdid.setAlwaysOnTop(true);

			}
		});

		this.izmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = KopijeProzor.this.kopijeTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(KopijeProzor.this, "Niste nista izabrali!");

				} else {
					int oznaka = (int) KopijeProzor.this.kopijeTabela.getValueAt(selektovaniRed, 0);
					Kopija kopija = KopijeProzor.this.videoteka.nadjiKopiju(oznaka);
					KopijaDodIzmDijalog kdid = new KopijaDodIzmDijalog(KopijeProzor.this.videoteka, kopija,
							KopijeProzor.this);
					WindowUtils.centirarnjeProzora(kdid);
					kdid.setVisible(true);
					kdid.setAlwaysOnTop(true);
				}
			}
		});

		this.obrisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = KopijeProzor.this.kopijeTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(KopijeProzor.this, "Niste nista izabrali!");

				} else {
					int potvrda = JOptionPane.showConfirmDialog(KopijeProzor.this, "Da li ste sigurni?",
							"Brisanje Kopije", JOptionPane.YES_NO_OPTION);
					if (potvrda == JOptionPane.YES_OPTION) {
						int oznaka = (int) KopijeProzor.this.kopijeTabela.getValueAt(selektovaniRed, 0);
						Kopija kopija = KopijeProzor.this.videoteka.nadjiKopiju(oznaka);
						if (kopija != null) {
							DefaultTableModel modelTabele = (DefaultTableModel) KopijeProzor.this.kopijeTabela
									.getModel();
							modelTabele.removeRow(selektovaniRed);
							KopijeProzor.this.videoteka.obrisiKopiju(kopija);
							KopijeProzor.this.videoteka.snimiKopije();
						} else {
							JOptionPane.showMessageDialog(KopijeProzor.this, "Greska prilikom pronalaženja kopije!");
						}
					}
				}

			}
		});
	}

	private void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(KopijeProzor.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					KopijeProzor.this.setVisible(false);
					KopijeProzor.this.dispose();
					UrednikProzor up = new UrednikProzor(videoteka);
					WindowUtils.centirarnjeProzora(up);
					up.setVisible(true);

				}
			}
		});

	}

}
