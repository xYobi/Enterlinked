module com.haris.enterlinked {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.haris.enterlinked to javafx.fxml;
    exports com.haris.enterlinked;
    exports com.haris.enterlinked.controller;
    opens com.haris.enterlinked.controller to javafx.fxml;
}