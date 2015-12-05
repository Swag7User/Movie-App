package com.UZH.MovieApp.server;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.*;

import com.UZH.MovieApp.shared.Movie;
import com.google.appengine.api.utils.SystemProperty;

@SuppressWarnings("serial")
public class MySQLAccess extends HttpServlet {

	private Connection connect = null;
	private Statement statement;
	private ResultSet resultSet;
	java.sql.Date nullDate = new java.sql.Date(0000, 00, 00);
	TimeZone tzone = TimeZone.getTimeZone("+00:00");

	public ArrayList<Movie> readDataBase(String querry) throws Exception {
		java.util.TimeZone.setDefault(tzone);
		ArrayList<Movie> ss_new = null;
		try {

			// This will load the MySQL driver, each DB has its own driver
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

				Class.forName("com.mysql.jdbc.GoogleDriver");
				connect = DriverManager
						.getConnection("jdbc:google:mysql://movieapp1122:moviedatabase2/movieapp?user=root");
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
		java.util.TimeZone.setDefault(tzone);
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		MySQLAccess access = new MySQLAccess();
		HashMap<String, Integer> mapCountry = new HashMap<String, Integer>();

		try {

			// This will load the MySQL driver, each DB has its own driver
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

				Class.forName("com.mysql.jdbc.GoogleDriver");
				// Setup the connection with the DB
				// connect =
				// DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
				// +
				// "user=root&password=lel");
				connect = DriverManager
						.getConnection("jdbc:google:mysql://movieapp1122:moviedatabase2/movieapp?user=root");
			} else {
				Class.forName("com.mysql.jdbc.Driver");
				connect = DriverManager.getConnection("jdbc:mysql://localhost/movieapp?user=root");
			}

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			System.out.println(querry);
			resultSet = statement.executeQuery(querry);
			System.out.println("First: " + resultSet.first());

			while (resultSet.next()) {
				if (resultSet.getString(1).contains(", ")) {
					String[] lineArr;
					lineArr = resultSet.getString(1).split(", ");
					for (String string : lineArr) {
						if (string.equalsIgnoreCase("United States of America")) {
							string = "United States";
						}
						if (mapCountry.containsKey(string)) {
							mapCountry.put(string, mapCountry.get(string) + resultSet.getInt(2));
						} else {
							mapCountry.put(string, resultSet.getInt(2));
						}
					}

					continue;
				} else {
					String string = resultSet.getString(1);
					if (string.equalsIgnoreCase("United States of America")) {
						string = "United States";
					}
					if (mapCountry.containsKey(string)) {
						mapCountry.put(string, mapCountry.get(string) + resultSet.getInt(2));
					} else {
						mapCountry.put(string, resultSet.getInt(2));
					}
				}

			}

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return mapCountry;

	}

	@SuppressWarnings("unused")
	private void writeMetaData(ResultSet resultSet) throws SQLException {
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	public ArrayList<Movie> writeResultSet(ResultSet resultSet, int symbols) throws SQLException {
		int i = 0;
		ArrayList<Movie> s_new = new ArrayList<Movie>();
		while (resultSet.next() && i <= symbols) {
			String wikiid = resultSet.getString(1);
			String freebaseid = resultSet.getString(2);
			String name = resultSet.getString(3);
			java.sql.Date timestamp = resultSet.getDate(4);
			// get year from resultset
			Calendar calendar = Calendar.getInstance();
			if (timestamp == null){
				calendar.setTime(nullDate);
			}
			else{
				calendar.setTime(timestamp);
			}
			Integer year = calendar.get(Calendar.YEAR);
			
			String boxoffice = resultSet.getString(5);
			String runtime = resultSet.getString(6);
			String languages = resultSet.getString(7);
			String countries = resultSet.getString(8);
			String genres = resultSet.getString(9);
			String releasedate;
			if (timestamp == null)
				releasedate = "---";
			else {
				releasedate = year.toString();
			}
			System.out.println(name + " :Date  : " + timestamp);
			System.out.println(name + " :String:" + releasedate);
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
