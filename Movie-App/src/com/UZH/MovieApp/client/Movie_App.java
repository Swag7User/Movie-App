package com.UZH.MovieApp.client;

import java.lang.StringBuilder;
import com.UZH.MovieApp.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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

	int wikiIdFieldCheck = -1;
	int freebaseIdFieldCheck = -1;
	int movieNameFieldCheck = -1;
	int releaseDateFieldCheck = -1;
	int boxofficeFieldCheck = -1;
	int runtimeFieldCheck = -1;
	int languageFieldCheck = -1;
	int countryFieldCheck = -1;
	int genreFieldCheck = -1;
	StringBuilder strQuerry = new StringBuilder("select * from movieapp.moviedata ");

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
		genreField.setText("Ganre");

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
		final CheckBox freebaseIdFieldSMALLERTHAN = new CheckBox("<");
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
		final CheckBox movieNameFieldSMALLERTHAN = new CheckBox("<");
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

		class MyHandler2 implements ClickHandler, KeyUpHandler {
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
				String textToServer2 = wikiIdField.getText();
				if (!FieldVerifier.isValidID(textToServer2)) {
					errorLabel.setText("Please enter only numbers");
					return;
				}

				// Then, we send the input to the server.
				sendButton2.setEnabled(false);
				textToServerLabel2.setText(textToServer2);
				serverResponseLabel2.setText("");
				String concat = modifyString(textToServer2);
				Window.alert(concat);
				strQuerry.append(concat);
				Window.alert(strQuerry.toString());

				dbconnection.getDBData(strQuerry.toString(), new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox2.setText("Remote Procedure Call - Failure");
						serverResponseLabel2.addStyleName("serverResponseLabelError");
						serverResponseLabel2.setHTML(SERVER_ERROR);
						dialogBox2.center();
						closeButton2.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox2.setText("Remote Procedure Call");
						serverResponseLabel2.removeStyleName("serverResponseLabelError");
						serverResponseLabel2.setHTML(result);
						dialogBox2.center();
						closeButton2.setFocus(true);
					}
				});
			}

			private String modifyString(String textToServer2) {
				if (wikiIdFieldCheck == -1){
					//
				}
				else{
				if (wikiIdFieldCheck == 2) {
					return "WHERE wikiid = " + textToServer2;
				} else if (wikiIdFieldCheck == 3) {
					return "WHERE wikiid > " + textToServer2;
				} else if (wikiIdFieldCheck == 4) {
					return "WHERE wikiid < " + textToServer2;
				} else {
					return null;
				}
				}
				return null;
			}

			// Add a handler to send the name to the server

		}

		MyHandler2 handler2 = new MyHandler2();
		sendButton2.addClickHandler(handler2);
		wikiIdField.addKeyUpHandler(handler2);

	}
	
	public String createQuerryString(){
		return null;
	}

}
