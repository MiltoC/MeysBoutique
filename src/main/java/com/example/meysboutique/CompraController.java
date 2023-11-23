package com.example.meysboutique;

import javafx.collections.FXCollections;
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
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class CompraController implements Initializable {

    @FXML
    private Button btnCargos;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnCompras;

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
    private ComboBox<String> cb_codigo_empleado;

    @FXML
    private ComboBox<String> cb_codigo_proveedor;

    @FXML
    private ComboBox<String> cb_codigo_transaccion;

    @FXML
    private ComboBox<String> cb_producto_venta;

    @FXML
    private TableColumn<DatosCompras, Integer> colCantidad;

    @FXML
    private TableColumn<DatosCompras, Integer> colCodigoCompra;

    @FXML
    private TableColumn<DatosCompras, Date> colFecha;

    @FXML
    private TableColumn<DatosCompras, String> colNombreProducto;

    @FXML
    private TableColumn<DatosCompras, String> colNombreProveedor;

    @FXML
    private TableColumn<DatosCompras, String> colNombreTransaccion;

    @FXML
    private TableColumn<DatosCompras, String> colNombreUsuario;

    @FXML
    private TableColumn<DatosCompras, BigDecimal> colPrecio;

    @FXML
    private TableColumn<DatosCompras, BigDecimal> colTotal;


    @FXML
    private Pane panel_agregar;

    @FXML
    private Pane pnl_botones;

    @FXML
    private Pane pnl_campos;

    @FXML
    private TableView<DatosCompras> tbl_datos_compras;

    private ObservableList<DatosCompras> compraData = FXCollections.observableArrayList();

    @FXML
    private TextField txf_cantidad;

    @FXML
    private TextField txf_precio;

    private int compraSeleccionada;

    public CompraController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asignarEventosHover(btnCompras);
        asignarEventosHover(btnUsuarios);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnMenu);
        asignarEventosHover(btnClientes);

        // Llena los combobox con los datos de la base de datos
        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreProveedor FROM tablaProveedor";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> proveedores = FXCollections.observableArrayList();

            while (resultSet.next()) {
                proveedores.add(resultSet.getString("nombreProveedor"));
            }

            // Verifica si la lista de productos está vacía
            if (!proveedores.isEmpty()) {
                // Si hay datos en la lista, establece la opción por defecto
                cb_codigo_proveedor.setItems(proveedores);
                cb_codigo_proveedor.setValue(proveedores.get(0));
            } else {
                // Si la lista está vacía, muestra un valor por defecto
                cb_codigo_proveedor.setItems(FXCollections.observableArrayList("No hay registros"));
                cb_codigo_proveedor.setValue("No hay registros");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT primerNombre FROM tablaUsuario";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> empleados = FXCollections.observableArrayList();

            while (resultSet.next()) {
                empleados.add(resultSet.getString("primerNombre"));
            }

            // Verifica si la lista de productos está vacía
            if (!empleados.isEmpty()) {
                // Si hay datos en la lista, establece la opción por defecto
                cb_codigo_empleado.setItems(empleados);
                cb_codigo_empleado.setValue(empleados.get(0));
            } else {
                // Si la lista está vacía, muestra un valor por defecto
                cb_codigo_empleado.setItems(FXCollections.observableArrayList("No hay registros"));
                cb_codigo_empleado.setValue("No hay registros");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT tipoTransaccion FROM tablaTransaccion";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> transacciones = FXCollections.observableArrayList();

            while (resultSet.next()) {
                transacciones.add(resultSet.getString("tipoTransaccion"));
            }

            // Verifica si la lista de productos está vacía
            if (!transacciones.isEmpty()) {
                // Si hay datos en la lista, establece la opción por defecto
                cb_codigo_transaccion.setItems(transacciones);
                cb_codigo_transaccion.setValue(transacciones.get(0));
            } else {
                // Si la lista está vacía, muestra un valor por defecto
                cb_codigo_transaccion.setItems(FXCollections.observableArrayList("No hay registros"));
                cb_codigo_transaccion.setValue("No hay registros");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreProducto FROM tablaProducto";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> productos = FXCollections.observableArrayList();

            while (resultSet.next()) {
                productos.add(resultSet.getString("nombreProducto"));
            }

            // Verifica si la lista de productos está vacía
            if (!productos.isEmpty()) {
                // Si hay datos en la lista, establece la opción por defecto
                cb_producto_venta.setItems(productos);
                cb_producto_venta.setValue(productos.get(0));
            } else {
                // Si la lista está vacía, muestra un valor por defecto
                cb_producto_venta.setItems(FXCollections.observableArrayList("No hay registros"));
                cb_producto_venta.setValue("No hay registros");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Llena la tabla con los datos de la base de datos

        colCodigoCompra.setCellValueFactory(new PropertyValueFactory<>("codigo_compra"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_compra"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("codigo_empleado"));
        colNombreTransaccion.setCellValueFactory(new PropertyValueFactory<>("codigo_transaccion"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("codigo_producto"));
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory<>("codigo_proveedor"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad_producto"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio_compra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total_compra"));

        tbl_datos_compras.setItems(compraData);

        tbl_datos_compras.setOnMouseClicked(event -> {
            DatosCompras compraSeleccionada = tbl_datos_compras.getSelectionModel().getSelectedItem();
            if (compraSeleccionada != null) {
                // Establece los datos de la fila seleccionada en los campos de texto

                // Selecciona el empleado en el ComboBox
                String nombreEmpleado = compraSeleccionada.getCodigo_empleado();
                cb_codigo_empleado.setValue(nombreEmpleado);

                // Selecciona la transacción en el ComboBox
                String codigoTransaccion = compraSeleccionada.getCodigo_transaccion();
                cb_codigo_transaccion.setValue(codigoTransaccion);

                // Selecciona el producto en el ComboBox
                String codigoProducto = compraSeleccionada.getCodigo_producto();
                cb_producto_venta.setValue(codigoProducto);

                // Selecciona el proveedor en el ComboBox
                String codigoProveedor = compraSeleccionada.getCodigo_proveedor();
                cb_codigo_proveedor.setValue(codigoProveedor);

                txf_cantidad.setText(compraSeleccionada.getCantidad_producto());
                txf_precio.setText(compraSeleccionada.getPrecio_compra());

                // Almacena el código de la compra seleccionada
                this.compraSeleccionada = Integer.parseInt(compraSeleccionada.getCodigo_compra());

                // Habilita los campos y botones de actualización y eliminación
                pnl_campos.setDisable(false);
                btn_actualizar.setDisable(false);
                btn_eliminar.setDisable(false);
            }

        });

        txf_cantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9.]*")) {
                txf_cantidad.setText(newValue.replaceAll("[^0-9.]", ""));
            }
            if (newValue.length() > 9) {
                txf_cantidad.setText(newValue.substring(0, 9));
            }
        });

        txf_precio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9.]*")) {
                txf_precio.setText(newValue.replaceAll("[^0-9.]", ""));
            }
            if (newValue.length() > 9) {
                txf_precio.setText(newValue.substring(0, 9));
            }
        });

        cargarDatosCompras();
    }

    @FXML
    void ClickBtnCancelar(ActionEvent event) {
        Limpiar();

        tbl_datos_compras.setDisable(false);
        pnl_campos.setDisable(true);
        panel_agregar.setDisable(true);

    }

    @FXML
    void ClickBtnDelete(ActionEvent event) {
        DatosCompras compraSeleccionada = tbl_datos_compras.getSelectionModel().getSelectedItem();
        if (compraSeleccionada != null) {
            // Muestra un cuadro de diálogo de confirmación para eliminar
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar esta compra?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si el usuario confirma la eliminación, procede a eliminar el registro
                int codigoCompra = Integer.parseInt(compraSeleccionada.getCodigo_compra());
                try {
                    Connection connection = DatabaseUtil.getConnection();

                    // Obtener la cantidad de productos comprados antes de la eliminación
                    int cantidadAnterior = obtenerCantidadAnteriorCompra(codigoCompra);

                    String deleteQuery = "DELETE FROM tablaCompra WHERE codigoCompra = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, codigoCompra);

                    int rowsDeleted = deleteStatement.executeUpdate();

                    if (rowsDeleted > 0) {

                        String selectedProducto = cb_producto_venta.getValue(); // Obtener el producto seleccionado
                        int codigoProducto = obtenerCodigoProducto(selectedProducto);

                        // Actualizar el stock restando la cantidad de productos eliminados
                        restarStockProducto(codigoProducto, cantidadAnterior);

                        mostrarMensajeExito("Eliminación exitosa", "Compra eliminada exitosamente.");
                        cargarDatosCompras();

                        tbl_datos_compras.getSelectionModel().clearSelection();
                        Limpiar();
                        btn_eliminar.setDisable(true);
                        btn_actualizar.setDisable(true);
                        pnl_campos.setDisable(true);
                    } else {
                        mostrarMensajeError("Error", "La eliminación de la compra falló.");
                    }
                } catch (SQLException e) {
                    mostrarMensajeError("Error", "Error al eliminar la compra: " + e.getMessage());
                }
            }
        } else {
            mostrarMensajeError("Error", "Debes seleccionar una compra para eliminar.");
        }
    }

    private void restarStockProducto(int codigoProducto, int cantidadRestar) throws SQLException {
        Connection connection = null;
        PreparedStatement updateStockStatement = null;

        try {
            connection = DatabaseUtil.getConnection();

            // Consulta SQL para restar la cantidad al stock del producto
            String updateStockQuery = "UPDATE tablaProducto SET stock = stock - ? WHERE codigoProducto = ?";
            updateStockStatement = connection.prepareStatement(updateStockQuery);
            updateStockStatement.setInt(1, cantidadRestar);
            updateStockStatement.setInt(2, codigoProducto);

            updateStockStatement.executeUpdate();
        } finally {
            if (updateStockStatement != null) {
                updateStockStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @FXML
    void ClickBtnNew(ActionEvent event) {
        btn_eliminar.setDisable(true);
        btn_actualizar.setDisable(true);
        tbl_datos_compras.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
        tbl_datos_compras.setDisable(true);
        panel_agregar.setDisable(false);
        pnl_campos.setDisable(false);
        Limpiar();
    }


    @FXML
    void usuariosOpen(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("usuarios-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Usuarios-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void ClickBtnUpdate(ActionEvent event) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Obtener los datos necesarios para una compra
        String selectedEmpleado = cb_codigo_empleado.getValue(); // Obtener el empleado seleccionado
        int codigoEmpleado = obtenerCodigoUsuario(selectedEmpleado);
        String selectedTransaccion = cb_codigo_transaccion.getValue(); // Obtener la transacción seleccionada
        int codigoTransaccion = obtenerCodigoTransaccion(selectedTransaccion);
        String selectedProducto = cb_producto_venta.getValue(); // Obtener el producto seleccionado
        int codigoProducto = obtenerCodigoProducto(selectedProducto);
        String selectedProveedor = cb_codigo_proveedor.getValue(); // Obtener el proveedor seleccionado
        int codigoProveedor = obtenerCodigoProveedor(selectedProveedor);
        int nuevaCantidadProducto = Integer.parseInt(txf_cantidad.getText()); // Obtener la nueva cantidad de producto
        BigDecimal precioCompra = new BigDecimal(txf_precio.getText()); // Obtener el precio de compra
        BigDecimal totalCompra = precioCompra.multiply(new BigDecimal(nuevaCantidadProducto)); // Calcular el total de la compra

        if (selectedEmpleado.isEmpty() || selectedTransaccion.isEmpty()
                || selectedProducto.isEmpty() || selectedProveedor.isEmpty() || txf_cantidad.getText().isEmpty() || txf_precio.getText().isEmpty()) {
            mostrarMensajeError("Error", "Debe llenar todos los campos.");
        } else {
            try {
                // Inicia una transacción
                connection.setAutoCommit(false);

                // Obtiene la cantidad anterior de producto antes de la actualización
                int cantidadAnterior = obtenerCantidadAnteriorCompra(compraSeleccionada); // Debes implementar esta función

                // Consulta SQL para actualizar una compra existente
                String updateCompraQuery = "UPDATE tablaCompra SET codigoUsuario = ?, codigoTransaccion = ?, codigoProducto = ?, codigoProveedor = ?, cantidad = ?, precio = ?, total = ? WHERE codigoCompra = ?";
                PreparedStatement updateCompraStatement = connection.prepareStatement(updateCompraQuery);
                updateCompraStatement.setInt(1, codigoEmpleado);
                updateCompraStatement.setInt(2, codigoTransaccion);
                updateCompraStatement.setInt(3, codigoProducto);
                updateCompraStatement.setInt(4, codigoProveedor);
                updateCompraStatement.setInt(5, nuevaCantidadProducto);
                updateCompraStatement.setBigDecimal(6, precioCompra);
                updateCompraStatement.setBigDecimal(7, totalCompra);
                updateCompraStatement.setInt(8, compraSeleccionada); // Utiliza el código de la compra a actualizar

                int rowsUpdated = updateCompraStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    // Actualiza el stock del producto en la tabla de productos
                    int diferenciaCantidad = nuevaCantidadProducto - cantidadAnterior;
                    String updateStockQuery = "UPDATE tablaProducto SET stock = stock + ? WHERE codigoProducto = ?";
                    PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery);
                    updateStockStatement.setInt(1, diferenciaCantidad);
                    updateStockStatement.setInt(2, codigoProducto);
                    updateStockStatement.executeUpdate();

                    // Confirma la transacción
                    connection.commit();

                    mostrarMensajeExito("Actualización exitosa", "Compra actualizada exitosamente.");
                    cargarDatosCompras();

                    tbl_datos_compras.getSelectionModel().clearSelection();
                    Limpiar();
                    btn_actualizar.setDisable(true);
                    pnl_campos.setDisable(true);
                    btn_eliminar.setDisable(true);
                } else {
                    mostrarMensajeError("Error", "La actualización de la compra falló.");
                }
            } catch (SQLException e) {
                // Si hay un error, realiza un rollback de la transacción
                connection.rollback();
                throw e;
            } finally {
                // Establece la conexión de nuevo en modo de autocommit
                connection.setAutoCommit(true);
            }
        }
    }

    private int obtenerCantidadAnteriorCompra(int codigoCompra) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int cantidadAnterior = 0;

        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT cantidad FROM tablaCompra WHERE codigoCompra = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, codigoCompra);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cantidadAnterior = resultSet.getInt("cantidad");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return cantidadAnterior;
    }


    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        // Obtener el código de usuario del usuario actualmente logueado
        int codigoUsuario = obtenerCodigoUsuarioActual();

        // Eliminar el registro de la tablaSesionUsuario asociado al usuario actual
        eliminarSesionUsuario(codigoUsuario);

        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Mostrar mensaje de cerrar sesión exitoso
        mostrarMensajeExito("Cerrar sesión", "Sesión cerrada exitosamente.");

        // Redireccionar a la ventana de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Login-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private int obtenerCodigoUsuarioActual() {
        // Realizar una consulta SQL para obtener el código de usuario
        int codigoUsuario = -1;  // Valor por defecto si no se puede obtener el código

        try {
            Connection connection = DatabaseUtil.getConnection();
            if (connection != null) {
                String selectQuery = "SELECT codigoUsuario FROM tablaSesionUsuario LIMIT 1";
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    codigoUsuario = resultSet.getInt("codigoUsuario");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarMensajeError("Error de SQL", "Ocurrió un error al ejecutar la consulta SQL: " + ex.getMessage());
        }

        return codigoUsuario;
    }

    private void eliminarSesionUsuario(int codigoUsuario) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            if (connection != null) {
                String deleteQuery = "DELETE FROM tablaSesionUsuario WHERE codigoUsuario = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, codigoUsuario);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarMensajeError("Error de SQL", "Ocurrió un error al ejecutar la consulta SQL: " + ex.getMessage());
        }
    }

    @FXML
    void clickAgregar(ActionEvent event) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Obtener los datos necesarios para una compra
        String selectedEmpleado = cb_codigo_empleado.getValue(); // Obtener el empleado seleccionado
        int codigoEmpleado = obtenerCodigoUsuario(selectedEmpleado);
        String selectedTransaccion = cb_codigo_transaccion.getValue(); // Obtener la transacción seleccionada
        int codigoTransaccion = obtenerCodigoTransaccion(selectedTransaccion);
        String selectedProducto = cb_producto_venta.getValue(); // Obtener el producto seleccionado
        int codigoProducto = obtenerCodigoProducto(selectedProducto);
        String selectedProveedor = cb_codigo_proveedor.getValue(); // Obtener el proveedor seleccionado
        int codigoProveedor = obtenerCodigoProveedor(selectedProveedor);
        int cantidadProducto = Integer.parseInt(txf_cantidad.getText()); // Obtener la cantidad de producto
        BigDecimal precioCompra = new BigDecimal(txf_precio.getText()); // Obtener el precio de compra
        BigDecimal totalCompra = precioCompra.multiply(new BigDecimal(cantidadProducto)); // Calcular el total de la compra

        String cantidad = txf_cantidad.getText();
        String precio = txf_precio.getText();


        if (selectedEmpleado.isEmpty() || selectedTransaccion.isEmpty()
                || selectedProducto.isEmpty() || selectedProveedor.isEmpty() || cantidad.isEmpty() || precio.isEmpty()) {
            mostrarMensajeError("Error", "Debe llenar todos los campos.");
        } else {
            try {
                // Inicia una transacción
                connection.setAutoCommit(false);

                // Consulta SQL para insertar una nueva compra
                String insertCompraQuery = "INSERT INTO tablaCompra (fechaVenta, codigoUsuario, codigoTransaccion, codigoProducto, codigoProveedor, cantidad, precio, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertCompraStatement = connection.prepareStatement(insertCompraQuery, Statement.RETURN_GENERATED_KEYS);
                insertCompraStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now())); // Fecha actual
                insertCompraStatement.setInt(2, codigoEmpleado);
                insertCompraStatement.setInt(3, codigoTransaccion);
                insertCompraStatement.setInt(4, codigoProducto);
                insertCompraStatement.setInt(5, codigoProveedor);
                insertCompraStatement.setInt(6, cantidadProducto);
                insertCompraStatement.setBigDecimal(7, precioCompra);
                insertCompraStatement.setBigDecimal(8, totalCompra);
                insertCompraStatement.executeUpdate();

                // Obtiene el código de la compra recién insertada
                ResultSet generatedKeys = insertCompraStatement.getGeneratedKeys();
                int codigoCompra = 0;
                if (generatedKeys.next()) {
                    codigoCompra = generatedKeys.getInt(1);
                }

                // Realiza la actualización del stock del producto en la tabla de productos
                String updateStockQuery = "UPDATE tablaProducto SET stock = stock + ? WHERE codigoProducto = ?";
                PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery);
                updateStockStatement.setInt(1, cantidadProducto);
                updateStockStatement.setInt(2, codigoProducto);
                updateStockStatement.executeUpdate();

                // Confirma la transacción
                connection.commit();

                mostrarMensajeExito("Registro exitoso", "Compra registrada exitosamente.");

                // Realiza aquí cualquier otra acción que necesites después de registrar la compra.
                cargarDatosCompras();

                panel_agregar.setDisable(true);
                pnl_campos.setDisable(true);
                tbl_datos_compras.getSelectionModel().clearSelection();
                tbl_datos_compras.setDisable(false);
                Limpiar();
            } catch (SQLException e) {
                // Si hay un error, realiza un rollback de la transacción
                connection.rollback();
                throw e;
            } finally {
                // Establece la conexión de nuevo en modo de autocommit
                connection.setAutoCommit(true);
            }
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

    private void Limpiar() {
        cb_codigo_empleado.setValue("");
        cb_codigo_proveedor.setValue("");
        cb_codigo_transaccion.setValue("");
        cb_producto_venta.setValue("");
        txf_cantidad.setText("");
        txf_precio.setText("");
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

    private void mostrarMensajeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private int obtenerCodigoProveedor(String nombreProveedor) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Consulta SQL para obtener el código del usuario
        String usuarioQuery = "SELECT codigoProveedor FROM tablaProveedor WHERE nombreProveedor = ?";
        PreparedStatement usuarioStatement = connection.prepareStatement(usuarioQuery);
        usuarioStatement.setString(1, nombreProveedor);
        ResultSet usuarioResultSet = usuarioStatement.executeQuery();

        int codigoProveedor = -1; // Valor por defecto en caso de que no se encuentre el usuario

        if (usuarioResultSet.next()) {
            codigoProveedor = usuarioResultSet.getInt("codigoProveedor");
        }
        return codigoProveedor;
    }

    private int obtenerCodigoUsuario(String nombreUsuario) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Consulta SQL para obtener el código del usuario
        String usuarioQuery = "SELECT codigoUsuario FROM tablaUsuario WHERE primerNombre = ?";
        PreparedStatement usuarioStatement = connection.prepareStatement(usuarioQuery);
        usuarioStatement.setString(1, nombreUsuario);
        ResultSet usuarioResultSet = usuarioStatement.executeQuery();

        int codigoUsuario = -1; // Valor por defecto en caso de que no se encuentre el usuario

        if (usuarioResultSet.next()) {
            codigoUsuario = usuarioResultSet.getInt("codigoUsuario");
        }
        return codigoUsuario;
    }

    private int obtenerCodigoTransaccion(String nombreTransaccion) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Consulta SQL para obtener el código del tipo de transacción
        String transaccionQuery = "SELECT codigoTransaccion FROM tablaTransaccion WHERE tipoTransaccion = ?";
        PreparedStatement transaccionStatement = connection.prepareStatement(transaccionQuery);
        transaccionStatement.setString(1, nombreTransaccion);
        ResultSet transaccionResultSet = transaccionStatement.executeQuery();

        int codigoTransaccion = -1; // Valor por defecto en caso de que no se encuentre la transacción

        if (transaccionResultSet.next()) {
            codigoTransaccion = transaccionResultSet.getInt("codigoTransaccion");
        }

        return codigoTransaccion;
    }

    private int obtenerCodigoProducto(String nombreProducto) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Consulta SQL para obtener el código del producto
        String productoQuery = "SELECT codigoProducto FROM tablaProducto WHERE nombreProducto = ?";
        PreparedStatement productoStatement = connection.prepareStatement(productoQuery);
        productoStatement.setString(1, nombreProducto);
        ResultSet productoResultSet = productoStatement.executeQuery();

        int codigoProducto = -1; // Valor por defecto en caso de que no se encuentre el producto

        if (productoResultSet.next()) {
            codigoProducto = productoResultSet.getInt("codigoProducto");
        }

        return codigoProducto;
    }

    private void cargarDatosCompras() {
        compraData.clear(); // Limpia los datos existentes

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT c.codigoCompra, c.fechaVenta, u.primerNombre AS nombreEmpleado, t.tipoTransaccion, p.nombreProducto, pr.nombreProveedor, c.cantidad, c.precio, c.total " +
                    "FROM tablaCompra c " +
                    "INNER JOIN tablaUsuario u ON c.codigoUsuario = u.codigoUsuario " +
                    "INNER JOIN tablaTransaccion t ON c.codigoTransaccion = t.codigoTransaccion " +
                    "INNER JOIN tablaProducto p ON c.codigoProducto = p.codigoProducto " +
                    "INNER JOIN tablaProveedor pr ON c.codigoProveedor = pr.codigoProveedor";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int codigoCompra = resultSet.getInt("codigoCompra");
                Date fechaCompra = resultSet.getDate("fechaVenta");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String nombreTransaccion = resultSet.getString("tipoTransaccion");
                String nombreProducto = resultSet.getString("nombreProducto");
                String nombreProveedor = resultSet.getString("nombreProveedor");
                int cantidadProducto = resultSet.getInt("cantidad");
                BigDecimal precioCompra = resultSet.getBigDecimal("precio");
                BigDecimal totalCompra = resultSet.getBigDecimal("total");

                DatosCompras compra = new DatosCompras(Integer.toString(codigoCompra), fechaCompra.toString(), nombreEmpleado, nombreTransaccion, nombreProducto, nombreProveedor, Integer.toString(cantidadProducto), precioCompra.toString(), totalCompra.toString());
                compraData.add(compra);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeError("Error en la base de datos", "No se pudieron cargar los datos desde la base de datos.");
        }
    }
}
