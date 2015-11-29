package com.UZH.MovieApp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import org.junit.Test;
import org.junit.Test.*;

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
		this.wikiid = null;
		this.freebaseid = null;
		this.name = null;
		this.releasedate = null;
		this.boxoffice = null;
		this.runtime = null;
		this.languages = null;
		this.countries = null;
		this.genres = null;
	}

	
	
	
	
	public void printMovie(){
		System.out.println(wikiid + "\n");	
	}
	public String movieNameToString(){
		return name;
	}

	/**
	 * @return the wikiid
	 */
	public String getWikiid() {
		return wikiid;
	}

	/**
	 * @param wikiid the wikiid to set
	 */
	public void setWikiid(String wikiid) {
		this.wikiid = wikiid;
	}

	/**
	 * @return the freebaseid
	 */
	public String getFreebaseid() {
		return freebaseid;
	}

	/**
	 * @param freebaseid the freebaseid to set
	 */
	public void setFreebaseid(String freebaseid) {
		this.freebaseid = freebaseid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the releasedate
	 */
	public String getReleasedate() {
		return releasedate;
	}

	/**
	 * @param releasedate the releasedate to set
	 */
	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}

	/**
	 * @return the boxoffice
	 */
	public String getBoxoffice() {
		return boxoffice;
	}

	/**
	 * @param boxoffice the boxoffice to set
	 */
	public void setBoxoffice(String boxoffice) {
		this.boxoffice = boxoffice;
	}

	/**
	 * @return the runtime
	 */
	public String getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the languages
	 */
	public String getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(String languages) {
		this.languages = languages;
	}

	/**
	 * @return the countries
	 */
	public String getCountries() {
		return countries;
	}

	/**
	 * @param countries the countries to set
	 */
	public void setCountries(String countries) {
		this.countries = countries;
	}

	/**
	 * @return the genres
	 */
	public String getGenres() {
		return genres;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(String genres) {
		this.genres = genres;
	}
	
}

