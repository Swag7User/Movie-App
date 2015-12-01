package com.UZH.MovieApp.client;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.client.SliderBar;

import com.googlecode.gwtTableToExcel.client.TableToExcelClient;

// Entry point classes define <code>onModuleLoad()</code>.
public class Movie_App implements EntryPoint {

	// The message displayed to the user when the server cannot be reached or
	// returns an error.
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	// needed for RPC call
	private final DBConnectionAsync dbconnection = GWT.create(DBConnection.class);

	// List with simplepager
	// gloabllist is an invisible copy of list without paging
	static List<Movie> list;
	static List<Movie> globalList;
	// initialize map
	WorldMap map;
	// Base String for SQL Query
	StringBuilder strQuerry = new StringBuilder("select * from movieapp.moviedata ");

	SliderBar fromSlider;
	SliderBar untilSlider;
	VerticalPanel verticalPanelSlider;
	VerticalPanel verticalPanel;
	VerticalPanel dialogVPanel;
	HorizontalPanel exportButtonPanel;
	HorizontalPanel exportExportPanelRight = new HorizontalPanel();
	HorizontalPanel exportExportPanelLeft = new HorizontalPanel();
	String widthSlider = "1500px";
	int firstMovieyear = 1886;
	int lastMovieyear = 2016;
	boolean sliderIsLoading;
	CellTable<Movie> exportTable;
	int sliderFromValue = firstMovieyear;
	int sliderUntilValue = lastMovieyear;
	HorizontalPanel exportPanel;

	// Clears SQL query, call this method before making an SQL query that uses
	// strQuerry as its base
	public void clearStringquerry() {
		strQuerry = new StringBuilder("select * from movieapp.moviedata ");
	}

	public void slider() {
		verticalPanelSlider = new VerticalPanel();
		verticalPanelSlider.clear();
		// verticalPanelSlider.setBorderWidth(1);

		fromSlider = new SliderBar(1886, 2016);
		fromSlider.setStepSize(1);
		fromSlider.setCurrentValue(firstMovieyear);
		fromSlider.setNumTicks(130);
		fromSlider.setNumLabels(26);
		verticalPanelSlider.add(fromSlider);
		fromSlider.setVisible(true);
		fromSlider.setHeight("50px");
		fromSlider.setWidth(widthSlider);

		fromSlider.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!sliderIsLoading) {
					sliderIsLoading = true;
					if (sliderUntilValue - sliderFromValue > 0) {
						sliderUntilValue = (int) untilSlider.getCurrentValue();
						sliderFromValue = (int) fromSlider.getCurrentValue();
					} else {
						sliderFromValue = (int) untilSlider.getCurrentValue();
						sliderUntilValue = (int) fromSlider.getCurrentValue();
					}
					// fromSlider.setCurrentValue(sliderFromValue);
					// untilSlider.setCurrentValue(sliderUntilValue);

					// releasedate > " + "'" + releasedate + "'"

					strQuerry.append("WHERE releasedate > '" + sliderFromValue + ".00.00' AND releasedate < '"
							+ sliderUntilValue + ".00.00' ");
					dbconnection.getDBData(strQuerry.toString(), new AsyncCallback<ArrayList<Movie>>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
						}

						public void onSuccess(ArrayList<Movie> result) {
							try {
								// ############ table ############
								list.clear();
								globalList.clear();
								for (Movie movie : result) {
									list.add(movie);
									globalList.add(movie);
								}

							} catch (NullPointerException e) {
								// serverResponseLabel2.setHTML("AW SHIT,
								// NULLPOINTER IS IN DA HOUSE!");
							}
							System.out.println(strQuerry);
							clearStringquerry();
						}
					});
					sliderIsLoading = false;
				} else {
					// TO DO: WHAT HAPPENS IF STH IS LOADING
					// fromSlider.setCurrentValue((double)sliderFromValue);
				}
			}
		});

		untilSlider = new SliderBar(1886, 2016);

		untilSlider.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!sliderIsLoading) {
					sliderIsLoading = true;
					if (sliderUntilValue - sliderFromValue > 0) {
						sliderUntilValue = (int) untilSlider.getCurrentValue();
						sliderFromValue = (int) fromSlider.getCurrentValue();
					} else {
						sliderFromValue = (int) untilSlider.getCurrentValue();
						sliderUntilValue = (int) fromSlider.getCurrentValue();
					}

					// fromSlider.setCurrentValue(sliderFromValue);
					// untilSlider.setCurrentValue(sliderUntilValue);

					// releasedate > " + "'" + releasedate + "'"
					final StringBuilder concatSlider = new StringBuilder("WHERE releasedate > '" + sliderFromValue + ".00.00' AND releasedate < '"
							+ sliderUntilValue + ".00.00' ");
					strQuerry.append(concatSlider);
					dbconnection.getDBData(strQuerry.toString(), new AsyncCallback<ArrayList<Movie>>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
						}

						public void onSuccess(ArrayList<Movie> result) {
							try {
								// ############ table ############
								list.clear();
								globalList.clear();
								for (Movie movie : result) {
									list.add(movie);
									globalList.add(movie);
								}
								
								map.printMap("SELECT countries, Count(*) FROM moviedata " + concatSlider + " GROUP BY countries", verticalPanel);
								
							} catch (NullPointerException e) {
								// serverResponseLabel2.setHTML("AW SHIT,
								// NULLPOINTER IS IN DA HOUSE!");
							}
							System.out.println(strQuerry);
							clearStringquerry();

						}
					});
					sliderIsLoading = false;
				} else {
					// TO DO do sth if loading
					// untilSlider.setCurrentValue((double)sliderUntilValue);
				}

			}
		});
		untilSlider.setStepSize(1);
		untilSlider.setCurrentValue(lastMovieyear);
		untilSlider.setNumTicks(130);
		untilSlider.setNumLabels(26);
		verticalPanelSlider.add(untilSlider);
		RootPanel.get().add(verticalPanelSlider);
		untilSlider.setVisible(true);
		untilSlider.setHeight("50px");
		untilSlider.setWidth(widthSlider);

	}

	public void buttonExportList() {

		@SuppressWarnings("deprecation")
		TableToExcelClient tableToExcelClient = new TableToExcelClient(ResultTable.movieTable);
		exportExportPanelLeft.add(tableToExcelClient.getExportFormWidget());
	}

	public void buttonExportTable() {
		@SuppressWarnings("deprecation")
		TableToExcelClient tableToExcelClient = new TableToExcelClient(ResultTable.globalMovieTable);
		exportExportPanelRight.add(tableToExcelClient.getExportFormWidget());

	}

	public void onModuleLoad() {
		HorizontalPanel hPanel = new HorizontalPanel();
		
		// Load the filtering table onto the root panel
		final FilteringTable filteringTable = new FilteringTable();
		hPanel.add(filteringTable.createFilterTable());
		// needed for Background image
		RootPanel.getBodyElement().addClassName("rootPanel");

		// ############ wolrd map ############
		map = new WorldMap();
		verticalPanel = new VerticalPanel();
		map.printMap("SELECT countries, Count(*) FROM moviedata GROUP BY countries", verticalPanel);
		// example query for WHERE query
		// "SELECT countries, Count(*) FROM moviedata WHERE releasedate =
		// '2001-08-24' GROUP BY countries"
		hPanel.add(verticalPanel);
		RootPanel.get().add(hPanel);
		// ############### END ####################

		slider();

		// ############ Main movie Table ############
		ResultTable mainTable = new ResultTable();
		VerticalPanel mainResultTable = mainTable.createTable();

		// ############### END ####################

		final TextBox AmountXField = new TextBox();

		HorizontalPanel labelExportPanel = new HorizontalPanel();
		VerticalPanel mainExportPanel = new VerticalPanel();

		HorizontalPanel exportLablePanelRight = new HorizontalPanel();
		HorizontalPanel exportLablePanelRightX = new HorizontalPanel();
		HorizontalPanel exportLablePanelLeft = new HorizontalPanel();

		exportExportPanelRight = new HorizontalPanel();
		HorizontalPanel exportExportPanelRightX = new HorizontalPanel();
		exportExportPanelLeft = new HorizontalPanel();

		exportPanel = new HorizontalPanel();

		Label labelList = new Label("List:");
		Label labelAll = new Label("Download Amount:");
		Button LimitSetButton = new Button("Set Amount");
		LimitSetButton.setWidth("100px");
		LimitSetButton.addClickHandler(new ClickHandler() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(ClickEvent event) {
				FieldVerifier fv = new FieldVerifier();
				if (fv.isValidInt(AmountXField.getText())) {
					System.out.println(Integer.parseInt(AmountXField.getText()));
					ResultTable.globalMovieTable.setVisibleRange(0, Integer.parseInt(AmountXField.getText()));
				}
			}
		});

		exportLablePanelLeft.add(labelList);
		exportLablePanelRight.add(labelAll);
		exportExportPanelRightX.add(LimitSetButton);

		exportLablePanelLeft.setWidth("100px");
		exportLablePanelRight.setWidth("100px");
		exportLablePanelRightX.setWidth("100px");

		labelExportPanel.add(exportLablePanelLeft);
		labelExportPanel.add(exportLablePanelRight);
		labelExportPanel.add(exportExportPanelRightX);

		exportExportPanelLeft.setWidth("100px");
		exportExportPanelRight.setWidth("100px");
		exportExportPanelRightX.setWidth("100px");

		// labelExportPanel.setBorderWidth(1);
		exportPanel.add(exportExportPanelLeft);
		exportPanel.add(exportExportPanelRight);
		exportPanel.add(AmountXField);

		// exportPanel.setBorderWidth(1);
		labelAll.addStyleName("gwt-Label");
		labelList.addStyleName("gwt-Label");

		labelExportPanel.addStyleName("gwt-Label");

		mainExportPanel.add(labelExportPanel);
		mainExportPanel.add(exportPanel);
		mainExportPanel.setBorderWidth(1);

		labelList.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		labelAll.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		buttonExportList();
		buttonExportTable();

		mainResultTable.add(mainExportPanel);
		RootPanel.get().add(mainResultTable);
		
		// ################ DATA SOURCE ##################
		FlexTable sourceTable = new FlexTable();
		sourceTable.setHTML(0, 0, "<b>Data Source:</b>");
		sourceTable.setText(0, 1, "David Bamman, Brendan O'Connor and Noah Smith, 'Learning Latent Personas of Film Characters,' in: Proceedings of the Annual Meeting of the Association for Computational Linguistics (ACL 2013), Sofia, Bulgaria, 2013.");
		sourceTable.setHTML(1, 0, "<b>Picture Source:</b>");
		sourceTable.setHTML(1, 1, "http://goo.gl/ULO4VT");
		sourceTable.addStyleName("sourceTable");
		// ################# END ######################
		
		// add data source flextable to root panel
		RootPanel.get().add(sourceTable);

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
				filteringTable.sendButton2.setEnabled(true);
				filteringTable.sendButton2.setFocus(true);
				filteringTable.masterSendButton.setEnabled(true);
			}
		});

		@SuppressWarnings("serial")
		class FilteringHandler implements ClickHandler, KeyUpHandler, java.io.Serializable {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			// Fired when the user types in the nameField.
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			 // Send the filtering query from the nameField to the server and wait for a response.
			private void sendNameToServer() {
				// First, we validate the input.
				filteringTable.errorLabel.setText("");
				String textWikiId = filteringTable.wikiIdField.getText();
				String textFreebaseId = filteringTable.freebaseIdField.getText();
				String movieName = filteringTable.movieNameField.getText();
				String releasedate = filteringTable.releaseDateField.getText();
				String boxoffice = filteringTable.boxofficeField.getText();
				String runtime = filteringTable.runtimeField.getText();
				String language = filteringTable.languageField.getText();
				String country = filteringTable.countryField.getText();
				String genre = filteringTable.genreField.getText();
				String limit = filteringTable.limitField.getText();
				if ((!FieldVerifier.isValidID(textWikiId)) && (!FieldVerifier.isValidID(textFreebaseId))) {
					filteringTable.errorLabel.setText("Error");
					return;
				}

				// Then, we send the input to the server.
				filteringTable.sendButton2.setEnabled(false);
				textToServerLabel2.setText(textWikiId);
				// textToServerLabel2.setText(textFreebaseId);
				serverResponseLabel2.setText("");
				final String concat = modifyString(textWikiId, textFreebaseId, movieName, releasedate, boxoffice,
						runtime, language, country, genre, limit);
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
						try {
							list.clear();
							globalList.clear();
							for (Movie movie : result) {
								list.add(movie);
								globalList.add(movie);
							}
							map.printMap("SELECT countries, Count(*) FROM moviedata " + concat + " GROUP BY countries", verticalPanel);
						} catch (NullPointerException e) {
							serverResponseLabel2.setHTML("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
						}
						clearStringquerry();
						filteringTable.sendButton2.setEnabled(true);
					}

				});
			}

			private String modifyString(String textWikiId, String textFreebaseId, String movieName, String releasedate,
					String boxoffice, String runtime, String language, String country, String genre, String limit) {
				StringBuilder querryConcatination = new StringBuilder();

				if (filteringTable.wikiIdFieldCheck == -1) {
					querryConcatination.append("WHERE 1=1 ");
				} else {
					if (filteringTable.wikiIdFieldCheck == 2) {
						querryConcatination.append(" WHERE wikiid = " + textWikiId);
					} else if (filteringTable.wikiIdFieldCheck == 3) {
						querryConcatination.append(" WHERE wikiid > " + textWikiId);
					} else if (filteringTable.wikiIdFieldCheck == 4) {
						querryConcatination.append(" WHERE wikiid < " + textWikiId);
					}
				}
				if (filteringTable.wikiCheck == 0 && filteringTable.freebaseIdFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.wikiCheck == 1 && filteringTable.freebaseIdFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}
				if (filteringTable.freebaseIdFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("freebaseid = '" + textFreebaseId + "'");
				}
				if (filteringTable.freebaseCheck == 0 && filteringTable.movieNameFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.freebaseCheck == 1 && filteringTable.movieNameFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (filteringTable.movieNameFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("name LIKE '" + "%" + movieName + "%" + "'");
				}
				if (filteringTable.nameCheck == 0 && filteringTable.releaseDateFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.nameCheck == 1 && filteringTable.releaseDateFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (filteringTable.releaseDateFieldCheck == -1) {
					// do nothing I guess
				} else {
					if (filteringTable.releaseDateFieldCheck == 2) {
						querryConcatination.append("releasedate = " + "'" + releasedate + "'");
					} else if (filteringTable.releaseDateFieldCheck == 3) {
						querryConcatination.append("releasedate > " + "'" + releasedate + "'");
					} else if (filteringTable.releaseDateFieldCheck == 4) {
						querryConcatination.append("releasedate < " + "'" + releasedate + "'");
					}
				}
				if (filteringTable.releasedateCheck == 0 && filteringTable.boxofficeFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.releasedateCheck == 1 && filteringTable.boxofficeFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (filteringTable.boxofficeFieldCheck == -1) {
					// do nothing I guess
				} else {
					if (filteringTable.boxofficeFieldCheck == 2) {
						querryConcatination.append("boxoffice = " + boxoffice);
					} else if (filteringTable.boxofficeFieldCheck == 3) {
						querryConcatination.append("boxoffice > " + boxoffice);
					} else if (filteringTable.boxofficeFieldCheck == 4) {
						querryConcatination.append("boxoffice < " + boxoffice);
					}
				}
				if (filteringTable.boxofficeCheck == 0 && filteringTable.runtimeFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.boxofficeCheck == 1 && filteringTable.runtimeFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}
				// test
				if (filteringTable.runtimeFieldCheck == -1) {
					// do nothing I guess
				} else {
					if (filteringTable.runtimeFieldCheck == 2) {
						querryConcatination.append("runtime = " + runtime);
					} else if (filteringTable.runtimeFieldCheck == 3) {
						querryConcatination.append("runtime > " + runtime);
					} else if (filteringTable.runtimeFieldCheck == 4) {
						querryConcatination.append("runtime < " + runtime);
					}
				}
				if (filteringTable.runtimeCheck == 0 && filteringTable.languageFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.runtimeCheck == 1 && filteringTable.languageFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (filteringTable.languageFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("languages LIKE '" + language + "'");
				}
				if (filteringTable.languagesCheck == 0 && filteringTable.countryFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.languagesCheck == 1 && filteringTable.countryFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (filteringTable.countryFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("countries LIKE '%" + country + "%'");
				}
				if (filteringTable.countryCheck == 0 && filteringTable.genreFieldCheck != -1) {
					querryConcatination.append(" AND ");
				} else if (filteringTable.countryCheck == 1 && filteringTable.genreFieldCheck != -1) {
					querryConcatination.append(" OR ");
				}

				if (filteringTable.genreFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append("genres LIKE '" + "%" + genre + "%" + "'");
				}
				if (filteringTable.limitFieldCheck == -1) {
					// do nothing I guess
				} else {
					querryConcatination.append(" LIMIT " + limit);
				}
				return querryConcatination.toString();
			}
		}

		@SuppressWarnings("serial")
		class MasterHandler implements ClickHandler, KeyUpHandler, java.io.Serializable {
			// Fired when the user clicks on the sendButton.
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}
			// Fired when the user types in the nameField.
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}
			// Send the master query from the nameField to the server and wait for a response.
			private void sendNameToServer() {
				// First, we validate the input.
				filteringTable.errorLabel.setText("");
				final String masterText = filteringTable.masterField.getText();

				// Then, we send the input to the server.
				filteringTable.masterSendButton.setEnabled(false);
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
							list.clear();
							globalList.clear();
							for (Movie movie : result) {
								list.add(movie);
								globalList.add(movie);
							}
							map.printMap("SELECT countries, Count(*) FROM moviedata WHERE " + masterText + " GROUP BY countries", verticalPanel);


						} catch (NullPointerException e) {
							serverResponseLabel2.setHTML("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
							;
						}
						clearStringquerry();
						filteringTable.masterSendButton.setEnabled(true);
					}

				});
			}
		}

		FilteringHandler filteringHandler = new FilteringHandler();
		filteringTable.sendButton2.addClickHandler(filteringHandler);

		MasterHandler masterHandler = new MasterHandler();
		filteringTable.masterSendButton.addClickHandler(masterHandler);

		// ###################### page load table #######################
		// load data into table on page load
		StringBuilder[] querryArray = new StringBuilder[8];
		querryArray[0] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000");
		querryArray[1] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 10000");
		querryArray[2] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 20000");
		querryArray[3] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 30000");
		querryArray[4] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 40000");
		querryArray[5] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 50000");
		querryArray[6] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 10000 offset 60000");
		querryArray[7] = new StringBuilder("SELECT * FROM movieapp.moviedata LIMIT 11471 offset 70000");

		for (final StringBuilder onLoadQuerry : querryArray) {
			dbconnection.getDBData(onLoadQuerry.toString(), new AsyncCallback<ArrayList<Movie>>() {
				public void onFailure(Throwable caught) {
					// fAILED, DO NOTHING I GUESS
				}

				public void onSuccess(ArrayList<Movie> result) {
					try {
						for (Movie movie : result) {
							list.add(movie);
							globalList.add(movie);
						}
					} catch (NullPointerException e) {
						System.out.println("AW SHIT, NULLPOINTER IS IN DA HOUSE!");
					}

				}

			});

		}
		// ######################## END #########################
	}

}
