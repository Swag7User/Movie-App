package com.UZH.MovieApp.server.generating;
//created and maintained by Sandro Luck
//13-927-769
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DataSetImporter("movies_80000.tsv","output_test.sql");
	}
	
	//Initializing countries
	Country a = new Country("Hallo", 200);

}