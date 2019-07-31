package com.hokumus.conn.db.doa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hokumus.conn.db.model.User;

public class UserTableManageDAO {

	public User getUserForUserName(String userName) throws Exception {
		User temp = null;
		Connection conn = DbConnector.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "Select username, password,name from usr where username= '" + userName + "'";
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			temp = new User();
			temp.setUserName(rs.getString("username"));
			temp.setPassword(rs.getString("password"));
			temp.setName(rs.getString("name"));
		}
		return temp;

	}

	public List<User> getAllUser() throws Exception {
		List<User> liste = new ArrayList<User>();
		Connection conn = DbConnector.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "Select id ,username, password,name from usr ";
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			User temp = new User();
			temp.setId(rs.getLong("id"));
			temp.setUserName(rs.getString("username"));
			temp.setPassword(rs.getString("password"));
			temp.setName(rs.getString("name"));
			liste.add(temp);
		}
		return liste;

	}

	public boolean insertUser(User usr) throws Exception {
		Connection conn = DbConnector.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "insert into usr(id, username,password,name) values(nextval('seq_usr') ,'" + usr.getUserName()
				+ "', '" + usr.getPassword() + "', '" + usr.getName() + "' ) ";
		int count = stmt.executeUpdate(sql);
		System.out.println(sql);

		return count > 0;

	}

	public boolean updateUser(User temp) throws Exception {

		Connection conn = DbConnector.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "update  usr set  username = '" + temp.getUserName() + "'  , password = '" + temp.getPassword()
				+ "'  , name = '" + temp.getName() + "'  where id = " + temp.getId();
		int count = stmt.executeUpdate(sql);
		System.out.println(sql);

		return count > 0;
	}

	public Boolean deleteUser(Long id) throws Exception {

		Connection conn = DbConnector.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "delete from usr  where id=" + id;
		int count = stmt.executeUpdate(sql);
		System.out.println(sql);

		return count > 0;
	}

}
