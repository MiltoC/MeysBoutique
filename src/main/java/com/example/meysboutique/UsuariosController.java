package com.example.meysboutique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsuariosController implements Initializable{

    @FXML
    private Button btnBoutiques;

    @FXML
    private Button btnCargos;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnUsuarios;

    private ObservableList<DatosUsuarios> usuarioData = FXCollections.observableArrayList();

    @FXML
    private TableColumn<DatosUsuarios, String> colApellido;

    @FXML
    private TableColumn<DatosUsuarios, String> colContraseña;

    @FXML
    private TableColumn<DatosUsuarios, String> colCorreo;

    @FXML
    private TableColumn<DatosUsuarios, Date> colFecha;

    @FXML
    private TableColumn<DatosUsuarios, Integer> colMunicipio;

    @FXML
    private TableColumn<DatosUsuarios, String> colNombre;

    @FXML
    private TableColumn<DatosUsuarios, String> colTelefono;

    @FXML
    private TableView<DatosUsuarios> tbl_datos_usuarios;


    @FXML
    void menuInicioOpen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        //redirecciona a la ventana de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Inicio-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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

    private void mostrarMensajeExito(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura las celdas de la tabla para mostrar los datos de DatosUsuarios
        colNombre.setCellValueFactory(new PropertyValueFactory<>("primerNombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colContraseña.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        colMunicipio.setCellValueFactory(new PropertyValueFactory<>("codigoMunicipio"));

        tbl_datos_usuarios.setItems(usuarioData);

        // Llama a un método para cargar los datos desde la base de datos
        cargarDatosUsuarios();
    }




    private void cargarDatosUsuarios() {
        usuarioData.clear();
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT codigoUsuario, primerNombre, primerApellido, fechaNacimiento, telefono, correo, contraseña, codigoMunicipio FROM tablaUsuario";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int codigoUsuario = resultSet.getInt("codigoUsuario");
                String primerNombre = resultSet.getString("primerNombre");
                String primerApellido = resultSet.getString("primerApellido");
                Date fechaNacimiento = resultSet.getDate("fechaNacimiento");
                String telefonoUsuario = resultSet.getString("telefono");
                String correoUsuario = resultSet.getString("correo");
                String contrasenaUsuario = resultSet.getString("contraseña");
                int municipioUsuario = resultSet.getInt("codigoMunicipio");

                DatosUsuarios usuarios = new DatosUsuarios(codigoUsuario, primerNombre, primerApellido, fechaNacimiento, telefonoUsuario, correoUsuario, contrasenaUsuario, municipioUsuario);
                usuarioData.add(usuarios);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron cargar los datos desde la base de datos.");
        }
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
