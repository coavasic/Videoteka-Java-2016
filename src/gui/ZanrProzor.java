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

import film.Zanr;
import guiDialogs.ZanrDodIzmDijalog;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class ZanrProzor extends JFrame {

	private Videoteka videoteka;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenuItem dodaj;
	private JMenuItem izmeni;
	private JMenuItem obrisi;

	private JScrollPane skrolTabele;
	private JTable zanrTabela;

	public ZanrProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Zanrovi");
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

		String[] zaglavlje = new String[] { "Oznaka", "Naziv" };
		Object[][] podaci = new Object[this.videoteka.getListaZanr().size()][zaglavlje.length];

		for (int i = 0; i < this.videoteka.getListaZanr().size(); i++) {
			Zanr zanrovi = this.videoteka.getListaZanr().get(i);
			podaci[i][0] = zanrovi.getOznaka();
			podaci[i][1] = zanrovi.getNaziv();
		}

		DefaultTableModel model = new DefaultTableModel(podaci, zaglavlje);
		zanrTabela = new JTable(model);
		zanrTabela.setColumnSelectionAllowed(false);
		zanrTabela.setRowSelectionAllowed(true);
		zanrTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		zanrTabela.setDefaultEditor(Object.class, null);
		zanrTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(zanrTabela);
		add(skrolTabele, BorderLayout.CENTER);

	}

	public JTable getZanrTabela() {
		return zanrTabela;
	}

	private void initAkcije() {
		this.dodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ZanrDodIzmDijalog zndid = new ZanrDodIzmDijalog(ZanrProzor.this.videoteka, null, ZanrProzor.this);
				zndid.setVisible(true);
				WindowUtils.centirarnjeProzora(zndid);
				zndid.setAlwaysOnTop(true);
			}
		});

		this.izmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ZanrProzor.this.zanrTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ZanrProzor.this, "Niste ništa izabrali");
				} else {
					String naziv = (String) ZanrProzor.this.zanrTabela.getValueAt(selektovaniRed, 1);
					Zanr zanr = ZanrProzor.this.videoteka.nadjiZanr(naziv);
					ZanrDodIzmDijalog zndid = new ZanrDodIzmDijalog(ZanrProzor.this.videoteka, zanr, ZanrProzor.this);
					zndid.setVisible(true);
					WindowUtils.centirarnjeProzora(zndid);
					zndid.setAlwaysOnTop(true);
				}
			}
		});

		this.obrisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ZanrProzor.this.zanrTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ZanrProzor.this, "Niste ništa izabrali");
				} else {
					int potvrda = JOptionPane.showConfirmDialog(ZanrProzor.this, "Da li ste sigurni?", "Brisanje Žanra",
							JOptionPane.YES_NO_OPTION);
					if (potvrda == JOptionPane.YES_OPTION) {
						String naziv = (String) ZanrProzor.this.zanrTabela.getValueAt(selektovaniRed, 1);
						Zanr zanr = ZanrProzor.this.videoteka.nadjiZanr(naziv);
						if (zanr != null) {
							DefaultTableModel modelTabele = (DefaultTableModel) ZanrProzor.this.zanrTabela.getModel();
							modelTabele.removeRow(selektovaniRed);
							ZanrProzor.this.videoteka.obrisiZanr(zanr);
							ZanrProzor.this.videoteka.snimiZanrove();
						} else {
							JOptionPane.showMessageDialog(ZanrProzor.this, "Greška prilikom pronalaženja žanra");
						}
					}
				}

			}
		});
	}

	private void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(ZanrProzor.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					ZanrProzor.this.setVisible(false);
					ZanrProzor.this.dispose();
					UrednikProzor urednikProzor = new UrednikProzor(videoteka);
					WindowUtils.centirarnjeProzora(urednikProzor);
					urednikProzor.setVisible(true);
				}
			}
		});

	}

}
