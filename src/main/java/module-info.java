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
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens com.zodiac.homehealthdevicedatalogger.Controllers to javafx.fxml;
    opens com.zodiac.homehealthdevicedatalogger.Models to com.fasterxml.jackson.databind;

    exports com.zodiac.homehealthdevicedatalogger.Controllers;
    exports com.zodiac.homehealthdevicedatalogger.Models;
    exports com.zodiac.homehealthdevicedatalogger;

}