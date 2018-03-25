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

@SuppressWarnings("serial")

public class KorpaProzor extends JFrame {

	private Videoteka videoteka;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenuItem brisi;
	private JScrollPane skrolTabele;
	private JTable korpaTabela;

	public KorpaProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Korpa");
		setSize(900, 420);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		tabelaInit();
		initAkcije();
	}

	private void tabelaInit() {

		this.mainMenu = new JMenuBar();
		this.file = new JMenu("File");
		this.brisi = new JMenuItem("Obrisi");

		this.file.add(brisi);
		this.mainMenu.add(file);
		setJMenuBar(mainMenu);

		String[] zaglavlje = new String[] { "Oznaka Kopije", "Film", "Medijum" };
		Object[][] podaci = new Object[this.videoteka.getKorpa().size()][zaglavlje.length];

		for (int i = 0; i < videoteka.getKorpa().size(); i++) {
			Kopija kopija = this.videoteka.getKorpa().get(i);
			podaci[i][0] = kopija.getOznaka();
			podaci[i][1] = kopija.getFilm();
			podaci[i][2] = kopija.getOznakaMedijuma();
		}

		DefaultTableModel modelTabele = new DefaultTableModel(podaci, zaglavlje);
		korpaTabela = new JTable(modelTabele);
		korpaTabela.setColumnSelectionAllowed(false);
		korpaTabela.setRowSelectionAllowed(true);
		korpaTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		korpaTabela.setDefaultEditor(Object.class, null);
		korpaTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(korpaTabela);
		add(skrolTabele, BorderLayout.CENTER);

	}

	public JTable getKorpaTabela() {
		return korpaTabela;
	}

	private void initAkcije() {
		this.brisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = KorpaProzor.this.korpaTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(KorpaProzor.this, "Morate nesto odabrati");

				} else {
					int oznaka = (int) KorpaProzor.this.korpaTabela.getValueAt(selektovaniRed, 0);
					Kopija kopija = KorpaProzor.this.videoteka.nadjiKopiju(oznaka);
					if (kopija != null) {
						int potvrda = JOptionPane.showConfirmDialog(KorpaProzor.this, "Da li ste sigurni?",
								"Brisanje iz korpe", JOptionPane.YES_NO_OPTION);
						if (potvrda == JOptionPane.YES_OPTION) {
							DefaultTableModel modelTabele = (DefaultTableModel) KorpaProzor.this.korpaTabela.getModel();
							modelTabele.removeRow(selektovaniRed);
							videoteka.brisiIzKorpe(kopija);
						}
					} else {
						JOptionPane.showMessageDialog(KorpaProzor.this, "Greska pri pretrazi kopija!");
					}
				}

			}
		});

	}
}