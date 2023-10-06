module com.example.meysboutique {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.meysboutique to javafx.fxml;
    exports com.example.meysboutique;
}