package com.hokumus.conn.db.ui;

import javax.swing.JFrame;

import com.hokumus.conn.db.blo.UserContBLO;
import com.hokumus.conn.db.doa.DbConnector;
import com.hokumus.conn.db.model.User;
import com.hokumus.conn.db.util.Utils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GirisFrame extends JFrame {
	private JTextField txtKadi;
	private JTextField txtSifre;

	public GirisFrame() {
		initialize();
	}

	private void initialize() {
		setTitle("Giriþ Ekraný");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 250, 420, 363);
		getContentPane().setLayout(null);

		JLabel lblKullancAd = new JLabel("Kullan\u0131c\u0131 Ad\u0131");
		lblKullancAd.setBounds(39, 116, 132, 24);
		getContentPane().add(lblKullancAd);

		JLabel lblifre = new JLabel("\u015Eifre");
		lblifre.setBounds(39, 146, 132, 24);
		getContentPane().add(lblifre);

		txtKadi = new JTextField();
		txtKadi.setBounds(220, 118, 148, 20);
		getContentPane().add(txtKadi);
		txtKadi.setColumns(10);
		txtKadi.setText("admin");

		txtSifre = new JTextField();
		txtSifre.setColumns(10);
		txtSifre.setBounds(220, 148, 148, 20);
		txtSifre.setText("123");
		getContentPane().add(txtSifre);

		JButton btnIptal = new JButton("\u0130ptal");
		btnIptal.setBounds(64, 215, 91, 23);
		getContentPane().add(btnIptal);

		JButton btnGiris = new JButton("Giri\u015F");
		btnGiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserContBLO blo = new UserContBLO();
				Object[] dizi = blo.getUserForUserName(txtKadi.getText());
				for (int i = 0; i < dizi.length; i++) {
					if (dizi[i] instanceof User) {
						User temp = (User) dizi[0];
						if (temp.getPassword().equals(txtSifre.getText())) {
							JOptionPane.showMessageDialog(GirisFrame.this, "Hoþgeldin Sahip " + temp.getName());
							Utils.loginedUser = temp;
							MainFrame mf = new MainFrame();
							mf.setVisible(true);
							GirisFrame.this.setVisible(false);
							break;
						} else {
							// þifre yalnlýþ mesajý
						}

					} else if (dizi[i] == null) {
						JOptionPane.showMessageDialog(GirisFrame.this, "Default Kullanýcý Eklendi ");
						break;
					} else if (dizi[i].equals("Kullanýcý Bulunamadý")) {
						JOptionPane.showMessageDialog(GirisFrame.this, "Böyle Bir Kullanýcý Yoktur" + dizi[++i]);
						break;
					}

					else if (dizi[i].equals("Hata Oluþtu")) {
						JOptionPane.showMessageDialog(GirisFrame.this, "Ýþlem yapýlýrken hata oluþtu" + dizi[++i]);
						break;
					}
				}

			}
		});
		btnGiris.setBounds(212, 215, 91, 23);
		getContentPane().add(btnGiris);
		DbConnector.getConnection();
		DbConnector.getConnection();
		DbConnector.getConnection();
	}
}
