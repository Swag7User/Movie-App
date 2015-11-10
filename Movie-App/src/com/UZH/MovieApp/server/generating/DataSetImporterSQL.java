package com.UZH.MovieApp.server.generating;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;

public class DataSetImporterSQL {
	

	//created and maintained by Sandro Luck
	//13-927-769

		private final String inputFileName;
		private String outputFileName;
		private FileReader fileReader;
		private BufferedReader bufferedReader;
		private String lastRaw;
		private PrintWriter writer;
		
		private int maxPerInsert=600;
		private int currentInsert=600;
		
		
		private Movie[] movies;
		
		public DataSetImporterSQL(String inputFileName, String outputFileName){
			this.inputFileName=inputFileName;
			this.outputFileName = outputFileName;
			movies=new Movie[100000];
			
			//init SQL FILE
			try {
				this.fileReader = new FileReader(inputFileName);
				this.bufferedReader = new BufferedReader(fileReader);
				this.writer = new PrintWriter(outputFileName);
				initializeDatabase();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//init movies as class
			int i=0;
			try {
				lastRaw=bufferedReader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(lastRaw!=null&&!lastRaw.isEmpty()){
				movies[i]=new Movie(lastRaw);
					try {
						lastRaw=bufferedReader.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
			}
			//write movies to SQL file
			i=0;
			for(Movie movie:movies){
				if(movie!=null&&i<maxPerInsert){
					if(i<maxPerInsert&i>0){
						writer.print(",\n");
					}
					printMovieToFile(movie);
					i++;
				}
				else if(movie!=null){
					i=0;
					writer.print(";\nINSERT INTO `moviedata` (`wikiid`,`freebaseid`,`name`,`releasedate`,`boxoffice`,`runtime`,`languages`,`countries`,`genres`) VALUES");	    		
					writer.print("\n" );				
				}
			}
			//at end of SQL
			//writer.println("\n\n/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n"+"/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;\n"+"/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;\n");
		    writer.close();
		}
		
		private void initializeDatabase(){
			writer.println("/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;");
			writer.println("/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;");
			writer.println("/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;");
			writer.println("/*!40101 SET NAMES utf8 */;");
			writer.println("/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;");
			writer.println("/*!40103 SET TIME_ZONE='+00:00' */;");
			writer.println("/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;");
			writer.println("/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;");
			writer.println("/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;");
			writer.println("/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;");
			
			writer.println("\nCREATE TABLE IF NOT EXISTS `moviedata`");
			writer.println("(");
			writer.println("  `wikiid` int(11) DEFAULT NULL,");
			writer.println("  `freebaseid` varchar(20) CHARACTER SET utf8 DEFAULT NULL,");
			writer.println("  `name` varchar(35) CHARACTER SET utf8 DEFAULT NULL,");
			writer.println("  `releasedate` DATE,");
			writer.println("  `boxoffice` double(16,1) DEFAULT NULL,");
			writer.println("  `runtime` decimal(10,1) DEFAULT NULL,");
			writer.println("  `languages` varchar(500) CHARACTER SET utf8 DEFAULT NULL,");
			writer.println("  `countries` varchar(200) CHARACTER SET utf8 DEFAULT NULL,");
			writer.println("  `genres` varchar(400) CHARACTER SET utf8 DEFAULT NULL");
			writer.println(") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
			
			writer.print("INSERT INTO `moviedata` ( `wikiid`, `freebaseid`, `name`, `releasedate`, `boxoffice`, `runtime`, `languages`, `countries`, `genres`) VALUES\n");
		}
		
		private void printMovieToFile(Movie movie){
			String temp;
			int tempInt=0;
			writer.print("(");
		    //Insert Wiki-id
			if(movie.getWikiID()!=-1){
				writer.print(movie.getWikiID()+", ");
			}
			else{
				writer.print("NULL, ");
			}
    		//Insert Freebase-id
			if(movie.getFreebaseID()!=null){
    		writer.print("'"+movie.getFreebaseID()+"', ");
			}
    		else{
				writer.print("NULL, ");
			}
    		//Insert Name
			if(movie.getName()!=null){
				temp=movie.getName();
			   	temp=temp.replace("\'", "\'\'");
			   	writer.print("'"+temp+"', ");
			}
    		else{
				writer.print("NULL, ");
			}
    		//Insert Release-Date
    		if(movie.getYear()!=-1){
    		writer.print("'"+movie.getYear());
	    		if(movie.getMonth()!=-1){
	    			if(movie.getMonth()>=10){
	        		writer.print("-"+movie.getMonth());
	    			}
	    			else{
	    			writer.print("-0"+movie.getMonth());
	    			}
	        		if(movie.getDay()!=-1){
	        			if(movie.getDay()>=10){
	    	        		writer.print("-"+movie.getDay());
	    	    			}
	    	    			else{
	    	    			writer.print("-0"+movie.getDay());
	    	    			}
		        	}
	        		else{
	        			writer.print("-00");
	        		}
	        	}
	    		else{
	    			writer.print("-00-00");
	    		}
	    		writer.print("', ");
    		}
    		else{
    		//if year is not set make it NULL
        	writer.print("NULL, ");
    		}
    		
    		//Insert Revenue
    		if(movie.getRevenue()!=-1){
    		writer.print(movie.getRevenue()+", ");
    		}
    		else{
				writer.print("NULL, ");
			}
		    //Insert Length
    		if(movie.getRuntime()!=-1){
		    writer.print(movie.getRuntime()+", ");
    		}
		    else{
				writer.print("NULL, ");
			}
		    //Insert Language
    		if(movie.getLanguage().size()>0){
    			tempInt=0;
		    	ArrayBlockingQueue<String> movieLanguage=movie.getLanguage();
			    writer.print("'");
			    if(movieLanguage.size()>1){
			    for(String string:movieLanguage){
			    	if(movieLanguage.size()-tempInt>1){
			    		writer.print(string+", ");
			    	}
			    	else{
			    		writer.print(string);
			    	}
			    	tempInt++;
			     }
			    }
			    else{
			    	for(String string:movieLanguage){
				    	writer.print(string);
				    }
			    }
			    writer.print("', ");
    		}
		    else{
				writer.print("NULL, ");
			}
		    //Insert Country
    		if(movie.getCountry().size()>0){
    				tempInt=0;
			    	ArrayBlockingQueue<String> movieCountry=movie.getCountry();
				    writer.print("'");
				    if(movieCountry.size()>1){
				    for(String string:movieCountry){
				    	if(movieCountry.size()-tempInt>1){
				    		writer.print(string+", ");
				    	}
				    	else{
				    		writer.print(string);
				    	}
				    	tempInt++;
				     }
				    }
				    else{
				    	for(String string:movieCountry){
					    	writer.print(string);
					    }
				    }
				    writer.print("', ");
    		}
			else{
				writer.print("NULL, ");
			}
    		
		    //Insert Genre
    		if(movie.getGenre().size()>0){
    			tempInt=0;
    			String temp2;
		    	ArrayBlockingQueue<String> movieGenre=movie.getGenre();
			    
		    	writer.print("'");
			    if(movieGenre.size()>1){
			    for(String string:movieGenre){
			    	if(movieGenre.size()-tempInt>1){
			    		temp2=string;
			    		temp2=temp2.replace("'", "''");
			    		writer.print(temp2+", ");
			    	}
			    	else{
			    		temp2=string;
			    		temp2=temp2.replace("'", "''");
			    		writer.print(temp2);
			    	}
			    	tempInt++;
			     }
			    }
			    else{
			    	for(String string:movieGenre){
			    		temp2=string;
			    		temp2=temp2.replace("'", "''");
				    	writer.print(temp2);
				    }
			    }
			    writer.print("' ");
    		}
			else{
				writer.print("NULL ");
			}
    		
		    //finish one data-set
		    writer.print(")");
		}
	}


