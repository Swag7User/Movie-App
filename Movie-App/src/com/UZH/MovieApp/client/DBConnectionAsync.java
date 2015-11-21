package com.UZH.MovieApp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.UZH.MovieApp.shared.Movie;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DBConnectionAsync {
	void getDBData(String querry, AsyncCallback<ArrayList<Movie>> callback);
	void getDBDataHash(String querry, AsyncCallback<HashMap<String, Integer>> callback);
}