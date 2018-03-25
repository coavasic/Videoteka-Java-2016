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

import film.Medijum;
import guiDialogs.MedijumIzmenaDijalog;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class MedijumProzor extends JFrame {
	private Videoteka videoteka;
	private JMenuBar mainMenu;
	private JMenu file;
	private JMenuItem izmeni;

	private JScrollPane skrolTabele;
	private JTable medijumTabela;

	public MedijumProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Medijumi");
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
		this.izmeni = new JMenuItem("Izmeni");

		this.file.add(izmeni);

		this.mainMenu.add(file);

		setJMenuBar(mainMenu);
		String[] zaglavlje = new String[] { "Oznaka", "Cena" };
		Object[][] podaci = new Object[this.videoteka.getListaMedijum().size()][zaglavlje.length];

		for (int i = 0; i < this.videoteka.getListaMedijum().size(); i++) {
			Medijum medijum = this.videoteka.getListaMedijum().get(i);
			podaci[i][0] = medijum.getOznaka();
			podaci[i][1] = medijum.getCena();
		}

		DefaultTableModel modelTabele = new DefaultTableModel(podaci, zaglavlje);
		medijumTabela = new JTable(modelTabele);
		medijumTabela.setColumnSelectionAllowed(false);
		medijumTabela.setRowSelectionAllowed(true);
		medijumTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		medijumTabela.setDefaultEditor(Object.class, null);
		medijumTabela.getTableHeader().setReorderingAllowed(false);

		skrolTabele = new JScrollPane(medijumTabela);
		add(skrolTabele, BorderLayout.CENTER);

	}

	public JTable getMedijumTabela() {
		return medijumTabela;
	}

	private void initAkcije() {
		this.izmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selektovaniRed = MedijumProzor.this.medijumTabela.getSelectedRow();
				if (selektovaniRed == -1) {
					JOptionPane.showMessageDialog(MedijumProzor.this, "Niste ništa izabrali!");

				} else {
					String oznaka = (String) MedijumProzor.this.medijumTabela.getValueAt(selektovaniRed, 0);
					Medijum medijum = MedijumProzor.this.videoteka.nadjiMedijum(oznaka);
					MedijumIzmenaDijalog mid = new MedijumIzmenaDijalog(MedijumProzor.this.videoteka,
							MedijumProzor.this, medijum);
					mid.setVisible(true);
					WindowUtils.centirarnjeProzora(mid);
					mid.setAlwaysOnTop(true);
				}

			}
		});

	}

	public void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(MedijumProzor.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					MedijumProzor.this.setVisible(false);
					MedijumProzor.this.dispose();
					UrednikProzor urednikProzor = new UrednikProzor(videoteka);
					WindowUtils.centirarnjeProzora(urednikProzor);
					urednikProzor.setVisible(true);
				}
			}
		});
	}

}
