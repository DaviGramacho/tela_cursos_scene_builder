package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import org.example.classes.Curso;

public class CadastroCursoController {

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

    // Lista observável para armazenar os cursos
    private final ObservableList<Curso> cursos = FXCollections.observableArrayList();

    public void mouseEntrou(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: #eaf2ff;");
    }

    public void mouseSaiu(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: transparent;");
    }


    /**
     * Inicializa a tela e configura a tabela.
     */
    @FXML
    public void initialize() {
        // Configuração da coluna de seleção
        tblSelecionarCurso.setCellValueFactory(cellData -> cellData.getValue().selecionadoProperty());
        tblSelecionarCurso.setCellFactory(CheckBoxTableCell.forTableColumn(tblSelecionarCurso));

        // Configuração das outras colunas

        tblIdCurso.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));

        // Adiciona cursos de exemplo
        cursos.addAll(
                new Curso(false, 1, "Análise e Desenvolvimento de Sistemas", "Matutino"),
                new Curso(false, 2, "Banco de Dados", "Noturno"),
                new Curso(false, 3, "Desenvolvimento de Software Multiplataforma", "Matutino")
        );

        // Atribui a lista de cursos à TableView
        tblViewCurso.setItems(cursos);

        cursos.forEach(curso -> {
            curso.selecionadoProperty().addListener((obs, oldVal, newVal) -> {
                String status = newVal ? "selecionada" : "desmarcada";
                System.out.println("Caixa " + status + " para o curso: " + curso.getNome());
            });
        });
    }

    /**
     * Abre um popup para adicionar um novo curso.
     */
    @FXML
    private void abrirPopupCurso() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/PopupCurso.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.setTitle("Adicionar Curso");
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();

        PopupCursoController popupController = loader.getController();
        if (popupController.isConfirmado()) {
            String nomeCurso = popupController.getCursoSelecionado();
            String turnoCurso = popupController.getTurnoSelecionado();

            // Cria e adiciona o novo curso
            Curso novoCurso = new Curso(false, cursos.size() + 1, nomeCurso, turnoCurso);
            cursos.add(novoCurso);
        }
    }

    /**
     * Abre um popup para filtrar os cursos por período.
     */
    @FXML
    private void abrirPopupFiltro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/PopupFiltro.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.setTitle("Filtrar Cursos");
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();

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

    /**
     * Exclui os cursos selecionados.
     */
    @FXML
    private void deletarCursosSelecionados() {
        ObservableList<Curso> cursosSelecionados = FXCollections.observableArrayList();
        for (Curso curso : cursos) {
            if (curso.isSelecionado()) {
                cursosSelecionados.add(curso);
            }
        }

        // Remove os cursos selecionados da lista principal
        cursos.removeAll(cursosSelecionados);

        // Atualiza a tabela
        tblViewCurso.refresh();
    }
}