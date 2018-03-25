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

import guiDialogs.ZaposeniDodIzmDijalog;
import korisnik.Zaposleni;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class ZaposleniProzor extends JFrame {
	private Videoteka videoteka;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenuItem dodaj;
	private JMenuItem izmeni;
	private JMenuItem obrisi;

	private JScrollPane skrolTabele;
	private JTable zaposleniTabela;

	public ZaposleniProzor(Videoteka videoteka) {
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
		String[] zaglavlje = new String[] { "Ime", "Prezime", "JMBG", "Adresa", "Pol", "Plata", "Korisnicko Ime",
				"Tip Zaposlenog" };
		Object[][] podaci = new Object[this.videoteka.getListaZaposleni().size()][zaglavlje.length];

		for (int i = 0; i < this.videoteka.getListaZaposleni().size(); i++) {
			Zaposleni zaposleni = this.videoteka.getListaZaposleni().get(i);
			podaci[i][0] = zaposleni.getIme();
			podaci[i][1] = zaposleni.getPrezime();
			podaci[i][2] = zaposleni.getJmbg();
			podaci[i][3] = zaposleni.getAdresa();
			podaci[i][4] = zaposleni.getPol();
			podaci[i][5] = zaposleni.getPlata();
			podaci[i][6] = zaposleni.getKorisnickoIme();
			podaci[i][7] = zaposleni.getTipZaposlenog();
		}

		DefaultTableModel modelTabele = new DefaultTableModel(podaci, zaglavlje);
		zaposleniTabela = new JTable(modelTabele);
		zaposleniTabela.setColumnSelectionAllowed(false);
		zaposleniTabela.setRowSelectionAllowed(true);
		zaposleniTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		zaposleniTabela.setDefaultEditor(Object.class, null);
		zaposleniTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(zaposleniTabela);
		add(skrolTabele, BorderLayout.CENTER);
	}

	public JTable getZaposleniTabela() {
		return zaposleniTabela;

	}

	private void initAkcije() {
		this.dodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ZaposeniDodIzmDijalog zdid = new ZaposeniDodIzmDijalog(ZaposleniProzor.this.videoteka, null,
						ZaposleniProzor.this);
				WindowUtils.centirarnjeProzora(zdid);
				zdid.setVisible(true);
				zdid.setAlwaysOnTop(true);
			}
		});

		this.izmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selektovaniRed = ZaposleniProzor.this.zaposleniTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ZaposleniProzor.this, "Niste ništa izabrali!");

				} else {
					String ime = (String) ZaposleniProzor.this.zaposleniTabela.getValueAt(selektovaniRed, 0);
					Zaposleni zaposleni = ZaposleniProzor.this.videoteka.pronadjiZaposlenog(ime);
					ZaposeniDodIzmDijalog zdid = new ZaposeniDodIzmDijalog(ZaposleniProzor.this.videoteka, zaposleni,
							ZaposleniProzor.this);
					WindowUtils.centirarnjeProzora(zdid);
					zdid.setVisible(true);
					zdid.setAlwaysOnTop(true);
				}

			}
		});

		this.obrisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selektovaniRed = ZaposleniProzor.this.zaposleniTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ZaposleniProzor.this, "Niste ništa izabrali!");

				} else {
					int potvrda = JOptionPane.showConfirmDialog(ZaposleniProzor.this, "Da li ste sigurni?",
							"Brisanje Zaposlenog", JOptionPane.YES_NO_OPTION);
					if (potvrda == JOptionPane.YES_OPTION) {
						String ime = (String) ZaposleniProzor.this.zaposleniTabela.getValueAt(selektovaniRed, 0);
						Zaposleni zaposleni = ZaposleniProzor.this.videoteka.pronadjiZaposlenog(ime);
						if (zaposleni != null) {
							DefaultTableModel modelTabele = (DefaultTableModel) ZaposleniProzor.this.zaposleniTabela
									.getModel();
							modelTabele.removeRow(selektovaniRed);
							ZaposleniProzor.this.videoteka.obrisiZaposlenog(zaposleni);
							ZaposleniProzor.this.videoteka.sacuvajZaposlene();
						} else {
							JOptionPane.showMessageDialog(ZaposleniProzor.this,
									"Greska prilikom pronalazenja zaposlenog!");
						}
					}
				}

			}
		});
	}

	public void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(ZaposleniProzor.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					ZaposleniProzor.this.setVisible(false);
					ZaposleniProzor.this.dispose();
					UrednikProzor urednikProzor = new UrednikProzor(videoteka);
					WindowUtils.centirarnjeProzora(urednikProzor);
					urednikProzor.setVisible(true);
				}
			}
		});
	}

}
