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

import guiDialogs.ClanoviDodIzmDijalog;
import korisnik.Aktivnost;
import korisnik.Clan;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class ClanoviProzor extends JFrame {

	private Videoteka videoteka;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenu edit;
	private JMenuItem dodaj;
	private JMenuItem izmeni;
	private JMenuItem obrisi;
	private JMenuItem aktivan;
	private JMenuItem neaktivan;
	private JScrollPane skrolTabele;
	private JTable clanoviTabela;

	public ClanoviProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Clanarine");
		setSize(700, 450);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		izlazDijalog();
		initGui();
		initAction();
	}

	private void initGui() {

		this.mainMenu = new JMenuBar();
		this.file = new JMenu("File");
		this.edit = new JMenu("Edit");
		this.dodaj = new JMenuItem("Dodaj");
		this.izmeni = new JMenuItem("Izmeni");
		this.obrisi = new JMenuItem("Izbrisi");
		this.aktivan = new JMenuItem("Aktivan");
		this.neaktivan = new JMenuItem("Neaktivan");

		this.file.add(dodaj);
		this.file.add(izmeni);
		this.file.add(obrisi);
		this.edit.add(aktivan);
		this.edit.add(neaktivan);

		this.mainMenu.add(file);
		this.mainMenu.add(edit);

		setJMenuBar(mainMenu);

		String[] zaglavlje = new String[] { "Ime", "Prezime", "JMBG", "Adresa", "Pol", "Broj Karte", "Aktivnost" };
		Object[][] podaci = new Object[this.videoteka.getListaClan().size()][zaglavlje.length];

		for (int i = 0; i < this.videoteka.getListaClan().size(); i++) {
			Clan clan = this.videoteka.getListaClan().get(i);
			podaci[i][0] = clan.getIme();
			podaci[i][1] = clan.getPrezime();
			podaci[i][2] = clan.getJmbg();
			podaci[i][3] = clan.getAdresa();
			podaci[i][4] = clan.getPol();
			podaci[i][5] = clan.getBrojKarte();
			podaci[i][6] = clan.getAktivnost();
		}

		DefaultTableModel modelTabele = new DefaultTableModel(podaci, zaglavlje);
		clanoviTabela = new JTable(modelTabele);
		clanoviTabela.setColumnSelectionAllowed(false);
		clanoviTabela.setRowSelectionAllowed(true);
		clanoviTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clanoviTabela.setDefaultEditor(Object.class, null);
		clanoviTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(clanoviTabela);
		add(skrolTabele, BorderLayout.CENTER);

	}

	public JTable getClanoviTabela() {
		return clanoviTabela;
	}

	private void initAction() {
		this.dodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClanoviDodIzmDijalog cdid = new ClanoviDodIzmDijalog(ClanoviProzor.this.videoteka, null,
						ClanoviProzor.this);
				WindowUtils.centirarnjeProzora(cdid);
				cdid.setVisible(true);
				cdid.setAlwaysOnTop(true);

			}
		});

		this.izmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selektovaniRed = ClanoviProzor.this.clanoviTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ClanoviProzor.this, "Niste nista izabrali!");

				} else {
					String ime = (String) ClanoviProzor.this.clanoviTabela.getValueAt(selektovaniRed, 0);
					Clan clan = ClanoviProzor.this.videoteka.nadjiClana(ime);
					ClanoviDodIzmDijalog cdid = new ClanoviDodIzmDijalog(ClanoviProzor.this.videoteka, clan,
							ClanoviProzor.this);
					WindowUtils.centirarnjeProzora(cdid);
					cdid.setVisible(true);
					cdid.setAlwaysOnTop(true);
				}

			}
		});

		this.obrisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ClanoviProzor.this.clanoviTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ClanoviProzor.this, "Niste nista izabrali!");

				} else {
					int potvrda = JOptionPane.showConfirmDialog(ClanoviProzor.this, "Da li ste sigurni?",
							"Brisanje Clana", JOptionPane.YES_NO_OPTION);
					if (potvrda == JOptionPane.YES_OPTION) {
						String ime = (String) ClanoviProzor.this.clanoviTabela.getValueAt(selektovaniRed, 0);
						Clan clan = ClanoviProzor.this.videoteka.nadjiClana(ime);
						if (clan != null) {
							DefaultTableModel modelTabele = (DefaultTableModel) ClanoviProzor.this.clanoviTabela
									.getModel();
							modelTabele.removeRow(selektovaniRed);
							ClanoviProzor.this.videoteka.obrisiClana(clan);
							ClanoviProzor.this.videoteka.snimiClanove();
						} else {
							JOptionPane.showMessageDialog(ClanoviProzor.this, "Greska prilikom pronalazenja clana!");
						}
					}
				}

			}
		});

		this.aktivan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ClanoviProzor.this.clanoviTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ClanoviProzor.this, "Niste nista izabrali!");

				} else {
					int red = getClanoviTabela().getSelectedRow();
					String ime = (String) ClanoviProzor.this.clanoviTabela.getValueAt(selektovaniRed, 0);
					Clan clan = ClanoviProzor.this.videoteka.nadjiClana(ime);
					Aktivnost aktivnost = Aktivnost.AKTIVAN;
					clan.setAktivnost(aktivnost);
					getClanoviTabela().setValueAt(aktivnost, red, 6);

				}

				videoteka.snimiClanove();

			}
		});

		this.neaktivan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = ClanoviProzor.this.clanoviTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(ClanoviProzor.this, "Niste nista izabrali!");

				} else {

					int red = getClanoviTabela().getSelectedRow();
					String ime = (String) ClanoviProzor.this.clanoviTabela.getValueAt(selektovaniRed, 0);
					Clan clan = ClanoviProzor.this.videoteka.nadjiClana(ime);
					Aktivnost aktivnost = Aktivnost.NIJE_AKTIVAN;
					clan.setAktivnost(aktivnost);
					getClanoviTabela().setValueAt(aktivnost, red, 6);

				}

				videoteka.snimiClanove();

			}
		});
	}

	private void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(ClanoviProzor.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					ClanoviProzor.this.setVisible(false);
					ClanoviProzor.this.dispose();
					RadnikProzor rp = new RadnikProzor(videoteka);
					WindowUtils.centirarnjeProzora(rp);
					rp.setVisible(true);

				}
			}
		});

	}

}
