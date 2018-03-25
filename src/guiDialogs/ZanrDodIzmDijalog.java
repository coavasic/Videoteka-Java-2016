package guiDialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import film.Zanr;
import gui.ZanrProzor;
import net.miginfocom.swing.MigLayout;
import utils.StringUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class ZanrDodIzmDijalog extends JFrame {
	private Videoteka videoteka;
	private ZanrProzor zanrProzor;
	private Zanr zanr;

	private JLabel lblOznaka;
	private JTextField tfOznaka;
	private JLabel lblNaziv;
	private JTextField tfNaziv;
	private JButton btnPotvrdi;

	public ZanrDodIzmDijalog(Videoteka videoteka, Zanr zanr, ZanrProzor zanrProzor) {
		this.videoteka = videoteka;
		this.zanrProzor = zanrProzor;
		this.zanr = zanr;
		setTitle("Dodavanje žanra");
		if (zanr != null) {
			setTitle("Izmena Žanra");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initKomponente();
		initAkcije();
		pack();

	}

	private void initKomponente() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]5[]15[]");
		setLayout(layout);
		String oznaka = "Z" + Integer.toString(videoteka.getListaZanr().size() + 1);

		lblOznaka = new JLabel("Oznaka");
		tfOznaka = new JTextField(20);
		tfOznaka.setText(oznaka);
		tfOznaka.setEnabled(false);
		lblNaziv = new JLabel("Naziv");
		tfNaziv = new JTextField(20);
		btnPotvrdi = new JButton("Potvrdi");

		this.getRootPane().setDefaultButton(btnPotvrdi);

		if (zanr != null) {
			stareVrednosti();
		}

		add(lblOznaka);
		add(tfOznaka);
		add(lblNaziv);
		add(tfNaziv);
		add(btnPotvrdi);
		add(new JLabel());

	}

	private void initAkcije() {
		this.btnPotvrdi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String oznaka = tfOznaka.getText().trim();
				String naziv = tfNaziv.getText().trim();
				if (oznaka.equals("") || naziv.equals("")) {
					JOptionPane.showMessageDialog(ZanrDodIzmDijalog.this, "Niste uneli naziv!");
				} else {
					String nazivVelikoPocetno = StringUtils.cpzPocetnoSlovo(naziv);
					int red = zanrProzor.getZanrTabela().getSelectedRow();
					DefaultTableModel model = (DefaultTableModel) zanrProzor.getZanrTabela().getModel();

					if (zanr == null) {
						zanr = new Zanr(oznaka, nazivVelikoPocetno);
						videoteka.dodajZanr(zanr);
						Object[] noviRed = new Object[] { oznaka, nazivVelikoPocetno };
						model.addRow(noviRed);

					} else {
						zanr.setOznaka(oznaka);
						zanr.setNaziv(nazivVelikoPocetno);

						ZanrDodIzmDijalog.this.zanrProzor.getZanrTabela().setValueAt(oznaka, red, 0);
						ZanrDodIzmDijalog.this.zanrProzor.getZanrTabela().setValueAt(nazivVelikoPocetno, red, 1);
					}
					videoteka.snimiZanrove();
					ZanrDodIzmDijalog.this.dispose();
					ZanrDodIzmDijalog.this.setVisible(false);

				}

			}
		});
	}

	private void stareVrednosti() {
		tfOznaka.setText(String.valueOf(zanr.getOznaka()));
		tfNaziv.setText(zanr.getNaziv());
	}

}
