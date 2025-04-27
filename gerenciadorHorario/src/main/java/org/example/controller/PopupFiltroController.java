package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PopupFiltroController {

    @FXML
    private ComboBox<String> comboTurno;

    @FXML
    private Button btnAplicar;

    private String turnoSelecionado;
    private boolean confirmado = false;

    @FXML
    private void initialize() {
        // Preenche a ComboBox com as opções de turno
        comboTurno.getItems().addAll("Matutino", "Noturno", "EaD");

        // Ação do botão Aplicar
        btnAplicar.setOnAction(event -> {
            turnoSelecionado = comboTurno.getValue();
            if (turnoSelecionado == null) {
                mostrarAlerta("Por favor, selecione um turno.");
            } else {
                confirmado = true;  // Marcar como confirmado
                fecharPopup();
            }
        });
    }

    // Método para mostrar um alerta
    private void mostrarAlerta(String mensagem) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Seleção inválida");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Método para fechar o popup
    private void fecharPopup() {
        Stage stage = (Stage) btnAplicar.getScene().getWindow();
        stage.close();
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public String getTurnoSelecionado() {
        return turnoSelecionado;
    }
}
