package org.example.dao;

// Importações da classe de modelo e da conexão
import org.example.classes.Curso;
import org.example.database.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar operações no banco de dados
 * relacionadas à entidade Curso (DAO = Data Access Object).
 */
public class CursoDAO {

    /**
     * Insere um novo curso no banco de dados.
     *
     * Objeto Curso com nome e coordenador preenchidos
     */
    public void criar(Curso curso) {
        String sql = "INSERT INTO curso (nome, coordenador) VALUES (?, ?)";

        try (
                // Abre a conexão com o banco
                Connection con = Conexao.conectar();
                // Prepara o comando SQL com parâmetros
                PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            // Define os valores para os parâmetros
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCoordenador());

            // Executa o comando no banco de dados
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Em caso de erro, imprime mensagem no console
            System.err.println("Erro ao gravar curso: " + e.getMessage());
        }
    }

    /**
     * Lista todos os cursos que não estão marcados como deletados.
     *
     * Lista de cursos ativos (deletado = 0)
     * throws SQLException Se ocorrer erro ao acessar o banco
     */
    public List<Curso> listarTodos() throws SQLException {
        // Consulta que retorna apenas cursos não deletados
        String sql = "SELECT id_curso, nome, coordenador, deletado FROM curso WHERE deletado = 0";

        List<Curso> lista = new ArrayList<>();

        try (
                Connection con = Conexao.conectar();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            // Para cada linha do resultado...
            while (rs.next()) {
                Curso c = new Curso(
                        false,                          // sempre começa com não selecionado
                        rs.getLong("id_curso"),
                        rs.getString("nome"),
                        rs.getString("coordenador"),
                        rs.getBoolean("deletado")       // esse é o valor que vem do banco
                );
                lista.add(c); // adiciona à lista
            }
        }

        return lista; // retorna todos os cursos carregados
    }

    /**
     * Deleta (fisicamente) um curso do banco de dados pelo ID.
     *
     *  ID do curso a ser removido
     *
     * Observação: se quiser um *soft delete* (marcar como deletado),
     * substitua o SQL por:
     *    UPDATE curso SET deletado = 1 WHERE id_curso = ?
     */
    public void delete(long id) {
        String sql = "UPDATE curso SET deletado = 1 WHERE id_curso = ?";

        try (
                Connection con = Conexao.conectar();
                PreparedStatement stmt = con.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("Curso marcado como deletado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar curso: " + e.getMessage());
        }
    }

}
