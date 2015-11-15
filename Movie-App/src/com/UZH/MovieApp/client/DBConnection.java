package com.UZH.MovieApp.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.UZH.MovieApp.shared.Movie;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MySQLConnection")
public interface DBConnection extends RemoteService {
	
	
 public ArrayList<Movie> getDBData(String querry);

}