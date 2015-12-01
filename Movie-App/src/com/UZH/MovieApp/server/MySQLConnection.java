package com.UZH.MovieApp.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.UZH.MovieApp.client.DBConnection;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.UZH.MovieApp.server.MySQLAccess;
import com.UZH.MovieApp.shared.Movie;

@SuppressWarnings("serial")
public class MySQLConnection extends RemoteServiceServlet implements DBConnection {

	public ArrayList<Movie> getDBData(String querry) {

		ArrayList<Movie> k = null;

		MySQLAccess dao = new MySQLAccess();
		try {
			k = new ArrayList<Movie>(dao.readDataBase(querry));

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
