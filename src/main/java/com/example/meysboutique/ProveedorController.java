package com.example.meysboutique;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.meysboutique.DatabaseUtil.getConnection;

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
    private ComboBox<String> txf_direccion_proveedor;

    @FXML
    private TableView<DatosProveedores> tbl_datos_proveedor;

    @FXML
    private TextField txf_nombre_proveedor;

    @FXML
    private TextField txf_nombre_encargado;

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
                DatosProveedores proveedor = tbl_datos_proveedor.getItems().get(nfila);
                eliminarProveedorDeBaseDeDatos(proveedor); // Elimina de la base de datos
                tbl_datos_proveedor.getItems().remove(nfila); // Elimina de la TableView
                vaciar();
            } else {
                mostrarMensajeExito("Seleccione una fila","No se ha seleccionado ninguna fila para eliminar.");
                tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
            }
        }

        tbl_datos_proveedor.getSelectionModel().clearSelection();
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
        String nombreProveedor = txf_nombre_proveedor.getText();
        String nombreEncargado = txf_nombre_encargado.getText();
        String direccion = txf_direccion_proveedor.getValue();
        String telefono = txf_telefono_proveedor.getText();



        if (nombreProveedor.isEmpty() || nombreEncargado.isEmpty() || direccion == null || telefono.isEmpty()) {

            mostrarMensajeError("Error en los campos de texto", "Por favor, complete todos los campos.");
            return;
        } else {
            if (!telefono.matches("^\\d{4}-\\d{4}$")){
                mostrarMensajeError("Error en el campo de teléfono", "El número de teléfono debe tener el formato: 0000-0000.");
                return;
            }else{
                if (estadoGuardado == 1) {
                    // Crear un nuevo objeto Proveedor y agregarlo a la TableView
                    DatosProveedores proveedor = new DatosProveedores(0, nombreProveedor, nombreEncargado, direccion, telefono);
                    tbl_datos_proveedor.getItems().add(proveedor);

                    // Insertar datos en la base de datos
                    insertarProveedorEnBaseDeDatos(proveedor);

                    // Actualizar el objeto en la TableView con el código generado
                    proveedor.setCodigoProveedor(obtenerCodigoProveedorDesdeBaseDeDatos()); // Reemplaza esto con el método real para obtener el código
                    tbl_datos_proveedor.refresh(); // Actualizar la vista de la tabla

                    mostrarMensajeExito("Datos","Datos Guardados");
                    tbl_datos_proveedor.setDisable(false);
                    tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
                } else if (estadoGuardado == 2) {
                    // Actualizar el registro seleccionado en la TableView
                    int nfila = tbl_datos_proveedor.getSelectionModel().getSelectedIndex();
                    if (nfila >= 0) {
                        DatosProveedores proveedor = tbl_datos_proveedor.getItems().get(nfila);
                        proveedor.setNombreProveedor(nombreProveedor);
                        proveedor.setNombreEncargado(nombreEncargado);
                        proveedor.setDireccion(direccion);
                        proveedor.setTelefono(telefono);
                        tbl_datos_proveedor.refresh(); // Actualizar la vista de la tabla

                        // Actualizar datos en la base de datos
                        actualizarProveedorEnBaseDeDatos(proveedor);

                        mostrarMensajeExito("Datos","Datos Actualizados");
                        tbl_datos_proveedor.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
                        tbl_datos_proveedor.setDisable(false);
                    } else {
                        mostrarMensajeExito("Seleccionar fila","No se ha seleccionado ninguna fila para actualizar.");
                    }
                }
                cargarDatosDesdeBaseDeDatos();
            }

            // Limpia los campos TextField después de agregar o actualizar los datos
            vaciar();
            pnl_campos.setDisable(true);
            pnl_botones.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargarDatosDesdeBaseDeDatos();

        asignarEventosHover(btnBoutiques);
        asignarEventosHover(btnUsuarios);
        asignarEventosHover(btnCargos);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnMenu);
        asignarEventosHover(btnClientes);

        // Inicializar las columnas de la TableView
        // Configura las celdas de las columnas para mostrar los datos
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEncargado"));
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

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreMunicipio FROM tablaMunicipio";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> municipios = FXCollections.observableArrayList();

            while (resultSet.next()) {
                municipios.add(resultSet.getString("nombreMunicipio"));
            }

            txf_direccion_proveedor.setItems(municipios);

            // Establece la opción por defecto
            txf_direccion_proveedor.setValue(municipios.get(0));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Agrega un oyente para el evento de clic en la tabl
        tbl_datos_proveedor.setOnMouseClicked(event -> {
            DatosProveedores proveedorSeleccionado = tbl_datos_proveedor.getSelectionModel().getSelectedItem();
            if (proveedorSeleccionado != null) {
                // Establece los datos de la fila seleccionada en los campos de texto
                txf_nombre_proveedor.setText(proveedorSeleccionado.getNombreProveedor());
                txf_nombre_encargado.setText(proveedorSeleccionado.getNombreEncargado());
                txf_direccion_proveedor.setValue("Valor deseado");
                txf_telefono_proveedor.setText(proveedorSeleccionado.getTelefono());
            }
        });

        tbl_datos_proveedor.setOnMouseClicked(event -> {
            DatosProveedores proveedorSeleccionado = tbl_datos_proveedor.getSelectionModel().getSelectedItem();
            if (proveedorSeleccionado != null) {
                // Establece los datos de la fila seleccionada en los campos de texto
                txf_nombre_proveedor.setText(proveedorSeleccionado.getNombreProveedor());
                txf_nombre_encargado.setText(proveedorSeleccionado.getNombreEncargado());

                // Verifica si la dirección del proveedor está en la lista de direcciones
                String direccion = proveedorSeleccionado.getDireccion();

                txf_direccion_proveedor.setValue(direccion);

                txf_telefono_proveedor.setText(proveedorSeleccionado.getTelefono());
            }
        });


        // Agregar validador para el campo nombre del proveedor
        txf_nombre_proveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                txf_nombre_proveedor.setText(oldValue); // Limitar a 100 caracteres
            } else if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                txf_nombre_proveedor.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        // Agregar validador para los otros campos
        txf_nombre_encargado.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]*$ ")) {
                txf_nombre_encargado.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
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
        txf_nombre_proveedor.clear();
        txf_nombre_encargado.clear();
        txf_telefono_proveedor.clear();
        txf_direccion_proveedor.setValue(null);
    }

    private void insertarProveedorEnBaseDeDatos(DatosProveedores proveedor) {
        try (Connection con = getConnection()) {


            String selectedMunicipio = txf_direccion_proveedor.getValue(); // Obtener el nombre del municipio seleccionado
            int codigoMunicipio = obtenerCodigoMunicipio(selectedMunicipio);

            String insertSQL = "INSERT INTO tablaProveedor (nombreProveedor, nombreEncargado, telefono, codigoMunicipio) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertSQL);
            stmt.setString(1, proveedor.getNombreProveedor());
            stmt.setString(2, proveedor.getNombreEncargado());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setInt(4, codigoMunicipio);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron insertar los datos en la base de datos.");
        }
    }


    private void actualizarProveedorEnBaseDeDatos(DatosProveedores proveedor) {

        try (Connection con = getConnection()) {

            String selectedMunicipio = txf_direccion_proveedor.getValue();
            int nuevoCodigoMunicipio = obtenerCodigoMunicipio(selectedMunicipio);

            String updateSQL = "UPDATE tablaProveedor SET nombreProveedor = ?, nombreEncargado = ?, telefono = ?, codigoMunicipio = ? WHERE codigoProveedor = ?";
            PreparedStatement stmt = con.prepareStatement(updateSQL);
            stmt.setString(1, proveedor.getNombreProveedor());
            stmt.setString(2, proveedor.getNombreEncargado());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setInt(4, nuevoCodigoMunicipio);
            stmt.setInt(5, proveedor.getCodigoProveedor()); // Agrega el código del proveedor que se está actualizando


            cargarDatosDesdeBaseDeDatos();

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron actualizar los datos en la base de datos.");
        }
    }

    public int obtenerCodigoMunicipio(String nombreMunicipio) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Consulta SQL para obtener el código del municipio
        String municipioQuery = "SELECT codigoMunicipio FROM tablaMunicipio WHERE nombreMunicipio = ?";
        PreparedStatement municipioStatement = connection.prepareStatement(municipioQuery);
        municipioStatement.setString(1, nombreMunicipio);
        ResultSet municipioResultSet = municipioStatement.executeQuery();

        int codigoMunicipio = 1; // Valor por defecto en caso de que no se encuentre el municipio

        if (municipioResultSet.next()) {
            codigoMunicipio = municipioResultSet.getInt("codigoMunicipio");
        }

        return codigoMunicipio;
    }



    private void cargarDatosDesdeBaseDeDatos() {
        try (Connection con = getConnection()) {
            // Consulta SQL para obtener los datos de proveedores con el nombre del municipio
            String selectSQL = "SELECT P.codigoProveedor, P.nombreProveedor, P.nombreEncargado, M.nombreMunicipio, P.telefono " +
                    "FROM tablaProveedor AS P " +
                    "INNER JOIN tablaMunicipio AS M ON P.codigoMunicipio = M.codigoMunicipio";
            PreparedStatement stmt = con.prepareStatement(selectSQL);
            ResultSet resultSet = stmt.executeQuery();

            // Limpiar los datos actuales de la TableView
            tbl_datos_proveedor.getItems().clear();

            // Llenar la TableView con los datos recuperados
            while (resultSet.next()) {
                int codigoProveedor = resultSet.getInt("codigoProveedor");
                String nombreProveedor = resultSet.getString("nombreProveedor");
                String nombreEncargado = resultSet.getString("nombreEncargado");
                String nombreMunicipio = resultSet.getString("nombreMunicipio");
                String telefono = resultSet.getString("telefono");

                DatosProveedores proveedor = new DatosProveedores(codigoProveedor, nombreProveedor, nombreEncargado, nombreMunicipio, telefono);
                tbl_datos_proveedor.getItems().add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron cargar los datos desde la base de datos.");
        }
    }

    private void eliminarProveedorDeBaseDeDatos(DatosProveedores proveedor) {
        try (Connection con = getConnection()) {
            String deleteSQL = "DELETE FROM tablaProveedor WHERE codigoProveedor = ?";
            PreparedStatement stmt = con.prepareStatement(deleteSQL);

            stmt.setInt(1, proveedor.getCodigoProveedor()); // Asigna el valor del parámetro ? aquí
            System.out.println("SQL Query: " + stmt.toString());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                mostrarMensajeExito("Eliminar registro", "Registro eliminado con éxito.");
            } else {
                mostrarMensajeError("Eliminar registro", "No se pudo eliminar el registro.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudo eliminar el registro de la base de datos.");
        }
    }

    private int obtenerCodigoProveedorDesdeBaseDeDatos() {
        int codigoProveedor = 0; // Reemplaza esto con la lógica para obtener el código del proveedor
        // Consulta tu base de datos y obtén el código del nuevo proveedor insertado
        return codigoProveedor;
    }


}
