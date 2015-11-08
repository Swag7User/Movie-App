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
	
	public String getDBData(int symbols) {
		
		String k = "";
	
	    MySQLAccess dao = new MySQLAccess();
	    try {
	    	k = k.concat(dao.readDataBase(symbols));
		//	System.out.println("k: " + k);

		} catch (Exception e) {
	    	k = k.concat("something borkered");
			e.printStackTrace();
		}
	
		return k;
	}

}
