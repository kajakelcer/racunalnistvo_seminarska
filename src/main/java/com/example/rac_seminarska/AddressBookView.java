package com.example.rac_seminarska;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AddressBookView {
    private AddressBook addressBook;
    private TableView<Contact> tableView;
    private BorderPane borderPane;

    //constructor here
    public AddressBookView(AddressBook addressBook) {
        this.addressBook = addressBook;
        this.tableView = createTableView();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setBottom(createButtonBox()); // Use createButtonBox() method instead of vbox

        updateTableView();

        // Set the borderPane as the root of the view
        this.borderPane = borderPane;
    }


    public BorderPane getView() {
        return borderPane;
    }

    private TableView<Contact> createTableView() {
        TableView<Contact> tableView = new TableView<>();
        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Contact, String> phoneColumn = new TableColumn<>("Phone");

        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        phoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhoneNumber()));

        tableView.getColumns().addAll(nameColumn, phoneColumn);
        return tableView;
    }



    private Button createAddButton() {
        Button addButton = new Button("Add Contact");
        addButton.setOnAction(e -> {
            Contact newContact = createContactDialog();
            if (newContact != null) {
                addressBook.addContact(newContact);
                updateTableView();
            }
        });
        return addButton;
    }

    private Button createDeleteButton() {
        Button deleteButton = new Button("Delete Contact");
        deleteButton.setOnAction(e -> {
            Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                addressBook.deleteContact(selectedContact);
                updateTableView();
            }
        });
        return deleteButton;
    }

    private Contact createContactDialog() {
        Dialog<Contact> dialog = new Dialog<>();
        dialog.setTitle("Add New Contact");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        TextField nameField = createNonNumericTextField("Name:");
        TextField phoneField = createNumericTextField("Phone:");

        dialog.getDialogPane().setContent(new VBox(new Label("Name:"), nameField, new Label("Phone:"), phoneField));

        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                return new Contact(nameField.getText(), phoneField.getText());
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private TextField createNonNumericTextField(String promptText) {
        TextField textField = createTextField(promptText);

        // Restrict input to non-numeric only
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                String newText = change.getControlNewText();
                if (newText.matches("[^\\d]*")) {
                    return change; // Accept the change
                }
                return null; // Reject the change
            }
            return change;
        }));

        return textField;
    }


    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private TextField createNumericTextField(String promptText) {
        TextField textField = createTextField(promptText);

        // Restrict input to numeric only
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) {
                    return change; // Accept the change
                }
                return null; // Reject the change
            }
            return change;
        }));

        return textField;
    }


    private void updateTableView() {
        ObservableList<Contact> contactList = tableView.getItems();
        contactList.setAll(addressBook.getAllContacts());
    }


    private Button createEditButton() {
        Button editButton = new Button("Edit Contact");
        editButton.setOnAction(e -> {
            Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                System.out.println("Edit button clicked for: " + selectedContact.getName());
                editContactDialog(selectedContact);
            }
        });
        return editButton;
    }


    private VBox createButtonBox() {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(createAddButton(), createEditButton(), createDeleteButton());
        return vbox;
    }

    private void editContactDialog(Contact contact) {
        Dialog<Contact> dialog = new Dialog<>();
        dialog.setTitle("Edit Contact");

        ButtonType editButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editButton, ButtonType.CANCEL);

        // Set up text fields with current contact information
        TextField nameField = createTextField("Name:");
        TextField phoneField = createNumericTextField("Phone:");

        // Set initial values
        nameField.setText(contact.getName());
        phoneField.setText(contact.getPhoneNumber());

        dialog.getDialogPane().setContent(new VBox(new Label("Name:"), nameField, new Label("Phone:"), phoneField));

        dialog.setResultConverter(buttonType -> {
            if (buttonType == editButton) {
                // Validate input
                if (!nameField.getText().isEmpty() && !phoneField.getText().isEmpty()) {
                    // Update contact information
                    contact.setName(nameField.getText());
                    contact.setPhoneNumber(phoneField.getText());
                    return contact;
                } else {
                    // Show an alert for invalid input
                    showAlert("Invalid Input", "Name and Phone cannot be empty");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            // Handle the edited contact, if needed
            updateTableView();
        });
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    Button createSaveButton(String fileName) {
        Button saveButton = new Button("Save Contacts");
        saveButton.setOnAction(e -> addressBook.saveContactsToFile(fileName));
        return saveButton;
    }

    Button createLoadButton(String fileName) {
        Button loadButton = new Button("Load Contacts");
        loadButton.setOnAction(e -> {
            addressBook.loadContactsFromFile(fileName);
            updateTableView();
        });
        return loadButton;
    }
}
