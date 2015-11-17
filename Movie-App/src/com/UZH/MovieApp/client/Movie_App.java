package com.UZH.MovieApp.client;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import com.UZH.MovieApp.shared.FieldVerifier;
import com.UZH.MovieApp.shared.Movie;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

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
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private final DBConnectionAsync dbconnection = GWT.create(DBConnection.class);

	// =, <, >, Checkboxes
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
	int genreCheck = 0;

	Vector<String> tableString = new Vector<String>();
	StringBuilder strQuerry = new StringBuilder("select * from movieapp.moviedata ");

	public void clearStringquerry() {
		strQuerry = new StringBuilder("select * from movieapp.moviedata ");
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// create text boxes
		final Button sendButton = new Button("Send");
		final Button sendButton2 = new Button("Check DB");
		final TextBox nameField = new TextBox();
		final TextBox wikiIdField = new TextBox();
		final TextBox freebaseIdField = new TextBox();
		final TextBox movieNameField = new TextBox();
		final TextBox releaseDateField = new TextBox();
		final TextBox boxofficeField = new TextBox();
		final TextBox runtimeField = new TextBox();
		final TextBox languageField = new TextBox();
		final TextBox countryField = new TextBox();
		final TextBox genreField = new TextBox();
		final TextBox limitField = new TextBox();
		final Label errorLabel = new Label();
		// Set standard Text inside textboxes
		nameField.setText("Movie Freak");
		wikiIdField.setText("Wiki-ID");
		freebaseIdField.setText("freebase-ID");
		movieNameField.setText("Movie Name");
		releaseDateField.setText("release Date YYYY-MM-DD");
		boxofficeField.setText("Boxoffice");
		runtimeField.setText("run time");
		languageField.setText("Language");
		countryField.setText("Country");
		genreField.setText("Genre");
		limitField.setText("Limit");

		// Make new check boxes
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

				} else {
					wikiIdFieldCheck = -1;
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
				} else {
					wikiIdFieldCheck = -1;
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
				} else {
					wikiIdFieldCheck = -1;
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

				} else {
					freebaseIdFieldCheck = -1;
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
				} else {
					freebaseIdFieldCheck = -1;
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
				} else {
					freebaseIdFieldCheck = -1;
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

				} else {
					movieNameFieldCheck = -1;
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
				} else {
					movieNameFieldCheck = -1;
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
				} else {
					movieNameFieldCheck = -1;
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

				} else {
					releaseDateFieldCheck = -1;
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
				} else {
					releaseDateFieldCheck = -1;
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
				} else {
					releaseDateFieldCheck = -1;
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

				} else {
					boxofficeFieldCheck = -1;
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
				} else {
					boxofficeFieldCheck = -1;
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
				} else {
					boxofficeFieldCheck = -1;
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

				} else {
					runtimeFieldCheck = -1;
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
				} else {
					runtimeFieldCheck = -1;
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
				} else {
					runtimeFieldCheck = -1;
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

				} else {
					languageFieldCheck = -1;
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
				} else {
					languageFieldCheck = -1;
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
				} else {
					languageFieldCheck = -1;
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

				} else {
					countryFieldCheck = -1;
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
				} else {
					countryFieldCheck = -1;
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
				} else {
					countryFieldCheck = -1;
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

				} else {
					genreFieldCheck = -1;
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
				} else {
					genreFieldCheck = -1;
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
				} else {
					genreFieldCheck = -1;
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
				} else {
					limitFieldCheck = -1;
				}
			}
		});
		
		// ############### AND ORS #################
		// ############### wiki AND OR #################
		final CheckBox wikiAND = new CheckBox("&");
		final CheckBox wikiOR = new CheckBox("||");
		wikiOR.setEnabled(true);
		// Hook up a handler to find out when they're clicked clicked.
		wikiAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					wikiCheck = 0;
					wikiOR.setValue(false);
				} else {
					wikiCheck = -1;
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
					wikiCheck = -1;
				}
			}
		});
		// ############### freebase AND OR #################
		final CheckBox freebaseAND = new CheckBox("&");
		final CheckBox freebaseOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		freebaseAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					freebaseCheck = 0;
					freebaseOR.setValue(false);
				} else {
					freebaseCheck = -1;
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
					freebaseCheck = -1;
				}
			}
		});
		// ############### name AND OR #################
		final CheckBox nameAND = new CheckBox("&");
		final CheckBox nameOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		nameAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					nameCheck = 0;
					nameOR.setValue(false);
				} else {
					nameCheck = -1;
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
					nameCheck = -1;
				}
			}
		});
		// ############### releasedate AND OR #################
		final CheckBox releasedateAND = new CheckBox("&");
		final CheckBox releasedateOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		releasedateAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					releasedateCheck = 0;
					releasedateOR.setValue(false);
				} else {
					releasedateCheck = -1;
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
					releasedateCheck = -1;
				}
			}
		});
		// ############### boxoffice AND OR #################
		final CheckBox boxofficeAND = new CheckBox("&");
		final CheckBox boxofficeOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		boxofficeAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					boxofficeCheck = 0;
					boxofficeOR.setValue(false);
				} else {
					boxofficeCheck = -1;
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
					boxofficeCheck = -1;
				}
			}
		});
		// ############### runtime AND OR #################
		final CheckBox runtimeAND = new CheckBox("&");
		final CheckBox runtimeOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		runtimeAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					runtimeCheck = 0;
					runtimeOR.setValue(false);
				} else {
					runtimeCheck = -1;
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
					runtimeCheck = -1;
				}
			}
		});
		// ############### languages AND OR #################
		final CheckBox languagesAND = new CheckBox("&");
		final CheckBox languagesOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		languagesAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					languagesCheck = 0;
					languagesOR.setValue(false);
				} else {
					languagesCheck = -1;
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
					languagesCheck = -1;
				}
			}
		});
		// ############### country AND OR #################
		final CheckBox countryAND = new CheckBox("&");
		final CheckBox countryOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		countryAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					countryCheck = 0;
					countryOR.setValue(false);
				} else {
					countryCheck = -1;
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
					countryCheck = -1;
				}
			}
		});
		// ############### genre AND OR #################
		final CheckBox genreAND = new CheckBox("&");
		final CheckBox genreOR = new CheckBox("||");
		// Hook up a handler to find out when they're clicked clicked.
		genreAND.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					genreCheck = 0;
					genreOR.setValue(false);
				} else {
					genreCheck = -1;
				}
			}
		});
		genreOR.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				if (checked) {
					genreCheck = 1;
					genreAND.setValue(false);
				} else {
					genreCheck = -1;
				}
			}
		});

	//	final CheckBox genreFieldSMALLERTHAN = new CheckBox("<");
		

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");
		sendButton2.addStyleName("sendButton2");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("nameFieldContainer2").add(wikiIdField);
		RootPanel.get("sendButtonContainer2").add(sendButton2);
		RootPanel.get("freebaseIdFieldContainer").add(freebaseIdField);
		RootPanel.get("movieNameFieldContainer").add(movieNameField);
		RootPanel.get("releaseDateFieldContainer").add(releaseDateField);
		RootPanel.get("boxofficeFieldContainer").add(boxofficeField);
		RootPanel.get("runtimeFieldContainer").add(runtimeField);
		RootPanel.get("languageFieldContainer").add(languageField);
		RootPanel.get("countryFieldContainer").add(countryField);
		RootPanel.get("genreFieldContainer").add(genreField);
		RootPanel.get("limitFieldContainer").add(limitField);
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
		VerticalPanel dialogVPanel = new VerticalPanel();
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
		final HTML serverResponseLabel2 = new HTML();
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

		// Add a handler to close the DialogBox greeting message
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
		// Add a handler to close the DialogBox movie data
		closeButton2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox2.hide();
				sendButton2.setEnabled(true);
				sendButton2.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField

		class MyHandler implements ClickHandler, KeyUpHandler {
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
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
			}
		}

		// create a handler for the send2 button

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);

		class MyHandler2 implements ClickHandler, KeyUpHandler, java.io.Serializable {
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
						/*	releasedateColumn.setSortable(true);
							boxofficeColumn.setSortable(true);
							runtimeColumn.setSortable(true);
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
							List<Movie> list = dataProvider.getList();

							for (Movie movie : result) {
								list.add(movie);
						//		movie.printMovie();
							}

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
										return (o2 != null) ? Integer.valueOf(o1.wikiid).compareTo(Integer.valueOf(o2.wikiid)) : 1;
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
										return (o2 != null) ? Integer.valueOf(o1.boxoffice).compareTo(Integer.valueOf(o2.boxoffice)) : 1;
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
										return (o2 != null) ? Integer.valueOf(o1.runtime).compareTo(Integer.valueOf(o2.runtime)) : 1;
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
										return (o2 != null) ? Integer.valueOf(o1.releasedate).compareTo(Integer.valueOf(o2.releasedate)) : 1;
									}
									return -1;
								}
							});
							movieTable.addColumnSortHandler(columnSortHandler);

							// We know that the data is sorted alphabetically by
							// default.
							movieTable.getColumnSortList().push(nameColumn);


							RootPanel.get().add(movieTable);

							// ############ table ############

							// serverResponseLabel2.setHTML(test.toString());
						} catch (NullPointerException e) {
							serverResponseLabel2.setHTML("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
							;
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
				if (wikiCheck == 0 && freebaseIdFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if(wikiCheck == 1 && freebaseIdFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}
				if (freebaseIdFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("freebaseid = '" + textFreebaseId + "'");
				}
				if (freebaseCheck == 0 &&  movieNameFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if(freebaseCheck == 1 &&  movieNameFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (movieNameFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("name LIKE '" + "%" + movieName + "%" + "'");
				}
				if (nameCheck == 0 && releaseDateFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if (nameCheck == 1 && releaseDateFieldCheck != -1){
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
				if (releasedateCheck == 0 && boxofficeFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if (releasedateCheck == 1 && boxofficeFieldCheck != -1){
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
				if (boxofficeCheck == 0 && runtimeFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if (boxofficeCheck == 1 && runtimeFieldCheck != -1){
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
				if (runtimeCheck == 0 && languageFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if (runtimeCheck == 1 && languageFieldCheck != -1){
					querryConcatination.append(" OR ");
				}

				if (languageFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("languages LIKE '" + language + "'");
				}
				if (languagesCheck == 0 && countryFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if (languagesCheck == 1 && countryFieldCheck != -1){
					querryConcatination.append(" OR ");
				}

				if (countryFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("countries LIKE '" + country + "'");
				}
				if (countryCheck == 0 && genreFieldCheck != -1){
					querryConcatination.append(" AND ");
				}
				else if (countryCheck == 1 && genreFieldCheck != -1){
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

		MyHandler2 handler2 = new MyHandler2();
		sendButton2.addClickHandler(handler2);
		wikiIdField.addKeyUpHandler(handler2);
		freebaseIdField.addKeyUpHandler(handler2);
		movieNameField.addKeyUpHandler(handler2);

	}

}
