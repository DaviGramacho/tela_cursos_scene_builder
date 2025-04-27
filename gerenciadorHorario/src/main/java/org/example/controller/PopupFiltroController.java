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

    @FXML
    private Button btnLimpar;

    private String turnoSelecionado;
    private boolean confirmado = false;
    private boolean limparFiltro = false;

    @FXML
    private void initialize() {
        // Preenche a ComboBox com opções
        comboTurno.getItems().addAll("Matutino", "Noturno", "EaD");

        // Botão Aplicar
        btnAplicar.setOnAction(event -> {
            turnoSelecionado = comboTurno.getValue();
            if (turnoSelecionado == null) {
                mostrarAlerta("Por favor, selecione um turno.");
            } else {
                confirmado = true;
                fecharPopup();
            }
        });

        // Botão Limpar
        btnLimpar.setOnAction(event -> {
            limparFiltro = true;
            confirmado = true;
            fecharPopup();
        });
    }

    private void mostrarAlerta(String mensagem) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Seleção inválida");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharPopup() {
        Stage stage = (Stage) btnAplicar.getScene().getWindow();
        stage.close();
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public boolean isLimparFiltro() {
        return limparFiltro;
    }

    public String getTurnoSelecionado() {
        return turnoSelecionado;
    }
}
