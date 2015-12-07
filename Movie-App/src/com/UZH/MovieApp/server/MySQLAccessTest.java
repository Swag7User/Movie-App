package com.UZH.MovieApp.server;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.UZH.MovieApp.shared.Movie;


public class MySQLAccessTest {
       ArrayList<Movie> k = null;
       @Test
       public void test() {
           MySQLAccess test = new MySQLAccess();
           try {
                k = new ArrayList<Movie>(test.readDataBase("select * from movieapp.moviedata WHERE wikiid = 330"));
           } catch (Exception e) {
                k = null;
               e.printStackTrace();
           }
           Movie testMovie1 = k.get(0);
           Movie testMovie2 = new Movie("330", "/m/0ktn59", "Actrius", "1996", "0", "90.0", "Catalan, Spanish", "Spain", "Drama, Comedy-drama");
           assertEquals(testMovie1.getWikiid(), testMovie2.getWikiid());
           assertEquals(testMovie1.getFreebaseid(), testMovie2.getFreebaseid());
           assertEquals(testMovie1.getName(), testMovie2.getName());
           assertEquals(testMovie1.getReleasedate(), testMovie2.getReleasedate());
           assertEquals(testMovie1.getBoxoffice(), testMovie2.getBoxoffice());
           assertEquals(testMovie1.getRuntime(), testMovie2.getRuntime());
           assertEquals(testMovie1.getLanguages(), testMovie2.getLanguages());
           assertEquals(testMovie1.getCountries(), testMovie2.getCountries());
           assertEquals(testMovie1.getGenres(), testMovie2.getGenres());


       }
    }

