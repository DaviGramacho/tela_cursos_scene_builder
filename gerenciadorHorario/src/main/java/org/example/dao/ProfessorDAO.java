package org.example.dao;

import org.example.classes.Professor;
import org.example.database.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações CRUD de Professor.
 */
public class ProfessorDAO {

    /**
     * Cria um novo registro de professor.
     */
    public void criarProfessor(String nome, String email) {
        String sql = "INSERT INTO professor(nome, email) VALUES (?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar professor: " + e.getMessage());
        }
    }

    /**
     * Lista todos os professores.
     */
    public List<Professor> buscarProfessores() {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM professor";
        try (Connection con = Conexao.conectar();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                professores.add(new Professor(
                        rs.getInt("id_professor"),
                        rs.getString("nome"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professores: " + e.getMessage(), e);
        }
        return professores;
    }

    /**
     * Busca um professor pelo ID.
     */
    public Professor buscarPorId(int id) {
        String sql = "SELECT * FROM professor WHERE id_professor = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Professor(
                            rs.getInt("id_professor"),
                            rs.getString("nome"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza nome e email de um professor existente.
     */
    public void alterarDados(int id, String nome, String email) {
        String sql = "UPDATE professor SET nome = ?, email = ? WHERE id_professor = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor: " + e.getMessage(), e);
        }
    }

    /**
     * Deleta um professor pelo ID.
     */
    public void deletar(int id) {
        String sql = "DELETE FROM professor WHERE id_professor = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar professor: " + e.getMessage());
        }
    }

    /**
     * Busca professores pelo nome exato.
     */
    public List<Professor> buscarPorNome(String nome) {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM professor WHERE nome = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Professor(
                            rs.getInt("id_professor"),
                            rs.getString("nome"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por nome: " + e.getMessage(), e);
        }
        return lista;
    }
}
