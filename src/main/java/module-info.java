module com.example.ipfinderjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.ipfinderjavafx to javafx.fxml;
    exports com.example.ipfinderjavafx;
}