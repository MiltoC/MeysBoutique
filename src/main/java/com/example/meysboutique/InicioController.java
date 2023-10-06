package com.example.meysboutique;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioController implements Initializable {
    public InicioController() {
    }
    @FXML
    private Button btnBoutiques;

    @FXML
    private Button btnCargos;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button hola;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnProveedores;

    @FXML
    private Button btnVentas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asignarEventosHover(btnBoutiques);
        asignarEventosHover(hola);
        asignarEventosHover(btnCargos);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnProveedores);
        asignarEventosHover(btnClientes);
        asignarEventosHover(btnProductos);
        asignarEventosHover(btnVentas);

    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        //muestra mensaje de registro exitoso
        mostrarMensajeExito("Cerrar sesión", "Sesión cerrada exitosamente.");

        //redirecciona a la ventana de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Login-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void proveedoresOpen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("proveedor-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Proveedores-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void clientesOpen(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("cliente-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Clientes-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void asignarEventosHover(Button boton) {
        boton.setOnMouseEntered(event -> {
            boton.setStyle("-fx-background-color: rgb(238, 187, 195, 0.4); -fx-text-fill: white;");
        });

        boton.setOnMouseExited(event -> {
            boton.setStyle("-fx-background-color: transparent;");
        });
    }

    private void mostrarMensajeExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
