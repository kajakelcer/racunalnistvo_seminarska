package com.example.rac_seminarska;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ContactView {
    private Contact contact;

    public ContactView(Contact contact) {
        this.contact = contact;
    }

    public VBox getView() {
        VBox vbox = new VBox();
        Label nameLabel = new Label("Name: " + contact.getName());
        Label phoneLabel = new Label("Phone: " + contact.getPhoneNumber());

        vbox.getChildren().addAll(nameLabel, phoneLabel);
        return vbox;
    }
}
