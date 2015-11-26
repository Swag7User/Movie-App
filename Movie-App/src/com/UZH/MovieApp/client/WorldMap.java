package com.UZH.MovieApp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.UZH.MovieApp.shared.Movie;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
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
	private int sliderWidth = 715;
	
	public WorldMap(){
		
	}
	
	public Widget printMap(){
		Runnable onLoadCallback = new Runnable(){

			@Override
			public void run() {
				
				dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Number of movies");
				
				
				DBConnectionAsync conn = GWT.create(DBConnection.class);
				String strQuerry = "SELECT countries, Count(*) FROM moviedata Group By countries";
				conn.getDBDataHash(strQuerry, new AsyncCallback<HashMap<String, Integer>>(){
					
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
						options.setWidth(725);
						options.setHeight(500);
						geomap = new GeoMap(dataTable, options);
						
						slider = new SliderBar(1886,2016);
						slider.setStepSize(1);
						slider.setCurrentValue(1950.0);
						slider.setNumTicks(130);
						slider.setNumLabels((int)(slider.getMaxValue() - slider.getMinValue())/sliderWidth);
						slider.setVisible(true);
						slider.setHeight("50px");
						slider.setWidth(Integer.toString(sliderWidth) + "px");
						
						scrollPanel.add(geomap);
						//scrollPanel.add(slider);
						System.out.println("dataTable finished");
					}
				});
			}
			
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
		return scrollPanel;
	}
}
