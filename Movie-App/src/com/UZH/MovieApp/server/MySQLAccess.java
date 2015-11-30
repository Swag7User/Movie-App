package com.UZH.MovieApp.server;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ArrayList;
import javax.servlet.http.*;

import com.UZH.MovieApp.shared.Movie;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.repackaged.com.google.common.collect.ArrayListMultimap;

@SuppressWarnings("serial")
public class MySQLAccess extends HttpServlet {

	private Connection connect = null;
	private Statement statement;
	private ResultSet resultSet;

	public ArrayList<Movie> readDataBase(String querry) throws Exception {
		ArrayList<Movie> ss_new = null;
		try {

			// This will load the MySQL driver, each DB has its own driver
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

				Class.forName("com.mysql.jdbc.GoogleDriver");
				connect = DriverManager
						.getConnection("jdbc:google:mysql://movieapp1122:moviedatabase3/movieapp?user=root");
			} else {
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection("jdbc:mysql://localhost/movieapp?user=root");
			}
			// This makes the SQL pointer continuous instead of chunk sized
			statement = connect.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
					java.sql.ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(Integer.MIN_VALUE);

			int limit = 40000;

			System.out.println(querry);
			resultSet = statement.executeQuery(querry);
			ss_new = writeResultSet(resultSet, limit);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		return ss_new;

	}

	public HashMap<String, Integer> readDataBaseResultSet(String querry) throws Exception {
		ArrayListMultimap<String, Integer> mapCountry2 = ArrayListMultimap.create();
		HashMap<String, Integer> mapCountry = new HashMap<String, Integer>();
		HashMap<String, Integer> distinct = new HashMap<String, Integer>();
		try {

			// This will load the MySQL driver, each DB has its own driver
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

				Class.forName("com.mysql.jdbc.GoogleDriver");
				connect = DriverManager
						.getConnection("jdbc:google:mysql://movieapp1122:moviedatabase3/movieapp?user=root");
			} else {
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection("jdbc:mysql://localhost/movieapp?user=root");
			}

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			System.out.println(querry);
			resultSet = statement.executeQuery(querry);
			System.out.println("First: " + resultSet.first());

			int curr = 0;
			while (resultSet.next()) {
				if (resultSet.getString(1).contains(", ")) {
					String[] lineArr;
					lineArr = resultSet.getString(1).split(",");
					for (String string : lineArr) {
						mapCountry2.put(string, resultSet.getInt(2));
						if (mapCountry.containsKey(string)) {
							mapCountry.put(string, curr + resultSet.getInt(2));
						}
						mapCountry.put(string, resultSet.getInt(2));
						curr = resultSet.getInt(2);
					}
					continue;
				}

				if (resultSet.getString(1).equalsIgnoreCase("United States of America")) {
					distinct.put("United States", resultSet.getInt(2));
				}
				distinct.put(resultSet.getString(1), resultSet.getInt(2));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return distinct;

	}

	@SuppressWarnings("unused")
	private void writeMetaData(ResultSet resultSet) throws SQLException {
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	private ArrayList<Movie> writeResultSet(ResultSet resultSet, int symbols) throws SQLException {
		int i = 0;
		ArrayList<Movie> s_new = new ArrayList<Movie>();
		while (resultSet.next() && i <= symbols) {
			String wikiid = resultSet.getString(1);
			String freebaseid = resultSet.getString(2);
			String name = resultSet.getString(3);
			String releasedate = resultSet.getString(4);
			String boxoffice = resultSet.getString(5);
			String runtime = resultSet.getString(6);
			String languages = resultSet.getString(7);
			String countries = resultSet.getString(8);
			String genres = resultSet.getString(9);
			s_new.add(
					new Movie(wikiid, freebaseid, name, releasedate, boxoffice, runtime, languages, countries, genres));
			i++;
		}
		return s_new;
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
