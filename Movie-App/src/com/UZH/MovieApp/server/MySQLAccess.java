package com.UZH.MovieApp.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.io.*;
import javax.servlet.http.*;

import org.mortbay.log.Log;

import com.UZH.MovieApp.shared.Movie;
import com.google.appengine.api.utils.SystemProperty;

public class MySQLAccess extends HttpServlet{

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public ArrayList<Movie> readDataBase(String querry) throws Exception  {
		String ss = "";
		ArrayList<Movie> ss_new = null;
		try {
				
			// This will load the MySQL driver, each DB has its own driver
			  if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			  
			Class.forName("com.mysql.jdbc.GoogleDriver");
			// Setup the connection with the DB
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/feedback?" +
			// "user=root&password=lel");
			connect = DriverManager.getConnection("jdbc:google:mysql://movieapp1122:moviedatabase2/movieapp?user=root");
			  }
			  else{
					Class.forName("com.mysql.jdbc.Driver");
					connect = DriverManager.getConnection("jdbc:mysql://localhost/movieapp?user=root");
			  }

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// Result set get the result of the SQL query
			// resultSet = statement.executeQuery("select * from
			// feedback.comments");
			int symbols = 100;
			String sqlQuerry = "select * from movieapp.moviedata LIMIT ";
			sqlQuerry = sqlQuerry.concat(Integer.toString(symbols));
			System.out.println(querry);
			resultSet = statement.executeQuery(querry);
		//	ss = "";
		//	ss = ss.concat(writeResultSet(resultSet,symbols));
			ss_new = writeResultSet(resultSet,symbols);
		

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
/*		for ( Movie o : ss_new){
			System.out.println("ss: " + ss_new);
			o.printMovie();
		}
*/		return ss_new;

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

	private ArrayList<Movie> writeResultSet(ResultSet resultSet, int symbols) throws SQLException {
		// ResultSet is initially before the first data set
		int i = 0;
		ArrayList<Movie> s_new = new ArrayList<Movie>();
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
		//	s_new = new ArrayList<Movie>();
			s_new.add(new Movie(wikiid, freebaseid, name, releasedate, boxoffice, runtime, languages, countries, genres));
		
			
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
