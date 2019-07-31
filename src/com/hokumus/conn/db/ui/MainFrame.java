package com.hokumus.conn.db.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hokumus.conn.db.blo.UserContBLO;
import com.hokumus.conn.db.model.ReturnType;
import com.hokumus.conn.db.model.User;
import com.hokumus.conn.db.util.Utils;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
	private JScrollPane scrollUsr;
	private JTable tblUsr;
	private JButton btnGetUsr;
	private JTextField txtKadi;
	private JTextField txtSifre;
	private JTextField txtName;
	private JLabel lblKullancAd;
	private JLabel lblifre;
	private JLabel lblIsim;
	private JButton btnKaydet;
	private JButton btnUpdate;

	public MainFrame() {
		initialize();
		initConnection();
	}

	private void initConnection() {

		getBtnKaydet().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UserContBLO blo = new UserContBLO();
					User temp = new User();
					temp.setUserName(getTxtKadi().getText());
					temp.setPassword(getTxtSifre().getText());
					temp.setName(getTxtName().getText());
					boolean durum = blo.insertUser(temp);
					if (durum) {
						JOptionPane.showMessageDialog(MainFrame.this, "Kullanýcý Eklendi..!");
						tabloDoldur(e);
					}

					else {
						JOptionPane.showMessageDialog(MainFrame.this, "Kullanýcý Eklenemedi..!");
					}

				} catch (Exception e2) {
					e2.printStackTrace();

				}

			}
		});

		getBtnGetUsr().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tabloDoldur(e);
			}
		});

		getBtnUpdate().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<User> liste = new ArrayList<User>();
				User temp = new User();
				temp.setId(selectedId);
				temp.setName(getTxtName().getText());
				temp.setPassword(getTxtSifre().getText());
				temp.setUserName(getTxtKadi().getText());
				liste.add(temp);
				UserContBLO blo = new UserContBLO();
				try {
					blo.updateUser(liste);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		getBtnDelete().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserContBLO blo = new UserContBLO();
				// selectedId = 150L; kata kulle var
				User delUser = new User();
				delUser.setName(getTxtName().getText());
				delUser.setUserName(getTblUsr().getValueAt(getTblUsr().getSelectedRow(), 0).toString());
				delUser.setPassword(getTblUsr().getValueAt(getTblUsr().getSelectedRow(), 1).toString());
				delUser.setId(Long.parseLong(getTblUsr().getValueAt(getTblUsr().getSelectedRow(), 3).toString()));
				Object[] gelen = blo.deleteUserForId(delUser);
				if (((ReturnType) gelen[0]) == ReturnType.ISLEM_BASARILI) {
					JOptionPane.showMessageDialog(MainFrame.this, gelen[1].toString());
				} else if (((ReturnType) gelen[0]) == ReturnType.ISLEM_BASARISIZ) {
					JOptionPane.showMessageDialog(MainFrame.this, gelen[1].toString());
				} else if (((ReturnType) gelen[0]) == ReturnType.HATA_OLUSTU) {
					JOptionPane.showMessageDialog(MainFrame.this, gelen[1].toString() +" " + gelen[2].toString());
				}
				tabloDoldur(e);

			}
		});

	}

	public void tabloDoldur(ActionEvent e) {
		UserContBLO blo = new UserContBLO();
		try {
			List<User> liste = blo.getAllUser();
			String[] columnNames = new String[] { "Kullanýcý Adý", "Þifre", "Ýsim", "ID" };
			String[][] data = new String[liste.size()][columnNames.length];
			for (int i = 0; i < liste.size(); i++) {
				data[i][0] = liste.get(i).getUserName();
				data[i][1] = liste.get(i).getPassword();
				data[i][2] = liste.get(i).getName();
				data[i][3] = liste.get(i).getId().toString();

			}
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			getTblUsr().setModel(model);

		} catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(MainFrame.this, "Kullanýcýlar Listelenemedi..!");
		}

	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 450);
		setTitle("Ana Ekran - " + Utils.loginedUser.getName());
		getContentPane().setLayout(null);
		getContentPane().add(getScrollUsr());
		getContentPane().add(getBtnGetUsr());
		getContentPane().add(getTxtKadi());
		getContentPane().add(getTxtSifre());
		getContentPane().add(getTxtName());
		getContentPane().add(getLblKullancAd());
		getContentPane().add(getLblifre());
		getContentPane().add(getLblIsim());
		getContentPane().add(getBtnKaydet());
		getContentPane().add(getBtnUpdate());
		getContentPane().add(getBtnDelete());

	}

	private JScrollPane getScrollUsr() {
		if (scrollUsr == null) {
			scrollUsr = new JScrollPane();
			scrollUsr.setBounds(10, 146, 344, 207);
			scrollUsr.setViewportView(getTblUsr());
		}
		return scrollUsr;
	}

	private Long selectedId;
	private JButton btnDelete;

	private JTable getTblUsr() {
		if (tblUsr == null) {
			tblUsr = new JTable();
			tblUsr.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					int row = getTblUsr().getSelectedRow();
					getTxtKadi().setText(getTblUsr().getValueAt(row, 0).toString());
					getTxtSifre().setText(getTblUsr().getValueAt(row, 1).toString());
					getTxtName().setText(getTblUsr().getValueAt(row, 2).toString());
					selectedId = Long.parseLong(getTblUsr().getValueAt(row, 3).toString());
				}
			});
		}
		return tblUsr;
	}

	private JButton getBtnGetUsr() {
		if (btnGetUsr == null) {
			btnGetUsr = new JButton("Kullan\u0131c\u0131lar\u0131 Listele");
			btnGetUsr.setBounds(22, 375, 146, 23);
		}
		return btnGetUsr;
	}

	private JTextField getTxtKadi() {
		if (txtKadi == null) {
			txtKadi = new JTextField();
			txtKadi.setBounds(22, 37, 135, 20);
			txtKadi.setColumns(10);
		}
		return txtKadi;
	}

	private JTextField getTxtSifre() {
		if (txtSifre == null) {
			txtSifre = new JTextField();
			txtSifre.setBounds(216, 37, 153, 20);
			txtSifre.setColumns(10);
		}
		return txtSifre;
	}

	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setBounds(22, 115, 135, 20);
			txtName.setColumns(10);
		}
		return txtName;
	}

	private JLabel getLblKullancAd() {
		if (lblKullancAd == null) {
			lblKullancAd = new JLabel("Kullan\u0131c\u0131 Ad\u0131");
			lblKullancAd.setBounds(22, 12, 97, 14);
		}
		return lblKullancAd;
	}

	private JLabel getLblifre() {
		if (lblifre == null) {
			lblifre = new JLabel("\u015Eifre");
			lblifre.setBounds(216, 11, 97, 14);
		}
		return lblifre;
	}

	private JLabel getLblIsim() {
		if (lblIsim == null) {
			lblIsim = new JLabel("\u0130sim");
			lblIsim.setBounds(22, 90, 97, 14);
		}
		return lblIsim;
	}

	private JButton getBtnKaydet() {
		if (btnKaydet == null) {
			btnKaydet = new JButton("Kaydet");

			btnKaydet.setBounds(178, 114, 91, 23);
		}
		return btnKaydet;
	}

	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton("G\u00FCncelle");
			btnUpdate.setBounds(278, 114, 91, 23);
		}
		return btnUpdate;
	}

	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("Kullan\u0131c\u0131 Silme");
			btnDelete.setBounds(235, 375, 119, 23);
		}
		return btnDelete;
	}
}
