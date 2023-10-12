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
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductoController implements Initializable {

    public ProductoController() {
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
    private ComboBox<String> cb_categoria_producto;

    @FXML
    private ComboBox<?> cb_codigo_producto;

    @FXML
    private ComboBox<String> cb_codigo_producto_proveedor;

    @FXML
    private TableColumn<DatosProductos, Integer> colCategoria_producto;

    @FXML
    private TableColumn<DatosProductos, Integer> colCodigo_producto;

    @FXML
    private TableColumn<DatosProductos, Integer> colCodigo_proveedor;

    @FXML
    private TableColumn<DatosProductos, String> colDescripcion_producto;

    @FXML
    private TableColumn<DatosProductos, String> colNombre_producto;

    @FXML
    private TableColumn<DatosProductos, Integer> colPrecio_producto;

    @FXML
    private TableColumn<DatosProductos, ?> colStok_producto;

    @FXML
    private Pane pnl_botones;

    @FXML
    private Pane pnl_campos;

    @FXML
    private TableView<DatosProductos> tbl_datos_productos;

    @FXML
    private TextField txf_descripcion_producto;

    @FXML
    private TextField txf_nombre_producto;

    @FXML
    private TextField txf_precio_producto;

    private int productoSeleccionado;
    private ObservableList<DatosProductos> productData = FXCollections.observableArrayList();



    @FXML
    void ClickBtnCancelar(ActionEvent event) {
        txf_nombre_producto.setText("");
        txf_descripcion_producto.setText("");
        txf_precio_producto.setText("");
        cb_categoria_producto.setValue(cb_categoria_producto.getItems().get(0));
        cb_codigo_producto_proveedor.setValue(cb_codigo_producto_proveedor.getItems().get(0));

        tbl_datos_productos.setDisable(false);
        pnl_campos.setDisable(true);
        pnl_botones.setDisable(true);

    }

    @FXML
    void ClickBtnDelete(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de eliminar?");
        alert.setContentText("Esta acción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();

        try {
            Connection connection = DatabaseUtil.getConnection();

            // Obtén el producto seleccionado
            DatosProductos productoSeleccionado = tbl_datos_productos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado != null) {

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int codigoProducto = productoSeleccionado.getCodigoProducto();

                    // Consulta SQL para eliminar el cliente
                    String deleteProductoQuery = "DELETE FROM tablaProducto WHERE codigoProducto = ?";
                    PreparedStatement deleteProductoStatement = connection.prepareStatement(deleteProductoQuery);
                    deleteProductoStatement.setInt(1, codigoProducto);
                    deleteProductoStatement.executeUpdate();

                    mostrarMensajeExito("Eliminación exitosa", "Producto eliminado exitosamente.");

                    cargarDatosProductos();

                    tbl_datos_productos.getSelectionModel().clearSelection();
                    Limpiar();
                    btn_eliminar.setDisable(true);
                    btn_actualizar.setDisable(true);
                    pnl_campos.setDisable(true);
                }

            } else {
                mostrarMensajeError("Error", "Debes seleccionar un Producto para eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error", "Error al eliminar el Producto.");
        }
    }

    @FXML
    void ClickBtnNew(ActionEvent event) {
        pnl_botones.setDisable(false);
        pnl_campos.setDisable(false);
        btn_eliminar.setDisable(true);
        btn_actualizar.setDisable(true);
        tbl_datos_productos.getSelectionModel().clearSelection();//para que se deseleccione el siguiente elemento de la tabla
        tbl_datos_productos.setDisable(true);
        Limpiar();
    }

    @FXML
    void ClickBtnUpdate(ActionEvent event) {
        try {
            Connection connection = DatabaseUtil.getConnection();

            // Obtén el producto seleccionado
            DatosProductos productoSeleccionado = tbl_datos_productos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado != null) {
                int codigoProducto = productoSeleccionado.getCodigoProducto();

                String producto = txf_nombre_producto.getText();
                String descripcion = txf_descripcion_producto.getText();
                String precio = txf_precio_producto.getText();

                String selectedCategoria = cb_categoria_producto.getValue(); // Obtener el nombre del categoria seleccionado
                int codigoCategoria = obtenerCodigoCategoria(selectedCategoria); // Obtener el código del categoria seleccionado

                String selectedProveedor = cb_codigo_producto_proveedor.getValue(); // Obtener el nombre del proveedor seleccionado
                int codigoProveedor = obtenerCodigoProveedor(selectedProveedor); // Obtener el código del proveedor seleccionado

                if (producto.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
                    mostrarMensajeError("Error", "Debe llenar todos los campos.");
                } else {
                    if(validarCampos()){
                        // Consulta SQL para actualizar el cliente
                        String updateProductoQuery = "UPDATE tablaProducto SET nombreProducto = ?, descripcion = ?, precio = ?, codigoCategoria = ?, codigoProveedor = ? WHERE codigoProducto = ?";
                        PreparedStatement updateProductoStatement = connection.prepareStatement(updateProductoQuery);
                        updateProductoStatement.setString(1, producto);
                        updateProductoStatement.setString(2, descripcion);
                        updateProductoStatement.setString(3, precio);
                        updateProductoStatement.setInt(4, codigoCategoria);
                        updateProductoStatement.setInt(5, codigoProveedor);
                        updateProductoStatement.setInt(6, codigoProducto);
                        updateProductoStatement.executeUpdate();

                        mostrarMensajeExito("Actualización exitosa", "Producto actualizado exitosamente.");

                        cargarDatosProductos();

                        tbl_datos_productos.getSelectionModel().clearSelection();
                        Limpiar();
                        btn_eliminar.setDisable(true);
                        btn_actualizar.setDisable(true);
                        pnl_campos.setDisable(true);
                    }
                }
            } else {
                mostrarMensajeError("Error", "Debes seleccionar un Producto para actualizar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error", "Error al actualizar el Producto.");
        }
    }

    @FXML
    void clickAgregar(ActionEvent event) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();
        String producto = txf_nombre_producto.getText();
        String descripcion = txf_descripcion_producto.getText();
        String precio = txf_precio_producto.getText();

        String selectedCategoria = cb_categoria_producto.getValue(); // Obtener el nombre del categoria seleccionado
        int codigoCategoria = obtenerCodigoCategoria(selectedCategoria); // Obtener el código del categoria seleccionado

        String selectedProveedor = cb_codigo_producto_proveedor.getValue(); // Obtener el nombre del proveedor seleccionado
        int codigoProveedor = obtenerCodigoProveedor(selectedProveedor); // Obtener el código del proveedor seleccionado

        if (producto.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            mostrarMensajeError("Error", "Debe llenar todos los campos.");
        } else {
            if(validarCampos()){
                // Consulta SQL para insertar un nuevo cliente
                String insertProductoQuery = "INSERT INTO tablaProducto (nombreProducto, descripcion, precio, codigoCategoria, codigoProveedor) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertProductoStatement = connection.prepareStatement(insertProductoQuery);
                insertProductoStatement.setString(1, producto);
                insertProductoStatement.setString(2, descripcion);
                insertProductoStatement.setString(3, precio);
                insertProductoStatement.setInt(4, codigoCategoria);
                insertProductoStatement.setInt(5, codigoProveedor);
                insertProductoStatement.executeUpdate();

                mostrarMensajeExito("Registro exitoso", "Cliente registrado exitosamente.");

                cargarDatosProductos();

                pnl_botones.setDisable(false);
                pnl_campos.setDisable(true);
                tbl_datos_productos.getSelectionModel().clearSelection();
                tbl_datos_productos.setDisable(false);
                Limpiar();
            }
        }
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



    private void cargarDatosProductos() {
        productData.clear(); // Limpia los datos existentes

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT p.codigoProducto, p.nombreProducto, p.descripcion, p.precio, c.nombreCategoria , tp.nombreProveedor " +
                    "FROM tablaProducto p " +
                    "INNER JOIN tablaProveedor tp ON p.codigoProveedor = tp.codigoProveedor "+
                    "INNER JOIN tablaCategoria c ON p.codigoCategoria = c.codigoCategoria";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int codigoProducto = resultSet.getInt("codigoProducto");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcion");
                BigDecimal precio = resultSet.getBigDecimal("precio");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String nombreProveedor = resultSet.getString("nombreProveedor");


                DatosProductos producto = new DatosProductos(codigoProducto, nombreProducto, descripcion, precio, nombreCategoria, nombreProveedor);
                productData.add(producto);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int obtenerCodigoProveedor(String nombreProveedor) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Consulta SQL para obtener el código del proveedor
        String proveedorQuery = "SELECT codigoProveedor FROM tablaProveedor WHERE nombreProveedor = ?";
        PreparedStatement proveedorStatement = connection.prepareStatement(proveedorQuery);
        proveedorStatement.setString(1, nombreProveedor);
        ResultSet proveedorResultSet = proveedorStatement.executeQuery();

        int codigoProveedor = 1; // Valor por defecto en caso de que no se encuentre el proveedor

        if (proveedorResultSet.next()) {
            codigoProveedor = proveedorResultSet.getInt("codigoProveedor");
        }

        return codigoProveedor;
    }

    private boolean validarCampos() {
        boolean camposValidos = true;

        // Validar el campo de nombre
        //String telefono = txf_telefono_cliente.getText();
        //if (telefono.isEmpty() || !telefono.matches("^\\d{4}-\\d{4}$")) {
            //mostrarMensajeError("Error en el teléfono", "El teléfono debe tener el formato 0000-0000.");
            //camposValidos = false;
        //}

       // String dui = txf_dui_cliente.getText();
        //if (dui.isEmpty() || !dui.matches("^\\d{8}-\\d$")) {
            //mostrarMensajeError("Error en el DUI", "El DUI debe tener el formato 00000000-0.");
            //camposValidos = false;
        //}

        return camposValidos;
    }

    private int obtenerCodigoCategoria(String nombreCategoria) throws SQLException {
        Connection connection = null;
        connection = DatabaseUtil.getConnection();

        // Consulta SQL para obtener el código del categoria
        String categoriaQuery = "SELECT codigoCategoria FROM tablaCategoria WHERE nombreCategoria = ?";
        PreparedStatement categoriaStatement = connection.prepareStatement(categoriaQuery);
        categoriaStatement.setString(1, nombreCategoria);
        ResultSet categoriaResultSet = categoriaStatement.executeQuery();

        int codigoCategoria = 1; // Valor por defecto en caso de que no se encuentre el categoria

        if (categoriaResultSet.next()) {
            codigoCategoria = categoriaResultSet.getInt("codigoCategoria");
        }

        return codigoCategoria;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asignarEventosHover(btnBoutiques);
        asignarEventosHover(btnUsuarios);
        asignarEventosHover(btnCargos);
        asignarEventosHover(btnCerrar);
        asignarEventosHover(btnMenu);
        asignarEventosHover(btnClientes);

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreCategoria FROM tablaCategoria";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> categorias = FXCollections.observableArrayList();

            while (resultSet.next()) {
                categorias.add(resultSet.getString("nombreCategoria"));
            }

            cb_categoria_producto.setItems(categorias);

            // Establece la opción por defecto
            cb_categoria_producto.setValue(categorias.get(0));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DatabaseUtil.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT nombreProveedor FROM tablaProveedor";
            ResultSet resultSet = statement.executeQuery(query);

            ObservableList<String> proveedores = FXCollections.observableArrayList();

            while (resultSet.next()) {
                proveedores.add(resultSet.getString("nombreProveedor"));
            }

            cb_codigo_producto_proveedor.setItems(proveedores);

            // Establece la opción por defecto
            cb_codigo_producto_proveedor.setValue(proveedores.get(0));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configura las columnas para mostrar los datos de los clientes
        colCodigo_producto.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        colNombre_producto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colDescripcion_producto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio_producto.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCategoria_producto.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        colCodigo_proveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));

        // Asigna los datos a la tabla
        tbl_datos_productos.setItems(productData);

        // Carga los datos iniciales de los clientes
        cargarDatosProductos();

        tbl_datos_productos.setOnMouseClicked(event -> {
            DatosProductos productoSeleccionado = tbl_datos_productos.getSelectionModel().getSelectedItem();
            if (productoSeleccionado != null) {
                // Establece los datos de la fila seleccionada en los campos de texto
                txf_nombre_producto.setText(productoSeleccionado.getNombreProducto());
                txf_descripcion_producto.setText(productoSeleccionado.getDescripcion());
                txf_precio_producto.setText(productoSeleccionado.getPrecio().toString());
                cb_categoria_producto.setValue(productoSeleccionado.getNombreCategoria());
                cb_codigo_producto_proveedor.setValue(productoSeleccionado.getNombreProveedor());

                productoSeleccionado = tbl_datos_productos.getSelectionModel().getSelectedItem();
                this.productoSeleccionado = productoSeleccionado.getCodigoProducto();

                pnl_campos.setDisable(false);
                btn_actualizar.setDisable(true);
                btn_eliminar.setDisable(true);
            }
        });

        txf_nombre_producto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                txf_nombre_producto.setText(oldValue); // Limitar a 100 caracteres
            } else if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                txf_nombre_producto.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_descripcion_producto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                txf_descripcion_producto.setText(oldValue); // Limitar a 100 caracteres
            } else if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                txf_descripcion_producto.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        txf_precio_producto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txf_precio_producto.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 10) {
                txf_precio_producto.setText(newValue.substring(0, 10));
            }
        });

    }

    private void Limpiar() {
        // Limpia todas las cajas de texto y restablece la selección en el ComboBox
        txf_nombre_producto.clear();
        txf_descripcion_producto.clear();
        txf_precio_producto.clear();
    }
}
