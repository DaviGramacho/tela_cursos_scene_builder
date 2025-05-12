package org.example.controller;

// Importações JavaFX para elementos da interface
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

// Importações da aplicação: DAO e modelo
import org.example.dao.ProfessorDAO;
import org.example.classes.Professor;

import java.util.List;

// Controlador do popup de cadastro de curso
public class PopupCursoController {

    // Elementos da interface definidos no FXML
    @FXML private Button btnConfirmar;
    @FXML private Button btnCancelar;
    @FXML private ComboBox<String> comboTurno;
    @FXML private ComboBox<String> comboCoordenador;
    @FXML private TextField txtCurso;

    // Variáveis para armazenar estado e seleção do usuário
    private boolean confirmado = false;
    private String cursoSelecionado;
    private String coordenadorSelecionado;

    // Método chamado automaticamente ao inicializar o FXML
    @FXML
    private void initialize() {
        // Preenche a ComboBox de turnos
        comboTurno.getItems().addAll("Matutino", "Noturno");

        // Carrega os nomes dos professores para a ComboBox de coordenadores
        carregarCoordenadores();
    }

    // Busca os professores do banco de dados e adiciona à ComboBox
    private void carregarCoordenadores() {
        try {
            List<Professor> profs = new ProfessorDAO().buscarProfessores();
            profs.forEach(p -> comboCoordenador.getItems().add(p.getNomeProfessor()));
        } catch (Exception e) {
            exibirErro("Erro ao carregar coordenadores: " + e.getMessage());
        }
    }

    // Ação do botão Confirmar
    @FXML
    private void Confirmar() {
        cursoSelecionado = txtCurso.getText();
        coordenadorSelecionado = comboCoordenador.getValue();

        // Valida se o nome do curso foi preenchido
        if (cursoSelecionado == null || cursoSelecionado.isEmpty()) {
            exibirErro("Nome do curso obrigatório!");
            return;
        }

        // Valida se um coordenador foi selecionado
        if (coordenadorSelecionado == null) {
            exibirErro("Selecione um coordenador!");
            return;
        }

        // Marca como confirmado e fecha a janela
        confirmado = true;
        fecharPopup();
    }

    // Ação do botão Cancelar
    @FXML
    private void cancelar() {
        fecharPopup();
    }

    // Exibe uma janela de erro com a mensagem fornecida
    private void exibirErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    // Fecha o popup atual
    private void fecharPopup() {
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.close();
    }

    // Métodos públicos para obter os dados selecionados
    public boolean isConfirmado(){
        return confirmado;
    }
    public String getCursoSelecionado(){
        return cursoSelecionado;
    }
    public String getCoordenadorSelecionado(){
        return coordenadorSelecionado;
    }
}
