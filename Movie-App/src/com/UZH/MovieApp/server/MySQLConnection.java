package com.UZH.MovieApp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.UZH.MovieApp.client.DBConnection;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.UZH.MovieApp.server.MySQLAccess;
import com.UZH.MovieApp.shared.Movie;

public class MySQLConnection extends RemoteServiceServlet implements DBConnection {

	public ArrayList<Movie> getDBData(String querry) {

		ArrayList<Movie> k = null;

		MySQLAccess dao = new MySQLAccess();
		try {
			k = new ArrayList<Movie>(dao.readDataBase(querry));
			/*
			 * System.out.println("k: " + k); for ( Movie o : k){
			 * o.printMovie(); }
			 */

		} catch (Exception e) {

			e.printStackTrace();
		}

		return k;
	}

	@Override
	public HashMap<String, Integer> getDBDataHash(String querry) {
		MySQLAccess access = new MySQLAccess();
		try {
			return access.readDataBaseResultSet(querry);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
