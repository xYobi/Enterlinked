module com.haris.enterlinked {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;
    requires java.net.http;
    requires com.zaxxer.hikari;
    requires java.naming;
    requires jdk.crypto.ec;

    opens com.haris.enterlinked to javafx.fxml;
    exports com.haris.enterlinked;
    exports com.haris.enterlinked.controller;
    opens com.haris.enterlinked.controller to javafx.fxml;
}