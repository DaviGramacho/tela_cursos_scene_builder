package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Region;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import org.example.classes.Curso;

public class CadastroCursoController {


    @FXML
    private Button btnAdicionarCurso;

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

    public void mouseEntrou(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: #eaf2ff;");
    }

    public void mouseSaiu(MouseEvent mouseEvent) {
        ((Region) mouseEvent.getSource()).setStyle("-fx-background-color: transparent;");
    }

    @FXML
    public void initialize() {
        // Configuração da tabela
        tblViewCurso.setEditable(true);
        tblSelecionarCurso.setEditable(true); // Apenas a coluna da CheckBox é editável

        // Desabilitar edição para colunas de texto
        tblIdCurso.setEditable(false);
        tblNomeCurso.setEditable(false);
        tblPeriodo.setEditable(false);

        // Configurar coluna da CheckBox
        tblSelecionarCurso.setCellValueFactory(cellData -> cellData.getValue().selecionadoProperty());
        tblSelecionarCurso.setCellFactory(CheckBoxTableCell.forTableColumn(tblSelecionarCurso));

        // Configurar colunas de texto
        tblIdCurso.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));

        // Garantir exibição correta do texto
        tblIdCurso.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tblNomeCurso.setCellFactory(TextFieldTableCell.forTableColumn());
        tblPeriodo.setCellFactory(TextFieldTableCell.forTableColumn());

        // Adicionar cursos
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

        // Listener para log no console
        cursos.forEach(curso -> {
            curso.selecionadoProperty().addListener((obs, oldVal, newVal) -> {
                String status = newVal ? "selecionada" : "desmarcada";
                System.out.println("Caixa " + status + " para o curso: " + curso.getNome());
            });
        });

        btnDeletar.setOnAction(event -> deletarCursosSelecionados());

    }

    private void deletarCursosSelecionados() {
        // Criar uma cópia da lista para evitar ConcurrentModificationException
        ObservableList<Curso> cursosParaRemover = cursos.filtered(Curso::isSelecionado);

        // Remover todos os cursos selecionados
        cursos.removeAll(cursosParaRemover);

        System.out.println(cursosParaRemover.size() + " curso(s) removido(s) com sucesso!");
    }
}