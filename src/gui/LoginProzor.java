package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import korisnik.TipZaposlenog;
import korisnik.Zaposleni;
import net.miginfocom.swing.MigLayout;
import utils.WindowUtils;
import videoteka.Videoteka;

@SuppressWarnings("serial")
public class LoginProzor extends JFrame {
	private Videoteka videoteka;

	private JLabel lblPoruka;
	private JLabel lblKorIme;
	private JTextField tfKorIme;
	private JLabel lblLozinka;
	private JPasswordField pfLozinka;
	private JButton btnOK;

	public LoginProzor(Videoteka videoteka) {
		this.videoteka = videoteka;
		setTitle("Logovanje");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		initGui();
		initAction();
		pack();

	}

	private void initGui() {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]20[]5[]10[]");
		setLayout(layout);

		this.lblPoruka = new JLabel("Logovanje.");
		this.lblKorIme = new JLabel("Korisnicko ime:");
		this.tfKorIme = new JTextField(20);
		this.lblLozinka = new JLabel("Sifra:");
		this.pfLozinka = new JPasswordField(20);
		this.btnOK = new JButton("Potvrdi");
		new JButton("Otkazi");

		this.getRootPane().setDefaultButton(btnOK);

		add(lblPoruka, "span2");
		add(lblKorIme);
		add(tfKorIme);
		add(lblLozinka);
		add(pfLozinka);
		add(new JLabel());
		add(btnOK, "gapleft 167");
	}

	private void initAction() {

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String korisnickoIme = tfKorIme.getText().trim();
				String lozinka = new String(pfLozinka.getPassword());

				if (korisnickoIme.equals("") || lozinka.equals("")) {
					JOptionPane.showMessageDialog(LoginProzor.this, "Nisu popunjena sva polja!");
				} else {
					Zaposleni zaposleni = videoteka.loginProvera(korisnickoIme, lozinka);
					if (zaposleni != null) {
						videoteka.setUlogovan(zaposleni.getIme());
						LoginProzor.this.setVisible(false);
						LoginProzor.this.dispose();
						if (zaposleni.getTipZaposlenog().equals(TipZaposlenog.RADNIK)) {
							RadnikProzor radnikProzor = new RadnikProzor(videoteka);
							WindowUtils.centirarnjeProzora(radnikProzor);
							radnikProzor.setVisible(true);
						} else if (zaposleni.getTipZaposlenog().equals(TipZaposlenog.UREDNIK)) {
							UrednikProzor urednikProzor = new UrednikProzor(videoteka);
							WindowUtils.centirarnjeProzora(urednikProzor);
							urednikProzor.setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(LoginProzor.this, "Pogresni login podaci!");
					}
				}

			}
		});

	}

}
