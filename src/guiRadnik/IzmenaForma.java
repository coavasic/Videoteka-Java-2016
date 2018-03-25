package guiRadnik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import film.Iznajmljivanje;
import net.miginfocom.swing.MigLayout;
import utils.NumberUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")

public class IzmenaForma extends JFrame {

	private Videoteka videoteka;
	private IznajmljivanjeProzor iznajmljivanjeProzor;
	private Iznajmljivanje iznajmljivanje;

	private JLabel lblOznaka;
	private JTextField tfOznaka;
	private JLabel lblZaposleni;
	private JTextField tfZaposleni;
	private JLabel lblClan;
	private JTextField tfClan;
	private JLabel lblDatumIzn;
	private JTextField tfDazumIzn;
	private JLabel lblDatumVracanja;
	private JTextField tfDatumVracanja;
	private JLabel lblFilm;
	private JTextField tfFilm;
	private JLabel lblCena;
	private JTextField tfCena;
	private JLabel lblBrDana;
	private JTextField tfBrDana;
	private JButton btnPotvrdi;
	private double cenaMedijuma;

	public IzmenaForma(Videoteka videoteka, IznajmljivanjeProzor iznajmljivanjaForma, Iznajmljivanje iznajmljivanje,
			double cenaMedijuma) {
		this.cenaMedijuma = cenaMedijuma;
		this.videoteka = videoteka;
		this.iznajmljivanjeProzor = iznajmljivanjaForma;
		this.iznajmljivanje = iznajmljivanje;
		setTitle("Izmena Iznajmljivanja");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGUI();
		initAkcije();
		pack();
	}

	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][][][]20[]");
		setLayout(layout);

		lblOznaka = new JLabel("Oznaka");
		tfOznaka = new JTextField(10);
		lblZaposleni = new JLabel("Zaposleni");
		tfZaposleni = new JTextField(20);
		lblClan = new JLabel("Clan");
		tfClan = new JTextField(20);
		lblDatumIzn = new JLabel("Datum Iznajmljivanja");
		tfDazumIzn = new JTextField(20);
		lblDatumVracanja = new JLabel("Datum Vracanja");
		tfDatumVracanja = new JTextField(20);
		lblFilm = new JLabel("Film");
		tfFilm = new JTextField(20);
		lblCena = new JLabel("Cena");
		tfCena = new JTextField(20);
		lblBrDana = new JLabel("Broj dana");
		tfBrDana = new JTextField(20);
		btnPotvrdi = new JButton("Potvrdi");

		this.getRootPane().setDefaultButton(btnPotvrdi);

		add(lblOznaka);
		add(tfOznaka);
		tfOznaka.setText(String.valueOf(iznajmljivanje.getOznaka()));
		tfOznaka.setEnabled(false);
		add(lblZaposleni);
		add(tfZaposleni);
		tfZaposleni.setText(iznajmljivanje.getZaposleni());
		tfZaposleni.setEnabled(false);
		add(lblClan);
		add(tfClan);
		tfClan.setText(iznajmljivanje.getClan());
		tfClan.setEnabled(false);
		add(lblDatumIzn);
		add(tfDazumIzn);
		tfDazumIzn.setText(iznajmljivanje.getDatumIznajmljivanja());
		tfDazumIzn.setEnabled(false);
		add(lblDatumVracanja);
		add(tfDatumVracanja);
		if (iznajmljivanje.getDatumVracanja().equals("Nije Odredjen")) {
			tfDatumVracanja.setText(iznajmljivanje.getDatumIznajmljivanja());
		} else {
			tfDatumVracanja.setText(iznajmljivanje.getDatumVracanja());
		}
		add(lblFilm);
		add(tfFilm);
		tfFilm.setText(iznajmljivanje.getKopija());
		tfFilm.setEnabled(false);
		add(lblCena);
		add(tfCena);
		tfCena.setText(String.valueOf(iznajmljivanje.getCena()));
		tfCena.setEnabled(false);
		add(lblBrDana);
		add(tfBrDana);
		tfBrDana.setText(String.valueOf(iznajmljivanje.getBrojDana()));
		tfBrDana.setEnabled(false);
		add(new JLabel());
		add(btnPotvrdi, "gapleft 167");

	}

	private void initAkcije() {

		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = null;
				boolean formatDatuma = true;
				int brDana = 0;

				int oznaka = Integer.parseInt(tfOznaka.getText());
				String datumIzn = tfDazumIzn.getText();
				String datumVracanja = tfDatumVracanja.getText();

				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();

				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

				try {
					date = sdf.parse(datumIzn);
				} catch (ParseException e1) {
				}
				cal2.setTime(date);

				try {
					date = sdf.parse(datumVracanja);
				} catch (ParseException e2) {
					formatDatuma = false;
					tfDatumVracanja.setText(datumIzn);
				}

				if (!(formatDatuma)) {
					JOptionPane.showMessageDialog(IzmenaForma.this, "Format datuma nije dobar");
				} else {
					cal1.setTime(date);

					if (datumVracanja.equals(datumIzn)) {
						datumVracanja = "Nije Odredjen";
					} else {
						brDana = NumberUtils.daniIzmedju(cal1.getTime(), cal2.getTime());
					}

					if (brDana < 0) {
						JOptionPane.showMessageDialog(IzmenaForma.this,
								"Datum vracanja mora biti isti ili posle datuma iznajmljivanja.");
					} else {

						int red = iznajmljivanjeProzor.getIznajmljivanjeTabela().getSelectedRow();

						iznajmljivanje.setOznaka(oznaka);
						iznajmljivanje.setDatumVracanja(datumVracanja);
						iznajmljivanje.setBrojDana(brDana);

						double ukupnaCena = cenaMedijuma * brDana;

						IzmenaForma.this.iznajmljivanjeProzor.getIznajmljivanjeTabela().setValueAt(oznaka, red, 1);
						IzmenaForma.this.iznajmljivanjeProzor.getIznajmljivanjeTabela().setValueAt(datumVracanja, red,
								5);
						IzmenaForma.this.iznajmljivanjeProzor.getIznajmljivanjeTabela().setValueAt(brDana, red, 8);
						IzmenaForma.this.iznajmljivanjeProzor.getIznajmljivanjeTabela().setValueAt(ukupnaCena, red, 9);

						videoteka.snimiIznajmljivanja();
						IzmenaForma.this.dispose();
						IzmenaForma.this.setVisible(false);
					}

				}

			}
		});

	}

}
