package org.example.controller;

// Importações JavaFX para interface gráfica
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

// Importações do projeto: DAO e modelo
import org.example.dao.CursoDAO;
import org.example.classes.Curso;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

// Controlador da tela de cadastro e gerenciamento de cursos
public class CadastroCursoController {

    // Elementos da interface FXML (tabela e colunas)
    @FXML private TableView<Curso> tblViewCurso;
    @FXML private TableColumn<Curso, Boolean> tblSelecionarCurso;
    @FXML private TableColumn<Curso, Integer> tblIdCurso;
    @FXML private TableColumn<Curso, String> tblNomeCurso;
    @FXML private TableColumn<Curso, String> tblCoordenador;

    // DAO para interagir com o banco de dados
    private final CursoDAO cursoDAO = new CursoDAO();

    // Lista observável de cursos, com filtro aplicado
    private final ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private final FilteredList<Curso> cursosFiltrados = new FilteredList<>(cursos, p -> true);

    // Armazena o filtro atual aplicado por coordenador
    private String filtroAtual = "";

    // Inicializa a tela: configura a tabela e carrega dados do banco
    @FXML
    public void initialize() {
        configurarTabela();
        carregarCursosDoBanco();
    }

    // Configura como os dados serão exibidos nas colunas da tabela
    private void configurarTabela() {
        // Coluna com checkbox de seleção
        tblSelecionarCurso.setCellValueFactory(cd -> cd.getValue().selecionadoProperty());
        tblSelecionarCurso.setCellFactory(CheckBoxTableCell.forTableColumn(tblSelecionarCurso));

        // Demais colunas (id, nome, coordenador)
        tblIdCurso.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblNomeCurso.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblCoordenador.setCellValueFactory(new PropertyValueFactory<>("coordenador"));

        // Define a lista filtrada como fonte de dados da tabela
        tblViewCurso.setItems(cursosFiltrados);
    }

    // Carrega os cursos do banco de dados e atualiza a tabela
    private void carregarCursosDoBanco() {
        try {
            cursos.clear(); // limpa lista atual
            cursos.addAll(cursoDAO.listarTodos()); // adiciona do banco
        } catch (SQLException e) {
            System.err.println("Erro ao carregar cursos: " + e.getMessage());
        }
    }

    // Abre o popup para adicionar novo curso
    @FXML
    private void abrirPopupCurso() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/PopupCurso.fxml"));
        Parent root = loader.load();

        // Cria nova janela modal
        Stage popupStage = new Stage();
        popupStage.setTitle("Adicionar Curso");
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait(); // espera o popup ser fechado

        // Obtém dados do controller do popup
        PopupCursoController popupCtrl = loader.getController();
        if (popupCtrl.isConfirmado()) {
            // Cria novo curso e salva no banco
            Curso novo = new Curso(popupCtrl.getCursoSelecionado(), popupCtrl.getCoordenadorSelecionado());
            cursoDAO.criar(novo);
            carregarCursosDoBanco(); // atualiza a lista
        }
    }

    // Abre o popup para aplicar ou remover filtros por coordenador
    @FXML
    private void abrirPopupFiltro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/PopupFiltro.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.setTitle("Filtrar Cursos");
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();

        PopupFiltroController popFiltro = loader.getController();
        if (popFiltro.isConfirmado()) {
            if (popFiltro.isLimparFiltro()) {
                // Limpa o filtro atual
                filtroAtual = "";
                cursosFiltrados.setPredicate(c -> true);
            } else {
                // Aplica filtro por coordenador
                aplicarFiltro(popFiltro.getCoordenadorSelecionado());
            }
        }
    }

    // Aplica filtro na lista de cursos por nome do coordenador
    private void aplicarFiltro(String coordenador) {
        filtroAtual = coordenador.toLowerCase(); // filtro em minúsculas
        cursosFiltrados.setPredicate(c ->
                filtroAtual.isEmpty() ||
                        c.getCoordenador().toLowerCase().contains(filtroAtual)
        );
    }

//    // Deleta todos os cursos que estão marcados com o checkbox
//    @FXML
//    private void deletarCursosSelecionados() {
//        //logica deletar
//        }
//    }

    // Efeito visual: muda o fundo ao passar o mouse
    @FXML
    private void mouseEntrou(MouseEvent e) {
        ((Region)e.getSource()).setStyle("-fx-background-color: #eaf2ff;");
    }

    // Efeito visual: volta ao normal ao sair o mouse
    @FXML
    private void mouseSaiu(MouseEvent e) {
        ((Region)e.getSource()).setStyle("-fx-background-color: transparent;");
    }
}
