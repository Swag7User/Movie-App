package com.UZH.MovieApp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.UZH.MovieApp.shared.Movie;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.team10.agrardataviewer.shared.DataRow;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.widgetideas.client.SliderBar;



public class WorldMap extends Composite{
	private DataTable dataTable;
	private GeoMap.Options options;
	private GeoMap geomap;
	private ScrollPanel scrollPanel = new ScrollPanel();
	private SliderBar slider;
	protected String year;
	
	public WorldMap(){
		
		}
		
	
	
	public Widget printMap(final StringBuilder strQuerry, final int year){
		Runnable onLoadCallback = new Runnable(){
			
			
			@Override
			public void run() {
				dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Number of movies");
				
				String query = strQuerry.substring(8, strQuerry.length()-1);
				DBConnectionAsync conn = GWT.create(DBConnection.class);
				// TODO leere query
				String finalQuery = "";
				if(strQuerry.length()<33){
					finalQuery = "SELECT countries, Count(*) FROM movieapp.moviedata Group By countries";
				}
				else{
					finalQuery = "SELECT countries, Count(*) "+ query +"AND year="+ Integer.toString(year) +" Group By countries";
				}
				conn.getDBDataHash(finalQuery, new AsyncCallback<HashMap<String, Integer>>(){
					
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
					}

					@Override
					public void onSuccess(HashMap<String, Integer> result) {
						// TODO Auto-generated method stub
						dataTable.addRows(result.size());
						int i=0;
						for (Entry<String, Integer> entry : result.entrySet()) {
							//System.out.println(Integer.toString(entry.getValue()));
							dataTable.setValue(i, 0, entry.getKey());
							dataTable.setValue(i, 1, entry.getValue());
							i++;
						}
						
						options = GeoMap.Options.create();
						options.setDataMode(GeoMap.DataMode.REGIONS);
						options.setRegion("world");
						geomap = new GeoMap(dataTable, options);
						scrollPanel.add(geomap);
						System.out.println("dataTable finished");
					}
				});
			}
			
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
		return scrollPanel;
	}
}
