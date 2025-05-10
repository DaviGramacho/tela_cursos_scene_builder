package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.DAO.CursoDAO;
import org.example.classes.Curso;

import java.io.IOException;

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
    private TableColumn<Curso, String> tblCoordenador;

    CursoDAO cursoDAO = new CursoDAO();

    private final ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private final FilteredList<Curso> cursosFiltrados = new FilteredList<>(cursos, p -> true);

    private String filtroAtual = "";

    @FXML
    public void initialize() {
        configurarTabela();
        carregarCursosIniciais();
    }

    private void configurarTabela() {


        tblSelecionarCurso.setCellValueFactory(cellData -> cellData.getValue().selecionadoProperty());
        tblSelecionarCurso.setCellFactory(CheckBoxTableCell.forTableColumn(tblSelecionarCurso));

        tblIdCurso.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblCoordenador.setCellValueFactory(new PropertyValueFactory<>("coordenador"));

        tblViewCurso.setItems(cursosFiltrados);
    }


    private void carregarCursosIniciais() {
        cursos.addAll(
                new Curso(false, 1, "Análise e Desenvolvimento de Sistemas", "Sabah"),
                new Curso(false, 2, "Banco de Dados", "Leonidas"),
                new Curso(false, 3, "Desenvolvimento de Software Multiplataforma", "Adicione o coordenador aqui"),
                new Curso(false, 4, "Redes de Computadores", "Adicione o coordenador aqui"),
                new Curso(false, 5, "Jogos Digitais", "Adicione o coordenador aqui")
        );

        cursos.forEach(curso ->
                curso.selecionadoProperty().addListener((obs, oldVal, newVal) ->
                        System.out.println("Curso " + curso.getNome() + " " + (newVal ? "selecionado" : "deselecionado"))
                )
        );
    }


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

            Curso novoCurso = new Curso(false, cursos.size() + 1, nomeCurso, turnoCurso);
            cursos.add(novoCurso);
        }
    }

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
            if (popupController.isLimparFiltro()) {
                // Se clicou em "Limpar", remove o filtro
                filtroAtual = "";
                cursosFiltrados.setPredicate(curso -> true); // Mostra todos
            } else {
                String turnoSelecionado = popupController.getTurnoSelecionado();
                aplicarFiltro(turnoSelecionado);
            }
        }
    }

    private void aplicarFiltro(String turnoSelecionado) {
        filtroAtual = turnoSelecionado.toLowerCase();
        cursosFiltrados.setPredicate(curso ->
                filtroAtual.isEmpty() || curso.getCoordenador().toLowerCase().contains(filtroAtual)
        );
    }

    @FXML
    private void deletarCursosSelecionados() {

        cursos.removeIf(Curso::isSelecionado);

        // Atualiza o filtro, se existir
        if (!filtroAtual.isEmpty()) {
            cursosFiltrados.setPredicate(curso ->
                    curso.getCoordenador().toLowerCase().contains(filtroAtual)
            );
        }
    }

    @FXML
    private void mouseEntrou(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: #eaf2ff;");
    }

    @FXML
    private void mouseSaiu(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: transparent;");
    }
}
