package com.UZH.MovieApp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.UZH.MovieApp.client.DBConnection;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.UZH.MovieApp.server.MySQLAccess;

public class MySQLConnection extends RemoteServiceServlet implements DBConnection {
	
	public String getDBData(String symbols) {
		
	
	    MySQLAccess dao = new MySQLAccess();
	    try {
			dao.readDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String s = "okay";
		String q = new MySQLAccess().gib();
		
		return q;
	}

/*
	private Connection conn = null;
	private String status;
	private String url = "jdbc:mysql://Localhost/testdb";
	private String user = "root";
	private String pass = "lel";

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			// NEVER catch exceptions like this
			// shut up, I do what I want
		}
	}

	public String authenticateUser(String inp1, String inp2) throws IllegalArgumentException {
		String User = null;
		String password = null;
		// User user;
		try {
			PreparedStatement ps = conn.prepareStatement("select readonly * from users where	username = \"" + user
					+ "\" AND " + "password = \"" + pass + "\"");
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				User = new String(result.getString(1));
				password = new String(result.getString(2));
				// user = new User(result.getString(1), result.getString(2));
			}
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			// do stuff on fail
			//return "nopenope";
		}
		String result1 = "true/xxx";
		String result2 = "false/xxx";
		if (inp1.equals(User)) {
			return result1;
		} else {
			return result2;
		}

	}*/

}
