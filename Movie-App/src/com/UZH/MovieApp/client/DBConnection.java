package com.UZH.MovieApp.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MySQLConnection")
public interface DBConnection extends RemoteService {
	
	
 public Vector<String> getDBData(String querry);

}
