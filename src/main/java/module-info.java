module com.example.rac_seminarska {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.rac_seminarska to javafx.fxml;
    exports com.example.rac_seminarska;
}