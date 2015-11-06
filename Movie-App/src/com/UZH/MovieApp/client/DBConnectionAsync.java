package com.UZH.MovieApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DBConnectionAsync {
	
 void getDBData(String symbols, AsyncCallback<String> callback);

}