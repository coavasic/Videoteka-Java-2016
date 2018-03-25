package guiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import film.Medijum;
import gui.MedijumProzor;
import net.miginfocom.swing.MigLayout;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class MedijumIzmenaDijalog extends JFrame {
	private Videoteka videoteka;
	private MedijumProzor medijumProzor;
	private Medijum medijum;

	private JLabel lblOznaka;
	private JTextField tfOznaka;
	private JLabel lblCena;
	private JTextField tfCena;
	private JButton btnPotvrdi;

	public MedijumIzmenaDijalog(Videoteka videoteka, MedijumProzor medijumProzor, Medijum medijum) {
		this.videoteka = videoteka;
		this.medijumProzor = medijumProzor;
		this.medijum = medijum;
		setTitle("Izmena Medijuma");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initKomponente();
		initAkcije();
		pack();
	}

	private void initKomponente() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]5[]15[]");
		setLayout(layout);

		lblOznaka = new JLabel("Oznaka");
		tfOznaka = new JTextField(20);
		tfOznaka.setText(medijum.getOznaka());
		tfOznaka.setEnabled(false);
		lblCena = new JLabel("Cena");
		tfCena = new JTextField(20);
		tfCena.setText(String.valueOf(medijum.getCena()));
		btnPotvrdi = new JButton("Potvrdi");

		this.getRootPane().setDefaultButton(btnPotvrdi);

		add(lblOznaka);
		add(tfOznaka);
		add(lblCena);
		add(tfCena);
		add(new JLabel());
		add(btnPotvrdi, "gapleft 167");

	}

	private void initAkcije() {
		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int cena = 0;
				boolean format = true;
				String oznaka = tfOznaka.getText();
				String cenaString = tfCena.getText().trim();

				if (oznaka.equals("") || cenaString.equals("")) {
					JOptionPane.showMessageDialog(MedijumIzmenaDijalog.this, "Niste uneli sve podatke");
				} else {
					try {
						cena = Integer.parseInt(cenaString);
					} catch (Exception e) {
						format = false;
					}

					if (format && cena >= 0) {
						int selektovaniRed = medijumProzor.getMedijumTabela().getSelectedRow();
						medijum.setCena(cena);
						MedijumIzmenaDijalog.this.medijumProzor.getMedijumTabela().setValueAt(cena, selektovaniRed, 1);
						videoteka.snimiMedijume();
						MedijumIzmenaDijalog.this.dispose();
						MedijumIzmenaDijalog.this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(MedijumIzmenaDijalog.this, "Cena mora biti broj veci od 0");
					}
				}
			}
		});
	}
}
