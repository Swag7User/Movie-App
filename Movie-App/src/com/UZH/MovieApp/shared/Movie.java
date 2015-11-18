package com.UZH.MovieApp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Movie  implements Serializable{
	public String wikiid;
	public String freebaseid;
	public String name;
	public String releasedate;
	public String boxoffice;
	public String runtime;
	public String languages;
	public String countries;
	public String genres;


	public Movie(String wikiid, String freebaseid, String name, String releasedate, String boxoffice, String runtime,
			String languages, String countries, String genres) {
		this.wikiid = wikiid;
		this.freebaseid = freebaseid;
		this.name = name;
		this.releasedate = releasedate;
		this.boxoffice = boxoffice;
		this.runtime = runtime;
		this.languages = languages;
		this.countries = countries;
		this.genres = genres;
	}
	
	public Movie(){
		
	}
	
	
	public void printMovie(){
		System.out.println(wikiid + "\n");	
	}
	public String movieNameToString(){
		return name;
	}

}

