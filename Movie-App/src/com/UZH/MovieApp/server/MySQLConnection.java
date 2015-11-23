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
import com.google.appengine.repackaged.com.google.common.collect.ArrayListMultimap;
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
		/*HashMap<String, Integer> hash = new HashMap<String, Integer>();
		MySQLAccess access = new MySQLAccess();
		//java.sql.ResultSet result;
		ArrayListMultimap<String,Integer> mapCountry2 = ArrayListMultimap.create();
		HashMap<String, Integer> mapCountry = new HashMap<String, Integer>();
		HashMap<String, Integer> distinct = new HashMap<String, Integer>();
		HashMap<String, Integer> combinedMap = new HashMap<String, Integer>();
		
		try{
			//java.sql.ResultSet result = access.readDataBaseResultSet(querry);
			int curr = 0;
			while (result.next()){
				if (result.getString(1).contains(", ")) {
					String[] lineArr;
					lineArr = result.getString(1).split(",");
					for (String string : lineArr) {
						mapCountry2.put(string, result.getInt(2));
						if(mapCountry.containsKey(string)) {
							mapCountry.put(string, curr + result.getInt(2));
						}
						mapCountry.put(string, result.getInt(2));
						
						System.out.println(string + " -- " + result.getInt(2));
						curr = result.getInt(2);
					}
					
					continue;
//				mapCountry.remove(result.getString(1));
				}
				
				
				System.out.println("curr: " + result.getInt(2) + " String: " + result.getString(1));
				if (result.getString(1).equalsIgnoreCase("United States of America")) {
					distinct.put("United States", result.getInt(2));
				}
				distinct.put(result.getString(1), result.getInt(2));
//				if (result.getString(1).contains(", ")) {
//					continue;
//				}
//				distinct.put(result.getString(1), result.getInt(2));
//				mapCountry.put(result.getString(count),result.getInt(count));
//				count++;
			}
			Set<String> keys = mapCountry2.keySet();
			System.out.println("SET OF mapCountry 2: " + keys);
			System.out.println("mapCountry222222: " + mapCountry2.entries());
			System.out.println("distinct: " + distinct.entrySet());
			System.out.println("SizeDIstinct: " + distinct.size());
			System.out.println("mapCounty: " + mapCountry.entrySet());
			System.out.println("SizeMapCountry: " + mapCountry.size());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return distinct;*/
	}

}
