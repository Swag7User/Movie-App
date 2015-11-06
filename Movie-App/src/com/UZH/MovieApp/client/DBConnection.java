package com.UZH.MovieApp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MySQLConnection")
public interface DBConnection extends RemoteService {
	
	
 public String getDBData(String symbols);

}
