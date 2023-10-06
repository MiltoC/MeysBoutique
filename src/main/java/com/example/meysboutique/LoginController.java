package com.example.meysboutique;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField contraseña;

    @FXML
    private TextField usuario;

    public LoginController() {
    }

    private final String usuarioCorrecto = "Admin";
    private final String contraseñaCorrecta = "admin123";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                usuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });
    }

    public void btnAcceder(ActionEvent actionEvent) throws IOException {
        String usuarioIngresado = usuario.getText();
        String contraseñaIngresada = contraseña.getText();

        if (usuarioIngresado.equals(usuarioCorrecto) && contraseñaIngresada.equals(contraseñaCorrecta)) {
            // Las credenciales son correctas, permite el acceso al sistema
            Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Inicio-Mey's Boutique");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {
            // Las credenciales son incorrectas, muestra un mensaje de error
            mostrarMensajeError("Credenciales incorrectas", "El nombre de usuario o la contraseña son incorrectos.");
        }
    }

    public void btnRegistrarse(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("registro-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Registro-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
