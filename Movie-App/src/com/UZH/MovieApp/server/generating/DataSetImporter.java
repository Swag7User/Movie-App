package com.UZH.MovieApp.server.generating;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

//created and maintained by Sandro Luck
//13-927-769
public class DataSetImporter {
	private final String inputFileName;
	private String outputFileName;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private String [] tokens;
	private String lastRaw;
	private String nextRaw;
	private PrintWriter writer;
	
	int a=0;
	private boolean stop = false;
	
	public DataSetImporter(String inputFileName, String outputFileName){
		this.inputFileName=inputFileName;
		this.outputFileName = outputFileName;
		try {
			this.fileReader = new FileReader(inputFileName);
			this.writer = new PrintWriter(outputFileName);
			initializeDatabase();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bufferedReader = new BufferedReader(fileReader);
		try {
			int amountQuery=0;//how much one query should handel
			lastRaw=bufferedReader.readLine();
			while(stop == false){
				nextRaw=bufferedReader.readLine();
				if(amountQuery<599){//500 entries per INSERT INTO STATEMENT
					if(lastRaw == null){
						stop = true;
					}else{
						String [] tokens = lastRaw.split("\t");
					    
					    printArrayToFile(tokens);
					    if(nextRaw!=null){
					    	writer.print(",\n");
					    }
					    else{
					    	writer.print(";\n");
					    }
					    amountQuery++;
					}
				}
				else if(amountQuery<600){//bad code here
					lastRaw=bufferedReader.readLine();
					if(lastRaw == null){
						stop = true;
					}else{
						String [] tokens = lastRaw.split("\t");
					    
					    printArrayToFile(tokens);
					    amountQuery++;
					}
				}
				else if(nextRaw != null){
			    		amountQuery=0;
			    		writer.print(";\nINSERT INTO `moviedata` (`wikiid`,`freebaseid`,`name`,`releasedate`,`boxoffice`,`runtime`,`languages`,`countries`,`genres`) VALUES");
			    		writer.print("\n" );

			   	}
				lastRaw=nextRaw;
			}
			writer.println("\n/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n"+"/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;\n"+"/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		writer.println("  `releasedate` varchar(10) CHARACTER SET utf8 DEFAULT NULL,");
		writer.println("  `boxoffice` int(12) DEFAULT NULL,");
		writer.println("  `runtime` decimal(3,1) DEFAULT NULL,");
		writer.println("  `languages` varchar(500) CHARACTER SET utf8 DEFAULT NULL,");
		writer.println("  `countries` varchar(200) CHARACTER SET utf8 DEFAULT NULL,");
		writer.println("  `genres` varchar(400) CHARACTER SET utf8 DEFAULT NULL");
		writer.println(") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
		
		writer.print("INSERT INTO `moviedata` ( `wikiid`, `freebaseid`, `name`, `releasedate`, `boxoffice`, `runtime`, `languages`, `countries`, `genres`) VALUES\n");
	}
	
	private void printArrayToFile(String[] tokens){
		writer.print("(");
	    for(int i=0; i<tokens.length-1; i++){
		    	if(tokens[i]!=null&&!tokens[i].isEmpty()){
		    		if(tokens[i].contains("\'")){
		    			tokens[i]=tokens[i].replace("\'", "\'\'");
		    		}
		    		if(tokens[i].contains("Ã")|tokens[i].contains("©")){
		    			tokens[i]=tokens[i].replace("Ã", "xxx");
		    			tokens[i]=tokens[i].replace("©", "yyy");
		    		}
		    		if(i==0|i==4|i==5){
		    			writer.print(tokens[i] + ", ");
		    		}
		    		else{
		    			writer.print("\'" + tokens[i] + "\', ");
		    		}
		    	}
		    	else{
		    		writer.print( "NULL, ");
		    	}
	    }
	    if(tokens[tokens.length-1].contains("\'")){
			tokens[tokens.length-1]=tokens[tokens.length-1].replace("\'", "\'\'");
		}
	    writer.print("\'" + tokens[tokens.length-1] + "\'");
	    writer.print(")" );
	}
}
