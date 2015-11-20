package com.UZH.MovieApp.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.team10.agrardataviewer.shared.DataRow;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.Visualisation;

public class WorldMap extends com.google.visualization.Visualization;{
	private DataTable dataTable;
	private GeoMap.Options options;
	private GeoMap geomap;
	private ScrollPanel scrollPanel = new ScrollPanel();
	
	public WorldMap(){
		
	}
	
	public Widget printMap(){
		/*Runnable onLoadCallback = new Runnable(){

			@Override
			public void run() {
				dataTable = DataTable.create();
				dataTable.addColumn(ColumnType.STRING, "Country");
				dataTable.addColumn(ColumnType.NUMBER, "Value");
				dataTable.addRows(3);
				dataTable.setValue(0, 0, "Italy");
				dataTable.setValue(0, 1, 1000);
				options = GeoMap.Options.create();
				options.setDataMode(GeoMap.DataMode.REGIONS);
				options.setRegion("world");
				geomap = new GeoMap(dataTable, options);
				scrollPanel.add(geomap);
			}
			
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);*/
		dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Value");
		dataTable.addRows(3);
		dataTable.setValue(0, 0, "Italy");
		dataTable.setValue(0, 1, 1000);
		options = GeoMap.Options.create();
		options.setDataMode(GeoMap.DataMode.REGIONS);
		options.setRegion("world");
		geomap = new GeoMap(dataTable, options);
		scrollPanel.add(geomap);
		return scrollPanel;
	}
}
