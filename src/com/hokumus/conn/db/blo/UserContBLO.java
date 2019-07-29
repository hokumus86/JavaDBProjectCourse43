package com.hokumus.conn.db.blo;

import java.util.List;

import com.hokumus.conn.db.doa.UserTableManageDAO;
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
					return new Object[] { "Kullanýcý Bulunamadý", "boþluk" };

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Object[] { "Hata Oluþtu", e.getMessage() };
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
			if(user.getId()==0) {
				durum = dao.insertUser(user);
			}
			else {
				durum = dao.updateUser(user);
			}
			
		}
		
		
		return durum;
	}

}
