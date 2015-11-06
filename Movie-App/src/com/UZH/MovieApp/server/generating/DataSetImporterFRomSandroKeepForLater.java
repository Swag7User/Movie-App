package com.UZH.MovieApp.server.generating;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//created and maintained by Sandro Luck
//13-927-769
public class DataSetImporterFRomSandroKeepForLater {
	private final String inputFileName;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private String [] tokens;
	private String lastRaw;
	
	public DataSetImporterFRomSandroKeepForLater(String inputFileName){
		this.inputFileName=inputFileName;
		try {
			this.fileReader = new FileReader(inputFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bufferedReader = new BufferedReader(fileReader);
		//read all the movies
		try {
			lastRaw=bufferedReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(lastRaw!=null&&!lastRaw.isEmpty()){
			new Movie(lastRaw);
			try {
				lastRaw=bufferedReader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	
