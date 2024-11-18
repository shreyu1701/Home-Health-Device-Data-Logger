module com.zodiac.homehealthdevicedatalogger {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
	requires java.mail;
    requires com.google.gson;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires com.fasterxml.jackson.databind;
	requires java.sql;

	opens com.zodiac.homehealthdevicedatalogger.Controllers to javafx.fxml;
    opens com.zodiac.homehealthdevicedatalogger.Models to com.fasterxml.jackson.databind;
    opens com.zodiac.homehealthdevicedatalogger.Data to com.fasterxml.jackson.databind;

    exports com.zodiac.homehealthdevicedatalogger.Controllers;
    exports com.zodiac.homehealthdevicedatalogger.Models;
    exports com.zodiac.homehealthdevicedatalogger;

    exports com.zodiac.homehealthdevicedatalogger.Util;
}