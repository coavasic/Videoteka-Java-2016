package guiRadnik;

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

import film.Iznajmljivanje;
import gui.RadnikProzor;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class IznajmljivanjeProzor extends JFrame {

	private JMenuBar mainMenu;
	private JMenu fileMenu;
	private JMenuItem novoIznajmljivanje;
	private JMenuItem izmenaIznajmljivanja;
	private JMenuItem brisanjeIznajmljivanja;
	private JTable iznajmljivanjeTabela;
	private JScrollPane skrolTabele;

	private Videoteka videoteka;

	public IznajmljivanjeProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Iznajmljivanje prozor");
		setSize(1000, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		izlazDijalog();
		initProzor();
		initAction();
	}

	private void initProzor() {
		this.mainMenu = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.novoIznajmljivanje = new JMenuItem("Novo iznajmljivanje");
		this.izmenaIznajmljivanja = new JMenuItem("Izmena");
		this.brisanjeIznajmljivanja = new JMenuItem("Brisanje");

		this.fileMenu.add(novoIznajmljivanje);
		this.fileMenu.add(izmenaIznajmljivanja);
		this.fileMenu.add(brisanjeIznajmljivanja);
		this.mainMenu.add(fileMenu);
		setJMenuBar(mainMenu);

		String[] zaglavlje = new String[] { "Oznaka korpe", "Oznaka", "Zaposleni", "Clan", "Datum Iznajmljivanja",
				"Datum Vracanja", "Film  ", "Cena (po danu)", "Broj Dana", "Ukupno" };
		Object[][] podaci = new Object[this.videoteka.getListaIznajmljivanje().size()][zaglavlje.length];

		for (int i = 0; i < this.videoteka.getListaIznajmljivanje().size(); i++) {
			Iznajmljivanje iznajmljivanje = this.videoteka.getListaIznajmljivanje().get(i);
			podaci[i][0] = iznajmljivanje.getOznakaPaketa();
			podaci[i][1] = iznajmljivanje.getOznaka();
			podaci[i][2] = iznajmljivanje.getZaposleni();
			podaci[i][3] = iznajmljivanje.getClan();
			podaci[i][4] = iznajmljivanje.getDatumIznajmljivanja();
			podaci[i][5] = iznajmljivanje.getDatumVracanja();
			podaci[i][6] = iznajmljivanje.getKopija();
			podaci[i][7] = iznajmljivanje.getCena();
			podaci[i][8] = iznajmljivanje.getBrojDana();
			podaci[i][9] = iznajmljivanje.getCena() * iznajmljivanje.getBrojDana();

		}

		DefaultTableModel modelTabele = new DefaultTableModel(podaci, zaglavlje);
		iznajmljivanjeTabela = new JTable(modelTabele);
		iznajmljivanjeTabela.setColumnSelectionAllowed(false);
		iznajmljivanjeTabela.setRowSelectionAllowed(true);
		iznajmljivanjeTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iznajmljivanjeTabela.setDefaultEditor(Object.class, null);
		iznajmljivanjeTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(iznajmljivanjeTabela);
		add(skrolTabele, BorderLayout.CENTER);
	}

	public JTable getIznajmljivanjeTabela() {
		return getIznajmljivanjeTabela();
	}

	private void initAction() {
		this.novoIznajmljivanje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				IznajmljivanjeProzor.this.dispose();
				IznajmljivanjeProzor.this.setVisible(false);
				DodavanjeForma idid = new DodavanjeForma(IznajmljivanjeProzor.this.videoteka);
				WindowUtils.centirarnjeProzora(idid);
				idid.setVisible(true);
				idid.setAlwaysOnTop(true);
			}
		});

		this.izmenaIznajmljivanja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selektovaniRed = IznajmljivanjeProzor.this.iznajmljivanjeTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(IznajmljivanjeProzor.this, "Niste ništa izabrali!");

				} else {
					int oznaka = (int) IznajmljivanjeProzor.this.iznajmljivanjeTabela.getValueAt(selektovaniRed, 1);
					double cenaMedijuma = (double) IznajmljivanjeProzor.this.iznajmljivanjeTabela
							.getValueAt(selektovaniRed, 7);
					Iznajmljivanje iznajmljivanje = IznajmljivanjeProzor.this.videoteka.nadjiIznajmljivanje(oznaka);
					IzmenaForma iid = new IzmenaForma(IznajmljivanjeProzor.this.videoteka, IznajmljivanjeProzor.this,
							iznajmljivanje, cenaMedijuma);
					WindowUtils.centirarnjeProzora(iid);
					iid.setVisible(true);
					iid.setAlwaysOnTop(true);

				}

			}
		});

		this.brisanjeIznajmljivanja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selektovaniRed = IznajmljivanjeProzor.this.iznajmljivanjeTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(IznajmljivanjeProzor.this, "Niste ništa izabrali!");

				} else {
					int potvrda = JOptionPane.showConfirmDialog(IznajmljivanjeProzor.this, "Da li ste sigurni?",
							"Brisanje Iznajmljivanja", JOptionPane.YES_NO_OPTION);
					if (potvrda == JOptionPane.YES_OPTION) {
						int oznaka = (int) IznajmljivanjeProzor.this.iznajmljivanjeTabela.getValueAt(selektovaniRed, 1);
						Iznajmljivanje iznajmljivanje = IznajmljivanjeProzor.this.videoteka.nadjiIznajmljivanje(oznaka);
						if (iznajmljivanje != null) {
							DefaultTableModel modelTabele = (DefaultTableModel) IznajmljivanjeProzor.this.iznajmljivanjeTabela
									.getModel();
							modelTabele.removeRow(selektovaniRed);
							IznajmljivanjeProzor.this.videoteka.obrisiIznajmljivanje(iznajmljivanje);
							IznajmljivanjeProzor.this.videoteka.snimiIznajmljivanja();
						} else {
							JOptionPane.showMessageDialog(IznajmljivanjeProzor.this,
									"Greska prilikom pronalazenja zaposlenog!");
						}
					}
				}

			}
		});
	}

	private void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				IznajmljivanjeProzor.this.setVisible(false);
				IznajmljivanjeProzor.this.dispose();
				RadnikProzor rp = new RadnikProzor(IznajmljivanjeProzor.this.videoteka);
				WindowUtils.centirarnjeProzora(rp);
				rp.setVisible(true);

			}
		});

	}
}
