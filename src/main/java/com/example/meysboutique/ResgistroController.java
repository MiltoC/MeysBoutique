package com.example.meysboutique;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.EventObject;

public class ResgistroController {

    public ResgistroController() {
    }

    @FXML
    private TextField primerApellidoUsuario;

    @FXML
    private TextField confirmarUsuario;

    @FXML
    private TextField contraseñaUsuario;

    @FXML
    private TextField correoUsuario;

    @FXML
    private DatePicker fechaUsuario;

    @FXML
    private TextField primerNombreUsuario;

    @FXML
    private TextField segundoApellidoUsuario;

    @FXML
    private TextField segundoNombreUsuario;

    @FXML
    private TextField telefonoUsuario;

    @FXML
    private TextField tercerNombreUsuario;

    @FXML
    private TextField apellidoConyugalUsuario;

    @FXML
    private ComboBox<String> direccionUsuario;

    public void initialize() {
        // Establece la fecha inicial en el año 2023 (o el año que desees)
        fechaUsuario.setValue(LocalDate.of(2002, 1, 1));

        // Establece las opciones del ComboBox
        direccionUsuario.getItems().addAll("Ilobasco", "Cojutepeque", "Sensuntepeque");

        // Establece la opción por defecto
        direccionUsuario.setValue("Ilobasco");

        primerNombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                primerNombreUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        segundoNombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                segundoNombreUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        tercerNombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                tercerNombreUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        primerApellidoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                primerApellidoUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        segundoApellidoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                segundoApellidoUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        apellidoConyugalUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                apellidoConyugalUsuario.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        telefonoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[0-9-]*$")) {
                telefonoUsuario.setText(newValue.replaceAll("[^0-9-\\s]", ""));
            }
        });
    }

    public void btnRegistroAcceder(ActionEvent actionEvent) throws IOException {

        if (validarCampos()) {
            // Las credenciales son correctas, permite el acceso al sistema
            Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            //muestra mensaje de registro exitoso
            mostrarMensajeExito("Registro exitoso", "El usuario se ha registrado correctamente.");

            //redirecciona a la ventana de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Login-Mey's Boutique");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    void cancelarRegisterAcction(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        //redirecciona a la ventana de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Login-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        // Validar el campo de primer nombre
        String nombre = primerNombreUsuario.getText();
        if (nombre.isEmpty() || nombre.matches(".*\\d.*")) {
            mostrarMensajeError("Error en el nombre", "El primer nombre no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        // Validar el campo de segundo nombre
        String segundoNombre = segundoNombreUsuario.getText();
        if (segundoNombre.isEmpty() || !segundoNombre.matches("^[a-zA-Z]+$")) {
            mostrarMensajeError("Error en el segundo nombre", "El segundo nombre no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        String tercerNombre = tercerNombreUsuario.getText();
        if (!tercerNombre.isEmpty()) {
            if (!tercerNombre.matches("^[a-zA-Z]+$")) {
                mostrarMensajeError("Error en el tercer nombre", "El tercer nombre no puede: \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
                camposValidos = false;
            }
        }

        // Validar el campo de primer apellido
        String apellido = primerApellidoUsuario.getText();
        if (apellido.isEmpty() || !apellido.matches("^[a-zA-Z]+$")) {
            mostrarMensajeError("Error en el apellido", "El primer apellido no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        // Validar el campo de segundo apellido
        String segundoApellido = segundoApellidoUsuario.getText();
        if (segundoApellido.isEmpty() || !segundoApellido.matches("^[a-zA-Z]+$")) {
            mostrarMensajeError("Error en el segundo apellido", "El segundo apellido no puede: \n• estar vacío. \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
            camposValidos = false;
        }

        // Validar el campo de apellido apellido conyugal
        String apellidoConyugal = apellidoConyugalUsuario.getText();
        if (!apellidoConyugal.isEmpty()) {
            if (!apellidoConyugal.matches("^[a-zA-Z]+$")) {
                mostrarMensajeError("Error en el apellido conyugal", "El el apellido conyugal no puede: \n• contener números. \n• contener caracteres especiales. \n• contener espacios.");
                camposValidos = false;
            }
        }

        // Validar el campo de nombre
        String telefono = telefonoUsuario.getText();
        if (telefono.isEmpty() || !telefono.matches("^\\d{4}-\\d{4}$")) {
            mostrarMensajeError("Error en el teléfono", "El teléfono debe tener el formato 0000-0000.");
            camposValidos = false;
        }

        // Validar el campo de correo electrónico
        String correo = correoUsuario.getText();
        if (correo.isEmpty() || !correo.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
            mostrarMensajeError("Error en el correo", "El correo electrónico no es válido.");
            camposValidos = false;
        }

        // Validar el campo de fecha de nacimiento
        LocalDate fechaNacimiento = fechaUsuario.getValue();
        LocalDate fechaMinima = LocalDate.of(1980, 1, 1); // Cambia la fecha mínima según tus requisitos
        LocalDate fechaMaxima = LocalDate.of(2002, 12, 31);
        if (fechaNacimiento == null || fechaNacimiento.isBefore(fechaMinima) || fechaNacimiento.isAfter(fechaMaxima)) {
            mostrarMensajeError("Error en la fecha de nacimiento", "Selecciona una fecha de nacimiento válida.");
            camposValidos = false;
        }

        // Validar el campo de confirmación de contraseña
        String contraseña = contraseñaUsuario.getText();
        if (contraseña.isEmpty() || contraseña.length() < 6) {
            mostrarMensajeError("Error en la contraseña", "La contraseña no puede: \n • estar vacía \n • debe tener al menos 6 caracteres.");
            camposValidos = false;
        }

        // Validar el campo de confirmación de contraseña
        String confirmacionContraseña = confirmarUsuario.getText();
        if (!confirmacionContraseña.equals(contraseña)) {
            mostrarMensajeError("Error en la confirmación de contraseña", "Las contraseñas no coinciden.");
            camposValidos = false;
        }

        return camposValidos;
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
