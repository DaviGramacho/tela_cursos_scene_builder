package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PopupCursoController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button BtnCancelar;

    @FXML
    private ComboBox<String> comboTurno;

    @FXML
    private ComboBox<String> comboCoordenador;

    @FXML
    private TextField txtCurso;  // Alterado para TextField

    @FXML
    private Label tituloEscolhaTurno;

    private String turnoSelecionado;
    private String cursoSelecionado;
    private boolean confirmado = false;  // Variável de controle

    @FXML
    private void initialize() {
        // Preenche o ComboBox com os turnos
        comboTurno.getItems().addAll("Matutino", "Noturno", "EaD");

        // Ação do botão Confirmar
        btnConfirmar.setOnAction(event -> {
            turnoSelecionado = comboTurno.getValue();
            cursoSelecionado = txtCurso.getText();  // Pega o texto digitado no TextField

            // Verificar se ambos os campos foram preenchidos
            if (turnoSelecionado == null || cursoSelecionado == null || cursoSelecionado.isEmpty()) {
                mostrarAlerta("Por favor, selecione o turno e digite o nome do curso.");
            } else {
                confirmado = true;  // Marcar como confirmado
                fecharPopup();
            }
        });

        // Ação do botão Cancelar
        BtnCancelar.setOnAction(event -> fecharPopup());
    }

    // Método para mostrar um alerta
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Campos Incompletos");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Método para fechar o popup
    private void fecharPopup() {
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.close();
    }

    // Método que retorna se a ação foi confirmada
    public boolean isConfirmado() {
        return confirmado;
    }

    public String getTurnoSelecionado() {
        return turnoSelecionado;
    }

    public String getCursoSelecionado() {
        return cursoSelecionado;
    }
}
