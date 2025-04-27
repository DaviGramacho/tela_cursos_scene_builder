package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.input.MouseEvent;
import org.example.classes.Curso;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class CadastroCursoController {

    @FXML
    private Button btnAdicionarAula;

    @FXML
    private Button btnCoordenador;

    @FXML
    private Button btnCursos;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnDisciplinas;

    @FXML
    private Button btnExportar;

    @FXML
    private Button btnFiltro;

    @FXML
    private Button btnGradeHoraria;

    @FXML
    private Button btnProfessor;

    @FXML
    private Button btnSemestres;

    @FXML
    private TableView<Curso> tblViewCurso;

    @FXML
    private TableColumn<Curso, Boolean> tblSelecionarCurso;

    @FXML
    private TableColumn<Curso, Integer> tblIdCurso;

    @FXML
    private TableColumn<Curso, String> tblNomeCurso;

    @FXML
    private TableColumn<Curso, String> tblPeriodo;

    private final ObservableList<Curso> cursos = FXCollections.observableArrayList();

    // Método para alterar a cor ao passar o mouse
    public void mouseEntrou(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: #eaf2ff;");
    }

    // Método para voltar a cor ao sair do mouse
    public void mouseSaiu(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: transparent;");
    }

    @FXML
    public void initialize() {
        // Configuração da TableView
        tblSelecionarCurso.setCellValueFactory(cellData -> cellData.getValue().selecionadoProperty());
        tblSelecionarCurso.setCellFactory(CheckBoxTableCell.forTableColumn(tblSelecionarCurso));

        tblIdCurso.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));

        // Adiciona cursos de exemplo
        cursos.addAll(
                new Curso(false, 1, "Análise e Desenvolvimento de Sistemas", "Matutino"),
                new Curso(false, 2, "Banco de Dados", "Noturno"),
                new Curso(false, 3, "Desenvolvimento de Software Multiplataforma", "Matutino"),
                new Curso(false, 4, "Gestão da Produção Industrial", "Noturno"),
                new Curso(false, 5, "Gestão Empresarial", "EaD"),
                new Curso(false, 6, "Logística", "Matutino / Noturno"),
                new Curso(false, 7, "Manufatura Avançada", "Matutino")
        );

        // Atribui a lista de cursos à TableView
        tblViewCurso.setItems(cursos);
    }

    @FXML
    private void abrirPopupCurso() throws IOException {
        // Carrega o FXML do PopupCurso
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/PopupCurso.fxml"));
        Parent root = loader.load(); // Aqui, você carrega o FXML uma vez

        Stage popupStage = new Stage();
        popupStage.setTitle("Adicionar Curso");

        // Carrega a cena do FXML usando o 'root' já carregado
        Scene scene = new Scene(root);
        popupStage.setScene(scene);

        // Faz o popup ser modal (impede interação com a janela principal enquanto o popup estiver aberto)
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Mostra o popup
        popupStage.showAndWait();

        // Depois que o popup fechar, verifica se a ação foi confirmada
        PopupCursoController popupController = loader.getController();
        if (popupController.isConfirmado()) {  // Verifica se a ação foi confirmada
            String nomeCurso = popupController.getCursoSelecionado();
            String turnoCurso = popupController.getTurnoSelecionado();

            // Cria e adiciona o novo curso
            Curso novoCurso = new Curso(false, cursos.size() + 1, nomeCurso, turnoCurso);
            cursos.add(novoCurso);
        }
    }

    @FXML
    private void deletarCursosSelecionados() {
        // Cria uma lista para armazenar os cursos a serem removidos
        ObservableList<Curso> cursosSelecionados = FXCollections.observableArrayList();

        // Itera pela lista de cursos e adiciona à lista de deletar os cursos que estão com a checkbox marcada
        for (Curso curso : tblViewCurso.getItems()) {
            if (curso.isSelecionado()) {
                cursosSelecionados.add(curso);
            }
        }

        // Verifica se existem cursos selecionados
        if (!cursosSelecionados.isEmpty()) {
            // Remove os cursos selecionados da lista original
            cursos.removeAll(cursosSelecionados);
        } else {
            // Alerta se não houver cursos selecionados
            System.out.println("Nenhum curso selecionado para remoção.");
        }
    }

    @FXML
    private void abrirPopupFiltro() throws IOException {
        // Carrega o FXML do PopupFiltro
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/PopupFiltro.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.setTitle("Filtrar Cursos");

        Scene scene = new Scene(root);
        popupStage.setScene(scene);

        popupStage.initModality(Modality.APPLICATION_MODAL);

        popupStage.showAndWait();

        // Depois que o popup fechar, verifica se a ação foi confirmada
        PopupFiltroController popupController = loader.getController();
        if (popupController.isConfirmado()) {
            String turnoSelecionado = popupController.getTurnoSelecionado();

            // Filtra os cursos de acordo com o turno selecionado
            ObservableList<Curso> cursosFiltrados = FXCollections.observableArrayList();
            for (Curso curso : cursos) {
                if (curso.getPeriodo().equals(turnoSelecionado) || curso.getPeriodo().contains(turnoSelecionado)) {
                    cursosFiltrados.add(curso);
                }
            }

            tblViewCurso.setItems(cursosFiltrados);
        }
    }
}
