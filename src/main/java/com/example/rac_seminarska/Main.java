package com.example.rac_seminarska;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AddressBook addressBook = new AddressBook();
        AddressBookView addressBookView = new AddressBookView(addressBook);

        // Set the file name for saving and loading contacts
        String fileName = "contacts.dat";

        // Create buttons for saving and loading
        Button saveButton = addressBookView.createSaveButton(fileName);
        Button loadButton = addressBookView.createLoadButton(fileName);

        // Add save and load buttons to a VBox
        VBox buttonBox = new VBox(saveButton, loadButton);

        // Set up the layout
        BorderPane root = new BorderPane();
        root.setCenter(addressBookView.getView());
        root.setRight(buttonBox);

        // Set the scene and show the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Address Book App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
