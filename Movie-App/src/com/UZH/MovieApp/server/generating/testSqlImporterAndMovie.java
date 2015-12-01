package com.UZH.MovieApp.server.generating;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class testSqlImporterAndMovie {
	
	private String inputFileName;
	private String outputFileName;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private String lastRaw;
	private PrintWriter writer;
	@Test
	public void test() {
		//this inputFile does only contain the line(975900	/m/03vyhn	Ghosts of Mars	2001-08-24	14010832	98.0	{"/m/02h40lc": "English Language"}	{"/m/09c7w0": "United States of America"}	{"/m/01jfsb": "Thriller", "/m/06n90": "Science Fiction", "/m/03npn": "Horror", "/m/03k9fj": "Adventure", "/m/0fdjb": "Supernatural", "/m/02kdv5l": "Action", "/m/09zvmj": "Space western"})
		inputFileName="test_db.tsv";
		
		String [] stringTest= new String [25];//nr. of lines in outputFile
		
		//all lines in the order they should appear in our outputFile, so it can be read by the GoogleSQL
		stringTest[0]="/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;";
		stringTest[1]="/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;";
		stringTest[2]="/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;";
		stringTest[3]="/*!40101 SET NAMES utf8 */;";
		stringTest[4]="/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;";
		stringTest[5]="/*!40103 SET TIME_ZONE='+00:00' */;";
		stringTest[6]="/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;";
		stringTest[7]="/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;";
		stringTest[8]="/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;";
		stringTest[9]="/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;";
		stringTest[10]="";//empty string since it should be like this in outFile(easier readability)
		stringTest[11]="CREATE TABLE IF NOT EXISTS `moviedata`";
		stringTest[12]="(";
		stringTest[13]="  `wikiid` int(11) DEFAULT NULL,";
		stringTest[14]="  `freebaseid` varchar(20) CHARACTER SET utf8 DEFAULT NULL,";
		stringTest[15]="  `name` varchar(35) CHARACTER SET utf8 DEFAULT NULL,";
		stringTest[16]="  `releasedate` DATE,";
		stringTest[17]="  `boxoffice` double(16,1) DEFAULT NULL,";
		stringTest[18]="  `runtime` decimal(10,1) DEFAULT NULL,";
		stringTest[19]="  `languages` varchar(500) CHARACTER SET utf8 DEFAULT NULL,";
		stringTest[20]="  `countries` varchar(200) CHARACTER SET utf8 DEFAULT NULL,";
		stringTest[21]="  `genres` varchar(400) CHARACTER SET utf8 DEFAULT NULL";
		stringTest[22]=") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		stringTest[23]="INSERT INTO `moviedata` ( `wikiid`, `freebaseid`, `name`, `releasedate`, `boxoffice`, `runtime`, `languages`, `countries`, `genres`) VALUES";
		stringTest[24]="(975900, '/m/03vyhn', 'Ghosts of Mars', '2001-08-24', 1.4010832E7, 98.0, 'English', 'United States of America', 'Thriller, Science Fiction, Horror, Adventure' )";
		
		//name of the outputFile
		outputFileName="test_db_out.sql";
		//test this class, it should take an inputFile file and convert it to an outputFile(input file has to exist, outputFile must not exist)
		DataSetImporterSQL toTest=new DataSetImporterSQL(inputFileName,outputFileName);
		//init reader to check outputFile
		try {
			this.fileReader = new FileReader(outputFileName);
			this.bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String nextLine;
			int i=0;
			//read in nexgt file line and check for completeness .trim() since we only care about content of line and not amount of \t before or after sth.(could easily be extended) 
			while((nextLine=bufferedReader.readLine())!=null){
				//actual 25 test cases
				assertEquals(nextLine,stringTest[i] );
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
