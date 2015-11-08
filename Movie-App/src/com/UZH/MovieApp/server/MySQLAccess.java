package com.UZH.MovieApp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import java.io.*;
import javax.servlet.http.*;

import org.mortbay.log.Log;

import com.google.appengine.api.utils.SystemProperty;

public class MySQLAccess {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public String readDataBase(int symbols) throws Exception {
		String ss = "";

		try {
				
			// This will load the MySQL driver, each DB has its own driver
			  if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			  
			Class.forName("com.mysql.jdbc.GoogleDriver");
			// Setup the connection with the DB
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/feedback?" +
			// "user=root&password=lel");
			Log.info("initiate connection");
			connect = DriverManager.getConnection("jdbc:google:mysql://movieapp1122:moviedatabase2/movieapp?user=twat&password=lel");
			Log.info("got connection");
			  }
			  else{
					Class.forName("com.mysql.jdbc.Driver");
					connect = DriverManager.getConnection("jdbc:mysql://173.194.239.209/movieapp?" + "user=twat&password=lel");
			  }
			  
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			// resultSet = statement.executeQuery("select * from
			// feedback.comments");
			resultSet = statement.executeQuery("select * from movieapp.moviedata");
			ss = ss.concat(writeResultSet(resultSet,symbols));
		//	System.out.println("ss: " + ss);

			

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
