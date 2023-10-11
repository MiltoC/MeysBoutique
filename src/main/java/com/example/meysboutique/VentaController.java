package com.example.meysboutique;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentaController implements Initializable {

    public VentaController() {
    }

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

    @FXML
    private Button btn_New;

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private ComboBox<?> cb_codigo_empleado;

    @FXML
    private ComboBox<?> cb_nombre_cliente;

    @FXML
    private ComboBox<?> cb_producto_venta;

    @FXML
    private ComboBox<?> cb_transacción_venta;

    @FXML
    private TableColumn<?, ?> colCodigo_producto;

    @FXML
    private TableColumn<?, ?> col_cliente_venta;

    @FXML
    private TableColumn<?, ?> col_empleado_venta;

    @FXML
    private TableColumn<?, ?> col_fecha_venta;

    @FXML
    private TableColumn<?, ?> col_producto_venta;

    @FXML
    private TableColumn<?, ?> col_total_venta;

    @FXML
    private TableColumn<?, ?> col_transaccion_venta;

    @FXML
    private Pane pnl_botones;

    @FXML
    private Pane pnl_campos;

    @FXML
    private TableView<?> tbl_datos_productos;

    @FXML
    private TextField txf_codigo_venta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asignarEventosHover(btnBoutiques);
        asignarEventosHover(btnUsuarios);
        asignarEventosHover(btnCargos);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnMenu);
        asignarEventosHover(btnClientes);
    }

    @FXML
    void ClickBtnCancelar(ActionEvent event) {

    }

    @FXML
    void ClickBtnDelete(ActionEvent event) {

    }

    @FXML
    void ClickBtnNew(ActionEvent event) {

    }

    @FXML
    void ClickBtnUpdate(ActionEvent event) {

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
    void clickAgregar(ActionEvent event) {

    }

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

    private void asignarEventosHover(Button boton) {
        boton.setOnMouseEntered(event -> {
            boton.setStyle("-fx-background-color: rgb(238, 187, 195, 0.4); -fx-text-fill: white;");
        });

        boton.setOnMouseExited(event -> {
            boton.setStyle("-fx-background-color: transparent;");
        });
    }
}
