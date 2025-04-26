package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.input.MouseEvent;
import org.example.classes.Curso;

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
        // Corrigir isso:
        tblSelecionarCurso.setCellValueFactory(cellData -> cellData.getValue().selecionadoProperty());
        tblSelecionarCurso.setCellFactory(CheckBoxTableCell.forTableColumn(tblSelecionarCurso));

        tblIdCurso.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));

        cursos.addAll(
                new Curso(false, 1, "Análise e Desenvolvimento de Sistemas", "Matutino"),
                new Curso(false, 2, "Banco de Dados", "Noturno"),
                new Curso(false, 3, "Desenvolvimento de Software Multiplataforma", "Matutino"),
                new Curso(false, 4, "Gestão da Produção Industrial", "Noturno"),
                new Curso(false, 5, "Gestão Empresarial", "EaD"),
                new Curso(false, 6, "Logística", "Matutino / Noturno"),
                new Curso(false, 7, "Manufatura Avançada", "Matutino")
        );

        tblViewCurso.setItems(cursos);
    }

}
