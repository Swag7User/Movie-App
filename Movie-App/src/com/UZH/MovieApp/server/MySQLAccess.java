package com.UZH.MovieApp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public String readDataBase(int symbols) throws Exception {
		String ss = "";

		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/feedback?" +
			// "user=root&password=lel");
			connect = DriverManager.getConnection("jdbc:mysql://173.194.83.205/movieapp?" + "user=twat&password=lel");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			// resultSet = statement.executeQuery("select * from
			// feedback.comments");
			resultSet = statement.executeQuery("select * from movieapp.moviedata");
			ss = ss.concat(writeResultSet(resultSet,symbols));
		//	System.out.println("ss: " + ss);

			/*
			 * // PreparedStatements can use variables and are more efficient
			 * preparedStatement = connect.prepareStatement(
			 * "insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)"
			 * ); // "myuser, webpage, datum, summary, COMMENTS from //
			 * feedback.comments"); // Parameters start with 1
			 * preparedStatement.setString(1, "Test");
			 * preparedStatement.setString(2, "TestEmail");
			 * preparedStatement.setString(3, "TestWebpage");
			 * preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			 * preparedStatement.setString(5, "TestSummary");
			 * preparedStatement.setString(6, "TestComment");
			 * preparedStatement.executeUpdate();
			 * 
			 * preparedStatement = connect.prepareStatement(
			 * "SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments"
			 * ); resultSet = preparedStatement.executeQuery();
			 * writeResultSet(resultSet);
			 * 
			 * // Remove again the insert comment preparedStatement =
			 * connect.prepareStatement(
			 * "delete from feedback.comments where myuser= ? ; ");
			 * preparedStatement.setString(1, "Test");
			 * preparedStatement.executeUpdate();
			 * 
			 * resultSet = statement.executeQuery(
			 * "select * from movieapp.moviedata"); writeMetaData(resultSet);
			 */

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return ss;

	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	private String writeResultSet(ResultSet resultSet, int symbols) throws SQLException {
		// ResultSet is initially before the first data set
		int i = 0;
		String s = "";
		// String[] strarray = new String[10];
		while (resultSet.next() && i < symbols) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String wikiid = resultSet.getString(1);
			String freebaseid = resultSet.getString(2);
			String name = resultSet.getString(3);
			String releasedate = resultSet.getString(4);
			String boxoffice = resultSet.getString(5);
			String runtime = resultSet.getString(6);
			String languages = resultSet.getString(7);
			String countries = resultSet.getString(8);
			String genres = resultSet.getString(9);
			s = s.concat(wikiid);
			s = s.concat(" ");
			s = s.concat(freebaseid);
			s = s.concat(" ");
			s = s.concat(name);
			s = s.concat(" ");
			if (releasedate == null){
				s = s.concat("0");
			}
			else{
			s = s.concat(releasedate);
			}
			s = s.concat(" ");
			s = s.concat(boxoffice);
			s = s.concat(" ");
			if (runtime == null){
			s = s.concat("0");
			}
			else{
			s = s.concat(runtime);
			}
			s = s.concat(" ");
			s = s.concat(languages);
			s = s.concat(" ");
			s = s.concat(countries);
			s = s.concat(" ");
			s = s.concat(genres); 
			s = s.concat(" --- ");
			//System.out.println("s: " + s);

			/*
								 * System.out.println("wikiid: " + wikiid);
								 * System.out.println("freebaseid: " +
								 * freebaseid); System.out.println("name: " +
								 * name); System.out.println("releasedate: " +
								 * releasedate); System.out.println(
								 * "boxoffice: " + boxoffice);
								 * System.out.println("runtime: " + runtime);
								 * System.out.println("languages: " +
								 * languages); System.out.println("countries: "
								 * + countries); System.out.println("genres: " +
								 * genres);
								 */
			i++;
		}
		return s;
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
