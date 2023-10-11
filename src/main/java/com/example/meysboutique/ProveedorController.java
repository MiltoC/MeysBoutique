package com.example.meysboutique;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProveedorController implements Initializable {

    public ProveedorController() {
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
    private Button btn_actualizar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private Button btn_New;

    @FXML
    private TableColumn<DatosProveedores, String> colCodigo;

    @FXML
    private TableColumn<DatosProveedores, String> colDireccion;

    @FXML
    private TableColumn<DatosProveedores, String> colNombre;

    @FXML
    private TableColumn<DatosProveedores, String> colTelefono;

    @FXML
    private Pane pnl_botones;

    @FXML
    private Pane pnl_campos;

    @FXML
    private TableView<DatosProveedores> tbl_datos_proveedor;

    @FXML
    private TextField txf_codigo_proveedor;

    @FXML
    private TextField txf_direccion_proveedor;

    @FXML
    private TextField txf_nombre_proveedor;

    @FXML
    private TextField txf_telefono_proveedor;

    @FXML
    void ClickBtnCancelar(ActionEvent event) {
        vaciar();
        pnl_botones.setDisable(false);
        pnl_campos.setDisable(true);
        tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
        tbl_datos_proveedor.setDisable(false);
    }

    @FXML
    void ClickBtnDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de eliminar?");
        alert.setContentText("Esta acción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            int nfila = tbl_datos_proveedor.getSelectionModel().getSelectedIndex();

            if (nfila >= 0) {
                tbl_datos_proveedor.getItems().remove(nfila);
                mostrarMensajeExito("Eliminar registro","Fila Eliminada");
                tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
                vaciar();
            } else {
                mostrarMensajeExito("Seleccione una fila","No se ha seleccionado ninguna fila para eliminar.");
                tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
            }
        }
    }

    @FXML
    void ClickBtnNew(ActionEvent event) {
        estadoGuardado = 1;
        pnl_campos.setDisable(false);
        pnl_botones.setDisable(true);
        tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
        tbl_datos_proveedor.setDisable(true);
        vaciar();
    }

    @FXML
    void ClickBtnUpdate(ActionEvent event) {
        if (tbl_datos_proveedor.getSelectionModel().isEmpty()) {
            mostrarMensajeExito("Seleccionar fila","Seleccione una fila para editar.");
        } else {
            estadoGuardado = 2;
            pnl_campos.setDisable(false);
            pnl_botones.setDisable(true);
            tbl_datos_proveedor.setDisable(true);
        }
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

    int estadoGuardado = 0;

    @FXML
    void clickAgregar(ActionEvent event) {
        String codigo = txf_codigo_proveedor.getText();
        String nombre = txf_nombre_proveedor.getText();
        String direccion = txf_direccion_proveedor.getText();
        String telefono = txf_telefono_proveedor.getText();

        if (codigo.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            // Mostrar un mensaje de error
            // Puedes usar un Label o un cuadro de diálogo para mostrar el mensaje de error
            // Por ejemplo:
            mostrarMensajeError("Error en los campos de texto", "Por favor, complete todos los campos.");
            return;
        } else {
            if (!telefono.matches("^\\d{4}-\\d{4}$")){
                mostrarMensajeError("Error en el campo de teléfono", "El número de teléfono debe tener el formato: 0000-0000.");
                return;
            }else{
                if (estadoGuardado == 1) {
                    // Crear un nuevo objeto Proveedor y agregarlo a la TableView
                    DatosProveedores proveedor = new DatosProveedores(codigo, nombre, direccion, telefono);
                    tbl_datos_proveedor.getItems().add(proveedor);
                    mostrarMensajeExito("Datos","Datos Guardados");
                    tbl_datos_proveedor.setDisable(false);
                    tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
                } else if (estadoGuardado == 2) {
                    // Actualizar el registro seleccionado en la TableView
                    int nfila = tbl_datos_proveedor.getSelectionModel().getSelectedIndex();
                    if (nfila >= 0) {
                        DatosProveedores proveedor = tbl_datos_proveedor.getItems().get(nfila);
                        proveedor.setCodigo(codigo);
                        proveedor.setNombre(nombre);
                        proveedor.setDireccion(direccion);
                        proveedor.setTelefono(telefono);
                        tbl_datos_proveedor.refresh(); // Actualizar la vista de la tabla
                        mostrarMensajeExito("Datos","Datos Actualizados");
                        tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
                        tbl_datos_proveedor.setDisable(false);
                    } else {
                        mostrarMensajeExito("Seleccionar fila","No se ha seleccionado ninguna fila para actualizar.");
                    }
                }
            }

            // Limpia los campos TextField después de agregar o actualizar los datos
            vaciar();
            pnl_campos.setDisable(true);
            pnl_botones.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asignarEventosHover(btnBoutiques);
        asignarEventosHover(btnUsuarios);
        asignarEventosHover(btnCargos);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnMenu);
        asignarEventosHover(btnClientes);

        // Inicializar las columnas de la TableView
        // Configura las celdas de las columnas para mostrar los datos
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        // Agrega un oyente para el evento de selección en la tabla
        tbl_datos_proveedor.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends DatosProveedores> change) -> {
            if (!change.getList().isEmpty()) {
                // Si hay elementos seleccionados, habilita los botones "Actualizar" y "Eliminar"
                btn_actualizar.setDisable(false);
                btn_eliminar.setDisable(false);
            } else {
                // Si no hay elementos seleccionados, deshabilita los botones "Actualizar" y "Eliminar"
                btn_actualizar.setDisable(true);
                btn_eliminar.setDisable(true);
            }
        });

        // Agrega un oyente para el evento de clic en la tabla
        tbl_datos_proveedor.setOnMouseClicked(event -> {
            DatosProveedores proveedorSeleccionado = tbl_datos_proveedor.getSelectionModel().getSelectedItem();
            if (proveedorSeleccionado != null) {
                // Establece los datos de la fila seleccionada en los campos de texto
                txf_codigo_proveedor.setText(proveedorSeleccionado.getCodigo());
                txf_nombre_proveedor.setText(proveedorSeleccionado.getNombre());
                txf_direccion_proveedor.setText(proveedorSeleccionado.getDireccion());
                txf_telefono_proveedor.setText(proveedorSeleccionado.getTelefono());
            }
        });

        // Agregar validador para el campo de código
        txf_codigo_proveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_codigo_proveedor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Agregar validador para los otros campos
        txf_nombre_proveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$ ")) {
                txf_nombre_proveedor.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_direccion_proveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$")) {
                txf_direccion_proveedor.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_telefono_proveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_telefono_proveedor.setText(newValue.replaceAll("[^\\d-]", ""));
            }
            if (newValue.length() > 9) {
                txf_telefono_proveedor.setText(newValue.substring(0, 9));
            }
        });
    }


    private void asignarEventosHover(Button boton) {
        boton.setOnMouseEntered(event -> {
            boton.setStyle("-fx-background-color: rgb(238, 187, 195, 0.4); -fx-text-fill: white;");
        });

        boton.setOnMouseExited(event -> {
            boton.setStyle("-fx-background-color: transparent;");
        });
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

    private void vaciar() {
        txf_codigo_proveedor.clear();
        txf_nombre_proveedor.clear();
        txf_direccion_proveedor.clear();
        txf_telefono_proveedor.clear();
    }
}