package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopupCursoController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button BtnCancelar;

    @FXML
    private ComboBox<String> comboTurno;

    @FXML
    private ComboBox<String> comboCurso;

    @FXML
    private Label tituloEscolhaTurno;

    private String turnoSelecionado;
    private String cursoSelecionado;
    private boolean confirmado = false;  // Variável de controle

    @FXML
    private void initialize() {
        // Preenche os comboboxes
        comboTurno.getItems().addAll("Matutino", "Noturno", "EaD");
        comboCurso.getItems().addAll("Análise e Desenvolvimento de Sistemas", "Banco de Dados", "Desenvolvimento de Software Multiplataforma", "Gestão da Produção Industrial", "Gestão Empresarial", "Logística", "Manufatura Avançada");

        // Ação do botão Confirmar
        btnConfirmar.setOnAction(event -> {
            turnoSelecionado = comboTurno.getValue();
            cursoSelecionado = comboCurso.getValue();

            // Verificar se ambos os campos foram selecionados
            if (turnoSelecionado == null || cursoSelecionado == null) {
                mostrarAlerta("Por favor, selecione tanto o turno quanto o curso.");
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

