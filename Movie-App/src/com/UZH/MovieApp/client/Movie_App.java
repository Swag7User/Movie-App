package com.UZH.MovieApp.client;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.persistence.criteria.Root;

import com.UZH.MovieApp.shared.FieldVerifier;
import com.UZH.MovieApp.shared.Movie;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.asm.commons.AdviceAdapter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.widgetideas.client.SliderBar;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Movie_App implements EntryPoint {

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final DBConnectionAsync dbconnection = GWT.create(DBConnection.class);
	SliderBar slider;
	VerticalPanel verticalPanelSlider;
	VerticalPanel verticalPanel;
	VerticalPanel vPanel;
	VerticalPanel dialogVPanel;
	HorizontalPanel exportButtonPanel;
	WorldMap map;
	String widthSlider="1500px";
	int wikiIdFieldCheck = -1;
	int freebaseIdFieldCheck = -1;
	int movieNameFieldCheck = -1;
	int releaseDateFieldCheck = -1;
	int boxofficeFieldCheck = -1;
	int runtimeFieldCheck = -1;
	int languageFieldCheck = -1;
	int countryFieldCheck = -1;
	int genreFieldCheck = -1;
	int limitFieldCheck = -1;
	// AND OR Checkboxes
	int wikiCheck = 0;
	int freebaseCheck = 0;
	int nameCheck = 0;
	int releasedateCheck = 0;
	int boxofficeCheck = 0;
	int runtimeCheck = 0;
	int languagesCheck = 0;
	int countryCheck = 0;

	StringBuilder strQuerry = new StringBuilder("select * from movieapp.moviedata ");

	public void clearStringquerry() {
		strQuerry = new StringBuilder("select * from movieapp.moviedata ");
	}

	/**
	 * This is the entry point method.
	 */
	public void slider(){
				verticalPanelSlider = new VerticalPanel();
				verticalPanelSlider.clear();
				//verticalPanelSlider.setBorderWidth(1);
			

				slider = new SliderBar(1886,2016);
				slider.setStepSize(1);
				slider.setCurrentValue(1950.0);
				slider.setNumTicks(130);
				slider.setNumLabels(26);
				verticalPanelSlider.add(slider);
				RootPanel.get().add(verticalPanelSlider);
				slider.setVisible(true);
				slider.setHeight("50px");
				slider.setWidth(widthSlider);
	}
	public void buttonExport(){
		exportButtonPanel=new HorizontalPanel();
		Button exportButton = new Button("Export", new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("https://www.youtube.com/watch?v=wZZ7oFKsKzY", "_blank", "");
				//FIGURE OUT HOW TO DOWNLOAD STAFF
			}
		});
		exportButton.setPixelSize(50, 30);
		exportButtonPanel.add(exportButton);
		RootPanel.get("exportButtonContainer").add(exportButtonPanel);
	}
	public void onModuleLoad() {
		
		
		//world map
		map = new WorldMap();
		//map.setWidth(widthMapSlider);
		verticalPanel = new VerticalPanel();
		verticalPanel.setWidth("30%");
		verticalPanel.add(map.printMap());
		RootPanel.get().add(verticalPanel);
		//verticalPanelSlider.setWidth("1500px");
		//verticalPanelSlider.setHeight("10px");
		// create text boxes
		//SLIDER
		//slider();
		buttonExport();
		//SLIDER END
		
		
		final Button sendButton2 = new Button("Go!");
		//sendButton2.setSize("40Px", "20Px");
		final Button masterSendButton = new Button("Go!");
		final TextBox wikiIdField = new TextBox();
		wikiIdField.setEnabled(false);
		final TextBox freebaseIdField = new TextBox();
		freebaseIdField.setEnabled(false);
		final TextBox movieNameField = new TextBox();
		movieNameField.setEnabled(false);
		final TextBox releaseDateField = new TextBox();
		releaseDateField.setEnabled(false);
		final TextBox boxofficeField = new TextBox();
		boxofficeField.setEnabled(false);
		final TextBox runtimeField = new TextBox();
		runtimeField.setEnabled(false);
		final TextBox languageField = new TextBox();
		languageField.setEnabled(false);
		final TextBox countryField = new TextBox();
		countryField.setEnabled(false);
		final TextBox genreField = new TextBox();
		genreField.setEnabled(false);
		final TextBox limitField = new TextBox();
		limitField.setEnabled(false);
		limitField.setSize("145Px", "20Px");
		final TextBox masterField = new TextBox();
		masterField.setSize("145Px", "20Px");
		final Label errorLabel = new Label();
		// Set standard Text inside textboxes
		wikiIdField.setText("WikiID");
		freebaseIdField.setText("FreebaseID");
		movieNameField.setText("Name");
		releaseDateField.setText("Releasedate YYYY-MM-DD");
		boxofficeField.setText("Boxoffice");
		runtimeField.setText("Runtime");
		languageField.setText("Languages");
		countryField.setText("Countries");
		genreField.setText("Genres");
		limitField.setText("Limit");
		masterField.setText("SQL Querry");

		// Make new check boxes

		// ############### AND ORS #################
		// ############### wiki AND OR #################
		final CheckBox wikiAND = new CheckBox("&");
		final CheckBox wikiOR = new CheckBox("||");
		wikiAND.setEnabled(false);
		wikiOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		wikiAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					wikiCheck = 0;
					wikiOR.setValue(false);
				} else {
					wikiCheck = 0;
				}
			}
		});
		wikiOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					wikiCheck = 1;
					wikiAND.setValue(false);
				} else {
					wikiCheck = 0;
				}
			}
		});
		// ############### freebase AND OR #################
		final CheckBox freebaseAND = new CheckBox("&");
		final CheckBox freebaseOR = new CheckBox("||");
		freebaseAND.setEnabled(false);
		freebaseOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		freebaseAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					freebaseCheck = 0;
					freebaseOR.setValue(false);
				} else {
					freebaseCheck = 0;
				}
			}
		});
		freebaseOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					freebaseCheck = 1;
					freebaseAND.setValue(false);
				} else {
					freebaseCheck = 0;
				}
			}
		});
		// ############### name AND OR #################
		final CheckBox nameAND = new CheckBox("&");
		final CheckBox nameOR = new CheckBox("||");
		nameAND.setEnabled(false);
		nameOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		nameAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					nameCheck = 0;
					nameOR.setValue(false);
				} else {
					nameCheck = 0;
				}
			}
		});
		nameOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					nameCheck = 1;
					nameAND.setValue(false);
				} else {
					nameCheck = 0;
				}
			}
		});
		// ############### releasedate AND OR #################
		final CheckBox releasedateAND = new CheckBox("&");
		final CheckBox releasedateOR = new CheckBox("||");
		releasedateAND.setEnabled(false);
		releasedateOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		releasedateAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					releasedateCheck = 0;
					releasedateOR.setValue(false);
				} else {
					releasedateCheck = 0;
				}
			}
		});
		releasedateOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					releasedateCheck = 1;
					releasedateAND.setValue(false);
				} else {
					releasedateCheck = 0;
				}
			}
		});
		// ############### boxoffice AND OR #################
		final CheckBox boxofficeAND = new CheckBox("&");
		final CheckBox boxofficeOR = new CheckBox("||");
		boxofficeAND.setEnabled(false);
		boxofficeOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		boxofficeAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					boxofficeCheck = 0;
					boxofficeOR.setValue(false);
				} else {
					boxofficeCheck = 0;
				}
			}
		});
		boxofficeOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					boxofficeCheck = 1;
					boxofficeAND.setValue(false);
				} else {
					boxofficeCheck = 0;
				}
			}
		});
		// ############### runtime AND OR #################
		final CheckBox runtimeAND = new CheckBox("&");
		final CheckBox runtimeOR = new CheckBox("||");
		runtimeAND.setEnabled(false);
		runtimeOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		runtimeAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					runtimeCheck = 0;
					runtimeOR.setValue(false);
				} else {
					runtimeCheck = 0;
				}
			}
		});
		runtimeOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					runtimeCheck = 1;
					runtimeAND.setValue(false);
				} else {
					runtimeCheck = 0;
				}
			}
		});
		// ############### languages AND OR #################
		final CheckBox languagesAND = new CheckBox("&");
		final CheckBox languagesOR = new CheckBox("||");
		languagesAND.setEnabled(false);
		languagesOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		languagesAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					languagesCheck = 0;
					languagesOR.setValue(false);
				} else {
					languagesCheck = 0;
				}
			}
		});
		languagesOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					languagesCheck = 1;
					languagesAND.setValue(false);
				} else {
					languagesCheck = 0;
				}
			}
		});
		// ############### country AND OR #################
		final CheckBox countryAND = new CheckBox("&");
		final CheckBox countryOR = new CheckBox("||");
		countryAND.setEnabled(false);
		countryOR.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		countryAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					countryCheck = 0;
					countryOR.setValue(false);
				} else {
					countryCheck = 0;
				}
			}
		});
		countryOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					countryCheck = 1;
					countryAND.setValue(false);
				} else {
					countryCheck = 0;
				}
			}
		});

		// ########### Field Checkboxes ###############
		// ###############Wikipedia ID#################
		final CheckBox wikiIdFieldEQUAL = new CheckBox("=");
		final CheckBox wikiIdFieldBIGGERTHAN = new CheckBox(">");
		final CheckBox wikiIdFieldSMALLERTHAN = new CheckBox("<");
		// Hook up a handler to find out when they're clicked clicked.
		wikiIdFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					wikiIdFieldCheck = 2;
					wikiIdFieldBIGGERTHAN.setValue(false);
					wikiIdFieldSMALLERTHAN.setValue(false);
					wikiIdField.setEnabled(true);
				} else {
					wikiIdFieldCheck = -1;
					wikiIdField.setEnabled(false);
				}
			}
		});
		wikiIdFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					wikiIdFieldCheck = 3;
					wikiIdFieldEQUAL.setValue(false);
					wikiIdFieldSMALLERTHAN.setValue(false);
					wikiIdField.setEnabled(true);
				} else {
					wikiIdFieldCheck = -1;
					wikiIdField.setEnabled(false);
				}
			}
		});
		wikiIdFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					wikiIdFieldCheck = 4;
					wikiIdFieldBIGGERTHAN.setValue(false);
					wikiIdFieldEQUAL.setValue(false);
					wikiIdField.setEnabled(true);
				} else {
					wikiIdFieldCheck = -1;
					wikiIdField.setEnabled(false);
				}
			}
		});
		// ###############freebase ID#################
		final CheckBox freebaseIdFieldEQUAL = new CheckBox("=");
		final CheckBox freebaseIdFieldBIGGERTHAN = new CheckBox(">");
		freebaseIdFieldBIGGERTHAN.setEnabled(false);
		final CheckBox freebaseIdFieldSMALLERTHAN = new CheckBox("<");
		freebaseIdFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		freebaseIdFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					freebaseIdFieldCheck = 2;
					freebaseIdFieldBIGGERTHAN.setValue(false);
					freebaseIdFieldSMALLERTHAN.setValue(false);
					freebaseIdField.setEnabled(true);
					wikiAND.setEnabled(true);
					wikiOR.setEnabled(true);
				} else {
					freebaseIdFieldCheck = -1;
					freebaseIdField.setEnabled(false);
					wikiAND.setEnabled(false);
					wikiOR.setEnabled(false);
				}
			}
		});
		freebaseIdFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					freebaseIdFieldCheck = 3;
					freebaseIdFieldEQUAL.setValue(false);
					freebaseIdFieldSMALLERTHAN.setValue(false);
					freebaseIdField.setEnabled(true);
					wikiAND.setEnabled(true);
					wikiOR.setEnabled(true);
				} else {
					freebaseIdFieldCheck = -1;
					freebaseIdField.setEnabled(false);
					wikiAND.setEnabled(false);
					wikiOR.setEnabled(false);
				}
			}
		});
		freebaseIdFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					freebaseIdFieldCheck = 4;
					freebaseIdFieldBIGGERTHAN.setValue(false);
					freebaseIdFieldEQUAL.setValue(false);
					freebaseIdField.setEnabled(true);
					wikiAND.setEnabled(true);
					wikiOR.setEnabled(true);
				} else {
					freebaseIdFieldCheck = -1;
					freebaseIdField.setEnabled(false);
					wikiAND.setEnabled(false);
					wikiOR.setEnabled(false);
				}
			}
		});
		// ###############Movie Name#################
		final CheckBox movieNameFieldEQUAL = new CheckBox("=");
		final CheckBox movieNameFieldBIGGERTHAN = new CheckBox(">");
		movieNameFieldBIGGERTHAN.setEnabled(false);
		final CheckBox movieNameFieldSMALLERTHAN = new CheckBox("<");
		movieNameFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		movieNameFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					movieNameFieldCheck = 2;
					movieNameFieldBIGGERTHAN.setValue(false);
					movieNameFieldSMALLERTHAN.setValue(false);
					movieNameField.setEnabled(true);
					freebaseAND.setEnabled(true);
					freebaseOR.setEnabled(true);

				} else {
					movieNameFieldCheck = -1;
					movieNameField.setEnabled(false);
					freebaseAND.setEnabled(false);
					freebaseOR.setEnabled(false);
				}
			}
		});
		movieNameFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					movieNameFieldCheck = 3;
					movieNameFieldEQUAL.setValue(false);
					movieNameFieldSMALLERTHAN.setValue(false);
					movieNameField.setEnabled(true);
					freebaseAND.setEnabled(true);
					freebaseOR.setEnabled(true);
				} else {
					movieNameFieldCheck = -1;
					movieNameField.setEnabled(false);
					freebaseAND.setEnabled(false);
					freebaseOR.setEnabled(false);
				}
			}
		});
		movieNameFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					movieNameFieldCheck = 4;
					movieNameFieldBIGGERTHAN.setValue(false);
					movieNameFieldEQUAL.setValue(false);
					movieNameField.setEnabled(true);
					freebaseAND.setEnabled(true);
					freebaseOR.setEnabled(true);
				} else {
					movieNameFieldCheck = -1;
					movieNameField.setEnabled(false);
					freebaseAND.setEnabled(false);
					freebaseOR.setEnabled(false);
				}
			}
		});
		// ###############Release Date#################
		final CheckBox releaseDateFieldEQUAL = new CheckBox("=");
		final CheckBox releaseDateFieldBIGGERTHAN = new CheckBox(">");
		// releaseDateFieldBIGGERTHAN.setEnabled(false);
		final CheckBox releaseDateFieldSMALLERTHAN = new CheckBox("<");
		// releaseDateFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		releaseDateFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					releaseDateFieldCheck = 2;
					releaseDateFieldBIGGERTHAN.setValue(false);
					releaseDateFieldSMALLERTHAN.setValue(false);
					releaseDateField.setEnabled(true);
					nameAND.setEnabled(true);
					nameOR.setEnabled(true);
				} else {
					releaseDateFieldCheck = -1;
					releaseDateField.setEnabled(false);
					nameAND.setEnabled(false);
					nameOR.setEnabled(false);
				}
			}
		});
		releaseDateFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					releaseDateFieldCheck = 3;
					releaseDateFieldEQUAL.setValue(false);
					releaseDateFieldSMALLERTHAN.setValue(false);
					releaseDateField.setEnabled(true);
					nameAND.setEnabled(true);
					nameOR.setEnabled(true);
				} else {
					releaseDateFieldCheck = -1;
					releaseDateField.setEnabled(false);
					nameAND.setEnabled(false);
					nameOR.setEnabled(false);
				}
			}
		});
		releaseDateFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					releaseDateFieldCheck = 4;
					releaseDateFieldBIGGERTHAN.setValue(false);
					releaseDateFieldEQUAL.setValue(false);
					releaseDateField.setEnabled(true);
					nameAND.setEnabled(true);
					nameOR.setEnabled(true);
				} else {
					releaseDateFieldCheck = -1;
					releaseDateField.setEnabled(false);
					nameAND.setEnabled(false);
					nameOR.setEnabled(false);
				}
			}
		});
		// ###############Boxoffice#################
		final CheckBox boxofficeFieldEQUAL = new CheckBox("=");
		final CheckBox boxofficeFieldBIGGERTHAN = new CheckBox(">");
		// boxofficeFieldBIGGERTHAN.setEnabled(false);
		final CheckBox boxofficeFieldSMALLERTHAN = new CheckBox("<");
		// boxofficeFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		boxofficeFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					boxofficeFieldCheck = 2;
					boxofficeFieldBIGGERTHAN.setValue(false);
					boxofficeFieldSMALLERTHAN.setValue(false);
					boxofficeField.setEnabled(true);
					releasedateAND.setEnabled(true);
					releasedateOR.setEnabled(true);
				} else {
					boxofficeFieldCheck = -1;
					boxofficeField.setEnabled(false);
					releasedateAND.setEnabled(false);
					releasedateOR.setEnabled(false);
				}
			}
		});
		boxofficeFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					boxofficeFieldCheck = 3;
					boxofficeFieldEQUAL.setValue(false);
					boxofficeFieldSMALLERTHAN.setValue(false);
					boxofficeField.setEnabled(true);
					releasedateAND.setEnabled(true);
					releasedateOR.setEnabled(true);
				} else {
					boxofficeFieldCheck = -1;
					boxofficeField.setEnabled(false);
					releasedateAND.setEnabled(false);
					releasedateOR.setEnabled(false);
				}
			}
		});
		boxofficeFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					boxofficeFieldCheck = 4;
					boxofficeFieldBIGGERTHAN.setValue(false);
					boxofficeFieldEQUAL.setValue(false);
					boxofficeField.setEnabled(true);
					releasedateAND.setEnabled(true);
					releasedateOR.setEnabled(true);
				} else {
					boxofficeFieldCheck = -1;
					boxofficeField.setEnabled(false);
					releasedateAND.setEnabled(false);
					releasedateOR.setEnabled(false);
				}
			}
		});
		// ###############Runtime#################
		final CheckBox runtimeFieldEQUAL = new CheckBox("=");
		final CheckBox runtimeFieldBIGGERTHAN = new CheckBox(">");
		// runtimeFieldBIGGERTHAN.setEnabled(false);
		final CheckBox runtimeFieldSMALLERTHAN = new CheckBox("<");
		// runtimeFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		runtimeFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					runtimeFieldCheck = 2;
					runtimeFieldBIGGERTHAN.setValue(false);
					runtimeFieldSMALLERTHAN.setValue(false);
					runtimeField.setEnabled(true);
					boxofficeAND.setEnabled(true);
					boxofficeOR.setEnabled(true);
				} else {
					runtimeFieldCheck = -1;
					runtimeField.setEnabled(false);
					boxofficeAND.setEnabled(false);
					boxofficeOR.setEnabled(false);
				}
			}
		});
		runtimeFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					runtimeFieldCheck = 3;
					runtimeFieldEQUAL.setValue(false);
					runtimeFieldSMALLERTHAN.setValue(false);
					runtimeField.setEnabled(true);
					boxofficeAND.setEnabled(true);
					boxofficeOR.setEnabled(true);
				} else {
					runtimeFieldCheck = -1;
					runtimeField.setEnabled(false);
					boxofficeAND.setEnabled(false);
					boxofficeOR.setEnabled(false);
				}
			}
		});
		runtimeFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					runtimeFieldCheck = 4;
					runtimeFieldBIGGERTHAN.setValue(false);
					runtimeFieldEQUAL.setValue(false);
					runtimeField.setEnabled(true);
					boxofficeAND.setEnabled(true);
					boxofficeOR.setEnabled(true);
				} else {
					runtimeFieldCheck = -1;
					runtimeField.setEnabled(false);
					boxofficeAND.setEnabled(false);
					boxofficeOR.setEnabled(false);
				}
			}
		});
		// ###############language#################
		final CheckBox languageFieldEQUAL = new CheckBox("=");
		final CheckBox languageFieldBIGGERTHAN = new CheckBox(">");
		languageFieldBIGGERTHAN.setEnabled(false);
		final CheckBox languageFieldSMALLERTHAN = new CheckBox("<");
		languageFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		languageFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					languageFieldCheck = 2;
					languageFieldBIGGERTHAN.setValue(false);
					languageFieldSMALLERTHAN.setValue(false);
					languageField.setEnabled(true);
					runtimeAND.setEnabled(true);
					runtimeOR.setEnabled(true);
				} else {
					languageFieldCheck = -1;
					languageField.setEnabled(false);
					runtimeAND.setEnabled(false);
					runtimeOR.setEnabled(false);
				}
			}
		});
		languageFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					languageFieldCheck = 3;
					languageFieldEQUAL.setValue(false);
					languageFieldSMALLERTHAN.setValue(false);
					languageField.setEnabled(true);
					runtimeAND.setEnabled(true);
					runtimeOR.setEnabled(true);
				} else {
					languageFieldCheck = -1;
					languageField.setEnabled(false);
					runtimeAND.setEnabled(false);
					runtimeOR.setEnabled(false);
				}
			}
		});
		languageFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					languageFieldCheck = 4;
					languageFieldBIGGERTHAN.setValue(false);
					languageFieldEQUAL.setValue(false);
					languageField.setEnabled(true);
					runtimeAND.setEnabled(true);
					runtimeOR.setEnabled(true);
				} else {
					languageFieldCheck = -1;
					languageField.setEnabled(false);
					runtimeAND.setEnabled(false);
					runtimeOR.setEnabled(false);
				}
			}
		});
		// ###############country#################
		final CheckBox countryFieldEQUAL = new CheckBox("=");
		final CheckBox countryFieldBIGGERTHAN = new CheckBox(">");
		countryFieldBIGGERTHAN.setEnabled(false);
		final CheckBox countryFieldSMALLERTHAN = new CheckBox("<");
		countryFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		countryFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					countryFieldCheck = 2;
					countryFieldBIGGERTHAN.setValue(false);
					countryFieldSMALLERTHAN.setValue(false);
					countryField.setEnabled(true);
					languagesAND.setEnabled(true);
					languagesOR.setEnabled(true);
				} else {
					countryFieldCheck = -1;
					countryField.setEnabled(false);
					languagesAND.setEnabled(false);
					languagesOR.setEnabled(false);
				}
			}
		});
		countryFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					countryFieldCheck = 3;
					countryFieldEQUAL.setValue(false);
					countryFieldSMALLERTHAN.setValue(false);
					countryField.setEnabled(true);
					languagesAND.setEnabled(true);
					languagesOR.setEnabled(true);
				} else {
					countryFieldCheck = -1;
					countryField.setEnabled(false);
					languagesAND.setEnabled(false);
					languagesOR.setEnabled(false);
				}
			}
		});
		countryFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					countryFieldCheck = 4;
					countryFieldBIGGERTHAN.setValue(false);
					countryFieldEQUAL.setValue(false);
					countryField.setEnabled(true);
					languagesAND.setEnabled(true);
					languagesOR.setEnabled(true);
				} else {
					countryFieldCheck = -1;
					countryField.setEnabled(false);
					languagesAND.setEnabled(false);
					languagesOR.setEnabled(false);
				}
			}
		});
		// ###############genre#################
		final CheckBox genreFieldEQUAL = new CheckBox("=");
		final CheckBox genreFieldBIGGERTHAN = new CheckBox(">");
		genreFieldBIGGERTHAN.setEnabled(false);
		final CheckBox genreFieldSMALLERTHAN = new CheckBox("<");
		genreFieldSMALLERTHAN.setEnabled(false);
		// Hook up a handler to find out when they're clicked clicked.
		genreFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					genreFieldCheck = 2;
					genreFieldBIGGERTHAN.setValue(false);
					genreFieldSMALLERTHAN.setValue(false);
					genreField.setEnabled(true);
					countryAND.setEnabled(true);
					countryOR.setEnabled(true);
				} else {
					genreFieldCheck = -1;
					genreField.setEnabled(false);
					countryAND.setEnabled(false);
					countryOR.setEnabled(false);
				}
			}
		});
		genreFieldBIGGERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					genreFieldCheck = 3;
					genreFieldEQUAL.setValue(false);
					genreFieldSMALLERTHAN.setValue(false);
					genreField.setEnabled(true);
					countryAND.setEnabled(true);
					countryOR.setEnabled(true);
				} else {
					genreFieldCheck = -1;
					genreField.setEnabled(false);
					countryAND.setEnabled(false);
					countryOR.setEnabled(false);
				}
			}
		});
		genreFieldSMALLERTHAN.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					genreFieldCheck = 4;
					genreFieldBIGGERTHAN.setValue(false);
					genreFieldEQUAL.setValue(false);
					genreField.setEnabled(true);
					countryAND.setEnabled(true);
					countryOR.setEnabled(true);
				} else {
					genreFieldCheck = -1;
					genreField.setEnabled(false);
					countryAND.setEnabled(false);
					countryOR.setEnabled(false);
				}
			}
		});
		// ###############limit#################
		final CheckBox limitFieldEQUAL = new CheckBox("=");
		// Hook up a handler to find out when they're clicked clicked.
		limitFieldEQUAL.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					limitFieldCheck = 2;
					limitField.setEnabled(true);
				} else {
					limitFieldCheck = -1;
					limitField.setEnabled(false);
				}
			}
		});
		
		// ############ table ############
		
		// Create a CellTable.
		CellTable<Movie> movieTable = new CellTable<Movie>();

		// Create wikiid column.
		TextColumn<Movie> wikiidColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.wikiid;
			}
		};
		// Create freebaseid column.
		TextColumn<Movie> freebaseidColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.freebaseid;
			}
		};
		// Create name column.
		TextColumn<Movie> nameColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.name;
			}
		};
		// Create releasedate column.
		TextColumn<Movie> releasedateColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.releasedate;
			}
		};
		// Create boxoffice column.
		TextColumn<Movie> boxofficeColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.boxoffice;
			}
		};
		// Create runtime column.
		TextColumn<Movie> runtimeColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.runtime;
			}
		};
		// Create languages column.
		TextColumn<Movie> languagesColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.languages;
			}
		};
		// Create countries column.
		TextColumn<Movie> countriesColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.countries;
			}
		};
		// Create genres column.
		TextColumn<Movie> genresColumn = new TextColumn<Movie>() {
			@Override
			public String getValue(Movie movie) {
				return movie.genres;
			}
		};
		nameColumn.setSortable(true);
		wikiidColumn.setSortable(true);
		/*
		 * releasedateColumn.setSortable(true);
		 * boxofficeColumn.setSortable(true);
		 * runtimeColumn.setSortable(true);
		 */
		// Add the columns.
		movieTable.addColumn(wikiidColumn, "Wiki ID");
		movieTable.addColumn(freebaseidColumn, "Freebase ID");
		movieTable.addColumn(nameColumn, "Name");
		movieTable.addColumn(releasedateColumn, "Release Date");
		movieTable.addColumn(boxofficeColumn, "Boxoffice");
		movieTable.addColumn(runtimeColumn, "Runtime");
		movieTable.addColumn(languagesColumn, "Languages");
		movieTable.addColumn(countriesColumn, "Countries");
		movieTable.addColumn(genresColumn, "Genres");

		// Create a data provider.
		ListDataProvider<Movie> dataProvider = new ListDataProvider<>();

		// Connect the table to the data provider.

		dataProvider.addDataDisplay(movieTable);

		// Add the data to the data provider, which
		// automatically pushes it to the
		// widget.
		final List<Movie> list = dataProvider.getList();


		SimplePager pager = new SimplePager();
		pager.setDisplay(movieTable);
		vPanel = new VerticalPanel();
		vPanel.add(pager);
		vPanel.add(movieTable);

		// Add a ColumnSortEvent.ListHandler to connect
		// sorting to the
		// java.util.List.
		ListHandler<Movie> columnSortHandler = new ListHandler<Movie>(list);
		// name sorting
		columnSortHandler.setComparator(nameColumn, new Comparator<Movie>() {
			public int compare(Movie o1, Movie o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? o1.name.compareTo(o2.name) : 1;
				}
				return -1;
			}
		});
		// wiki id sorting
		columnSortHandler.setComparator(wikiidColumn, new Comparator<Movie>() {
			public int compare(Movie o1, Movie o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the wikiid columns.
				if (o1 != null) {
					return (o2 != null)
							? Integer.valueOf(o1.wikiid).compareTo(Integer.valueOf(o2.wikiid)) : 1;
				}
				return -1;
			}
		});
		// boxoffice sorting
		columnSortHandler.setComparator(boxofficeColumn, new Comparator<Movie>() {
			public int compare(Movie o1, Movie o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null)
							? Integer.valueOf(o1.boxoffice).compareTo(Integer.valueOf(o2.boxoffice))
							: 1;
				}
				return -1;
			}
		});
		// runtime sorting
		columnSortHandler.setComparator(runtimeColumn, new Comparator<Movie>() {
			public int compare(Movie o1, Movie o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null)
							? Integer.valueOf(o1.runtime).compareTo(Integer.valueOf(o2.runtime))
							: 1;
				}
				return -1;
			}
		});
		// releasedate sorting
		columnSortHandler.setComparator(releasedateColumn, new Comparator<Movie>() {
			public int compare(Movie o1, Movie o2) {
				if (o1 == o2) {
					return 0;
				}

				// Compare the name columns.
				if (o1 != null) {
					return (o2 != null) ? Integer.valueOf(o1.releasedate)
							.compareTo(Integer.valueOf(o2.releasedate)) : 1;
				}
				return -1;
			}
		});
		movieTable.addColumnSortHandler(columnSortHandler);

		// We know that the data is sorted alphabetically by
		// default.
		movieTable.getColumnSortList().push(nameColumn);

		RootPanel.get().add(vPanel);

		
		RootPanel.get().addStyleName("body");
		
		
		
		// We can add style names to widgets
		sendButton2.addStyleName("sendButton2");
		
		masterSendButton.addStyleName("masterSendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer2").add(wikiIdField);
		RootPanel.get("sendButtonContainer2").add(sendButton2);
		RootPanel.get("masterSendButton").add(masterSendButton);
		RootPanel.get("freebaseIdFieldContainer").add(freebaseIdField);
		RootPanel.get("movieNameFieldContainer").add(movieNameField);
		RootPanel.get("releaseDateFieldContainer").add(releaseDateField);
		RootPanel.get("boxofficeFieldContainer").add(boxofficeField);
		RootPanel.get("runtimeFieldContainer").add(runtimeField);
		RootPanel.get("languageFieldContainer").add(languageField);
		RootPanel.get("countryFieldContainer").add(countryField);
		RootPanel.get("genreFieldContainer").add(genreField);
		RootPanel.get("limitFieldContainer").add(limitField);
		RootPanel.get("masterFieldContainer").add(masterField);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		// get checkboxes
		RootPanel.get("wikiIdFieldEQUALContainer").add(wikiIdFieldEQUAL);
		RootPanel.get("wikiIdFieldBIGGERTHANContainer").add(wikiIdFieldBIGGERTHAN);
		RootPanel.get("wikiIdFieldSMALLERTHANContainer").add(wikiIdFieldSMALLERTHAN);
		RootPanel.get("freebaseIdFieldEQUALContainer").add(freebaseIdFieldEQUAL);
		RootPanel.get("freebaseIdFieldBIGGERTHANContainer").add(freebaseIdFieldBIGGERTHAN);
		RootPanel.get("freebaseIdFieldSMALLERTHANContainer").add(freebaseIdFieldSMALLERTHAN);
		RootPanel.get("movieNameFieldEQUALContainer").add(movieNameFieldEQUAL);
		RootPanel.get("movieNameFieldBIGGERTHANContainer").add(movieNameFieldBIGGERTHAN);
		RootPanel.get("movieNameFieldSMALLERTHANContainer").add(movieNameFieldSMALLERTHAN);
		RootPanel.get("releaseDateFieldEQUALContainer").add(releaseDateFieldEQUAL);
		RootPanel.get("releaseDateFieldBIGGERTHANContainer").add(releaseDateFieldBIGGERTHAN);
		RootPanel.get("releaseDateFieldSMALLERTHANContainer").add(releaseDateFieldSMALLERTHAN);
		RootPanel.get("boxofficeFieldEQUALContainer").add(boxofficeFieldEQUAL);
		RootPanel.get("boxofficeFieldBIGGERTHANContainer").add(boxofficeFieldBIGGERTHAN);
		RootPanel.get("boxofficeFieldSMALLERTHANContainer").add(boxofficeFieldSMALLERTHAN);
		RootPanel.get("runtimeFieldEQUALContainer").add(runtimeFieldEQUAL);
		RootPanel.get("runtimeFieldBIGGERTHANContainer").add(runtimeFieldBIGGERTHAN);
		RootPanel.get("runtimeFieldSMALLERTHANContainer").add(runtimeFieldSMALLERTHAN);
		RootPanel.get("languageFieldEQUALContainer").add(languageFieldEQUAL);
		RootPanel.get("languageFieldBIGGERTHANContainer").add(languageFieldBIGGERTHAN);
		RootPanel.get("languageFieldSMALLERTHANContainer").add(languageFieldSMALLERTHAN);
		RootPanel.get("countryFieldEQUALContainer").add(countryFieldEQUAL);
		RootPanel.get("countryFieldBIGGERTHANContainer").add(countryFieldBIGGERTHAN);
		RootPanel.get("countryFieldSMALLERTHANContainer").add(countryFieldSMALLERTHAN);
		RootPanel.get("genreFieldEQUALContainer").add(genreFieldEQUAL);
		RootPanel.get("genreFieldBIGGERTHANContainer").add(genreFieldBIGGERTHAN);
		RootPanel.get("genreFieldSMALLERTHANContainer").add(genreFieldSMALLERTHAN);
		RootPanel.get("limitFieldEQUALContainer").add(limitFieldEQUAL);

		// AND OR CHECKBOXES
		RootPanel.get("wikiAND").add(wikiAND);
		RootPanel.get("wikiOR").add(wikiOR);
		RootPanel.get("freebaseAND").add(freebaseAND);
		RootPanel.get("freebaseOR").add(freebaseOR);
		RootPanel.get("nameAND").add(nameAND);
		RootPanel.get("nameOR").add(nameOR);
		RootPanel.get("releasedateAND").add(releasedateAND);
		RootPanel.get("releasedateOR").add(releasedateOR);
		RootPanel.get("boxofficeAND").add(boxofficeAND);
		RootPanel.get("boxofficeOR").add(boxofficeOR);
		RootPanel.get("runtimeAND").add(runtimeAND);
		RootPanel.get("runtimeOR").add(runtimeOR);
		RootPanel.get("languagesAND").add(languagesAND);
		RootPanel.get("languagesOR").add(languagesOR);
		RootPanel.get("countryAND").add(countryAND);
		RootPanel.get("countryOR").add(countryOR);

		// Create the popup dialog box for User Greeting Message
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Create the popup dialog box for movie data
		final DialogBox dialogBox2 = new DialogBox();
		dialogBox2.setText("RPC to DB");
		dialogBox2.setAnimationEnabled(false);
		final Button closeButton2 = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton2.getElement().setId("closeButton");
		final Label textToServerLabel2 = new Label();
		final Label textToServerLabel3 = new Label();
		final HTML serverResponseLabel2 = new HTML();
		final HTML serverResponseLabel3 = new HTML();
		VerticalPanel dialogVPanel2 = new VerticalPanel();
		dialogVPanel2.setPixelSize(1500, 400);
		dialogVPanel2.addStyleName("dialogVPanel");
		dialogVPanel2.add(new HTML("<b>Number of Movies:</b>"));
		dialogVPanel2.add(textToServerLabel2);
		dialogVPanel2.add(new HTML("<br><b>Movies from DataBase:</b>"));
		dialogVPanel2.add(serverResponseLabel2);
		dialogVPanel2.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel2.add(closeButton2);
		dialogBox2.setWidget(dialogVPanel2);

		// Add a handler to close the DialogBox movie data
		closeButton2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox2.hide();
				sendButton2.setEnabled(true);
				sendButton2.setFocus(true);
				masterSendButton.setEnabled(true);
			}
		});
		



		// Create a handler for the sendButton and nameField

		class FilteringHandler implements ClickHandler, KeyUpHandler, java.io.Serializable {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textWikiId = wikiIdField.getText();
				String textFreebaseId = freebaseIdField.getText();
				String movieName = movieNameField.getText();
				String releasedate = releaseDateField.getText();
				String boxoffice = boxofficeField.getText();
				String runtime = runtimeField.getText();
				String language = languageField.getText();
				String country = countryField.getText();
				String genre = genreField.getText();
				String limit = limitField.getText();
				if ((!FieldVerifier.isValidID(textWikiId)) && (!FieldVerifier.isValidID(textFreebaseId))) {
					errorLabel.setText("Error");
					return;
				}

				// Then, we send the input to the server.
				sendButton2.setEnabled(false);
				textToServerLabel2.setText(textWikiId);
				// textToServerLabel2.setText(textFreebaseId);
				serverResponseLabel2.setText("");
				String concat = modifyString(textWikiId, textFreebaseId, movieName, releasedate, boxoffice, runtime,
						language, country, genre, limit);
				strQuerry.append(concat);

				dbconnection.getDBData(strQuerry.toString(), new AsyncCallback<ArrayList<Movie>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox2.setText("Remote Procedure Call - Failure");
						serverResponseLabel2.addStyleName("serverResponseLabelError");
						serverResponseLabel2.setHTML(SERVER_ERROR);
						dialogBox2.center();
						closeButton2.setFocus(true);
					}

					public void onSuccess(ArrayList<Movie> result) {
						// dialogBox2.setText("Your querry returned some
						// stuff:");
						// serverResponseLabel2.removeStyleName("serverResponseLabelError");
						try {

							// ############ table ############
							list.clear();
							for (Movie movie : result) {
								list.add(movie);
								// movie.printMovie();
							}
							// serverResponseLabel2.setHTML(test.toString());
						} catch (NullPointerException e) {
							serverResponseLabel2.setHTML("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
						}
						// dialogBox2.center();
						// closeButton2.setFocus(true);
						clearStringquerry();
						sendButton2.setEnabled(true);
					}

				});
			}

			private String modifyString(String textWikiId, String textFreebaseId, String movieName, String releasedate,
					String boxoffice, String runtime, String language, String country, String genre, String limit) {
				StringBuilder querryConcatination = new StringBuilder();

				if (wikiIdFieldCheck == -1) {
					querryConcatination.append("WHERE 1=1 ");
				} else {
					if (wikiIdFieldCheck == 2) {
						querryConcatination.append(" WHERE wikiid = " + textWikiId);
					} else if (wikiIdFieldCheck == 3) {
						querryConcatination.append(" WHERE wikiid > " + textWikiId);
					} else if (wikiIdFieldCheck == 4) {
						querryConcatination.append(" WHERE wikiid < " + textWikiId);
					}
				}
				if (wikiCheck == 0 && freebaseIdFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (wikiCheck == 1 && freebaseIdFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}
				if (freebaseIdFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("freebaseid = '" + textFreebaseId + "'");
				}
				if (freebaseCheck == 0 && movieNameFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (freebaseCheck == 1 && movieNameFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (movieNameFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("name LIKE '" + "%" + movieName + "%" + "'");
				}
				if (nameCheck == 0 && releaseDateFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (nameCheck == 1 && releaseDateFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (releaseDateFieldCheck == -1) {
					// do nothing I guess
				} else {
					if (releaseDateFieldCheck == 2) {
						querryConcatination.append("releasedate = " + "'" + releasedate + "'");
					} else if (releaseDateFieldCheck == 3) {
						querryConcatination.append("releasedate > " + "'" + releasedate + "'");
					} else if (releaseDateFieldCheck == 4) {
						querryConcatination.append("releasedate < " + "'" + releasedate + "'");
					}
				}
				if (releasedateCheck == 0 && boxofficeFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (releasedateCheck == 1 && boxofficeFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (boxofficeFieldCheck == -1) {
					// do nothing I guess
				} else {
					if (boxofficeFieldCheck == 2) {
						querryConcatination.append("boxoffice = " + boxoffice);
					} else if (boxofficeFieldCheck == 3) {
						querryConcatination.append("boxoffice > " + boxoffice);
					} else if (boxofficeFieldCheck == 4) {
						querryConcatination.append("boxoffice < " + boxoffice);
					}
				}
				if (boxofficeCheck == 0 && runtimeFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (boxofficeCheck == 1 && runtimeFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}
				// test
				if (runtimeFieldCheck == -1) {
					// do nothing I guess
				} else {
					if (runtimeFieldCheck == 2) {
						querryConcatination.append("runtime = " + runtime);
					} else if (runtimeFieldCheck == 3) {
						querryConcatination.append("runtime > " + runtime);
					} else if (runtimeFieldCheck == 4) {
						querryConcatination.append("runtime < " + runtime);
					}
				}
				if (runtimeCheck == 0 && languageFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (runtimeCheck == 1 && languageFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (languageFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("languages LIKE '" + language + "'");
				}
				if (languagesCheck == 0 && countryFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (languagesCheck == 1 && countryFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (countryFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("countries LIKE '" + country + "'");
				}
				if (countryCheck == 0 && genreFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (countryCheck == 1 && genreFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (genreFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("genres LIKE '" + "%" + genre + "%" + "'");
				}
				if (limitFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append(" LIMIT " + limit);
				}

				// Add a handler to send the name to the server
				// Window.alert(querryConcatination.toString());
				return querryConcatination.toString();

			}
		}

		class MasterHandler implements ClickHandler, KeyUpHandler, java.io.Serializable {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String masterText = masterField.getText();

				// Then, we send the input to the server.
				masterSendButton.setEnabled(false);
				textToServerLabel3.setText(masterText);
				// textToServerLabel2.setText(textFreebaseId);
				textToServerLabel3.setText("");
				StringBuilder masterQuerry = new StringBuilder("select * from movieapp.moviedata WHERE ");
				masterQuerry.append(masterText);

				dbconnection.getDBData(masterQuerry.toString(), new AsyncCallback<ArrayList<Movie>>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox2.setText("Remote Procedure Call - Failure");
						serverResponseLabel3.addStyleName("serverResponseLabelError");
						serverResponseLabel3.setHTML(SERVER_ERROR);
						dialogBox2.center();
						closeButton2.setFocus(true);
					}

					public void onSuccess(ArrayList<Movie> result) {
						try {

							// ############ table ############
							list.clear();
							for (Movie movie : result) {
								list.add(movie);
								// movie.printMovie();
							}

						} catch (NullPointerException e) {
							serverResponseLabel2.setHTML("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
							;
						}
						// dialogBox2.center();
						// closeButton2.setFocus(true);
						clearStringquerry();
						masterSendButton.setEnabled(true);
					}

				});
			}
		}

		FilteringHandler filteringHandler = new FilteringHandler();
		sendButton2.addClickHandler(filteringHandler);
		
		MasterHandler masterHandler = new MasterHandler();
		masterSendButton.addClickHandler(masterHandler);

	}

}
