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
	private Statement statement;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet;

	public ArrayList<Movie> readDataBase(String querry) throws Exception  {
		ArrayList<Movie> ss_new = null;
		try {
				
			// This will load the MySQL driver, each DB has its own driver
			  if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			  
			Class.forName("com.mysql.jdbc.GoogleDriver");
			connect = DriverManager.getConnection("jdbc:google:mysql://movieapp1122:moviedatabase3/movieapp?user=root");
			  }
			  else{
					Class.forName("com.mysql.jdbc.Driver");
					connect = DriverManager.getConnection("jdbc:mysql://localhost/movieapp?user=root");
			  }

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(Integer.MIN_VALUE);
	//		statement = connect.createStatement();
		//	statement.setFetchSize(0);

			// Result set get the result of the SQL query
			// resultSet = statement.executeQuery("select * from
			// feedback.comments");
			int symbols = 40000;
			
		//	String sqlQuerry = "select * from movieapp.moviedata LIMIT ";
		//	sqlQuerry = sqlQuerry.concat(Integer.toString(symbols));
			System.out.println(querry);
			resultSet = statement.executeQuery(querry);
		//	ss = "";
		//	ss = ss.concat(writeResultSet(resultSet,symbols));
			System.out.println("start resultset");
			ss_new = writeResultSet(resultSet,symbols);
			System.out.println("end resultset");


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
		System.out.println("finished querry");
		while (resultSet.next()) {
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
			
			if(i == 10000) 		System.out.println("10000");
			if(i == 20000) 		System.out.println("20000");
			if(i == 30000) 		System.out.println("30000");
			if(i == 40000) 		System.out.println("40000");
			if(i == 50000) 		System.out.println("50000");
			if(i == 60000) 		System.out.println("60000");
			if(i == 70000) 		System.out.println("70000");
			if(i == 80000) 		System.out.println("80000");
			
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
