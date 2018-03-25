package guiRadnik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import videoteka.Videoteka;

public class DodavanjeUKorpu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenuItem dodaj;
	private JScrollPane skrolTabele;
	private JTable kopijeTabela;
	private Videoteka videoteka;

	public DodavanjeUKorpu(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Dodavanje u korpu");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		tabelaInit();
		initAkcije();
	}

	private void tabelaInit() {
		this.mainMenu = new JMenuBar();
		this.file = new JMenu("File");
		this.dodaj = new JMenuItem("Dodaj");

		this.file.add(dodaj);
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
				int selektovaniRed = DodavanjeUKorpu.this.kopijeTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(DodavanjeUKorpu.this, "Morate nesto izabrati!");

				} else {
					int oznaka = (int) DodavanjeUKorpu.this.kopijeTabela.getValueAt(selektovaniRed, 0);
					Kopija kopija = DodavanjeUKorpu.this.videoteka.nadjiKopiju(oznaka);

					if (kopija.getBrojPrimeraka() > 0) {
						int potvrda = JOptionPane.showConfirmDialog(DodavanjeUKorpu.this, "Da li ste sigurni?",
								"Dodavanje u korpu", JOptionPane.YES_NO_OPTION);
						if (potvrda == JOptionPane.YES_OPTION) {
							videoteka.dodajUKorpu(kopija);
						}
					} else {
						JOptionPane.showMessageDialog(DodavanjeUKorpu.this, "Ne postoji vise kopija");
					}

				}

			}
		});

	}

}
