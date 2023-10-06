package com.example.meysboutique;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private ProgressIndicator ProgressIndicator;

    public HelloController() {
    }

    public void initialize() {
        // Inicializa el Task y el ProgressIndicator
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // Simula un proceso
                    updateProgress(i, 100); // Actualiza el progreso
                }
                return null;
            }
        };

        ProgressIndicator.progressProperty().bind(task.progressProperty());

        // Cuando el Task llega al 100%, abre la ventana de login
        task.setOnSucceeded(event -> {
            try {
                openLoginWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        new Thread(task).start();
    }

    private void openLoginWindow() throws IOException {
        Stage currentStage = (Stage) ProgressIndicator.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Login-Mey's Boutique");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}