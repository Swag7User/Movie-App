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
           assertEquals(k, null);
       }
    }

