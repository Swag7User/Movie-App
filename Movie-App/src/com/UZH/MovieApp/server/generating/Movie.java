package com.UZH.MovieApp.server.generating;

//created and maintained by Sandro Luck
//13-927-769
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

public class Movie {
	private int wikiID;
	private String freebaseID;
	private String name;
	private Calendar releaseCalender;
	
	private int year;
	private int month;
	private int day;
	
	private double revenue;
	private double runtime;
	private DateFormat df;
	
	private ArrayBlockingQueue<String>language;
	private ArrayBlockingQueue<String>languageCode;
	
	private ArrayBlockingQueue<String>country;
	private ArrayBlockingQueue<String>countryCode;
	
	private ArrayBlockingQueue<String>genre;
	private ArrayBlockingQueue<String>genreCode;
	
	private String []temporary;
	private String []temporaryTuple;
	
	private Date date;
	
	public Movie(String raw){
		//String wiki, String freebase,String movie, String dateString, String revenue, String runtime,String [] languageCountryGenre
		releaseCalender = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd"); 
		String [] tokens = raw.split("\t");
		language=new ArrayBlockingQueue<String>(20);
		languageCode=new ArrayBlockingQueue<String>(20);
		
		country=new ArrayBlockingQueue<String>(20);
		countryCode=new ArrayBlockingQueue<String>(20);
		
		genre=new ArrayBlockingQueue<String>(20);
		genreCode=new ArrayBlockingQueue<String>(20);
		//init all to -1
		year=-1;
		month=-1;
		day=-1;
		
		revenue=-1;
		wikiID=-1;
		runtime=-1;
		
		
		//in this array is saved if information is missing,true if info available
		boolean [] tokensFlag=new boolean[tokens.length];
		//check format is the same for all
		for (int i = 0; i < tokens.length; i++) {
			if(tokens[i]!=null&&!tokens[i].isEmpty()){
				tokensFlag[i]=true;
			}
		}
		
		
		//wiki id
		if(tokensFlag[0]){
			this.wikiID=Integer.parseInt(tokens[0]);
		}
		//String freebase
		if(tokensFlag[1]){
			this.freebaseID=tokens[1];
		}
		//name has to be set
		if(tokensFlag[2]){
			this.name=tokens[2];
		}
		//covnert string to date
		if(tokensFlag[3]){
			
			  
			
			if(tokens[3].length()==4){
				year=Integer.parseInt(tokens[3]);
			}
			else if(tokens[3].length()==7){
				year=Integer.parseInt(tokens[3].substring(0, 4));
				month=Integer.parseInt(tokens[3].substring(5, 7).replace("0", ""));
			}
			else{
				year=Integer.parseInt(tokens[3].substring(0, 4));
				month=Integer.parseInt(tokens[3].substring(5, 7).replace("0", ""));
				day=Integer.parseInt(tokens[3].substring(8, 10).replace("0", ""));
			}
		}
		if(tokensFlag[4]){
			this.revenue=Double.parseDouble(tokens[4]);
		}
		if(tokensFlag[5]){
			this.runtime=Double.parseDouble(tokens[5]);
		}
		if(tokensFlag[6]){
			//take string apart too save all in language array, and language code array
			
			//first split at "," to take apart bsp.: {"/m/064_8sq": "French Language", "/m/05zjd": "Portuguese Language", "/m/02h40lc": "English Language"}
			tokens[6]=tokens[6].replace("{", "");
			tokens[6]=tokens[6].replace("}", "");
			//bsp.:"/m/064_8sq": "French Language", "/m/05zjd": "Portuguese Language", "/m/02h40lc": "English Language"		
			tokens[6]=tokens[6].replace("\"","");
			//bsp.:/m/064_8sq: French Language, /m/05zjd: Portuguese Language, /m/02h40lc: English Language
			tokens[6]=tokens[6].replace("Language","");
			tokens[6]=tokens[6].replace("language","");
			//bsp.:/m/064_8sq: French , /m/05zjd: Portuguese , /m/02h40lc: English
			temporary=tokens[6].split(",");
			//bsp.:/m/064_8sq: French 
			for (int i = 0; i < temporary.length; i++) {
				temporaryTuple=temporary[i].split(":");
				if(temporaryTuple.length>=2){
					//bsp.:/m/064_8sq    bsp.: French Language
					

					if(temporaryTuple[0]!=null&&!temporaryTuple[0].isEmpty()){
						temporaryTuple[0]=temporaryTuple[0].trim();
						languageCode.add(temporaryTuple[0]);
					}
					if(temporaryTuple[1]!=null&&!temporaryTuple[1].isEmpty()){
						temporaryTuple[1]=temporaryTuple[1].trim();
						language.add(temporaryTuple[1]);
					}
				}
			}
		}
		if(tokensFlag[7]){
			//take string apart too save all countries in country array, and country code in country code array
			
			//first split at "," to take apart bsp.: {"/m/064_8sq": "French Language", "/m/05zjd": "Portuguese Language", "/m/02h40lc": "English Language"}
			tokens[7]=tokens[7].replace("{", "");
			tokens[7]=tokens[7].replace("}", "");
			//bsp.:"/m/064_8sq": "French Language", "/m/05zjd": "Portuguese Language", "/m/02h40lc": "English Language"		
			tokens[7]=tokens[7].replace("\"","");
			//bsp.:/m/064_8sq: French , /m/05zjd: Portuguese , /m/02h40lc: English
			temporary=tokens[7].split(",");
			//bsp.:/m/064_8sq: French 
			for (int i = 0; i < temporary.length; i++) {
				temporaryTuple=temporary[i].split(":");
				if(temporaryTuple.length>=2){
					//bsp.:/m/064_8sq    bsp.: French Language
					
					if(temporaryTuple[0]!=null&&!temporaryTuple[0].isEmpty()){
						temporaryTuple[0]=temporaryTuple[0].trim();
						countryCode.add(temporaryTuple[0]);
					}
					if(temporaryTuple[1]!=null&&!temporaryTuple[1].isEmpty()){
						temporaryTuple[1]=temporaryTuple[1].trim();
						country.add(temporaryTuple[1]);
					}
				}
			}
		}
		if(tokensFlag[8]){
			//take string apart too save all genres in genres array, and genres code in genres code array
			
			//first split at "," to take apart bsp.: {"/m/064_8sq": "French Language", "/m/05zjd": "Portuguese Language", "/m/02h40lc": "English Language"}
			tokens[8]=tokens[8].replace("{", "");
			tokens[8]=tokens[8].replace("}", "");
			//bsp.:"/m/064_8sq": "French Language", "/m/05zjd": "Portuguese Language", "/m/02h40lc": "English Language"		
			tokens[8]=tokens[8].replace("\"","");
			//bsp.:/m/064_8sq: French , /m/05zjd: Portuguese , /m/02h40lc: English
			temporary=tokens[8].split(",");
			//bsp.:/m/064_8sq: French 
			for (int i = 0; i < temporary.length; i++) {
				temporaryTuple=temporary[i].split(":");
				if(temporaryTuple.length>=2){
					//bsp.:/m/064_8sq    bsp.: French Language
					
					if(temporaryTuple[0]!=null&&!temporaryTuple[0].isEmpty()){
						temporaryTuple[0]=temporaryTuple[0].trim();
						genreCode.add(temporaryTuple[0]);
					}
					if(temporaryTuple[1]!=null&&!temporaryTuple[1].isEmpty()){
						temporaryTuple[1]=temporaryTuple[1].trim();
						genre.add(temporaryTuple[1]);
					}
				}
			
			}
		}
		//this.printMovie();
	}
	
	
	public int getYear() {
		return year;
	}


	public int getMonth() {
		return month;
	}


	public int getDay() {
		return day;
	}


	/**
	 * @return the wikiID
	 */
	public int getWikiID() {
		return wikiID;
	}


	/**
	 * @return the freebaseID
	 */
	public String getFreebaseID() {
		return freebaseID;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the revenue
	 */
	public double getRevenue() {
		return revenue;
	}


	/**
	 * @return the runtime
	 */
	public double getRuntime() {
		return runtime;
	}


	/**
	 * @return the language
	 */
	public ArrayBlockingQueue<String> getLanguage() {
		return language;
	}


	/**
	 * @return the languageCode
	 */
	public ArrayBlockingQueue<String> getLanguageCode() {
		return languageCode;
	}


	/**
	 * @return the country
	 */
	public ArrayBlockingQueue<String> getCountry() {
		return country;
	}


	/**
	 * @return the countryCode
	 */
	public ArrayBlockingQueue<String> getCountryCode() {
		return countryCode;
	}


	/**
	 * @return the genre
	 */
	public ArrayBlockingQueue<String> getGenre() {
		return genre;
	}


	/**
	 * @return the genreCode
	 */
	public ArrayBlockingQueue<String> getGenreCode() {
		return genreCode;
	}


	//mainly for test reasons
	public void printMovie(){
		System.out.print("WIKI ID:"+this.wikiID+
				"    freebaseID:"+this.freebaseID+
				"    Movie:"+this.name+
				"    revenue:"+this.revenue+
				"    runtime:"+this.runtime);
			System.out.print("    release date:");
			if(year!=-1){
				System.out.print(year);
			}
			if(month!=-1){
				System.out.print("-"+month);
			}
			if(day!=-1){
				System.out.print("-"+day);
			}
			for (String string : language) {
				System.out.print("    Language:"+string);
			}
			for (String string : country) {
				System.out.print("    Country:"+string);
			}
			for (String string : genre) {
				System.out.print("    Genre:"+string);
			}
			System.out.print("\n");
	}
}
