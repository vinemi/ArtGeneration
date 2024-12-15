module com.example.artgeneration {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.logging.log4j;


    opens com.example.artgeneration to javafx.fxml;
    exports com.example.artgeneration;
}