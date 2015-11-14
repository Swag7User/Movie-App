package com.UZH.MovieApp.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DBConnectionAsync {
	
 void getDBData(String querry, AsyncCallback<Vector<String>> callback);

}