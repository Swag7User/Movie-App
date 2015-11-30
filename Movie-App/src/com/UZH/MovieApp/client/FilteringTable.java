package com.UZH.MovieApp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.SliderBar;

public class FilteringTable extends Composite{

	private final DBConnectionAsync dbconnection = GWT.create(DBConnection.class);
	VerticalPanel tablePanel;
	VerticalPanel dialogVPanel;
	HorizontalPanel exportButtonPanel;
	WorldMap map;
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

	public void buttonExport() {
		exportButtonPanel = new HorizontalPanel();
		Button exportButton = new Button("Export", new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open("https://www.youtube.com/watch?v=wZZ7oFKsKzY", "_blank", "");
				// FIGURE OUT HOW TO DOWNLOAD STAFF
			}
		});
		exportButton.setPixelSize(50, 30);
		exportButtonPanel.add(exportButton);
	//	RootPanel.get("exportButtonContainer").add(exportButtonPanel);
	}
	final Button sendButton2 = new Button("Go!");
	final Button masterSendButton = new Button("Go!");
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
	final TextBox masterField = new TextBox();
	final Label errorLabel = new Label();

	public Widget createFilterTable(){
	// -------------------------------------------------------------------------

	wikiIdField.setEnabled(false);
	freebaseIdField.setEnabled(false);
	movieNameField.setEnabled(false);
	releaseDateField.setEnabled(false);
	boxofficeField.setEnabled(false);
	runtimeField.setEnabled(false);
	languageField.setEnabled(false);
	countryField.setEnabled(false);
	genreField.setEnabled(false);
	limitField.setEnabled(false);

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
	final CheckBox wikiAND = new CheckBox("and");
	final CheckBox wikiOR = new CheckBox("or");
	wikiAND.setEnabled(false);
	wikiAND.setValue(true);
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
				wikiAND.setValue(true);

			}
		}
	});
	// ############### freebase AND OR #################
	final CheckBox freebaseAND = new CheckBox("and");
	final CheckBox freebaseOR = new CheckBox("or");
	freebaseAND.setEnabled(false);
	freebaseAND.setValue(true);
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
				freebaseAND.setValue(true);
			}
		}
	});
	// ############### name AND OR #################
	final CheckBox nameAND = new CheckBox("and");
	final CheckBox nameOR = new CheckBox("or");
	nameAND.setEnabled(false);
	nameAND.setValue(true);
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
				nameAND.setValue(true);
			}
		}
	});
	// ############### releasedate AND OR #################
	final CheckBox releasedateAND = new CheckBox("and");
	final CheckBox releasedateOR = new CheckBox("or");
	releasedateAND.setEnabled(false);
	releasedateAND.setValue(true);
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
				releasedateAND.setValue(true);
			}
		}
	});
	// ############### boxoffice AND OR #################
	final CheckBox boxofficeAND = new CheckBox("and");
	final CheckBox boxofficeOR = new CheckBox("or");
	boxofficeAND.setEnabled(false);
	boxofficeAND.setValue(true);
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
				boxofficeAND.setValue(true);
			}
		}
	});
	// ############### runtime AND OR #################
	final CheckBox runtimeAND = new CheckBox("and");
	final CheckBox runtimeOR = new CheckBox("or");
	runtimeAND.setEnabled(false);
	runtimeAND.setValue(true);
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
				runtimeAND.setValue(true);
			}
		}
	});
	// ############### languages AND OR #################
	final CheckBox languagesAND = new CheckBox("and");
	final CheckBox languagesOR = new CheckBox("or");
	languagesAND.setEnabled(false);
	languagesAND.setValue(true);
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
				languagesAND.setValue(true);
			}
		}
	});
	// ############### country AND OR #################
	final CheckBox countryAND = new CheckBox("and");
	final CheckBox countryOR = new CheckBox("or");
	countryAND.setEnabled(false);
	countryAND.setValue(true);
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
				countryAND.setValue(true);
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
	// Hook up a handler to find out when they're clicked clicked.
	freebaseIdFieldEQUAL.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			boolean checked = ((CheckBox) event.getSource()).getValue();
			if (checked) {
				freebaseIdFieldCheck = 2;
				// freebaseIdFieldBIGGERTHAN.setValue(false);
				// freebaseIdFieldSMALLERTHAN.setValue(false);
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
	// Hook up a handler to find out when they're clicked clicked.
	movieNameFieldEQUAL.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			boolean checked = ((CheckBox) event.getSource()).getValue();
			if (checked) {
				movieNameFieldCheck = 2;
				// movieNameFieldBIGGERTHAN.setValue(false);
				// movieNameFieldSMALLERTHAN.setValue(false);
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
	// Hook up a handler to find out when they're clicked clicked.
	languageFieldEQUAL.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			boolean checked = ((CheckBox) event.getSource()).getValue();
			if (checked) {
				languageFieldCheck = 2;
				// languageFieldBIGGERTHAN.setValue(false);
				// languageFieldSMALLERTHAN.setValue(false);
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
	// Hook up a handler to find out when they're clicked clicked.
	countryFieldEQUAL.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			boolean checked = ((CheckBox) event.getSource()).getValue();
			if (checked) {
				countryFieldCheck = 2;
				// countryFieldBIGGERTHAN.setValue(false);
				// countryFieldSMALLERTHAN.setValue(false);
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
	// Hook up a handler to find out when they're clicked clicked.
	genreFieldEQUAL.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			boolean checked = ((CheckBox) event.getSource()).getValue();
			if (checked) {
				genreFieldCheck = 2;
				// genreFieldBIGGERTHAN.setValue(false);
				// genreFieldSMALLERTHAN.setValue(false);
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
	sendButton2.addStyleName("sendButton2");

	masterSendButton.addStyleName("masterSendButton");
	
	FlexTable filteringTable = new FlexTable();
	filteringTable.setWidget(0, 0, wikiIdFieldEQUAL);
	filteringTable.setWidget(0, 1, wikiIdFieldBIGGERTHAN);
	filteringTable.setWidget(0, 2, wikiIdFieldSMALLERTHAN);
	filteringTable.setWidget(0, 3, wikiIdField);
	filteringTable.setWidget(1, 0, wikiAND);
	filteringTable.setWidget(1, 1, wikiOR);
	filteringTable.setText(1, 2, "");
	filteringTable.setText(1, 3, "");
	filteringTable.setWidget(2, 0, freebaseIdFieldEQUAL);
	filteringTable.setWidget(2, 3, freebaseIdField);
	filteringTable.setWidget(3, 0, freebaseAND);
	filteringTable.setWidget(3, 1, freebaseOR);
	filteringTable.setWidget(4, 0, movieNameFieldEQUAL);
	filteringTable.setWidget(4, 3, movieNameField);
	filteringTable.setWidget(5, 0, nameAND);
	filteringTable.setWidget(5, 1, nameOR);
	filteringTable.setWidget(6, 0, releaseDateFieldEQUAL);
	filteringTable.setWidget(6, 1, releaseDateFieldBIGGERTHAN);
	filteringTable.setWidget(6, 2, releaseDateFieldSMALLERTHAN);
	filteringTable.setWidget(6, 3, releaseDateField);
	filteringTable.setWidget(7, 0, releasedateAND);
	filteringTable.setWidget(7, 1, releasedateOR);
	filteringTable.setWidget(8, 0, boxofficeFieldEQUAL);
	filteringTable.setWidget(8, 1, boxofficeFieldBIGGERTHAN);
	filteringTable.setWidget(8, 2, boxofficeFieldSMALLERTHAN);
	filteringTable.setWidget(8, 3, boxofficeField);
	filteringTable.setWidget(9, 0, boxofficeAND);
	filteringTable.setWidget(9, 1, boxofficeOR);
	filteringTable.setWidget(10, 0, runtimeFieldEQUAL);
	filteringTable.setWidget(10, 1, runtimeFieldBIGGERTHAN);
	filteringTable.setWidget(10, 2, runtimeFieldSMALLERTHAN);
	filteringTable.setWidget(10, 3, runtimeField);
	filteringTable.setWidget(11, 0, runtimeAND);
	filteringTable.setWidget(11, 1, runtimeOR);
	filteringTable.setWidget(12, 0, languageFieldEQUAL);
	filteringTable.setWidget(12, 3, languageField);
	filteringTable.setWidget(13, 0, languagesAND);
	filteringTable.setWidget(13, 1, languagesOR);
	filteringTable.setWidget(14, 0, countryFieldEQUAL);
	filteringTable.setWidget(14, 3, countryField);
	filteringTable.setWidget(15, 0, countryAND);
	filteringTable.setWidget(15, 1, countryOR);
	filteringTable.setWidget(16, 0, genreFieldEQUAL);
	filteringTable.setWidget(16, 3, genreField);
	filteringTable.setWidget(17, 0, limitFieldEQUAL);
	filteringTable.setWidget(17, 3, limitField);
	filteringTable.setWidget(18, 0, sendButton2);
	filteringTable.setWidget(18, 2, exportButtonPanel);
	filteringTable.setWidget(19, 0, errorLabel);
	

	
//	t.setWidget(19, 0, exportButtonPanel);
	
	FlexTable masterTable = new FlexTable();
	masterTable.setText(0, 0, "SQL:");
	masterTable.setWidget(0, 1, masterField);
	masterTable.setWidget(0, 2, masterSendButton);
	
	FlexTable sourceTable = new FlexTable();
	sourceTable.setHTML(0, 0, "<b>Data Source:</b>");
	sourceTable.setText(0, 1, "David Bamman, Brendan O'Connor and Noah Smith, ");
	sourceTable.setText(1, 1, "'Learning Latent Personas of Film Characters,' in: ");
	sourceTable.setText(2, 1, "Proceedings of the Annual Meeting of the Association for ");
	sourceTable.setText(3, 1, "Computational Linguistics (ACL 2013), Sofia, Bulgaria, 2013.");
	sourceTable.setHTML(4, 0, "<b>Picture Source:</b>");
	sourceTable.setHTML(4, 1, "http://goo.gl/ULO4VT");

	
//	t.getFlexCellFormatter().setColSpan(1, 0, 3);
	
	VerticalPanel filteringPanel = new VerticalPanel();
	filteringPanel.setStyleName("filteringTable"); 
	filteringPanel.add(filteringTable);
	filteringPanel.add(masterTable);
	filteringPanel.add(sourceTable);

	
	return filteringPanel;

	// -------------------------------------------------------------------------
	}
	
}