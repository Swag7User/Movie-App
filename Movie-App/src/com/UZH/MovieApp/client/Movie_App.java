package com.UZH.MovieApp.client;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.persistence.criteria.Root;

import org.apache.jsp.ah.searchDocumentBody_jsp;

import com.UZH.MovieApp.shared.FieldVerifier;
import com.UZH.MovieApp.shared.Movie;
import com.google.apphosting.client.datastoreservice.app.mobile.MobileEntityV4Normalizer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.asm.commons.AdviceAdapter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlexTable;
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
import com.google.gwt.user.client.ui.RootLayoutPanel;
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
	VerticalPanel tablePanel;
	VerticalPanel dialogVPanel;
	HorizontalPanel exportButtonPanel;
	WorldMap map;
	String widthSlider = "1500px";
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
	public void slider() {
		verticalPanelSlider = new VerticalPanel();
		verticalPanelSlider.clear();
		// verticalPanelSlider.setBorderWidth(1);

		slider = new SliderBar(1886, 2016);
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

	public void onModuleLoad() {
		
		final FilteringTable lel = new FilteringTable();
		RootPanel.get().add(lel.createFilterTable());

		RootPanel.getBodyElement().addClassName("rootPanel");

		// world map
		map = new WorldMap((int) (RootPanel.get("bannerTable").getAbsoluteLeft()
				- RootPanel.get("sourceDisplay").getOffsetWidth() * 1.09));
		// map.setWidth(widthMapSlider);
		verticalPanel = new VerticalPanel();
		// verticalPanel.setWidth("30%");
		verticalPanel.add(map.printMap());
		RootPanel.get().add(verticalPanel);
		// verticalPanelSlider.setWidth("1500px");
		// verticalPanelSlider.setHeight("10px");
		// create text boxes
		// SLIDER
		// slider();
		buttonExport();
		// SLIDER END



		// ############ table ############

		// Create a CellTable.
		CellTable<Movie> movieTable = new CellTable<Movie>();
		movieTable.addStyleName("gwt-CellTable");

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
		boxofficeColumn.setSortable(true);
		/*
		 * releasedateColumn.setSortable(true); runtimeColumn.setSortable(true);
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
		final List<Movie> movieList = dataProvider.getList();

		SimplePager pager = new SimplePager();
		pager.setDisplay(movieTable);
		tablePanel = new VerticalPanel();
		tablePanel.add(pager);
		tablePanel.setWidth("100%");
		tablePanel.add(movieTable);

		// Add a ColumnSortEvent.ListHandler to connect
		// sorting to the
		// java.util.List.
		ListHandler<Movie> columnSortHandler = new ListHandler<Movie>(movieList);
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
					return (o2 != null) ? Integer.valueOf(o1.releasedate).compareTo(Integer.valueOf(o2.releasedate))
							: 1;
				}
				return -1;
			}
		});
		movieTable.addColumnSortHandler(columnSortHandler);

		// We know that the data is sorted alphabetically by
		// default.
		movieTable.getColumnSortList().push(nameColumn);

		RootPanel.get().add(tablePanel);

		// We can add style names to widgets

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element



		

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
				lel.sendButton2.setEnabled(true);
				lel.sendButton2.setFocus(true);
				lel.masterSendButton.setEnabled(true);
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
				lel.errorLabel.setText("");
				String textWikiId = lel.wikiIdField.getText();
				String textFreebaseId = lel.freebaseIdField.getText();
				String movieName = lel.movieNameField.getText();
				String releasedate = lel.releaseDateField.getText();
				String boxoffice = lel.boxofficeField.getText();
				String runtime = lel.runtimeField.getText();
				String language = lel.languageField.getText();
				String country = lel.countryField.getText();
				String genre = lel.genreField.getText();
				String limit = lel.limitField.getText();
				if ((!FieldVerifier.isValidID(textWikiId)) && (!FieldVerifier.isValidID(textFreebaseId))) {
					lel.errorLabel.setText("Error");
					return;
				}

				// Then, we send the input to the server.
				lel.sendButton2.setEnabled(false);
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
							movieList.clear();
							for (Movie movie : result) {
								movieList.add(movie);
								// movie.printMovie();
							}
							// serverResponseLabel2.setHTML(test.toString());
						} catch (NullPointerException e) {
							serverResponseLabel2.setHTML("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
						}
						// dialogBox2.center();
						// closeButton2.setFocus(true);
						clearStringquerry();
						lel.sendButton2.setEnabled(true);
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
				lel.errorLabel.setText("");
				String masterText = lel.masterField.getText();

				// Then, we send the input to the server.
				lel.masterSendButton.setEnabled(false);
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
							movieList.clear();
							for (Movie movie : result) {
								movieList.add(movie);
								// movie.printMovie();
							}

						} catch (NullPointerException e) {
							serverResponseLabel2.setHTML("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
							;
						}
						// dialogBox2.center();
						// closeButton2.setFocus(true);
						clearStringquerry();
						lel.masterSendButton.setEnabled(true);
					}

				});
			}
		}

		FilteringHandler filteringHandler = new FilteringHandler();
		lel.sendButton2.addClickHandler(filteringHandler);

		MasterHandler masterHandler = new MasterHandler();
		lel.masterSendButton.addClickHandler(masterHandler);

		// create on load table
		StringBuilder[] querryArray = new StringBuilder[8];
		querryArray[0] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000");
		querryArray[1] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 10000");
		querryArray[2] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 20000");
		querryArray[3] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 30000");
		querryArray[4] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 40000");
		querryArray[5] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 50000");
		querryArray[6] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 60000");
		querryArray[7] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 70000");

		for (final StringBuilder onLoadQuerry : querryArray) {
			dbconnection.getDBData(onLoadQuerry.toString(), new AsyncCallback<ArrayList<Movie>>() {
				public void onFailure(Throwable caught) {
					// fAILED, DO NOTHING I GUESS
				}

				public void onSuccess(ArrayList<Movie> result) {
					// System.out.println(onLoadQuerry);
					try {

						// ############ table ############
						for (Movie movie : result) {
							movieList.add(movie);
							// movie.printMovie();
						}
						// serverResponseLabel2.setHTML(test.toString());
					} catch (NullPointerException e) {
						System.out.println("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
					}

				}

			});

		}

	}
}
