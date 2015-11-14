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
	
	public Vector<String> getDBData(String querry) {
		
	//	String k = "";
		Vector<String> k = null;
	
	    MySQLAccess dao = new MySQLAccess();
	    try {
			k = new Vector<String>(dao.readDataBase(querry));
		//	System.out.println("k: " + k);

		} catch (Exception e) {
	    	
			e.printStackTrace();
		}
	
		return k;
	}

}
