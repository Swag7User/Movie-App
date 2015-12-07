package com.UZH.MovieApp.client;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.RegionClickHandler;
import com.google.gwt.visualization.client.visualizations.GeoMap;
// import com.google.gwt.widgetideas.client.SliderBar;


public class WorldMap extends Composite{
	private DataTable dataTable;
	private GeoMap.Options options;
	private GeoMap geomap;
//	private SliderBar slider;
//	private String sliderWidth = "99%";
	private int mapWidth = 725;
	private int mapHeight = 500;
	
	public WorldMap(){
		
	}
	
	public WorldMap(int mapWidth, int mapHeight){
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}
	
	public Widget printMap(final VerticalPanel verticalPanel){
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
						options.setWidth(mapWidth);
						options.setHeight(mapHeight);
						geomap = new GeoMap(dataTable, options);
						
/*						slider = new SliderBar(1886,2016);
						slider.setStepSize(1);
						slider.setCurrentValue(1950.0);
						slider.setNumTicks(130);
						//slider.setNumLabels((int)(slider.getMaxValue() - slider.getMinValue())/sliderWidth);
						slider.setVisible(true);
						slider.setHeight("50px");
						slider.setWidth(sliderWidth);
*/						
						verticalPanel.clear();
						verticalPanel.add(geomap);
			//			verticalPanel.add(slider);
						
						//scrollPanel.setWidth("250%");
						//scrollPanel.add(geomap);
						//scrollPanel.add(slider);
						System.out.println("dataTable finished");
					}
				});
			}
			
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
		return verticalPanel;
	}
	
	public Widget printMap(final String querry, final VerticalPanel verticalPanel){
		Runnable onLoadCallback = new Runnable(){

			@Override
			public void run() {
				
				dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Number of movies");
				
				
				DBConnectionAsync conn = GWT.create(DBConnection.class);
				String strQuerry = querry;
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
						options.setWidth(mapWidth);
						options.setHeight(mapHeight);
						geomap = new GeoMap(dataTable, options);
						
						geomap.addRegionClickHandler(new RegionClickHandler(){

							@Override
							public void onRegionClick(RegionClickEvent event) {
								System.out.println(event.getRegion());
							}
							
						});
						
/*						slider = new SliderBar(1886,2016);
						slider.setStepSize(1);
						slider.setCurrentValue(1950.0);
						slider.setNumTicks(130);
						//slider.setNumLabels((int)(slider.getMaxValue() - slider.getMinValue())/sliderWidth);
						slider.setVisible(true);
						slider.setHeight("50px");
						slider.setWidth(sliderWidth);
*/						
						verticalPanel.clear();
						verticalPanel.add(geomap);
				//		verticalPanel.add(slider);
						
						//scrollPanel.setWidth("250%");
						//scrollPanel.add(geomap);
						//scrollPanel.add(slider);
						System.out.println("dataTable finished");
					}
				});
			}
			
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
		return verticalPanel;
	}
}