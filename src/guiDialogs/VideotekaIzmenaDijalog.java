package guiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gui.UrednikProzor;
import net.miginfocom.swing.MigLayout;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class VideotekaIzmenaDijalog extends JFrame {
	private Videoteka videoteka;

	private JLabel lblPib;
	private JTextField tfPib;
	private JLabel lblNaziv;
	private JTextField tfNaziv;
	private JLabel lblAdresa;
	private JTextField tfAdresa;
	private JButton btnPotvrdi;

	public VideotekaIzmenaDijalog(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Izmena Videoteke");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		initKomponente();
		initAkcije();
		izlazDijalog();
		pack();
	}

	private void initKomponente() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]5[]5[]15[]");
		setLayout(layout);

		lblPib = new JLabel("PIB");
		tfPib = new JTextField(20);
		lblNaziv = new JLabel("Naziv");
		tfNaziv = new JTextField(20);
		lblAdresa = new JLabel("Adresa");
		tfAdresa = new JTextField(20);
		btnPotvrdi = new JButton("Potvrdi");
		stareVrednosti();

		this.getRootPane().setDefaultButton(btnPotvrdi);

		add(lblPib);
		add(tfPib);
		add(lblNaziv);
		add(tfNaziv);
		add(lblAdresa);
		add(tfAdresa);
		add(new JLabel());
		add(btnPotvrdi, "gapleft 167");

	}

	private void stareVrednosti() {
		tfPib.setText(String.valueOf(videoteka.getPib()));
		tfNaziv.setText(videoteka.getNaziv());
		tfAdresa.setText(videoteka.getAdresa());

	}

	private void initAkcije() {
		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					videoteka.setPib(Integer.parseInt(tfPib.getText()));
					videoteka.setNaziv(tfNaziv.getText());
					videoteka.setAdresa(tfAdresa.getText());
					videoteka.snimiVideoteku();
					videoteka.ucitajVideoteku();
					VideotekaIzmenaDijalog.this.setVisible(false);
					VideotekaIzmenaDijalog.this.dispose();
					UrednikProzor urednikProzor = new UrednikProzor(videoteka);
					WindowUtils.centirarnjeProzora(urednikProzor);
					urednikProzor.setVisible(true);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "PIB nije odgovarajućeg formata");
				}

			}
		});

	}

	private void izlazDijalog() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int odgovor = JOptionPane.showConfirmDialog(VideotekaIzmenaDijalog.this, "Da li ste sigurni?",
						"Potvrda izlaza iz aplikacije", JOptionPane.YES_NO_OPTION);

				if (odgovor == JOptionPane.YES_OPTION) {
					VideotekaIzmenaDijalog.this.setVisible(false);
					VideotekaIzmenaDijalog.this.dispose();
					UrednikProzor urednikProzor = new UrednikProzor(videoteka);
					urednikProzor.setVisible(true);
					WindowUtils.centirarnjeProzora(urednikProzor);
				}
			}
		});
	}
}
