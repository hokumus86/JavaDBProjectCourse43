package com.hokumus.conn.db.blo;

import java.util.List;

import com.hokumus.conn.db.doa.UserTableManageDAO;
import com.hokumus.conn.db.model.ReturnType;
import com.hokumus.conn.db.model.User;

public class UserContBLO {
	private UserTableManageDAO dao = new UserTableManageDAO();

	public Object[] getUserForUserName(String userName) {
		try {
			List<User> liste = dao.getAllUser();
			if (liste.size() == 0) {
				User kullanici = new User();
				kullanici.setUserName("admin");
				kullanici.setPassword("123");
				kullanici.setName("admin");
				dao.insertUser(kullanici);
			} else {
				User temp = dao.getUserForUserName(userName);
				if (temp != null)
					return new Object[] { temp };
				else
					return new Object[] { "Kullan�c� Bulunamad�", "bo�luk" };

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Object[] { "Hata Olu�tu", e.getMessage() };
		}

		return new Object[] { null };
	}

	public List<User> getAllUser() throws Exception {

		List<User> liste = dao.getAllUser();

		return liste;
	}

	public boolean insertUser(User usr) throws Exception {

		return dao.insertUser(usr);

	}

	public Boolean updateUser(List<User> liste) throws Exception {
		boolean durum = false;
		for (User user : liste) {
			if (user.getId() == 0) {
				durum = dao.insertUser(user);
			} else {
				durum = dao.updateUser(user);
			}

		}

		return durum;
	}

	public Object[] deleteUserForId(User delUser) {
		try {
			Object[] sonuc = null;

			Boolean durum = dao.deleteUser(delUser.getId());

			if (durum) {
				return new Object[] { ReturnType.ISLEM_BASARILI, "Kullan�c� Ba�ar�yla Silindi" };
			} else
				return new Object[] { ReturnType.ISLEM_BASARISIZ, "Kullan�c� Silinemedi" };

		} catch (Exception e) {
			e.printStackTrace();
			return new Object[] { ReturnType.HATA_OLUSTU, "Silme i�leminde Bir hata oldu ", e.getMessage() };

		} finally {
			System.out.println("dfskdlfjsdlkj");
		}
	}

}
